package md.stratan.eye;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class SettingsDialog extends JDialog {

	// ----- window's parameters -----//
	private static final int WIDTH = 390;
	private static final int HEIGHT = 150;

	// ----- remainder's parameters -----//
	private int workTime;
	private int shortBreakTime;
	private boolean longBreaks;
	private int longBreakTime;
	private int shortBreaksBeforeLongOne;

	private PropertiesIO pio;

	private TimeController timeController;
	
	private RightClickMenu rightClickMenu;

	// ----- inject dependencies -----//
	public void setTimeController(TimeController timeController) {
		this.timeController = timeController;
	}
	
	public void setRightClickMenu(RightClickMenu rightClickMenu) {
		this.rightClickMenu = rightClickMenu;
	}

	// ----- -----//
	private JLabel statusLabel;
	private JPanel timeSettingsPanel;

	private JSpinner workTimeSpinner;
	private JSpinner shortBreakTimeSpinner;
	private JSpinner shortBreaksBeforeLongOneSpinner;
	private JCheckBox longBreaksCheckBox;
	private JLabel shortBreaksTextLabel;
	private JLabel longBreakTimeTextLabel;
	private JSpinner longBreakTimeSpinner;
	private JButton apply;
	private JButton cancel;

	// ----- -----//
	public SettingsDialog() {
		try {
			this.setTitle("EyesRelax v1.0");
			this.setSize(WIDTH, HEIGHT);

			Rectangle dimension = Utils.getMaxVisible();
			this.setLocation(dimension.width - WIDTH, dimension.height - HEIGHT);

			this.setAlwaysOnTop(true);
			this.setResizable(false);
			//this.setLocationByPlatform(true);
			// this.addWindowListener(windowListener);
			// this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

			initTimeSettingsPanel();
			loadParamsFromFile();
			storeParamsToDialog();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----- getters for remainder's parameters -----//
	public int getWorkTime() {
		return workTime * 60;
	}

	public int getShortBreakTime() {
		return shortBreakTime;
	}

	public boolean isLongBreaks() {
		return longBreaks;
	}

	public int getLongBreakTime() {
		return longBreakTime;
	}

	public int getShortBreaksBeforeLongOne() {
		return shortBreaksBeforeLongOne;
	}

	// ----- init methods -----//
	private void initTimeSettingsPanel() {
		
		timeSettingsPanel = new JPanel(new GridBagLayout());
		this.add(timeSettingsPanel);

		GridBagConstraints c = new GridBagConstraints();

		// ----- 0 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		JLabel statusTextLabel = new JLabel("Status");
		timeSettingsPanel.add(statusTextLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		statusLabel = new JLabel();
		timeSettingsPanel.add(statusLabel, c);

		// ----- 1 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		JLabel workTimeTextLabel = new JLabel("Work time (minutes)");
		timeSettingsPanel.add(workTimeTextLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		workTimeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		workTimeSpinner.addChangeListener(changeActionListener);
		((JFormattedTextField) ((JSpinner.DefaultEditor) workTimeSpinner
				.getEditor()).getTextField())
				.setHorizontalAlignment(JTextField.LEFT);
		timeSettingsPanel.add(workTimeSpinner, c);

		// ----- 2 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		JLabel shortBreakTimeTextLabel = new JLabel(
				"Short break time (seconds)");
		timeSettingsPanel.add(shortBreakTimeTextLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 2;
		shortBreakTimeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		shortBreakTimeSpinner.addChangeListener(changeActionListener);
		((JFormattedTextField) ((JSpinner.DefaultEditor) shortBreakTimeSpinner
				.getEditor()).getTextField())
				.setHorizontalAlignment(JTextField.LEFT);
		timeSettingsPanel.add(shortBreakTimeSpinner, c);

		// ----- 3 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		longBreaksCheckBox = new JCheckBox("Long breaks every");
		longBreaksCheckBox.addChangeListener(changeActionListener);
		timeSettingsPanel.add(longBreaksCheckBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		shortBreaksBeforeLongOneSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		shortBreaksBeforeLongOneSpinner.addChangeListener(changeActionListener);
		((JFormattedTextField) ((JSpinner.DefaultEditor) shortBreaksBeforeLongOneSpinner
				.getEditor()).getTextField())
				.setHorizontalAlignment(JTextField.LEFT);
		timeSettingsPanel.add(shortBreaksBeforeLongOneSpinner, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		shortBreaksTextLabel = new JLabel(" short breaks");
		timeSettingsPanel.add(shortBreaksTextLabel, c);

		// ----- 4 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		longBreakTimeTextLabel = new JLabel("Long break time (seconds)");
		timeSettingsPanel.add(longBreakTimeTextLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 2;
		longBreakTimeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		longBreakTimeSpinner.addChangeListener(changeActionListener);
		((JFormattedTextField) ((JSpinner.DefaultEditor) longBreakTimeSpinner
				.getEditor()).getTextField())
				.setHorizontalAlignment(JTextField.LEFT);
		timeSettingsPanel.add(longBreakTimeSpinner, c);

		// ----- 5 -----//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 1;
		apply = new JButton("Apply");
		apply.setEnabled(false);
		apply.addActionListener(buttonActionListener);
		timeSettingsPanel.add(apply, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 3;
		c.gridy = 5;
		c.gridwidth = 1;
		cancel = new JButton("Cancel");
		cancel.setEnabled(true);
		cancel.addActionListener(buttonActionListener);
		timeSettingsPanel.add(cancel, c);

	}

	private void loadParamsFromFile() {
		pio = new PropertiesIO();
		workTime = Integer.parseInt(pio.readProperty("workTime"));
		shortBreakTime = Integer.parseInt(pio.readProperty("shortBreakTime"));
		longBreaks = Boolean.parseBoolean(pio.readProperty("longBreaks"));
		shortBreaksBeforeLongOne = Integer.parseInt(pio
				.readProperty("shortBreaksBeforeLongOne"));
		longBreakTime = Integer.parseInt(pio.readProperty("longBreakTime"));
	}

	private void storeParamsToFile() {
		pio.writeProperty("workTime", Integer.toString(workTime));
		pio.writeProperty("shortBreakTime", Integer.toString(shortBreakTime));
		pio.writeProperty("longBreaks", Boolean.toString(longBreaks));
		pio.writeProperty("shortBreaksBeforeLongOne",
				Integer.toString(shortBreaksBeforeLongOne));
		pio.writeProperty("longBreakTime", Integer.toString(longBreakTime));
	}

	private void storeParamsToDialog() {
		workTimeSpinner.setValue(workTime);
		shortBreakTimeSpinner.setValue(shortBreakTime);
		shortBreaksBeforeLongOneSpinner.setValue(shortBreaksBeforeLongOne);
		longBreaksCheckBox.setSelected(longBreaks);
		longBreakTimeSpinner.setValue(longBreakTime);
		
		longBreakChanged();
	}

	private void loadParamsFromDialog() {
		workTime = (Integer) workTimeSpinner.getValue();
		shortBreakTime = (Integer) shortBreakTimeSpinner.getValue();
		longBreaks = (Boolean) longBreaksCheckBox.isSelected();
		shortBreaksBeforeLongOne = (Integer) shortBreaksBeforeLongOneSpinner
				.getValue();
		longBreakTime = (Integer) longBreakTimeSpinner.getValue();
	}

	// ----- listeners -----//
	private ActionListener buttonActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(apply)) {
				if (isParametersChanged()) {
					loadParamsFromDialog();
					storeParamsToFile();
					timeController.reset();

					if (!timeController.isRunning()) {
						rightClickMenu.getStartStopItem().setLabel(
								"Start");
					}
					
					apply.setEnabled(false);
					cancel.setEnabled(false);
				}
			} else {
				if (e.getSource().equals(cancel)) {
					storeParamsToDialog();

					apply.setEnabled(false);
					cancel.setEnabled(false);
				}
			}
		}
	};

	private ChangeListener changeActionListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {

			if (isParametersChanged()) {
				apply.setEnabled(true);
				cancel.setEnabled(true);
			} else {
				apply.setEnabled(false);
				cancel.setEnabled(false);
			}

			if (e.getSource().equals(longBreaksCheckBox)) {
				longBreakChanged();
			}
		}
	};

	// ----- util methods -----//
	public void paintStatus(String message) {
		statusLabel.setText(message);
	}

	private boolean isParametersChanged() {

		StringBuilder oldVal = new StringBuilder();
		oldVal.append(workTime);
		oldVal.append(shortBreakTime);
		oldVal.append(longBreaks);
		oldVal.append(longBreakTime);
		oldVal.append(shortBreaksBeforeLongOne);

		StringBuilder newVal = new StringBuilder();
		newVal.append(workTimeSpinner.getValue());
		newVal.append(shortBreakTimeSpinner.getValue());
		newVal.append(longBreaksCheckBox.isSelected());
		newVal.append(longBreakTimeSpinner.getValue());
		newVal.append(shortBreaksBeforeLongOneSpinner.getValue());

		return !oldVal.toString().equals(newVal.toString());
	}
	
	private void longBreakChanged(){
		if (longBreaksCheckBox.isSelected()) {
			shortBreaksBeforeLongOneSpinner.setEnabled(true);
			longBreakTimeSpinner.setEnabled(true);
			shortBreaksTextLabel.setEnabled(true);
			longBreakTimeTextLabel.setEnabled(true);
		} else {
			shortBreaksBeforeLongOneSpinner.setEnabled(false);
			longBreakTimeSpinner.setEnabled(false);
			shortBreaksTextLabel.setEnabled(false);
			longBreakTimeTextLabel.setEnabled(false);
		}
	}
}
