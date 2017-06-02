package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RemoveVictimButton extends JButton implements ActionListener {
	public VictimButton parent;
	
	public RemoveVictimButton(VictimButton parent) {
		super("remove");
		this.parent = parent;
		setBounds(275, 25+parent.pos*50, 100, 25);
		addActionListener(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.unloadVictim(parent.pos);
		}
	}
}
