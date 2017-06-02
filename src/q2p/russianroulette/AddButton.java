package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddButton extends JButton implements ActionListener {
	public byte pos;
	public byte id;
	public RemoveVictimButton removeVictimButton;
	
	public AddButton() {
		super("Add victim");
		update();
		addActionListener(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.addVictim();
			update();
		}
	}
	
	public void update() {
		setBounds(150, 25+Logic.victims.size()*50, 225, 25);
		if(Logic.victims.size() == 6) {
			setEnabled(false);
			setVisible(false);
		} else {
			setEnabled(true);
			setVisible(true);
		}
	}
}
