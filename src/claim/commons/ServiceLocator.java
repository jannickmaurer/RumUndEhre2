package claim.commons;

import java.util.Locale;
import java.util.logging.Logger;

import claim.client.Claim;

// Implemented by Jannick & Samuel
public class ServiceLocator {

	private static ServiceLocator serviceLocator;

	final private Class<?> APP_CLASS = Claim.class;
	final private String APP_NAME = "Claim";

	final private Locale[] locales = new Locale[] { new Locale("EN"), new Locale("DE") };

	private static Logger clientLogger = Logger.getLogger("Claim");
	private static Logger serverLogger = Logger.getLogger("Server");

	private Configuration configuration;
	private Translator translator;

	public static ServiceLocator getServiceLocator() {
		if (serviceLocator == null) {
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}

	private ServiceLocator() {
		// Currently nothing to do here. We must define this constructor anyway,
		// because the default constructor is public
	}

	public Class<?> getAPP_CLASS() {
		return APP_CLASS;
	}

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public Logger getClientLogger() {
		return clientLogger;
	}

	public Logger getServerLogger() {
		return serverLogger;
	}

	public void setClientLogger(Logger clientLogger) {
		this.clientLogger = clientLogger;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Locale[] getLocales() {
		return locales;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}
}
