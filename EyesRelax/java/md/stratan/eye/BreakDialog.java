package md.stratan.eye;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class BreakDialog extends JWindow {

	// ----- window's parameters -----//
	private static final int WIDTH = 950;
	private static final int HEIGHT = 100;

	// ----- -----//
	private JPanel leftPane;
	private JPanel rightPane;
	private JLabel timeLabel;

	JPanel rootPane = new JPanel();
	
	// ----- -----//
	public BreakDialog() {

		//setVisible(true);
		setAlwaysOnTop(true);
		setSize(WIDTH, HEIGHT);

		int centerX = Utils.getMaxVisible().width / 2 - WIDTH / 2;
		setLocation(centerX, 0);

		/*leftPane = new JPanel();
		try {
			Image image = ImageIO.read(BreakDialog.class.getClassLoader()
					.getResourceAsStream("break.png"));

			JLabel picLabel = new JLabel(new ImageIcon(image));
			//picLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
			leftPane.add(picLabel);

		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//rightPane = new JPanel();
		timeLabel = new JLabel("00:00");
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		/*rightPane.add(timeLabel);*/
		
		//rootPane.setLayout(new BorderLayout());
		rootPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		/*rootPane.add(leftPane);*/
		/*rootPane.add(rightPane);*/
		rootPane.add(timeLabel);

		add(rootPane);
		
	}
	private Long i = 0l;
	// ----- util methods -----//
	public void paintStatus(String message) {
		i++;
		
		timeLabel.setText(message);
		
		if(i%2==0){
			rootPane.setBackground(Color.RED);
		} else {
			rootPane.setBackground(null);
		}
		
	}

}
