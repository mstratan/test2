/**
 * 
 */
package md.stratan.eye;

/**
 * @author mstratan
 * @created Feb 28, 2011
 */
public class CommandController {

	private BreakDialog breakDialog;

	private SettingsDialog settingsDialog;

	private TimeController timeComtroller;

	private RightClickMenu rightClickMenu;

	public CommandController(BreakDialog breakDialog,
			SettingsDialog settingsDialog, TimeController timeComtroller,
			RightClickMenu rightClickMenu) {

		this.breakDialog = breakDialog;
		this.settingsDialog = settingsDialog;
		this.timeComtroller = timeComtroller;
		this.rightClickMenu = rightClickMenu;
	}

	
}
