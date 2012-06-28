package uk.co.stuforbes.template.thymeleaf.context;

import java.util.Map;

import org.thymeleaf.context.IContext;

public interface ContextFactory {

	IContext create(Map<String, Object> variableMap);
}
