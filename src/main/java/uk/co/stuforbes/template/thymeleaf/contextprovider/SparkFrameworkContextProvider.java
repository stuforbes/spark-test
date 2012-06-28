package uk.co.stuforbes.template.thymeleaf.contextprovider;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import spark.Request;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

public class SparkFrameworkContextProvider implements Supplier<IContext> {

	private final Request request;
	private final Map<String, Object> models;

	public SparkFrameworkContextProvider(final Request request) {
		this.request = request;
		this.models = Maps.newHashMap();
	}

	public void addModel(final String name, final Object model) {
		this.models.put(name, model);
	}

	public IContext get() {
		final Map<String, Object> results = new HashMap<String, Object>();
		for (final String attributeKey : request.attributes()) {
			results.put(attributeKey, request.attribute(attributeKey));
		}
		for (final String paramKey : request.queryParams()) {
			results.put(paramKey, request.queryParams(paramKey));
		}
		for (final Map.Entry<String, Object> entry : models.entrySet()) {
			results.put(entry.getKey(), entry.getValue());
		}
		return new Context(request.raw().getLocale(), results);
	}
}
