package uk.co.stuforbes.template;

import org.thymeleaf.context.IContext;

import com.google.common.base.Supplier;

public interface TemplateController {

	String process(final Supplier<IContext> contextProvider);

}
