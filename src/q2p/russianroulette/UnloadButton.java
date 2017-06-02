package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class UnloadButton extends JButton implements ActionListener {
	public UnloadButton() {
		super("load");
		setBounds(25, 25, 100, 25);
		addActionListener(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.loadOrUnload();
		}
	}
}
