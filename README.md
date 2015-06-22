# JLAW
Java Logitech API Wrapper

JLAW is a wrapper for the four Logitech SDKs (Logitech ArxControl SDK, Logitech Gkey SDK, Logitech LCD SDK and Logitech LED SDK) for Java. It provides four classes plus one utility class which provides as direct as possible access to the SDKs, with some adjustments. JLAW uses Logitech Gaming Software (LGS).

## Download
See the [releases](https://github.com/HON95/JLAW/releases) page.

## Usage
### Getting JLAW
The library consists of a Java front-end and a JNI/C++ back-end. Both of the components can be build from source or downloaded as pre-built binaries, in any of the four combinations. If you're using Maven for your project, you should download and install the front-end source and use it as a Maven dependency. The back-end binaries are easiest to just download pre-built.

#### Building JLAW Front-End from Source
Open the "JLAW front" directory and run Maven there (no arguments needed). This will build and install the project to you local repository. If you wish, you can open the directory as an Eclipse Java M2E project by creating a new Eclipse Maven project in that directory.

#### Building JLAW Back-End from Source
Download the Logitech SDKs and unzip them into the lib directory (must be named "Logitech ArxControl SDK", "Logitech Gkey SDK", "Logitech LCD SDK" and "Logitech LED SDK"). The header file for the LED SDK is most likely missing a very important line, so add "#include <Windows.h>" to the top of it if the project won't compile. Copy the JNI headers from a Java SDK (jni.h and jni_md.h) to a directory named "JNI" inside lib. Open the "JLAW back" directory and open the Visual Studio project (VS 2013 or other compatible) by opening the .sln file. Build with CTRL + SHIFT + B and check for errors. The x64 DLL will be placed in x64/Release and the win32 DLL will be placed in Release, remember to build using both win32 and x64.

#### Building the JLAW Test from Source
Open the "JLAW test" directory. Copy the JLAW back-end DLLs to "src/main/resources/natives" as "jlaw.dll" and "jlaw64.dll". Run Maven (no arguments needed). This will build the project and create an EXE program.

### Using JLAW
First you need to add the front-end as a dependency and load the native back-end. I suggest packaging both ends in your JAR or EXE. The front-end can be packaged using the Maven shader plugin and the back-end natives can be added as resources. The JlawUtil class contains the static method "loadNatives" for a convenient way to load the natives. The four classes JlawArxControl, JlawGkey, JlawLed and JlawLcd contains static methods which resembles the ones from the SDKs. All four SDKs require to be initialized before use and shutdown after. For inspiration, see the "JLAW test" sample project. The JLAW JavaDoc contains documentation about all the functions and callbacks. See the Logitech SDK documentation (included in the SDKs) for more detailed information.

## Contents of Repository
- JLAW back: The C++ JNI back-end (Visual Studio 2013 project)
- JLAW front: The Java front-end (Eclipse Juno, Maven, targeted at Java 1.6)
- JLAW test: A sample project using JLAW (Eclipse Juno, Maven, Java 1.6)
- lib: Where to place the Logitech SDKs and JNI headers (jni.h and jni_md.h inside folder JNI) (optional)
- bin: Where extract_dlls.bat copies the built JLAW DLLs to
- LICENSE.txt: License for the project (MIT License)
- make_headers.bat: A simple batch file to build the JLAW headers and place them directly in the back-end project
- extract_dlls.bat: A simple batch file to move the JLAW DLLs to the bin folder and rename them

## FAQ
Why does LGS use the Java icon for my applet or profile?
> I don't know. Maybe invoking the Java VM from another program (C++ and JNI, for example) will do the trick.

How do I remove all the messy LCD applets in LGSs applet list?
> You have to edit the LGS settings file. Close LGS first. Open the JSON file "~\AppData\Local\Logitech\Logitech Gaming Software\settings.json" and remove the applets you want gone from the lcd/applets section. Try not to break the syntax, obviously. Make a backup to be sure you don't need to reconfigure LGS if you mess up.

Questions related to the Logitech SDKs and usage of them:
> Read through the Logitech SDK documents found in the downloaded SDKs. Ask Logitech if you're still confused.

## Legal
JLAW is licensed under the [MIT License](http://choosealicense.com/licenses/mit/).
JLAW is not is not affiliated with Logitech or Java.
Logitech is a registered trademark of Logitech.
Java is a registered trademark of Oracle.
