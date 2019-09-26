package ninja.hon95.jlaw;

import ninja.hon95.jlaw.JlawUtil;

/**
 * JLAW Arx Control API
 */
public final class JlawArxControl {

	// Events //
	public static final int LOGI_ARX_EVENT_FOCUS_ACTIVE = 0x01;
	public static final int LOGI_ARX_EVENT_FOCUS_INACTIVE = 0x02;
	public static final int LOGI_ARX_EVENT_TAP_ON_TAG = 0x04;
	public static final int LOGI_ARX_EVENT_MOBILEDEVICE_ARRIVAL = 0x08;
	public static final int LOGI_ARX_EVENT_MOBILEDEVICE_REMOVAL = 0x10;

	// Device Types //
	public static final int LOGI_ARX_DEVICETYPE_IPHONE = 0x01;
	public static final int LOGI_ARX_DEVICETYPE_IPAD = 0x02;
	public static final int LOGI_ARX_DEVICETYPE_ANDROID_SMALL = 0x03;
	public static final int LOGI_ARX_DEVICETYPE_ANDROID_NORMAL = 0x04;
	public static final int LOGI_ARX_DEVICETYPE_ANDROID_LARGE = 0x05;
	public static final int LOGI_ARX_DEVICETYPE_ANDROID_XLARGE = 0x06;
	public static final int LOGI_ARX_DEVICETYPE_ANDROID_OTHER = 0x07;

	// Error Text Descriptions //
	private static final String[] ERROR_TEXT = {
			"Success",
			"Wrong parameter format",
			"Null parameter not supported",
			"Wrong file path",
			"SDK not initialized",
			"SDK already initialized",
			"Connection with Logitech Gaming Software broken",
			"Error creating thread",
			"Error copying memory"
	};

	/**
	 * Initializes the Arx Control system.
	 * 
	 * @param identifier
	 *            A unique identifier for your applet. Capped to 128 characters.
	 * @param friendlyName
	 *            A friendly name for your applet.
	 * @param callback
	 *            Callback for events from the applet.
	 * @return If the initialization was successful.
	 */
	public static boolean logiArxInit(String identifier, String friendlyName, JlawArxControlCallback callback) {
		if (identifier == null || friendlyName == null)
			throw new NullPointerException();
		return nLogiArxInit(identifier, friendlyName, callback);
	}

	private static native boolean nLogiArxInit(String identifier, String friendlyName, JlawArxControlCallback callback);

	/**
	 * Sends a file to Arx Control.
	 * 
	 * @param filePath
	 *            Path to file on local file system.
	 * @param fileName
	 *            ID to reference from the applet.
	 * @return If it was successful.
	 */
	public static boolean logiArxAddFileAs(String filePath, String fileName) {
		if (filePath == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddFileAs(filePath, fileName, "");
	}

	/**
	 * Sends a file to Arx Control.
	 * 
	 * @param filePath
	 *            Path to file on local file system.
	 * @param fileName
	 *            ID to reference from the applet. Capped to 256 characters.
	 * @param mimeType
	 *            MIME type of the file content.
	 * @return If it was successful.
	 */
	public static boolean logiArxAddFileAs(String filePath, String fileName, String mimeType) {
		if (filePath == null || fileName == null || mimeType == null)
			throw new NullPointerException();
		return nLogiArxAddFileAs(filePath, fileName, mimeType);
	}

	private static native boolean nLogiArxAddFileAs(String filePath, String fileName, String mimeType);

	/**
	 * Sends a block of bytes to Arx Control.
	 * 
	 * @param intContent
	 *            Array of data to send, in the form of ints. Each int should only hold only <b>one byte</b> of data.
	 * @param fileName
	 *            ID to reference from the applet. Capped to 256 characters.
	 * @return If it was successful.
	 * @see #logiArxAddFileAs(byte[], String)
	 */
	public static boolean logiArxAddContentAs(int[] intContent, String fileName) {
		if (intContent == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddContentAs(JlawUtil.toUBytesFromInts(intContent), fileName, "");
	}

	/**
	 * Sends a block of bytes to Arx Control.<br>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param content
	 *            Array of data to send, in the form of "unsigned" bytes.
	 * @param fileName
	 *            ID to reference from the applet. Capped to 256 characters.
	 * @return If it was successful.
	 * @see #logiArxAddFileAs(int[], String)
	 */
	public static boolean logiArxAddContentAs(byte[] content, String fileName) {
		if (content == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddContentAs(content, fileName, "");
	}

	/**
	 * Sends a block of bytes to Arx Control.
	 * 
	 * @param intContent
	 *            Array of data to send, in the form of ints. Each int should only hold <b>one byte</b> of data.
	 * @param fileName
	 *            ID to reference from the applet. Capped to 256 characters.
	 * @param mimeType
	 *            MIME type of content.
	 * @return If it was successful.
	 * @see #logiArxAddFileAs(byte[], String, String)
	 */
	public static boolean logiArxAddContentAs(int[] intContent, String fileName, String mimeType) {
		if (intContent == null || fileName == null || mimeType == null)
			throw new NullPointerException();
		return nLogiArxAddContentAs(JlawUtil.toUBytesFromInts(intContent), fileName, mimeType);
	}

	/**
	 * Sends a block of bytes to Arx Control.<br>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param content
	 *            Array of data to send, in the form of "unsigned" bytes.
	 * @param fileName
	 *            ID to reference from the applet. Capped to 256 characters.
	 * @param mimeType
	 *            MIME type of content.
	 * @return If it was successful.
	 * @see #logiArxAddFileAs(int[], String, String)
	 */
	public static boolean logiArxAddContentAs(byte[] content, String fileName, String mimeType) {
		if (content == null || fileName == null || mimeType == null)
			throw new NullPointerException();
		return nLogiArxAddContentAs(content, fileName, mimeType);
	}

	private static native boolean nLogiArxAddContentAs(byte[] content, String fileName, String mimeType);

	/**
	 * Saves a UTF-8 encoded string in a file and sends it to Arx Control.
	 * 
	 * @param stringContent
	 *            UTF-8 encoded string.
	 * @param fileName
	 *            ID to reference from applet.
	 * @return If it was successful.
	 */
	public static boolean logiArxAddUTF8StringAs(String stringContent, String fileName) {
		if (stringContent == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddUTF8StringAs(stringContent, fileName, "");
	}

	/**
	 * Saves a UTF-8 encoded string in a file and sends it to Arx Control.
	 * 
	 * @param stringContent
	 *            UTF-8 encoded string.
	 * @param fileName
	 *            ID to reference from applet.
	 * @param mimeType
	 *            MIME type of content
	 * @return If it was successful.
	 */
	public static boolean logiArxAddUTF8StringAs(String stringContent, String fileName, String mimeType) {
		if (stringContent == null || fileName == null || mimeType == null)
			throw new NullPointerException();
		return nLogiArxAddUTF8StringAs(stringContent, fileName, mimeType);
	}

	private static native boolean nLogiArxAddUTF8StringAs(String stringContent, String fileName, String mimeType);

	/**
	 * Compresses and saves an image to a PNG file and sends it to Arx Control.
	 * 
	 * @param intBitmap
	 *            A byte array containing the image. Each int should contain only <b>one byte</b>. BGRA-encoded.
	 * @param width
	 *            Width of image.
	 * @param height
	 *            Height of image.
	 * @param fileName
	 *            ID to reference from the applet.
	 * @return If it was successful.
	 * @see #logiArxAddImageFromBitmap(byte[], int, int, String)
	 */
	public static boolean logiArxAddImageFromBitmap(int[] intBitmap, int width, int height, String fileName) {
		if (intBitmap == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddImageFromBitmap(JlawUtil.toUBytesFromInts(intBitmap), width, height, fileName);
	}

	/**
	 * Compresses and saves an image to a PNG file and sends it to Arx Control.<br>
	 * Note: Since bytes in Java are signed, values between 128 and 255 are written from -128 to -1.
	 * 
	 * @param bitmap
	 *            A byte array containing the image. BGRA-encoded.
	 * @param width
	 *            Width of image.
	 * @param height
	 *            Height of image.
	 * @param fileName
	 *            ID to reference from the applet.
	 * @return If it was successful.
	 * @see #logiArxAddImageFromBitmap(byte[], int, int, String)
	 */
	public static boolean logiArxAddImageFromBitmap(byte[] bitmap, int width, int height, String fileName) {
		if (bitmap == null || fileName == null)
			throw new NullPointerException();
		return nLogiArxAddImageFromBitmap(bitmap, width, height, fileName);
	}

	private static native boolean nLogiArxAddImageFromBitmap(byte[] bitmap, int width, int height, String fileName);

	/**
	 * Sets which page is to be displayed on Arx Control.
	 * 
	 * @param fileName
	 *            Reference to file to display.
	 * @return If it was successful.
	 */
	public static boolean logiArxSetIndex(String fileName) {
		if (fileName == null)
			throw new NullPointerException();
		return nLogiArxSetIndex(fileName);
	}

	private static native boolean nLogiArxSetIndex(String fileName);

	/**
	 * Updates a tag property in the applet HTML pages.
	 * 
	 * @param tagId
	 *            Tag. Capped to 128 characters.
	 * @param prop
	 *            Tag property. Capped to 128 characters.
	 * @param newValue
	 *            New value for property on tag. Capped to 128 characters.
	 * @return If it was successful.
	 */
	public static boolean logiArxSetTagPropertyById(String tagId, String prop, String newValue) {
		if (tagId == null || prop == null || newValue == null)
			throw new NullPointerException();
		return nLogiArxSetTagPropertyById(tagId, prop, newValue);
	}

	private static native boolean nLogiArxSetTagPropertyById(String tagId, String prop, String newValue);

	/**
	 * Updates properties on a class of tags in the applet HTML pages.
	 * 
	 * @param tagsClass
	 *            Tag class. Capped to 128 characters.
	 * @param prop
	 *            Tag property. Capped to 128 characters.
	 * @param newValue
	 *            New value for tag properties in class. Capped to 128 characters.
	 * @return If it was successful.
	 */
	public static boolean logiArxSetTagsPropertyByClass(String tagsClass, String prop, String newValue) {
		if (tagsClass == null || prop == null || newValue == null)
			throw new NullPointerException();
		return nLogiArxSetTagsPropertyByClass(tagsClass, prop, newValue);
	}

	private static native boolean nLogiArxSetTagsPropertyByClass(String tagsClass, String prop, String newValue);

	/**
	 * Updates the content of a tag in the applet HTML pages.
	 * 
	 * @param tagId
	 *            Tag. Capped to 128 characters.
	 * @param newContent
	 *            New tag content. Capped to 128 characters.
	 * @return If it was successful.
	 */
	public static boolean logiArxSetTagContentById(String tagId, String newContent) {
		if (tagId == null || newContent == null)
			throw new NullPointerException();
		return nLogiArxSetTagContentById(tagId, newContent);
	}

	private static native boolean nLogiArxSetTagContentById(String tagId, String newContent);

	/**
	 * Updates the content of a class of tags in the applet HTML pages.
	 * 
	 * @param tagsClass
	 *            Tag class. Capped to 128 characters.
	 * @param newContent
	 *            New content of tags in class. Capped to 128 characters.
	 * @return
	 */
	public static boolean logiArxSetTagsContentByClass(String tagsClass, String newContent) {
		if (tagsClass == null || newContent == null)
			throw new NullPointerException();
		return nLogiArxSetTagsContentByClass(tagsClass, newContent);
	}

	private static native boolean nLogiArxSetTagsContentByClass(String tagsClass, String newContent);

	/**
	 * Returns the last error from SDK function calls.
	 * 
	 * @return Error code.
	 * @see #logiArxGetErrorText
	 */
	public static int logiArxGetLastError() {
		return nLogiArxGetLastError();
	}

	private static native int nLogiArxGetLastError();

	/**
	 * Shut down the Arx Control system.
	 */
	public static void logiArxShutdown() {
		nLogiArxShutdown();
	}

	private static native void nLogiArxShutdown();

	/**
	 * Returns text for the common error codes.
	 * 
	 * @param errorCode
	 *            Error code to get text for.
	 * @return Text of error.
	 * @see logiArxGetLastError
	 */
	public static String logiArxGetErrorText(int errorCode) {
		if (errorCode >= 0 && errorCode < ERROR_TEXT.length)
			return ERROR_TEXT[errorCode];
		return null;
	}

	private JlawArxControl() {}

	/**
	 * Callback for Arx events. Needs to be thread safe.
	 */
	public interface JlawArxControlCallback {

		/**
		 * Called when Arx events happen.
		 * 
		 * @param eventType
		 *            Type of event.<br>
		 *            Possible values:
		 *            <ul>
		 *            <li>LOGI_ARX_EVENT_FOCUS_ACTIVE</li>
		 *            <li>LOGI_ARX_EVENT_FOCUS_INACTIVE</li>
		 *            <li>LOGI_ARX_EVENT_TAP_ON_TAG</li>
		 *            <li>LOGI_ARX_EVENT_MOBILEDEVICE_ARRIVAL</li>
		 *            <li>LOGI_ARX_EVENT_MOBILEDEVICE_REMOVAL</li>
		 *            </ul>
		 * @param eventValue
		 *            Value of event.
		 * @param eventArg
		 *            Additional argument of event.
		 */
		public void call(int eventType, int eventValue, String eventArg);
	}
}
