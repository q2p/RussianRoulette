package q2p.russianroulette;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

//TODO: exception catch and errors
//TODO: add icon to window
//TODO: уничтожение всех элементов окна прерд выходом
//TODO: marathon
//TODO:hexagonal sweep

@SuppressWarnings("serial")
public class RouletteMain extends JFrame implements WindowListener {
	public static RouletteMain rouletteMain = null;

	public static BarrelCheck[] barrelChecks = new BarrelCheck[6];
	public static VictimButton[] victimButtons = new VictimButton[6];
	public static UnloadButton unloadButton;
	public static SpinButton spinButton;
	public static ClickButton clickButton;
	public static AddButton addButton;
	
	public static Object lock = new Object();
	
	private void init() {
		setResizable(false);
		setTitle("Russian roulette");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(450, 325));
		getContentPane().setLayout(null);

		unloadButton = new UnloadButton();
		spinButton = new SpinButton();
		clickButton = new ClickButton();
		addButton = new AddButton();
		for(byte i = 0; i < 6; i++) {
			victimButtons[i] = new VictimButton(i);
			victimButtons[i].unload();
			barrelChecks[i] = new BarrelCheck(i);
		}
		addButton.update();
		
		Logic.init();
		
		addWindowListener(this);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		rouletteMain = new RouletteMain();
		rouletteMain.init();
	}

	public void windowClosing(WindowEvent we) {
		synchronized (lock) {
			//TODO:
			System.out.println("CL");
			System.exit(0);
		}
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}