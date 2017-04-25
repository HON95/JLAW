package ninja.hon95.jlaw;


/**
 * JLAW Logitech Steering Wheel API
 */
public final class JlawSteeringWheel {

    public static final int LOGI_MAX_CONTROLLERS = 2;

    //Force types
    public static final int LOGI_FORCE_NONE = -1;
    public static final int LOGI_FORCE_SPRING = 0;
    public static final int LOGI_FORCE_CONSTANT = 1;
    public static final int LOGI_FORCE_DAMPER = 2;
    public static final int LOGI_FORCE_SIDE_COLLISION = 3;
    public static final int LOGI_FORCE_FRONTAL_COLLISION = 4;
    public static final int LOGI_FORCE_DIRT_ROAD = 5;
    public static final int LOGI_FORCE_BUMPY_ROAD = 6;
    public static final int LOGI_FORCE_SLIPPERY_ROAD = 7;
    public static final int LOGI_FORCE_SURFACE_EFFECT = 8;
    public static final int LOGI_NUMBER_FORCE_EFFECTS = 9;
    public static final int LOGI_FORCE_SOFTSTOP = 10;
    public static final int LOGI_FORCE_CAR_AIRBORNE = 11;

    // Periodic types  for surface effect
    public static final int LOGI_PERIODICTYPE_NONE = -1;
    public static final int LOGI_PERIODICTYPE_SINE = 0;
    public static final int LOGI_PERIODICTYPE_SQUARE = 1;
    public static final int LOGI_PERIODICTYPE_TRIANGLE = 2;

    // Devices types
    public static final int LOGI_DEVICE_TYPE_NONE = -1;
    public static final int LOGI_DEVICE_TYPE_WHEEL = 0;
    public static final int LOGI_DEVICE_TYPE_JOYSTICK = 1;
    public static final int LOGI_DEVICE_TYPE_GAMEPAD = 2;
    public static final int LOGI_DEVICE_TYPE_OTHER = 3;
    public static final int LOGI_NUMBER_DEVICE_TYPES = 4;

    // Manufacturer types
    public static final int LOGI_MANUFACTURER_NONE = -1;
    public static final int LOGI_MANUFACTURER_LOGITECH = 0;
    public static final int LOGI_MANUFACTURER_MICROSOFT = 1;
    public static final int LOGI_MANUFACTURER_OTHER = 2;

    // Model types
    public static final int LOGI_MODEL_G27 = 0;
    public static final int LOGI_MODEL_DRIVING_FORCE_GT = 1;
    public static final int LOGI_MODEL_G25 = 2;
    public static final int LOGI_MODEL_MOMO_RACING = 3;
    public static final int LOGI_MODEL_MOMO_FORCE = 4;
    public static final int LOGI_MODEL_DRIVING_FORCE_PRO = 5;
    public static final int LOGI_MODEL_DRIVING_FORCE = 6;
    public static final int LOGI_MODEL_NASCAR_RACING_WHEEL = 7;
    public static final int LOGI_MODEL_FORMULA_FORCE = 8;
    public static final int LOGI_MODEL_FORMULA_FORCE_GP = 9;
    public static final int LOGI_MODEL_FORCE_3D_PRO = 10;
    public static final int LOGI_MODEL_EXTREME_3D_PRO = 11;
    public static final int LOGI_MODEL_FREEDOM_24 = 12;
    public static final int LOGI_MODEL_ATTACK_3 = 13;
    public static final int LOGI_MODEL_FORCE_3D = 14;
    public static final int LOGI_MODEL_STRIKE_FORCE_3D = 15;
    public static final int LOGI_MODEL_G940_JOYSTICK = 16;
    public static final int LOGI_MODEL_G940_THROTTLE = 17;
    public static final int LOGI_MODEL_G940_PEDALS = 18;
    public static final int LOGI_MODEL_RUMBLEPAD = 19;
    public static final int LOGI_MODEL_RUMBLEPAD_2 = 20;
    public static final int LOGI_MODEL_CORDLESS_RUMBLEPAD_2 = 21;
    public static final int LOGI_MODEL_CORDLESS_GAMEPAD = 22;
    public static final int LOGI_MODEL_DUAL_ACTION_GAMEPAD = 23;
    public static final int LOGI_MODEL_PRECISION_GAMEPAD_2 = 24;
    public static final int LOGI_MODEL_CHILLSTREAM = 25;
    public static final int LOGI_MODEL_G29 = 26;
    public static final int LOGI_MODEL_G920 = 27;
    public static final int LOGI_NUMBER_MODELS = 28;

    private JlawSteeringWheel() {}

    /**
     * Call this function to initialize if you have already the window handle.
     * 
     * @param ignoreXInputControllers
     * @param hwnd
     * @return
     */
    public static boolean logiSteeringInitializeWithWindow(boolean ignoreXInputControllers, String windowTitle) {
        return nLogiSteeringInitializeWithWindow(ignoreXInputControllers, windowTitle);
    }

    private static native boolean nLogiSteeringInitializeWithWindow(boolean ignoreXInputControllers, String windowTitle);

    /**
     * Call this function before any other of the following.
     * 
     * @param ignoreXInputControllers
     * @return
     */
    public static boolean logiSteeringInitialize(boolean ignoreXInputControllers) {
        return nLogiSteeringInitialize(ignoreXInputControllers);
    }

    private static native boolean nLogiSteeringInitialize(boolean ignoreXInputControllers);

    /**
     * Returns the SDK version installed.
     * 
     * @return SDK version. Contains major, minor and build numbers separated by a period ("X.Y.Z").
     */
    public static String logiSteeringGetSdkVersion() {
        return nLogiSteeringGetSdkVersion();
    }

    private static native String nLogiSteeringGetSdkVersion();

    /**
     * Update the status of the controller.
     * 
     * @return
     */
    public static boolean logiUpdate() {
        return nLogiUpdate();
    }

    private static native boolean nLogiUpdate();

    /**
     * Get the state of the controller.
     * 
     * @param index
     * @return
     */
    public static DIJoystick2 logiGetState(int index) {
        return nLogiGetState(index);
    }

    private static native DIJoystick2 nLogiGetState(int index);

    /**
     * Get the computer specific operating system assigned controller GUID at a given index.
     * 
     * @param index
     * @return
     */
    public static String logiGetDevicePath(int index) {
        return nLogiGetDevicePath(index);
    }

    private static native String nLogiGetDevicePath(int index);

    /**
     * Get the friendly name of the product at index.
     * 
     * @param index
     * @return
     */
    public static String logiGetFriendlyProductName(int index) {
        return nLogiGetFriendlyProductName(index);
    }

    private static native String nLogiGetFriendlyProductName(int index);

    /**
     * Check if a generic device at index is connected.
     * 
     * @param index
     * @return
     */
    public static boolean logiIsConnected(int index) {
        return nLogiIsConnected(index);
    }

    private static native boolean nLogiIsConnected(int index);

    /**
     * Check if the device connected at index is of the same type specified by deviceType.
     * 
     * @param index
     * @param deviceType
     * @return
     */
    public static boolean logiIsDeviceConnected(int index, int deviceType) {
        return nLogiIsDeviceConnected(index, deviceType);
    }

    private static native boolean nLogiIsDeviceConnected(int index, int deviceType);

    /**
     * Check if the device connected at index is made from the manufacturer specified by manufacturerName.
     * 
     * @param index
     * @param manufacturerName
     * @return
     */
    public static boolean logiIsManufacturerConnected(int index, int manufacturerName) {
        return nLogiIsManufacturerConnected(index, manufacturerName);
    }

    private static native boolean nLogiIsManufacturerConnected(int index, int manufacturerName);

    /**
     * Check if the device connected at index is the model specified by modelName.
     * 
     * @param index
     * @param modelName
     * @return
     */
    public static boolean logiIsModelConnected(int index, int modelName) {
        return nLogiIsModelConnected(index, modelName);
    }

    private static native boolean nLogiIsModelConnected(int index, int modelName);

    /**
     * Check if the device connected at index is currently triggering the button specified by buttonNumber.
     * 
     * @param index
     * @param buttonNbr
     * @return
     */
    public static boolean logiButtonTriggered(int index, int buttonNumber) {
        return nLogiButtonTriggered(index, buttonNumber);
    }

    private static native boolean nLogiButtonTriggered(int index, int buttonNumber);

    /**
     * Check if on the device connected at index has been released the button specified by buttonNumber.
     * 
     * @param index
     * @param buttonNbr
     * @return
     */
    public static boolean logiButtonReleased(int index, int buttonNumber) {
        return nLogiButtonReleased(index, buttonNumber);
    }

    private static native boolean nLogiButtonReleased(int index, int buttonNumber);

    /**
     * Check if on the device connected at index is currently being pressed the button specified by buttonNumber.
     * 
     * @param index
     * @param buttonNumber
     * @return
     */
    public static boolean logiButtonIsPressed(int index, int buttonNumber) {
        return nLogiButtonIsPressed(index, buttonNumber);
    }

    private static native boolean nLogiButtonIsPressed(int index, int buttonNumber);

    /**
     * Generate non-linear values for the axis of the controller at index.
     * 
     * @param index
     * @param nonLinCoeff
     * @return
     */
    public static boolean logiGenerateNonLinearValues(int index, int nonLinCoeff) {
        return nLogiGenerateNonLinearValues(index, nonLinCoeff);
    }

    private static native boolean nLogiGenerateNonLinearValues(int index, int nonLinCoeff);

    /**
     * Get a non-linear value from a table previously generated.
     * 
     * @param index
     * @param inputValue
     * @return
     */
    public static int logiGetNonLinearValue(int index, int inputValue) {
        return nLogiGetNonLinearValue(index, inputValue);
    }

    private static native int nLogiGetNonLinearValue(int index, int inputValue);

    /**
     * Check if the controller at index has force feedback.
     * 
     * @param index
     * @return
     */
    public static boolean logiHasForceFeedback(int index) {
        return nLogiHasForceFeedback(index);
    }

    private static native boolean nLogiHasForceFeedback(int index);

    /**
     * Check if the controller at index is playing the force specified by forceType.
     * 
     * @param index
     * @param forceType
     * @return
     */
    public static boolean logiIsPlaying(int index, int forceType) {
        return nLogiIsPlaying(index, forceType);
    }

    private static native boolean nLogiIsPlaying(int index, int forceType);

    /**
     * Play the spring force on the controller at index with the specified parameters.
     * 
     * @param index
     * @param offsetPercentage
     * @param saturationPercentage
     * @param coefficientPercentage
     * @return
     */
    public static boolean logiPlaySpringForce(int index, int offsetPercentage, int saturationPercentage, int coefficientPercentage) {
        return nLogiPlaySpringForce(index, offsetPercentage, saturationPercentage, coefficientPercentage);
    }

    private static native boolean nLogiPlaySpringForce(int index, int offsetPercentage, int saturationPercentage, int coefficientPercentage);

    /**
     * Stop the spring force on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopSpringForce(int index) {
        return nLogiStopSpringForce(index);
    }

    private static native boolean nLogiStopSpringForce(int index);

    /**
     * Play the constant force on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlayConstantForce(int index, int magnitudePercentage) {
        return nLogiPlayConstantForce(index, magnitudePercentage);
    }

    private static native boolean nLogiPlayConstantForce(int index, int magnitudePercentage);

    /**
     * Stop the constant force on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopConstantForce(int index) {
        return nLogiStopConstantForce(index);
    }

    private static native boolean nLogiStopConstantForce(int index);

    /**
     * Play the damper force on the controller at index with the specified parameter.
     * 
     * @param index
     * @param coefficientPercentage
     * @return
     */
    public static boolean logiPlayDamperForce(int index, int coefficientPercentage) {
        return nLogiPlayDamperForce(index, coefficientPercentage);
    }

    private static native boolean nLogiPlayDamperForce(int index, int coefficientPercentage);

    /**
     * Stop the damper force on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopDamperForce(int index) {
        return nLogiStopDamperForce(index);
    }

    private static native boolean nLogiStopDamperForce(int index);

    /**
     * Play the side collision force on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlaySideCollisionForce(int index, int magnitudePercentage) {
        return nLogiPlaySideCollisionForce(index, magnitudePercentage);
    }

    private static native boolean nLogiPlaySideCollisionForce(int index, int magnitudePercentage);

    /**
     * Play the frontal collision force on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlayFrontalCollisionForce(int index, int magnitudePercentage) {
        return nLogiPlayFrontalCollisionForce(index, magnitudePercentage);
    }

    private static native boolean nLogiPlayFrontalCollisionForce(int index, int magnitudePercentage);

    /**
     * Play the dirt road effect on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlayDirtRoadEffect(int index, int magnitudePercentage) {
        return nLogiPlayDirtRoadEffect(index, magnitudePercentage);
    }

    private static native boolean nLogiPlayDirtRoadEffect(int index, int magnitudePercentage);

    /**
     * Stop the dirt road effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopDirtRoadEffect(int index) {
        return nLogiStopDirtRoadEffect(index);
    }

    private static native boolean nLogiStopDirtRoadEffect(int index);

    /**
     * Play the bumpy road effect on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlayBumpyRoadEffect(int index, int magnitudePercentage) {
        return nLogiPlayBumpyRoadEffect(index, magnitudePercentage);
    }

    private static native boolean nLogiPlayBumpyRoadEffect(int index, int magnitudePercentage);

    /**
     * Stop the bumpy road effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopBumpyRoadEffect(int index) {
        return nLogiStopBumpyRoadEffect(index);
    }

    private static native boolean nLogiStopBumpyRoadEffect(int index);

    /**
     * Play the slippery road effect on the controller at index with the specified parameter.
     * 
     * @param index
     * @param magnitudePercentage
     * @return
     */
    public static boolean logiPlaySlipperyRoadEffect(int index, int magnitudePercentage) {
        return nLogiPlaySlipperyRoadEffect(index, magnitudePercentage);
    }

    private static native boolean nLogiPlaySlipperyRoadEffect(int index, int magnitudePercentage);

    /**
     * Stop the slippery road effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopSlipperyRoadEffect(int index) {
        return nLogiStopSlipperyRoadEffect(index);
    }

    private static native boolean nLogiStopSlipperyRoadEffect(int index);

    /**
     * Play the surface effect on the controller at index with the specified parameter.
     * 
     * @param index
     * @param type
     * @param magnitudePercentage
     * @param period
     * @return
     */
    public static boolean logiPlaySurfaceEffect(int index, int type, int magnitudePercentage, int period) {
        return nLogiPlaySurfaceEffect(index, type, magnitudePercentage, period);
    }

    private static native boolean nLogiPlaySurfaceEffect(int index, int type, int magnitudePercentage, int period);

    /**
     * Stop the surface effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopSurfaceEffect(int index) {
        return nLogiStopSurfaceEffect(index);
    }

    private static native boolean nLogiStopSurfaceEffect(int index);

    /**
     * Play the car airborne effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiPlayCarAirborne(int index) {
        return nLogiPlayCarAirborne(index);
    }

    private static native boolean nLogiPlayCarAirborne(int index);

    /**
     * Stop the car airborne effect on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopCarAirborne(int index) {
        return nLogiStopCarAirborne(index);
    }

    private static native boolean nLogiStopCarAirborne(int index);

    /**
     * Play the soft stop force on the controller at index with the specified parameter.
     * 
     * @param index
     * @param usableRangePercentage
     * @return
     */
    public static boolean logiPlaySoftstopForce(int index, int usableRangePercentage) {
        return nLogiPlaySoftstopForce(index, usableRangePercentage);
    }

    private static native boolean nLogiPlaySoftstopForce(int index, int usableRangePercentage);

    /**
     * Stop the soft stop force on the controller at index.
     * 
     * @param index
     * @return
     */
    public static boolean logiStopSoftstopForce(int index) {
        return nLogiStopSoftstopForce(index);
    }

    private static native boolean nLogiStopSoftstopForce(int index);

    /**
     * Set preferred wheel properties specified by the struct properties.
     * 
     * @param properties
     * @return
     */
    public static boolean logiSetPreferredControllerProperties(LogiControllerPropertiesData properties) {
        return nLogiSetPreferredControllerProperties(properties);
    }

    private static native boolean nLogiSetPreferredControllerProperties(LogiControllerPropertiesData properties);

    /**
     * Fills the properties parameter with the current controller properties.
     * 
     * @param index
     * @return
     */
    public static LogiControllerPropertiesData logiGetCurrentControllerProperties(int index) {
        return nLogiGetCurrentControllerProperties(index);
    }

    private static native LogiControllerPropertiesData nLogiGetCurrentControllerProperties(int index);

    /**
     * Get current shifter mode (gated or sequential).
     * 
     * @param index
     * @return
     */
    public static int logiGetShifterMode(int index) {
        return nLogiGetShifterMode(index);
    }

    private static native int nLogiGetShifterMode(int index);

    /**
     * Sets the operating range in degrees on the controller at the index.
     * 
     * @param index
     * @param range
     * @return
     */
    public static boolean logiSetOperatingRange(int index, int range) {
        return nLogiSetOperatingRange(index, range);
    }

    private static native boolean nLogiSetOperatingRange(int index, int range);

    /**
     * Gets the current operating range in degrees on the controller at the index.
     * 
     * @param index
     * @return
     */
    public static int logiGetOperatingRange(int index) {
        return nLogiGetOperatingRange(index);
    }

    private static native int nLogiGetOperatingRange(int index);

    /**
     * Play the leds on the controller at index applying the specified parameters.
     * 
     * @param index
     * @param currentRPM
     * @param rpmFirstLedTurnsOn
     * @param rpmRedLine
     * @return
     */
    public static boolean logiPlayLeds(int index, float currentRPM, float rpmFirstLedTurnsOn, float rpmRedLine) {
        return nLogiPlayLeds(index, currentRPM, rpmFirstLedTurnsOn, rpmRedLine);
    }

    private static native boolean nLogiPlayLeds(int index, float currentRPM, float rpmFirstLedTurnsOn, float rpmRedLine);

    /**
     * Call this function to shutdown the SDK and destroy the controller and wheel objects.
     */
    public static void logiSteeringShutdown() {
        nLogiSteeringShutdown();
    }

    private static native void nLogiSteeringShutdown();

    /**
     * Container for wheel properties.
     */
    public static final class LogiControllerPropertiesData {

        public boolean forceEnable;
        public int overallGain;
        public int springGain;
        public int damperGain;
        public boolean defaultSpringEnabled;
        public int defaultSpringGain;
        public boolean combinePedals;
        public int wheelRange;
        public boolean gameSettingsEnabled;
        public boolean allowGameSettings;
    };

    /**
     * Container for the device's position, rotation, POV, velocity, acceleration, force, torque and buttons.
     */
    public static final class DIJoystick2 {

        public int positionX;
        public int positionY;
        public int positionZ;
        public int rotationX;
        public int rotationY;
        public int rotationZ;
        public int[] positionExtras;
        public int[] povDirections;
        public boolean[] buttonStates;
        public int velocityX;
        public int velocityY;
        public int velocityZ;
        public int angularVelocityX;
        public int angularVelocityY;
        public int angularVelocityZ;
        public int[] velocityExtras;
        public int accelerationX;
        public int accelerationY;
        public int accelerationZ;
        public int angularAccelerationX;
        public int angularAccelerationY;
        public int angularAccelerationZ;
        public int[] accelerationExtras;
        public int forceX;
        public int forceY;
        public int forceZ;
        public int torqueX;
        public int torqueY;
        public int torqueZ;
        public int[] forceExtras;
    }
}
