package uk.co.stuforbes.sparktest;

import uk.co.stuforbes.sparktest.authentication.AuthenticationManager;
import uk.co.stuforbes.sparktest.authentication.SimpleSparkAuthenticationManager;
import uk.co.stuforbes.sparktest.authentication.SparkRequestLifecycleContext;
import uk.co.stuforbes.sparktest.domain.FakeUserManager;
import uk.co.stuforbes.sparktest.domain.UserManager;
import uk.co.stuforbes.sparktest.keystore.Keystore;
import uk.co.stuforbes.sparktest.keystore.RedisKeystore;
import uk.co.stuforbes.sparktest.route.Module;
import uk.co.stuforbes.sparktest.route.module.LoginModule;
import uk.co.stuforbes.sparktest.route.module.UserModule;
import uk.co.stuforbes.sparktest.session.HttpSessionPersistence;
import uk.co.stuforbes.sparktest.session.SessionPersistence;
import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.thymeleaf.TemplateEngineFactory;
import uk.co.stuforbes.template.thymeleaf.ThymeLeafTemplateEngineApplication;
import uk.co.stuforbes.template.thymeleaf.XHTMLTemplateEngineFactory;
import uk.co.stuforbes.template.thymeleaf.context.ContextFactory;
import uk.co.stuforbes.template.thymeleaf.context.DefaultLocaleContextFactory;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class SparkTestModule extends AbstractModule {

	@Override
	protected void configure() {

		// templating
		bind(TemplateEngineFactory.class).to(XHTMLTemplateEngineFactory.class);
		bind(TemplateApplication.class).to(
				ThymeLeafTemplateEngineApplication.class);
		bind(ContextFactory.class).to(DefaultLocaleContextFactory.class);

		// persistence
		bindConstant().annotatedWith(Names.named("redis-keystore-host")).to(
				"localhost");
		bind(Keystore.class).to(RedisKeystore.class);

		// authentication
		final TypeLiteral<AuthenticationManager<SparkRequestLifecycleContext>> authManagerType = new TypeLiteral<AuthenticationManager<SparkRequestLifecycleContext>>() {
		};
		bind(authManagerType).to(SimpleSparkAuthenticationManager.class);
		bind(UserManager.class).to(FakeUserManager.class);

		// server
		bind(SessionPersistence.class).to(HttpSessionPersistence.class);

		// modules
		final Multibinder<Module> moduleBinder = Multibinder.newSetBinder(
				binder(), Module.class);
		moduleBinder.addBinding().to(LoginModule.class);
		moduleBinder.addBinding().to(UserModule.class);
	}
}
