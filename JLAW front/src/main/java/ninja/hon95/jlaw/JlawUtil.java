package ninja.hon95.jlaw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * JLAW Utilities
 */
public final class JlawUtil {

    /**
     * Extracts and loads the appropriate version of the native JLAW dll file from the current folder.
     * The folder can be either inside or outside the Java file system/environment.
     * You are free to load the library yourself, this is only for convenience.
     * <br />
     * Note: This method uses the property "sun.arch.data.model" to determine if the JVM is 64-bit.
     * 
     * @param folderPath
     *            The folder containing 'jlaw.dll' for 32-bit and jlaw64.dll for 64-bit systems.
     *            For example "/natives/". Cannot be null.
     * @param referenceClass
     *            A class inside the Java file system/environment. If this is null, this method will look in the normal file system instead.
     * @return If the loading was successful.
     * @throws UnsatisfiedLinkError
     *             if the library cannot be loaded.
     * @throws IOException
     *             if there was a problem while extracting the library file from the jar file.
     * @throws FileNotFoundException
     *             if the file was not found either inside or outside the Java file system/environment.
     * @see
     *      {@link #loadNatives(String, Class<?>, boolean)}
     */
    public static void loadNatives(String folderPath, Class<?> referenceClass) throws UnsatisfiedLinkError, IOException, FileNotFoundException {
        loadNatives(folderPath, referenceClass, System.getProperty("sun.arch.data.model").equals("64"));
    }

    /**
     * Extracts and loads the appropriate version of the native JLAW dll file from the current folder.
     * The folder can be either inside or outside the Java file system/environment.
     * You are free to load the library yourself, this is only for convenience.
     * 
     * @param folderPath
     *            The folder containing 'jlaw.dll' for 32-bit and jlaw64.dll for 64-bit systems.
     *            For example "/natives/". Cannot be null.
     * @param referenceClass
     *            A class inside the Java file system/environment. If this is null, this method will look in the normal file system instead.
     * @param x86_64
     *            If the JVM is 64-bit.
     * @return If the loading was successful.
     * @throws UnsatisfiedLinkError
     *             if the library cannot be loaded.
     * @throws IOException
     *             if there was a problem while extracting the library file from the jar file.
     * @throws FileNotFoundException
     *             if the file was not found either inside or outside the Java file system/environment.
     * @see
     *      {@link #loadNatives(String, Class<?>)}
     */
    public static void loadNatives(String folderPath, Class<?> referenceClass, boolean x86_64)
            throws UnsatisfiedLinkError, IOException, FileNotFoundException {
        if (folderPath == null)
            throw new NullPointerException();

        String fileName = (x86_64 ? "jlaw64.dll" : "jlaw.dll");
        String filePath = (folderPath.endsWith("/") || folderPath.endsWith("\\") ? folderPath + fileName : folderPath + '/' + fileName);
        File tempFile = File.createTempFile(fileName.split("\\.", 2)[0], ".dll");
        tempFile.deleteOnExit();

        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            // Look in Java FS/Env first, in file system secondly
            if (referenceClass != null)
                inStream = referenceClass.getResourceAsStream(filePath);
            if (inStream == null)
                inStream = new FileInputStream(filePath);
            outStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0)
                outStream.write(buffer, 0, length);
        } finally {
            if (inStream != null)
                inStream.close();
            if (outStream != null)
                outStream.close();
        }

        System.load(tempFile.getAbsolutePath());
    }

    /**
     * Convert an array of ints to an array of "unsigned" bytes, where values from 128 to 255 will be mapped from -128 to -1.
     * 
     * @param intArray
     *            Array of ints to be converted.
     * @return Array of "unsigned" bytes.
     */
    public static byte[] toUBytesFromInts(int[] intArray) {
        if (intArray == null)
            throw new NullPointerException();
        byte[] byteArray = new byte[intArray.length];
        for (int i = 0; i < intArray.length; i++)
            byteArray[i] = (byte) (intArray[i] & 0xFF);
        return byteArray;
    }

    /**
     * Convert an array of "unsigned" bytes to an array of ints, where values from 128 to 255 are mapped from -128 to -1.
     * 
     * @param byteArray
     *            Array of "unsigned" bytes to be converted.
     * @return Array of ints.
     */
    public static int[] toIntsFromUBytes(byte[] byteArray) {
        if (byteArray == null)
            throw new NullPointerException();
        int[] intArray = new int[byteArray.length];
        for (int i = 0; i < byteArray.length; i++)
            intArray[i] = byteArray[i] & 0xFF;
        return intArray;
    }

    private JlawUtil() {}
}
