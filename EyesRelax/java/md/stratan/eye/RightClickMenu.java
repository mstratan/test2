package md.stratan.eye;

import java.awt.MenuItem;
import java.awt.PopupMenu;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class RightClickMenu extends PopupMenu {

	// ----- menu's items -----//
	private MenuItem startStopItem;
	private MenuItem resetItem;
	private MenuItem settingsItem;
	private MenuItem exitItem;

	// ----- -----//
	public RightClickMenu() {

		startStopItem = new MenuItem("Stop");
		resetItem = new MenuItem("Reset");
		settingsItem = new MenuItem("Settings");
		exitItem = new MenuItem("Exit");
		
		this.add(startStopItem);
		this.add(resetItem);
		this.add(settingsItem);
		this.add(exitItem);
	}

	// ----- getters for the menu's items -----//
	public MenuItem getStartStopItem() {
		return startStopItem;
	}
}
