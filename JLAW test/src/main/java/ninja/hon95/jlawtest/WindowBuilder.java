package ninja.hon95.jlawtest;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.java.dev.designgridlayout.DesignGridLayout;

public final class WindowBuilder {

	private static final String ABOUT = "JLAW Test by HON95";
	private static final SimpleAttributeSet FONT_RED = new SimpleAttributeSet();
	{
		StyleConstants.setForeground(FONT_RED, Color.RED);
	}

	private static final String AC_ARX_TEST = "arx_test";
	private static final String AC_GKEY_TEST = "gkey_test";
	private static final String AC_LCD_TEST = "lcd_test";
	private static final String AC_LED_TEST = "led_test";
	private static final String AC_ARX_STOP = "arx_stop";
	private static final String AC_GKEY_STOP = "gkey_stop";
	private static final String AC_LCD_STOP = "lcd_stop";
	private static final String AC_LED_STOP = "led_stop";

	private static JFrame frame = null;
	private static JTextPane logPane;
	private static StatusWorker statusWorker;
	private static LogMessageWorker logWorker;

	private static JLabel nativeLibStatus;
	private static JLabel arxStatus;
	private static JLabel gkeyStatus;
	private static JLabel lcdStatus;
	private static JLabel ledStatus;
	private static JButton arxTest;
	private static JButton gkeyTest;
	private static JButton lcdTest;
	private static JButton ledTest;
	private static JButton arxStop;
	private static JButton gkeyStop;
	private static JButton lcdStop;
	private static JButton ledStop;

	public static void buildWindow() {
		if (frame != null)
			return;
		frame = new JFrame("JLAW Test");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setIconImages(loadIcons(Main.WINDOW_ICON_PATHS));
		frame.addWindowListener(buildWindowListener());

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		frame.getContentPane().add(splitPane);
		splitPane.setDividerLocation(Main.WINDOW_WIDTH / 2);

		JTabbedPane tabPane = new JTabbedPane();
		splitPane.add(tabPane);
		ActionListener actionListener = new TestActionListener();

		buildGeneralTab(tabPane);
		buildArxTab(tabPane, actionListener);
		buildGkeyTab(tabPane, actionListener);
		buildLcdTab(tabPane, actionListener);
		buildLedTab(tabPane, actionListener);

		logPane = new JTextPane();
		logPane.setEditable(false);
		splitPane.add(new JScrollPane(logPane));

		frame.setVisible(true);
		statusWorker = new StatusWorker();
		statusWorker.execute();
		logWorker = new LogMessageWorker(Main.getLogQueue());
		logWorker.execute();
	}

	private static List<? extends Image> loadIcons(String[] paths) {
		LinkedList<Image> icons = new LinkedList<Image>();
		for (String path : paths) {
			URL url = Main.class.getResource(path);
			if (url != null)
				icons.add(new ImageIcon(url).getImage());
			else
				Main.logError("[WINDOW] Icon image not found: " + path);
		}
		return icons;
	}

	private static WindowListener buildWindowListener() {
		return new WindowListener() {

			public void windowOpened(WindowEvent ev) {}

			public void windowIconified(WindowEvent ev) {}

			public void windowDeiconified(WindowEvent ev) {}

			public void windowDeactivated(WindowEvent ev) {}

			public void windowClosing(WindowEvent ev) {
				Main.stop();
			}

			public void windowClosed(WindowEvent ev) {}

			public void windowActivated(WindowEvent ev) {}
		};
	}

	private static void buildGeneralTab(JTabbedPane tabPane) {
		DesignGridLayout tabGeneral = newTab(tabPane, "General");
		tabGeneral.row().grid(new JLabel("About")).add(new JLabel(ABOUT));
		nativeLibStatus = new JLabel("Loading...");
		tabGeneral.row().grid(new JLabel("Native")).add(nativeLibStatus);
	}

	private static void buildArxTab(JTabbedPane tabPane, ActionListener actionListener) {
		DesignGridLayout tabArx = newTab(tabPane, "Arx Control");
		tabArx.row().grid(new JLabel("About")).add(new JLabel("Prints Arx Control events."));
		tabArx.row().grid(new JLabel("Note")).add(new JLabel("This sample does not create a Arx Control applet."));
		arxStatus = new JLabel(Tester.getArxStatus());
		tabArx.row().grid(new JLabel("Status")).add(arxStatus);
		arxTest = new JButton("Test");
		arxTest.addActionListener(actionListener);
		arxTest.setActionCommand(AC_ARX_TEST);
		arxTest.setEnabled(false);
		tabArx.row().grid().add(arxTest);
		arxStop = new JButton("Stop");
		arxStop.addActionListener(actionListener);
		arxStop.setActionCommand(AC_ARX_STOP);
		arxStop.setEnabled(false);
		tabArx.row().grid().add(arxStop);
	}

	private static void buildGkeyTab(JTabbedPane tabPane, ActionListener actionListener) {
		DesignGridLayout tabGkey = newTab(tabPane, "G-Key");
		tabGkey.row().grid(new JLabel("About")).add(new JLabel("Prints G-key events."));
		tabGkey.row().grid(new JLabel("Note")).add(new JLabel("<html>The test profile will be activated<br>every time this receives focus.</html>"));
		tabGkey.row().grid().add(new JLabel("You have to manually remove the profile from LGS."));
		gkeyStatus = new JLabel(Tester.getGkeyStatus());
		tabGkey.row().grid(new JLabel("Status")).add(gkeyStatus);
		gkeyTest = new JButton("Test");
		gkeyTest.addActionListener(actionListener);
		gkeyTest.setActionCommand(AC_GKEY_TEST);
		gkeyTest.setEnabled(false);
		tabGkey.row().grid().add(gkeyTest);
		gkeyStop = new JButton("Stop");
		gkeyStop.addActionListener(actionListener);
		gkeyStop.setActionCommand(AC_GKEY_STOP);
		gkeyStop.setEnabled(false);
		tabGkey.row().grid().add(gkeyStop);
	}

	private static void buildLcdTab(JTabbedPane tabPane, ActionListener actionListener) {
		DesignGridLayout tabLcd = newTab(tabPane, "LCD");
		tabLcd.row().grid(new JLabel("About")).add(new JLabel("Displays an image on connected displays."));
		tabLcd.row().grid(new JLabel("Note")).add(new JLabel("This sample was only tested with G19."));
		lcdStatus = new JLabel(Tester.getLcdStatus());
		tabLcd.row().grid(new JLabel("Status")).add(lcdStatus);
		lcdTest = new JButton("Test");
		lcdTest.addActionListener(actionListener);
		lcdTest.setActionCommand(AC_LCD_TEST);
		lcdTest.setEnabled(false);
		tabLcd.row().grid().add(lcdTest);
		lcdStop = new JButton("Stop");
		lcdStop.addActionListener(actionListener);
		lcdStop.setActionCommand(AC_LCD_STOP);
		lcdStop.setEnabled(false);
		tabLcd.row().grid().add(lcdStop);
	}

	private static void buildLedTab(JTabbedPane tabPane, ActionListener actionListener) {
		DesignGridLayout tabLed = newTab(tabPane, "LED");
		tabLed.row().grid(new JLabel("About")).add(new JLabel("Flashes your keyboard. Didn't work for me, though."));
		tabLed.row().grid(new JLabel("Note")).add(new JLabel("<html>Lighting for this sample was only tested with G710+.<br>It might contain bugs.</html>"));
		ledStatus = new JLabel(Tester.getLedStatus());
		tabLed.row().grid(new JLabel("Status")).add(ledStatus);
		ledTest = new JButton("Test");
		ledTest.addActionListener(actionListener);
		ledTest.setActionCommand(AC_LED_TEST);
		ledTest.setEnabled(false);
		tabLed.row().grid().add(ledTest);
		ledStop = new JButton("Stop");
		ledStop.addActionListener(actionListener);
		ledStop.setActionCommand(AC_LED_STOP);
		ledStop.setEnabled(false);
		tabLed.row().grid().add(ledStop);
	}

	private static DesignGridLayout newTab(JTabbedPane tabbedPane, String tabName) {
		JPanel tab = new JPanel();
		DesignGridLayout grid = new DesignGridLayout(tab);
		tabbedPane.addTab(tabName, new JScrollPane(tab));
		return grid;
	}

	public static void destroyWindow() {
		statusWorker.cancel(true);
		logWorker.cancel(true);
		frame.setVisible(false);
		frame.dispose();
		frame = null;
	}

	public static void setNativeLibStatus(final boolean linked) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (linked) {
					nativeLibStatus.setText("Linked");
					arxTest.setEnabled(true);
					gkeyTest.setEnabled(true);
					lcdTest.setEnabled(true);
					ledTest.setEnabled(true);
				} else {
					nativeLibStatus.setText("ERROR");
				}
			}
		});
	}

	private static class TestActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			String ac = ev.getActionCommand();
			Runnable runnable = null;
			if (ac.equals(AC_ARX_TEST)) {
				arxTest.setEnabled(false);
				arxStop.setEnabled(true);
				runnable = new Runnable() {
					public void run() {
						if (!Tester.isArxActive())
							Tester.testArx();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								arxTest.setEnabled(true);
								arxStop.setEnabled(false);
							}
						});
					}
				};
			} else if (ac.equals(AC_GKEY_TEST)) {
				gkeyTest.setEnabled(false);
				gkeyStop.setEnabled(true);
				runnable = new Runnable() {
					public void run() {
						if (!Tester.isGkeyActive())
							Tester.testGkey();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								gkeyTest.setEnabled(true);
								gkeyStop.setEnabled(false);
							}
						});
					}
				};
			} else if (ac.equals(AC_LCD_TEST)) {
				lcdTest.setEnabled(false);
				lcdStop.setEnabled(true);
				runnable = new Runnable() {
					public void run() {
						if (!Tester.isLcdActive())
							Tester.testLcd();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								lcdTest.setEnabled(true);
								lcdStop.setEnabled(false);
							}
						});
					}
				};
			} else if (ac.equals(AC_LED_TEST)) {
				ledTest.setEnabled(false);
				ledStop.setEnabled(true);
				runnable = new Runnable() {
					public void run() {
						if (!Tester.isLedActive())
							Tester.testLed();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								ledTest.setEnabled(true);
								ledStop.setEnabled(false);
							}
						});
					}
				};
			} else if (ac.equals(AC_ARX_STOP)) {
				Tester.stopArx();
			} else if (ac.equals(AC_GKEY_STOP)) {
				Tester.stopGkey();
			} else if (ac.equals(AC_LCD_STOP)) {
				Tester.stopLcd();
			} else if (ac.equals(AC_LED_STOP)) {
				Tester.stopLed();
			} else {
				Main.log("[WINDOW] Unknown action command: " + ac);
			}

			if (runnable != null) {
				Thread thread = new Thread(runnable);
				thread.setDaemon(false);
				thread.start();
			}
		}
	}

	private static class StatusWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				Thread.sleep(50);
				publish((Void) null);
			}
		}

		@Override
		protected void process(List<Void> chunks) {
			if (!arxStatus.getText().equals(Tester.getArxStatus()))
				arxStatus.setText(Tester.getArxStatus());
			if (!gkeyStatus.getText().equals(Tester.getGkeyStatus()))
				gkeyStatus.setText(Tester.getGkeyStatus());
			if (!lcdStatus.getText().equals(Tester.getLcdStatus()))
				lcdStatus.setText(Tester.getLcdStatus());
			if (!ledStatus.getText().equals(Tester.getLedStatus()))
				ledStatus.setText(Tester.getLedStatus());
		}
	}

	private static class LogMessageWorker extends SwingWorker<Void, LogMessage> {

		private final BlockingQueue<LogMessage> logQueue;

		private LogMessageWorker(BlockingQueue<LogMessage> logQueue) {
			this.logQueue = logQueue;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				LogMessage message = logQueue.take();
				(message.isError() ? System.err : System.out).println(message.getMessage());
				publish(message);
			}
		}

		@Override
		protected void process(List<LogMessage> messages) {
			for (LogMessage message : messages) {
				if (message == null)
					continue;

				if (frame == null)
					break;
				Document document = logPane.getDocument();
				String text = (document.getLength() == 0 ? message.getMessage() : '\n' + message.getMessage());
				MutableAttributeSet font = (message.isError() ? FONT_RED : null);
				try {
					document.insertString(document.getLength(), text, font);
				} catch (BadLocationException ex) {
				}
			}
		}
	}
}
