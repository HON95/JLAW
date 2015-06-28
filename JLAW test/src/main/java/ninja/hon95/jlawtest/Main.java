package ninja.hon95.jlawtest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ninja.hon95.jlaw.JlawUtil;

/*
 * TODO Place 'jlaw.dll' and 'jlaw64.dll' in 'src/main/resources/natives/' before building
 */

public final class Main {

	public static final String NATIVE_FOLDER_PATH = "/natives/";
	public static final String WINDOW_ICON_PATHS[] = {
			"/icons/application_16.png", "/icons/application_32.png",
			"/icons/application_64.png", "/icons/application_128.png",
			"/icons/application_256.png"
	};
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	private static volatile boolean running = true;
	private static BlockingQueue<LogMessage> logQueue = new LinkedBlockingQueue<LogMessage>();

	public static void main(String[] args) {
		start();
	}

	public static synchronized void start() {
		log("[CORE] Building frame...");
		WindowBuilder.buildWindow();
		log("[CORE] Loading natives...");
		loadNative();
		log("[CORE] Done.");
	}

	public static synchronized void stop() {
		if (!running)
			return;
		running = false;
		log("[CORE] Stopping...");
		Tester.stopAll();
		WindowBuilder.destroyWindow();
	}

	private static void loadNative() {
		try {
			JlawUtil.loadNatives(NATIVE_FOLDER_PATH, Main.class);
			WindowBuilder.setNativeLibStatus(true);
		} catch (Throwable tr) {
			StringBuilder builder = new StringBuilder("UnsatisfiedLinkError: ").append(tr.getLocalizedMessage());
			for (StackTraceElement e : tr.getStackTrace())
				builder.append('\n').append(e);
			logError(builder.toString());
			WindowBuilder.setNativeLibStatus(false);
		}
	}

	public static void log(String message) {
		logQueue.offer(new LogMessage(message, false));
	}

	public static void logf(String messageFormat, Object... args) {
		logQueue.offer(new LogMessage(String.format(messageFormat, args), false));
	}

	public static void logError(String message) {
		logQueue.offer(new LogMessage(message, true));
	}

	public static void logErrorf(String messageFormat, Object... args) {
		logQueue.offer(new LogMessage(String.format(messageFormat, args), true));
	}

	public static BlockingQueue<LogMessage> getLogQueue() {
		return logQueue;
	}
}
