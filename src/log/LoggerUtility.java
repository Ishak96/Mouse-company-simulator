package log;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Utility class used to generate Log4j logger.
 */
public class LoggerUtility {

//	private static final String HTML_LOG_CONFIG = "src/log/log4j-html.properties";
	private static final String FILE_LOG_CONFIG = "src/log/log4j-file.properties";

	public static Logger getLogger(Class<?> logClass) {
		PropertyConfigurator.configure(FILE_LOG_CONFIG);
		String className = logClass.getName();
		return Logger.getLogger(className);
	}
}