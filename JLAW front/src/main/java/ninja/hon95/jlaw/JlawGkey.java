package ninja.hon95.jlaw;

/**
 * JLAW G-key API
 */
public final class JlawGkey {

	public static final int LOGI_MAX_MOUSE_BUTTONS = 20;
	public static final int LOGI_MAX_GKEYS = 29;
	public static final int LOGI_MAX_M_STATES = 3;

	/**
	 * Initialized the G-key system.
	 * 
	 * @param callback
	 *            Callback for G-key events.
	 * @return If the initialization was successful.
	 */
	public static boolean logiGkeyInit(JlawGkeyCallback callback) {
		if (callback == null)
			throw new NullPointerException();
		return nLogiGkeyInit(callback);
	}

	private static native boolean nLogiGkeyInit(JlawGkeyCallback callback);

	/**
	 * Initialize the G-key system without a callback for G-key events.
	 * 
	 * @return If the initialization was successful.
	 */
	public static boolean logiGkeyInitWithoutCallback() {
		return nLogiGkeyInitWithoutCallback();
	}

	private static native boolean nLogiGkeyInitWithoutCallback();

	/**
	 * Check if a mouse button is pressed.
	 * 
	 * @param buttonNumber
	 *            Mouse button number.
	 * @return If the button is pressed.
	 */
	public static boolean logiGkeyIsMouseButtonPressed(int buttonNumber) {
		return nLogiGkeyIsMouseButtonPressed(buttonNumber);
	}

	private static native boolean nLogiGkeyIsMouseButtonPressed(int buttonNumber);

	/**
	 * Returns a friendly name for the mouse button.
	 * 
	 * @param buttonNumber
	 *            Mouse button number.
	 * @return Friendly name for mouse button.
	 */
	public static String logiGkeyGetMouseButtonString(int buttonNumber) {
		return nLogiGkeyGetMouseButtonString(buttonNumber);
	}

	private static native String nLogiGkeyGetMouseButtonString(int buttonNumber);

	/**
	 * Check if keyboard G-key is pressed and if specified mode is active.
	 * 
	 * @param gkeyNumber
	 *            G-key number.
	 * @param modeNumber
	 *            Mode number.
	 * @return If the G-key is pressed and specified mode is active.
	 */
	public static boolean logiGkeyIsKeyboardGkeyPressed(int gkeyNumber, int modeNumber) {
		return nLogiGkeyIsKeyboardGkeyPressed(gkeyNumber, modeNumber);
	}

	private static native boolean nLogiGkeyIsKeyboardGkeyPressed(int gkeyNumber, int modeNumber);

	/**
	 * Returns a friendly name for the G-key for specified mode.
	 * 
	 * @param gkeyNumber
	 *            G-key number.
	 * @param modeNumber
	 *            Mode number.
	 * @return Friendly name for G-key in mode.
	 */
	public static String logiGkeyGetKeyboardGkeyString(int gkeyNumber, int modeNumber) {
		return nLogiGkeyGetKeyboardGkeyString(gkeyNumber, modeNumber);
	}

	private static native String nLogiGkeyGetKeyboardGkeyString(int gkeyNumber, int modeNumber);

	/**
	 * Shutdown the G-key system.
	 */
	public static void logiGkeyShutdown() {
		nLogiGkeyShutdown();
	}

	private static native void nLogiGkeyShutdown();

	private JlawGkey() {}

	/**
	 * Callback for JLAW G-key API. Needs to be thread safe.
	 */
	public interface JlawGkeyCallback {

		/**
		 * Called when G-key events happen.
		 * 
		 * @param keyIndex
		 *            Index of the G-key or mouse button
		 * @param keyDown
		 *            If the button is pressed
		 * @param mState
		 *            M state
		 * @param fromMouse
		 *            If the event comes from a mouse
		 * @param gkeyName
		 *            Name of G-key or button
		 */
		public void call(int keyIndex, boolean keyDown, int mState, boolean fromMouse, String gkeyName);
	}
}
