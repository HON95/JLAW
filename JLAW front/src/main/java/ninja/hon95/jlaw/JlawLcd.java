package ninja.hon95.jlaw;

import ninja.hon95.jlaw.JlawUtil;

/**
 * JLAW LCD API
 */
public final class JlawLcd {

	// LCD types //
	public static final int LOGI_LCD_TYPE_MONO = 0x01;
	public static final int LOGI_LCD_TYPE_COLOR = 0x02;
	public static final int LOGI_LCD_TYPE_BOTH = LOGI_LCD_TYPE_MONO | LOGI_LCD_TYPE_COLOR;

	// Monochrome buttons //
	public static final int LOGI_LCD_MONO_BUTTON_0 = 0x01;
	public static final int LOGI_LCD_MONO_BUTTON_1 = 0x02;
	public static final int LOGI_LCD_MONO_BUTTON_2 = 0x04;
	public static final int LOGI_LCD_MONO_BUTTON_3 = 0x08;

	// Color buttons //
	public static final int LOGI_LCD_COLOR_BUTTON_LEFT = 0x0100;
	public static final int LOGI_LCD_COLOR_BUTTON_RIGHT = 0x0200;
	public static final int LOGI_LCD_COLOR_BUTTON_OK = 0x0400;
	public static final int LOGI_LCD_COLOR_BUTTON_CANCEL = 0x0800;
	public static final int LOGI_LCD_COLOR_BUTTON_UP = 0x1000;
	public static final int LOGI_LCD_COLOR_BUTTON_DOWN = 0x2000;
	public static final int LOGI_LCD_COLOR_BUTTON_MENU = 0x4000;

	// Screen sizes //
	public static final int LOGI_LCD_MONO_WIDTH = 160;
	public static final int LOGI_LCD_MONO_HEIGHT = 43;
	public static final int LOGI_LCD_COLOR_WIDTH = 320;
	public static final int LOGI_LCD_COLOR_HEIGHT = 240;

	// Bitmaps //
	public static final int LOGI_LCD_MONO_BITMAP_SIZE = LOGI_LCD_MONO_WIDTH * LOGI_LCD_MONO_HEIGHT;
	public static final int LOGI_LCD_COLOR_BITMAP_BYTES_PER_PIXEL = 4;
	public static final int LOGI_LCD_COLOR_BITMAP_SIZE = LOGI_LCD_COLOR_WIDTH * LOGI_LCD_COLOR_HEIGHT * LOGI_LCD_COLOR_BITMAP_BYTES_PER_PIXEL;

	/**
	 * Initializes your applet.
	 * 
	 * @param friendlyName
	 *            Name of applet.
	 * @param lcdType
	 *            LCD type.<br/>
	 *            Possible values:
	 *            <ul>
	 *            <li>LOGI_LCD_TYPE_MONO</li>
	 *            <li>LOGI_LCD_TYPE_COLOR</li>
	 *            <li>LOGI_LCD_TYPE_MONO | LOGI_LCD_TYPE_COLOR</li>
	 *            </ul>
	 * @return If the initialization was successful.
	 */
	public static boolean logiLcdInit(String friendlyName, int lcdType) {
		if (friendlyName == null)
			throw new NullPointerException();
		return nLogiLcdInit(friendlyName, lcdType);
	}

	private static native boolean nLogiLcdInit(String friendlyName, int lcdType);

	/**
	 * Check if LCD device is connected.<br/>
	 * Note: Applet must be initialized first.
	 * 
	 * @param lcdType
	 *            LCD type. <br/>
	 *            Possible values:
	 *            <ul>
	 *            <li>LOGI_LCD_TYPE_MONO</li>
	 *            <li>LOGI_LCD_TYPE_COLOR</li>
	 *            <li>LOGI_LCD_TYPE_MONO | LOGI_LCD_TYPE_COLOR</li>
	 *            </ul>
	 * @return If the LCD device is connected.
	 */
	public static boolean logiLcdIsConnected(int lcdType) {
		return nLogiLcdIsConnected(lcdType);
	}

	private static native boolean nLogiLcdIsConnected(int lcdType);

	/**
	 * Checks if a specified button on the LCD device is pressed.<br/>
	 * Note: The button will be considered pressed only if your applet is the one currently in the foreground.
	 * 
	 * @param button
	 *            Button ID.<br/>
	 *            Possible values:
	 *            <ul>
	 *            <li>LOGI_LCD_MONO_BUTTON_0</li>
	 *            <li>LOGI_LCD_MONO_BUTTON_1</li>
	 *            <li>LOGI_LCD_MONO_BUTTON_2</li>
	 *            <li>LOGI_LCD_MONO_BUTTON_3</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_LEFT</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_RIGHT</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_OK</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_CANCEL</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_UP</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_DOWN</li>
	 *            <li>LOGI_LCD_COLOR_BUTTON_MENU</li>
	 *            </ul>
	 * @return If the button is pressed.
	 */
	public static boolean logiLcdIsButtonPressed(int button) {
		return nLogiLcdIsButtonPressed(button);
	}

	private static native boolean nLogiLcdIsButtonPressed(int button);

	/**
	 * Updates the display.<br/>
	 * Should be called every frame of your main loop.
	 */
	public static void logiLcdUpdate() {
		nLogiLcdUpdate();
	}

	private static native void nLogiLcdUpdate();

	/**
	 * Shuts down your applet.
	 */
	public static void logiLcdShutdown() {
		nLogiLcdShutdown();
	}

	private static native void nLogiLcdShutdown();

	/**
	 * Sets the specified monochrome image as the background.<br/>
	 * Note: Since bytes in Java are signed, this method takes an array of ints instead.
	 * 
	 * @param intMonoBitmap
	 *            The image as a monochrome bitmap. Every pixel consists of 8 bits (0-255) and stored in one array element.
	 *            Ordered in rows from top left to bottom right. The width and height of the monochrome display is 160 x 43, so the array must consist of 6880
	 *            elements.
	 * @return If it was successful.
	 * @see #logiLcdMonoSetBackground(byte[])
	 */
	public static boolean logiLcdMonoSetBackground(int[] intMonoBitmap) {
		if (intMonoBitmap == null)
			throw new NullPointerException();
		if (intMonoBitmap.length != LOGI_LCD_MONO_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLcdMonoSetBackground(JlawUtil.toUBytesFromInts(intMonoBitmap));
	}

	/**
	 * Sets the specified monochrome image as the background.<br/>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param monoBitmap
	 *            The image as a monochrome bitmap. Every pixel consists of 8 bits (0-255) and stored in one array element.
	 *            Ordered in rows from top left to bottom right. The width and height of the monochrome display is 160 x 43, so the array must consist of 6880
	 *            elements.
	 * @return If it was successful.
	 * @see #logiLcdMonoSetBackground(int[])
	 */
	public static boolean logiLcdMonoSetBackground(byte[] monoBitmap) {
		if (monoBitmap == null)
			throw new NullPointerException();
		if (monoBitmap.length != LOGI_LCD_MONO_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLcdMonoSetBackground(monoBitmap);
	}

	private static native boolean nLogiLcdMonoSetBackground(byte[] monoBitmap);

	/**
	 * Sets the text for a specified line number.
	 * 
	 * @param lineNumber
	 *            The line number. Monochrome displays can have 4 lines, so this value must range from 0 to 3.
	 * @param text
	 *            The text to be displayed.
	 * @return If it was successful.
	 */
	public static boolean logiLcdMonoSetText(int lineNumber, String text) {
		if (text == null)
			throw new NullPointerException();
		return nLogiLcdMonoSetText(lineNumber, text);
	}

	private static native boolean nLogiLcdMonoSetText(int lineNumber, String text);

	/**
	 * Sets the specified color image as the background.<br/>
	 * Note: Since bytes in Java are signed, this method takes an array of shorts instead.
	 * 
	 * @param shortColorBitmap
	 *            The image as a color bitmap. Every pixel consists of 32 bits stored as BGRA with 8 bits each (0-255), and each byte stored in one array
	 *            element.
	 *            Ordered in rows from top left to bottom right. The width and height of the color display is 320 x 240 and each pixel has 4 components,
	 *            so the array must consist of 307200 elements.
	 * @return If it was successful.
	 * @see #logiLcdColorSetBackground(byte)
	 */
	public static boolean logiLcdColorSetBackground(int[] intColorBitmap) {
		if (intColorBitmap == null)
			throw new NullPointerException();
		if (intColorBitmap.length != LOGI_LCD_COLOR_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLcdColorSetBackground(JlawUtil.toUBytesFromInts(intColorBitmap));
	}

	/**
	 * Sets the specified color image as the background.<br/>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param colorBitmap
	 *            The image as a color bitmap. Every pixel consists of 32 bits stored as BGRA with 8 bits each (0-255), and each byte stored in one array
	 *            element.
	 *            Ordered in rows from top left to bottom right. The width and height of the color display is 320 x 240 and each pixel has 4 components,
	 *            so the array must consist of 307200 elements.
	 * @return If it was successful.
	 * @see #logiLcdColorSetBackground(int[])
	 */
	public static boolean logiLcdColorSetBackground(byte[] colorBitmap) {
		if (colorBitmap == null)
			throw new NullPointerException();
		if (colorBitmap.length != LOGI_LCD_COLOR_BITMAP_SIZE)
			throw new IllegalArgumentException("Wrong bitmap size");
		return nLogiLcdColorSetBackground(colorBitmap);
	}

	private static native boolean nLogiLcdColorSetBackground(byte[] colorBitmap);

	/**
	 * Sets the title at the top of the display as white text.
	 * 
	 * @param title
	 *            The title.
	 * @return If it was successful.
	 * @see #logiLcdColorSetTitle(String, int, int, int)
	 */
	public static boolean logiLcdColorSetTitle(String title) {
		if (title == null)
			throw new NullPointerException();
		return logiLcdColorSetTitle(title, 255, 255, 255);
	}

	/**
	 * Sets the title at the top of the display.
	 * 
	 * @param title
	 *            The title.
	 * @param red
	 *            Red color component.
	 * @param green
	 *            Green color component.
	 * @param blue
	 *            Blue color component.
	 * @return If it was successful.
	 */
	public static boolean logiLcdColorSetTitle(String title, int red, int green, int blue) {
		if (title == null)
			throw new NullPointerException();
		return nLogiLcdColorSetTitle(title, red, green, blue);
	}

	private static native boolean nLogiLcdColorSetTitle(String title, int red, int green, int blue);

	/**
	 * Sets the text for the specified line as white text.
	 * 
	 * @param lineNumber
	 *            The line number. Color displays have 8 lines, so this value must range from 0 to 7. Characters are fixed-width and one line can hold 29 of
	 *            them.
	 * @param text
	 *            The text.
	 * @return If it was successful.
	 * @see #logiLcdColorSetText(int, String, int, int, int)
	 */
	public static boolean logiLcdColorSetText(int lineNumber, String text) {
		if (text == null)
			throw new NullPointerException();
		return logiLcdColorSetText(lineNumber, text, 255, 255, 255);
	}

	/**
	 * Sets the text for the specified line.
	 * 
	 * @param lineNumber
	 *            The line number. Color displays have 8 lines, so this value must range from 0 to 7. Characters are fixed-width and one line can hold 29 of
	 *            them.
	 * @param text
	 *            The text.
	 * @param red
	 *            Red color component.
	 * @param green
	 *            Green color component.
	 * @param blue
	 *            Blue color component.
	 * @return If it was successful.
	 */
	public static boolean logiLcdColorSetText(int lineNumber, String text, int red, int green, int blue) {
		return nLogiLcdColorSetText(lineNumber, text, red, green, blue);
	}

	private static native boolean nLogiLcdColorSetText(int lineNumber, String text, int red, int green, int blue);

	private JlawLcd() {}
}
