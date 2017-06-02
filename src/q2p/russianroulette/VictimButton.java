package q2p.russianroulette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class VictimButton extends JButton implements ActionListener {
	public byte pos;
	public RemoveVictimButton removeVictimButton;
	
	public VictimButton(int pos) {
		super("victim");
		this.pos = (byte)pos;
		setBounds(150, 25+this.pos*50, 100, 25);
		addActionListener(this);
		removeVictimButton = new RemoveVictimButton(this);
		RouletteMain.rouletteMain.getContentPane().add(this);
		unload();
	}
		
	public void actionPerformed(ActionEvent ae) {
		synchronized (RouletteMain.lock) {
			Logic.checkVictim(pos);
		}
	}

	public void unload() {
		setEnabled(false);
		setVisible(false);
		removeVictimButton.setEnabled(false);
		removeVictimButton.setVisible(false);
		RouletteMain.addButton.update();
	}

	public void load() {
		setEnabled(true);
		setVisible(true);
		removeVictimButton.setEnabled(true);
		removeVictimButton.setVisible(true);
		RouletteMain.addButton.update();
	}
}
