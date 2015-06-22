package ninja.hon95.jlawtest;

import static ninja.hon95.jlaw.JlawArxControl.*;
import static ninja.hon95.jlaw.JlawGkey.*;
import static ninja.hon95.jlaw.JlawLcd.*;
import static ninja.hon95.jlaw.JlawLed.*;
import ninja.hon95.jlaw.JlawArxControl.JlawArxControlCallback;
import ninja.hon95.jlaw.JlawGkey.JlawGkeyCallback;

public final class Tester {

	private static Object arxLock = new Object();
	private static Object gkeyLock = new Object();
	private static Object lcdLock = new Object();
	private static Object ledLock = new Object();
	private static volatile boolean arxActive = false;
	private static volatile boolean gkeyActive = false;
	private static volatile boolean lcdActive = false;
	private static volatile boolean ledActive = false;
	private static volatile boolean arxStop;
	private static volatile boolean gkeyStop;
	private static volatile boolean lcdStop;
	private static volatile boolean ledStop;

	private static volatile String arxStatus = "Not initialized";
	private static volatile String gkeyStatus = "Not initialized";
	private static volatile String lcdStatus = "Not initialized";
	private static volatile String ledStatus = "Not initialized";

	public static void testArx() {
		synchronized (arxLock) {
			arxActive = true;
			arxStop = false;
			Main.log("[ARX] START");
			logiArxInit("ninja.hon95.jlawtest", "JLAW Test", new JlawArxControlCallback() {

				@Override
				public void call(int eventType, int eventValue, String eventArg) {
					String eventTypeString;
					switch (eventType) {
					case LOGI_ARX_EVENT_FOCUS_ACTIVE:
						eventTypeString = "LOGI_ARX_EVENT_FOCUS_ACTIVE";
						break;
					case LOGI_ARX_EVENT_FOCUS_INACTIVE:
						eventTypeString = "LOGI_ARX_EVENT_FOCUS_INACTIVE";
						break;
					case LOGI_ARX_EVENT_MOBILEDEVICE_ARRIVAL:
						eventTypeString = "LOGI_ARX_EVENT_MOBILEDEVICE_ARRIVAL";
						break;
					case LOGI_ARX_EVENT_MOBILEDEVICE_REMOVAL:
						eventTypeString = "LOGI_ARX_EVENT_MOBILEDEVICE_REMOVAL";
						break;
					case LOGI_ARX_EVENT_TAP_ON_TAG:
						eventTypeString = "LOGI_ARX_EVENT_TAP_ON_TAG";
						break;
					default:
						eventTypeString = "UNKNOWN";
						break;
					}
					Main.logf("[ARX] Event: type=%s value=%d arg='%s' ", eventTypeString, eventValue, eventArg);
				}
			});

			arxStatus = "Initialized";

			int errorCode = logiArxGetLastError();
			if (errorCode != 0)
				Main.logError("[ARX] Error: " + logiArxGetErrorText(errorCode));
			else
				Main.log("[ARX] No errors");

			while (!arxStop)
				sleep(10);

			Main.log("[ARX] STOP");
			logiArxShutdown();
			arxStatus = "Shut down";
			arxActive = false;
		}
	}

	public static void testGkey() {
		synchronized (gkeyLock) {
			gkeyActive = true;
			gkeyStop = false;
			Main.log("[GKEY] START");
			logiGkeyInit(new JlawGkeyCallback() {
				@Override
				public void call(int keyIndex, boolean keyDown, int mState, boolean fromMouse, String gkeyName) {
					Main.logf("[GKEY] Event: index=%d down=%b m_state=%d mouse=%b name=%s", keyIndex, keyDown, mState, fromMouse, gkeyName);
				}
			});

			gkeyStatus = "Initialized";

			while (!gkeyStop)
				sleep(10);

			Main.log("[GKEY] STOP");
			logiGkeyShutdown();
			gkeyStatus = "Shut down";
			gkeyActive = false;
		}
	}

	public static void testLcd() {
		synchronized (lcdLock) {
			lcdActive = true;
			lcdStop = false;
			boolean color = false;
			boolean mono = false;
			Main.log("[LCD] START");
			logiLcdInit("JLAW Test", LOGI_LCD_TYPE_COLOR | LOGI_LCD_TYPE_MONO);

			if (logiLcdIsConnected(LOGI_LCD_TYPE_COLOR)) {
				Main.log("[LCD] Connected to color LCD");
				color = true;
				int[] colorBitmap = new int[4 * LOGI_LCD_COLOR_WIDTH * LOGI_LCD_COLOR_HEIGHT];
				for (int i = 0; i + 3 < colorBitmap.length; i += 4) {
					colorBitmap[i] = (255 - (i % 256));
					colorBitmap[i + 1] = (i % 256);
					colorBitmap[i + 2] = ((i / LOGI_LCD_COLOR_WIDTH) % 256);
					colorBitmap[i + 3] = 255;
				}
				logiLcdColorSetTitle("JLAW", 255, 255, 255);
				logiLcdColorSetText(0, "Line 0 abc", 100, 100, 100);
				logiLcdColorSetText(2, "<--------- 29 wide --------->");
				logiLcdColorSetBackground(colorBitmap);
			}

			if (logiLcdIsConnected(LOGI_LCD_TYPE_MONO)) {
				Main.log("[LCD] Connected to mono LCD");
				mono = true;
				int[] monoBitmap = new int[LOGI_LCD_MONO_WIDTH * LOGI_LCD_MONO_HEIGHT];
				for (int i = 0; i < monoBitmap.length; i++) {
					monoBitmap[i] = (i % 256);
				}
				logiLcdMonoSetText(0, "Line 0");
				logiLcdMonoSetBackground(monoBitmap);
			}

			if (color && !mono)
				lcdStatus = "Initialized with color";
			else if (!color && mono)
				lcdStatus = "Initialized with mono";
			else if (color && !mono)
				lcdStatus = "Initialized with color and mono";
			else
				lcdStatus = "Initialized with neither color or mono";

			while (!lcdStop) {
				logiLcdUpdate();
				sleep(33);
			}

			Main.log("[LCD] STOP");
			logiLcdShutdown();
			lcdStatus = "Shut down";
			lcdActive = false;
		}
	}

	public static void testLed() {
		synchronized (ledLock) {
			ledActive = true;
			ledStop = false;
			Main.log("[LED] START");
			logiLedInit();
			logiLedSaveCurrentLighting();
			logiLedFlashLighting(100, 0, 0, LOGI_LED_DURATION_INFINATE, 500);

			ledStatus = "Initialized";

			while (!ledStop)
				sleep(10);

			Main.log("[LED] STOP");
			logiLedStopEffects();
			logiLedRestoreLighting();
			logiLedShutdown();
			ledStatus = "Shut down";
			ledActive = false;
		}
	}

	public static void stopAll() {
		if (arxActive)
			arxStop = true;
		if (gkeyActive)
			gkeyStop = true;
		if (lcdActive)
			lcdStop = true;
		if (ledActive)
			ledStop = true;
		synchronized (arxLock) {
		}
		synchronized (gkeyLock) {
		}
		synchronized (lcdLock) {
		}
		synchronized (ledLock) {
		}
	}

	public static boolean isArxActive() {
		return arxActive;
	}

	public static boolean isGkeyActive() {
		return gkeyActive;
	}

	public static boolean isLcdActive() {
		return lcdActive;
	}

	public static boolean isLedActive() {
		return ledActive;
	}

	public static void stopArx() {
		arxStop = true;
	}

	public static void stopGkey() {
		gkeyStop = true;
	}

	public static void stopLcd() {
		lcdStop = true;
	}

	public static void stopLed() {
		ledStop = true;
	}

	public static String getArxStatus() {
		return arxStatus;
	}

	public static String getGkeyStatus() {
		return gkeyStatus;
	}

	public static String getLcdStatus() {
		return lcdStatus;
	}

	public static String getLedStatus() {
		return ledStatus;
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
		}
	}
}
