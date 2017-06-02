package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ClickButton extends JButton implements ActionListener {
	public ClickButton() {
		super("click");
		setBounds(25, 125, 100, 25);
		addActionListener(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.click();
		}
	}
}
