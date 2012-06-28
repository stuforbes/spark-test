package uk.co.stuforbes.template.thymeleaf.context;

import java.util.Locale;
import java.util.Map;

import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import com.google.inject.Inject;

public class DefaultLocaleContextFactory implements ContextFactory {

	@Inject
	public DefaultLocaleContextFactory() {
	}

	public IContext create(final Map<String, Object> variableMap) {
		return new Context(Locale.getDefault(), variableMap);
	}

}
