package uk.co.stuforbes.template.thymeleaf.context;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.context.IContext;

import com.google.common.collect.Maps;

public class DefaultLocaleContextFactoryTest {

	private ContextFactory contextFactory;

	@Before
	public void initialise() {
		this.contextFactory = new DefaultLocaleContextFactory();
	}

	@Test
	public void setsDefaultLocale() {

		final IContext context = contextFactory.create(Collections
				.<String, Object> emptyMap());
		assertThat(context.getLocale(), is(Locale.getDefault()));
	}

	@Test
	public void holdsAllDefinedState() {
		final Map<String, Object> varMap = Maps.newHashMap();
		varMap.put("key-1", "hello");
		varMap.put("key-2", 1);
		varMap.put("key-3", 3.0);

		final IContext context = contextFactory.create(varMap);
		assertThat(context.getVariables().containsKey("key-1"), is(true));
		assertThat(context.getVariables().get("key-1"), is((Object) "hello"));
		assertThat(context.getVariables().containsKey("key-2"), is(true));
		assertThat(context.getVariables().get("key-2"), is((Object) 1));
		assertThat(context.getVariables().containsKey("key-3"), is(true));
		assertThat(context.getVariables().get("key-3"), is((Object) 3.0));

		assertThat(context.getVariables().containsKey("key-4"), is(false));
	}
}
