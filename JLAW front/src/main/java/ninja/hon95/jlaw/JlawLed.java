package ninja.hon95.jlaw;

import ninja.hon95.jlaw.JlawUtil;

/**
 * JLAW LED API
 */
public final class JlawLed {

	// Key bitmap //
	public static final int LOGI_LED_BITMAP_WIDTH = 21;
	public static final int LOGI_LED_BITMAP_HEIGHT = 6;
	public static final int LOGI_LED_BITMAP_BYTES_PER_KEY = 6;
	public static final int LOGI_LED_BITMAP_SIZE = LOGI_LED_BITMAP_WIDTH * LOGI_LED_BITMAP_HEIGHT * LOGI_LED_BITMAP_BYTES_PER_KEY;

	// Device types //
	public static final int LOGI_LED_DEVICETYPE_MONOCHROME = 0x01;
	public static final int LOGI_LED_DEVICETYPE_RGB = 0x02;
	public static final int LOGI_LED_DEVICETYPE_PERKEY_RGB = 0x04;
	public static final int LOGI_LED_DEVICETYPE_ALL = LOGI_LED_DEVICETYPE_MONOCHROME | LOGI_LED_DEVICETYPE_RGB | LOGI_LED_DEVICETYPE_PERKEY_RGB;

	// Other //
	public static final int LOGI_LED_DURATION_INFINATE = 0;

	/**
	 * Initialize the LED system.
	 * 
	 * @return If it was successful.
	 */
	public static boolean logiLedInit() {
		return nLogiLedInit();
	}

	private static native boolean nLogiLedInit();

	/**
	 * Returns the SDK version installed.
	 * 
	 * @return SDK version. Contains major, minor and build numbers separated by a period (".").
	 */
	public static String logiLedGetSdkVersion() {
		return nLogiLedGetSdkVersion();
	}

	private static native String nLogiLedGetSdkVersion();

	/**
	 * Sets the target device. By default, all devices are accepted.
	 * 
	 * @param targetDevice
	 *            Target device(s). Multiple values can be bitwisely OR-ed together.<br>
	 *            Possible values:
	 *            <ul>
	 *            <li>LOGI_LED_DEVICETYPE_MONOCHROME</li>
	 *            <li>LOGI_LED_DEVICETYPE_RGB</li>
	 *            <li>LOGI_LED_DEVICETYPE_PERKEY_RGB</li>
	 *            <li>LOGI_LED_DEVICETYPE_ALL</li>
	 *            </ul>
	 * 
	 */
	public static boolean logiLedSetTargetDevice(int targetDevice) {
		return nLogiLedSetTargetDevice(targetDevice);
	}

	private static native boolean nLogiLedSetTargetDevice(int targetDevice);

	/**
	 * Saves the lighting.
	 * 
	 * @return If it was successful.
	 * @see #logiLedRestoreLighting
	 */
	public static boolean logiLedSaveCurrentLighting() {
		return nLogiLedSaveCurrentLighting();
	}

	private static native boolean nLogiLedSaveCurrentLighting();

	/**
	 * Sets the lighting. For devices that only support a single color, the highest percentage of the three will define the intensity.
	 * 
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLighting(int redPercentage, int greenPercentage, int bluePercentage) {
		return nLogiLedSetLighting(redPercentage, greenPercentage, bluePercentage);
	}

	private static native boolean nLogiLedSetLighting(int redPercentage, int greenPercentage, int bluePercentage);

	/**
	 * Restores the last saved lighting.
	 * 
	 * @return If it was successful.
	 * @see #logiLedSaveCurrentLighting
	 */
	public static boolean logiLedRestoreLighting() {
		return nLogiLedRestoreLighting();
	}

	private static native boolean nLogiLedRestoreLighting();

	/**
	 * Plays a flashing effect and then restores the lighting.
	 * 
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @param milliSecondsDuration
	 *            Duration of effect in milliseconds. Can be set to LOGI_LED_DURATION_INFINATE to last indefinitely.
	 * @param milliSecondsInterval
	 *            Duration of flashing intervals in milliseconds.
	 * @return If it was successful.
	 * @see #logiLedStopEffects
	 */
	public static boolean logiLedFlashLighting(int redPercentage, int greenPercentage, int bluePercentage, int milliSecondsDuration, int milliSecondsInterval) {
		return nLogiLedFlashLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
	}

	private static native boolean nLogiLedFlashLighting(int redPercentage, int greenPercentage, int bluePercentage, int milliSecondsDuration, int milliSecondsInterval);

	/**
	 * Plays a pulsing effect and then restores the lighting.
	 * 
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @param milliSecondsDuration
	 *            Duration of effect in milliseconds. Can be set to LOGI_LED_DURATION_INFINATE to last indefinitely.
	 * @param milliSecondsInterval
	 *            Duration of pulsing intervals in milliseconds.
	 * @return If it was successful.
	 * @see #logiLedStopEffects
	 */
	public static boolean logiLedPulseLighting(int redPercentage, int greenPercentage, int bluePercentage, int milliSecondsDuration, int milliSecondsInterval) {
		return nLogiLedPulseLighting(redPercentage, greenPercentage, bluePercentage, milliSecondsDuration, milliSecondsInterval);
	}

	private static native boolean nLogiLedPulseLighting(int redPercentage, int greenPercentage, int bluePercentage, int milliSecondsDuration, int milliSecondsInterval);

	/**
	 * Stops active effects.
	 * 
	 * @return If it was successful.
	 */
	public static boolean logiLedStopEffects() {
		return nLogiLedStopEffects();
	}

	private static native boolean nLogiLedStopEffects();

	/**
	 * Sets the lighting from a bitmap.
	 * 
	 * @param intBitmap
	 *            Bitmap of key colors. Each int should only hold only <b>one byte</b> of data. RGBA encoded. Must be the same size as LOGI_LED_BITMAP_SIZE.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingFromBitmap(int[] intBitmap) {
		if (intBitmap == null)
			throw new NullPointerException();
		if (intBitmap.length != LOGI_LED_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLedSetLightingFromBitmap(JlawUtil.toUBytesFromInts(intBitmap));
	}

	/**
	 * Sets the lighting from a bitmap.<br>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param bitmap
	 *            Bitmap of key colors. RGBA encoded. Must be the same size as LOGI_LED_BITMAP_SIZE.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingFromBitmap(byte[] bitmap) {
		if (bitmap == null)
			throw new NullPointerException();
		if (bitmap.length != LOGI_LED_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLedSetLightingFromBitmap(bitmap);
	}

	private static native boolean nLogiLedSetLightingFromBitmap(byte[] bitmap);

	/**
	 * Sets the color of a key using its scan code.
	 * 
	 * @param keyCode
	 *            Scan code for the key.
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingForKeyWithScanCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage) {
		return nLogiLedSetLightingForKeyWithScanCode(keyCode, redPercentage, greenPercentage, bluePercentage);
	}

	private static native boolean nLogiLedSetLightingForKeyWithScanCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage);

	/**
	 * Sets the color of a key using its HID code.
	 * 
	 * @param keyCode
	 *            HID code for the key.
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingForKeyWithHidCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage) {
		return nLogiLedSetLightingForKeyWithHidCode(keyCode, redPercentage, greenPercentage, bluePercentage);
	}

	private static native boolean nLogiLedSetLightingForKeyWithHidCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage);

	/**
	 * Sets the color of a key using its quartz code.
	 * 
	 * @param keyCode
	 *            Quartz code for the key.
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingForKeyWithQuartzCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage) {
		return nLogiLedSetLightingForKeyWithQuartzCode(keyCode, redPercentage, greenPercentage, bluePercentage);
	}

	private static native boolean nLogiLedSetLightingForKeyWithQuartzCode(int keyCode, int redPercentage, int greenPercentage, int bluePercentage);

	/**
	 * Sets the color of a key using its key name.
	 * 
	 * @param keyCode
	 *            Key name.
	 * @param redPercentage
	 *            Red percentage.
	 * @param greenPercentage
	 *            Green percentage.
	 * @param bluePercentage
	 *            Blue percentage.
	 * @return If it was successful.
	 */
	public static boolean logiLedSetLightingForKeyWithKeyName(KeyName keyName, int redPercentage, int greenPercentage, int bluePercentage) {
		return nLogiLedSetLightingForKeyWithKeyName(keyName.getCode(), redPercentage, greenPercentage, bluePercentage);
	}

	private static native boolean nLogiLedSetLightingForKeyWithKeyName(int keyName, int redPercentage, int greenPercentage, int bluePercentage);

	/**
	 * Shuts down the LED system.
	 */
	public static void logiLedShutdown() {
		nLogiLedShutdown();
	}

	private static native void nLogiLedShutdown();

	private JlawLed() {}

	/**
	 * KeyNames for JLAW LED API
	 */
	public enum KeyName {

		ESC(0x01),
		F1(0x3b),
		F2(0x3c),
		F3(0x3d),
		F4(0x3e),
		F5(0x3f),
		F6(0x40),
		F7(0x41),
		F8(0x42),
		F9(0x43),
		F10(0x44),
		F11(0x57),
		F12(0x58),
		PRINT_SCREEN(0x137),
		SCROLL_LOCK(0x46),
		PAUSE_BREAK(0x145),
		TILDE(0x29),
		ONE(0x02),
		TWO(0x03),
		THREE(0x04),
		FOUR(0x05),
		FIVE(0x06),
		SIX(0x07),
		SEVEN(0x08),
		EIGHT(0x09),
		NINE(0x0A),
		ZERO(0x0B),
		MINUS(0x0C),
		EQUALS(0x0D),
		BACKSPACE(0x0E),
		INSERT(0x152),
		HOME(0x147),
		PAGE_UP(0x149),
		NUM_LOCK(0x45),
		NUM_SLASH(0x135),
		NUM_ASTERISK(0x37),
		NUM_MINUS(0x4A),
		TAB(0x0F),
		Q(0x10),
		W(0x11),
		E(0x12),
		R(0x13),
		T(0x14),
		Y(0x15),
		U(0x16),
		I(0x17),
		O(0x18),
		P(0x19),
		OPEN_BRACKET(0x1A),
		CLOSE_BRACKET(0x1B),
		BACKSLASH(0x2B),
		KEYBOARD_DELETE(0x153),
		END(0x14F),
		PAGE_DOWN(0x151),
		NUM_SEVEN(0x47),
		NUM_EIGHT(0x48),
		NUM_NINE(0x49),
		NUM_PLUS(0x4E),
		CAPS_LOCK(0x3A),
		A(0x1E),
		S(0x1F),
		D(0x20),
		F(0x21),
		G(0x22),
		H(0x23),
		J(0x24),
		K(0x25),
		L(0x26),
		SEMICOLON(0x27),
		APOSTROPHE(0x28),
		ENTER(0x1C),
		NUM_FOUR(0x4B),
		NUM_FIVE(0x4C),
		NUM_SIX(0x4D),
		LEFT_SHIFT(0x2A),
		Z(0x2C),
		X(0x2D),
		C(0x2E),
		V(0x2F),
		B(0x30),
		N(0x31),
		M(0x32),
		COMMA(0x33),
		PERIOD(0x34),
		FORWARD_SLASH(0x35),
		RIGHT_SHIFT(0x36),
		ARROW_UP(0x148),
		NUM_ONE(0x4F),
		NUM_TWO(0x50),
		NUM_THREE(0x51),
		NUM_ENTER(0x11C),
		LEFT_CONTROL(0x1D),
		LEFT_WINDOWS(0x15B),
		LEFT_ALT(0x38),
		SPACE(0x39),
		RIGHT_ALT(0x138),
		RIGHT_WINDOWS(0x15C),
		APPLICATION_SELECT(0x15D),
		RIGHT_CONTROL(0x11D),
		ARROW_LEFT(0x14B),
		ARROW_DOWN(0x150),
		ARROW_RIGHT(0x14D),
		NUM_ZERO(0x52),
		NUM_PERIOD(0x53);

		private final int gCode;

		private KeyName(int code) {
			gCode = code;
		}

		public int getCode() {
			return gCode;
		}
	}
}
