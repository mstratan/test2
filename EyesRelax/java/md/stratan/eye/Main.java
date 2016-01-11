package md.stratan.eye;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class Main {

	private TrayIcon trayIcon;

	private SettingsDialog settingsDialog;
	private TimeController timeController;
	private RightClickMenu rightClickMenu;

	private Image trayImageStarted;
	private Image trayImageStoped;

	public Main() {
		try {
			this.settingsDialog = new SettingsDialog();
			this.rightClickMenu = new RightClickMenu();
			this.settingsDialog.setRightClickMenu(rightClickMenu);
			this.timeController = new TimeController();
			this.timeController.setSettingsDialog(settingsDialog);
			this.timeController.loadParams();
			this.settingsDialog.setTimeController(timeController);

			rightClickMenu.addActionListener(rightClickMenuActionListener);

			trayImageStarted = ImageIO.read(Main.class.getClassLoader()
					.getResourceAsStream("Green_Eye.png"));

			trayImageStoped = ImageIO.read(Main.class.getClassLoader()
					.getResourceAsStream("Red_Eye.png"));

			trayIcon = new TrayIcon(trayImageStarted, "EyesRelax v1.0");
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(trayIconMouseListener);
			SystemTray.getSystemTray().add(trayIcon);

			trayIcon.setPopupMenu(rightClickMenu);

			timeController.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----- the mouse listener for the menu right click -----//
	private ActionListener rightClickMenuActionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();

			if (command.equals("Exit")) {
				System.exit(0);
			} else {
				if (command.equals("Settings")) {
					settingsDialog.setVisible(true);
				} else {
					if (command.equals("Stop")) {
						trayIcon.setImage(trayImageStoped);
						timeController.stop();
						rightClickMenu.getStartStopItem().setLabel("Continue");
					} else {
						if (command.equals("Start")
								|| command.equals("Continue")) {
							trayIcon.setImage(trayImageStarted);
							timeController.start();
							rightClickMenu.getStartStopItem().setLabel("Stop");
						} else {
							if (command.equals("Reset")) {
								if (!timeController.isRunning()) {
									rightClickMenu.getStartStopItem().setLabel(
											"Start");
								}
								timeController.reset();
							}
						}
					}
				}
			}
		}
	};

	// ----- the mouse listener for the tray icon click -----//
	private MouseListener trayIconMouseListener = new MouseAdapter() {

		public void mouseClicked(MouseEvent event) {

			if (event.getButton() == MouseEvent.BUTTON1) {

				if (settingsDialog.isVisible()) {
					settingsDialog.setVisible(false);
				} else {
					settingsDialog.setVisible(true);
				}
			}
		}

	};

	private static void createAndShowGUI() {
		new Main();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
