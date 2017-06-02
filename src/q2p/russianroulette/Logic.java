package q2p.russianroulette;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Logic {
	public static final Random random = new Random();
	
	public static final boolean barrel[] = new boolean[6];
	public static boolean isOpened = true;
	
	public static final ArrayList<File[]> victims = new ArrayList<File[]>(); 
	
	public static void spin() {
		boolean newBarrel[] = new boolean[6];
		byte off = (byte)random.nextInt(6);
		for(byte i = 0; i < 6; i++) {
			byte coff = (byte)(i-off);
			if(coff < 0) coff = (byte)(6 + coff);
			newBarrel[i] = barrel[coff];
		}
		for(byte i = 0; i < 6; i++) barrel[i] = newBarrel[i];
		updateGui();
	}

	public static void init() {
		useHole(0, true);
		for(byte i = 1; i < 6; i++) useHole(i, false);
	}

	public static void useHole(int id, boolean state) {
		if(isOpened) {
			barrel[id] = state;
			RouletteMain.barrelChecks[id].setSelected(barrel[id]);
		}
		updateGui();
	}
	
	private static void updateGui() {
		byte bulletsInside = 0;
		for(byte i = 0; i < 6; i++) if(barrel[i]) bulletsInside++;
		
		if(isOpened) {
			RouletteMain.unloadButton.setText("load");
			RouletteMain.clickButton.setEnabled(false);
		} else {
			RouletteMain.unloadButton.setText("unload");
			if(bulletsInside != 0 && !victims.isEmpty()) RouletteMain.clickButton.setEnabled(true);
			else RouletteMain.clickButton.setEnabled(false);
		}
		for(byte i = 0; i < 6; i++) {
			if(isOpened) RouletteMain.barrelChecks[i].setSelected(barrel[i]);
			else RouletteMain.barrelChecks[i].setSelected(false);
			RouletteMain.barrelChecks[i].setEnabled(isOpened);
			RouletteMain.barrelChecks[i].setVisible(isOpened);
		}
	}
	
	private static File[] selectVictimFile(File prev) {
		JFileChooser fChooser = new JFileChooser();
		fChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fChooser.setMultiSelectionEnabled(true);
		if(prev != null) fChooser.setCurrentDirectory(prev.getParentFile());
		if(fChooser.showOpenDialog(null)!= JFileChooser.APPROVE_OPTION) return null;
		return fChooser.getSelectedFiles();
	}
	
	public static void addVictim() {
		File[] out = selectVictimFile(null);
		if(out == null || out.length == 0) return;
		victims.add(out);
		RouletteMain.victimButtons[victims.size()-1].load();
	}

	public static void loadOrUnload() {
		isOpened = !isOpened;
		if(!isOpened) spin();
		else updateGui();
	}

	public static void click() {
		if(isOpened) return;
		if(barrel[0]) {
			killVictim();
			String msg = "Oh no! Gun shot! Next files lost:\n";
			byte to;
			if(victims.get(0).length > 16) to = 15;
			else to = (byte)Math.min(victims.get(0).length, 16);
			for(byte i = 0; i < to; i++) msg += victims.get(0)[i]+"\n";
			if(victims.get(0).length > 16) msg += "... and more.";
			unloadVictim(0);
			validateFiles();
			JOptionPane.showMessageDialog(RouletteMain.rouletteMain, msg, "OH NO!", JOptionPane.WARNING_MESSAGE);
		} else {
			ArrayList<File[]> newVictims = new ArrayList<File[]>();
			for(byte i = 0; i < victims.size(); i++) {
				byte coff = (byte)(i-1);
				if(coff < 0) coff = (byte)(victims.size()-1);
				newVictims.add(victims.get(coff));
			}
			for(byte i = 0; i < victims.size(); i++) victims.set(i, newVictims.remove(0));
			JOptionPane.showMessageDialog(RouletteMain.rouletteMain, "Gun clicked! Phew. Your files are fine", "Phew", JOptionPane.INFORMATION_MESSAGE);
		}
		for(byte i = 0; i < 5; i++) barrel[i] = barrel[i+1];
		barrel[5] = false;
		
		updateGui();
	}

	private static void killVictim() {
		File[] files = victims.get(0);
		for(File f : files) {
			if(f.isDirectory()) killInside(f);
			f.delete();
		}
	}
	
	private static void killInside(File dir) {
		File[] list = dir.listFiles();
		for(File f : list) {
			if(f.isDirectory()) killInside(f);
			f.delete();
		}
	}
	
	private static void validateFiles() {
		for(int v = 0; v < victims.size(); v++) {
			for(int i = 0; i < victims.get(v).length; i++) {
				if(!victims.get(v)[i].exists()) {
					victims.remove(v);
				}
			}
		}
		for(byte i = 0; i < victims.size(); i++) RouletteMain.victimButtons[i].load();
		for(byte i = (byte)victims.size(); i < 6; i++) RouletteMain.victimButtons[i].unload();
	}

	public static void checkVictim(byte id) {
		String msg = "Curent files are:\n";
		for(byte i = 0; i < Math.min(16, victims.get(id).length); i++) msg += victims.get(id)[i].getAbsolutePath()+"\n";
		msg += "do you want to change them?";
		int result = JOptionPane.showConfirmDialog(RouletteMain.rouletteMain, msg, "Files list", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(result != JOptionPane.YES_OPTION) return;

		victims.set(id, selectVictimFile(victims.get(id)[0]));
	}
	
	public static void unloadVictim(int id) {
		victims.remove(id);
		for(byte i = 0; i < victims.size(); i++) RouletteMain.victimButtons[i].load();
		for(byte i = (byte)victims.size(); i < 6; i++) RouletteMain.victimButtons[i].unload();
	}
}
