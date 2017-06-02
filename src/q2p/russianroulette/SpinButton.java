package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SpinButton extends JButton implements ActionListener {
	public SpinButton() {
		super("spin");
		//setBounds(275, 75, 175, 25);
		setBounds(25, 75, 100, 25);
		addActionListener(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.spin();
		}
	}
}
