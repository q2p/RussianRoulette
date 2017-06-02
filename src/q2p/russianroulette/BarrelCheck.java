package q2p.russianroulette;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class BarrelCheck extends JCheckBox implements ItemListener {
	byte id;
	
	public BarrelCheck(int id) {
		super("");
		addItemListener(this);
		//setBounds(225, 25+id*50, 175, 25);
		setBounds(400, 25+id*50, 175, 25);
		setEnabled(true);
		setSelected(false);
		this.id = (byte)id;
		RouletteMain.rouletteMain.getContentPane().add(this);
	}

	public void itemStateChanged(ItemEvent ie) {
		synchronized (RouletteMain.lock) {
			if(ie.getStateChange() == ItemEvent.SELECTED) Logic.useHole(id, true);
			else Logic.useHole(id, false);
		}
	}
}
