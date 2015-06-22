package ninja.hon95.jlawtest;

public class LogMessage {

	private final String message;
	private final boolean error;

	public LogMessage(String message, boolean error) {
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return (isError() ? "ERROR: " + getMessage() : getMessage());
	}
}
