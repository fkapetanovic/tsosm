package portal.config;

public class SysEnums {

	public enum Severity {

		TRACE(0), DEBUG(1), INFO(2), WARNING(3), ERROR(4), FATAL(5);

		private final int value;

		private Severity(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
