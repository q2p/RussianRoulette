package q2p.russianroulette;

public class Timing {
	private static long lastTime;
	private static long nowTime;
	private static long lastDelta;
	private static long delta = 0;
	public static float deltaF = 0;
	private static long toSleep;
	private static byte fps = 100;
	//TODO: 50fps
	private static short milisecPerFrame = (short) (1000/fps);

	public static void waitTime() {
		nowTime = System.currentTimeMillis();
		toSleep = lastTime + milisecPerFrame - nowTime;
		try {
			if(toSleep > 0) Thread.sleep(toSleep);
		} catch (InterruptedException e) {
			System.exit(1);
		}
		
		nowTime = System.currentTimeMillis();
		delta = nowTime - lastDelta;
		lastDelta = nowTime;
		lastTime += milisecPerFrame;
		
		deltaF = (float)delta / 1000;
	}

	public static void init() {
		lastTime = System.currentTimeMillis();
		lastDelta = lastTime;
	}
}
