package md.stratan.eye;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class TimeController {

	private BreakDialog breakDialog;
	private SettingsDialog settingsDialog;
	private Timer timer;

	// ----- local remainder's parameters used by timer -----//
	private int workTime;
	private int breakTime;
	private boolean longBreaks;
	private int shortBreaksBeforeLongOne;
	private boolean isBreak;

	// ----- -----//
	public TimeController() {

		this.breakDialog = new BreakDialog();
		this.timer = new Timer(1000, timerActionListener);
		this.timer.setInitialDelay(0);

	}

	// ----- inject dependencies -----//
	public void setSettingsDialog(SettingsDialog settingsDialog) {
		this.settingsDialog = settingsDialog;
	}

	// ----- loads the remainder parameters -----//
	public void loadParams() {

		this.workTime = settingsDialog.getWorkTime();
		this.breakTime = settingsDialog.getShortBreakTime();
		this.longBreaks = settingsDialog.isLongBreaks();
		this.shortBreaksBeforeLongOne = settingsDialog
				.getShortBreaksBeforeLongOne();
		isBreak = false;
	}

	// ----- starts the timer -----//
	public synchronized void start() {

		settingsDialog.paintStatus(Integer.toString(workTime));
		if (isBreak) {
			breakDialog.setVisible(true);
			settingsDialog.paintStatus("Break!");
		}
		timer.start();
		
	}

	
	// ----- stops the remainder parameters -----//
	public synchronized void stop() {

		timer.stop();
		settingsDialog.paintStatus("Stopped!");
		breakDialog.setVisible(false);
	}

	// ----- resets the remainder parameters -----//
	public synchronized void reset() {

		if (timer.isRunning()) {
			timer.stop();
			breakDialog.setVisible(false);
			settingsDialog.paintStatus(Integer.toString(workTime));
			loadParams();
			timer.start();
		} else {
			loadParams();
		}

	}

	public boolean isRunning() {
		return timer.isRunning();
	}

	// ----- listens the timer -----//
	private ActionListener timerActionListener = new ActionListener() {

		@Override
		public synchronized void actionPerformed(ActionEvent e) {

			if (isBreak) {
				if (breakTime > 0) {
					breakDialog.paintStatus(convert(breakTime));
					breakTime--;
				} else {
					isBreak = false;

					breakDialog.setVisible(false);
					//Utils.playSound();
					if (longBreaks) {
						if (shortBreaksBeforeLongOne > 0) {
							shortBreaksBeforeLongOne--;
						} else {
							shortBreaksBeforeLongOne = settingsDialog
									.getShortBreaksBeforeLongOne();
						}
					}

					workTime = settingsDialog.getWorkTime();
					settingsDialog.paintStatus(convert(workTime) + " until the next break");
					workTime--;
				}
			} else {
				if (workTime > 0) {
					settingsDialog.paintStatus(convert(workTime) + " until the next break");
					workTime--;
				} else {
					isBreak = true;

					settingsDialog.paintStatus("Break!");

					if (longBreaks) {
						if (shortBreaksBeforeLongOne > 0) {
							breakTime = settingsDialog.getShortBreakTime();
						} else {
							breakTime = settingsDialog.getLongBreakTime();
						}
					} else {
						breakTime = settingsDialog.getShortBreakTime();
					}
					breakDialog.paintStatus(convert(breakTime));
					breakDialog.setVisible(true);
					//Utils.playSound();
					breakTime--;
				}
			}
		}

	};

	private String convert(int seconds){
		int min = seconds/60;
		int sec = seconds%60;
		
		return (min<10 ? "0"+min : min) + ":" + (sec<10 ? "0"+sec : sec);
	}
}
