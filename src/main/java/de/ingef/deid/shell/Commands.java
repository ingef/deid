package de.ingef.deid.shell;

import de.ingef.deid.model.Job;
import de.ingef.deid.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Commands implements CommandLineRunner, ApplicationContextAware {

	public static final String LOAD_DEMO_DATA_OPTION = "loadDemoData";
	@Autowired
	private JobRepository jobRepository;

	private ApplicationContext context;

	private void fillDemoData() {
		log.info("Adding test jobs to repository");
		jobRepository.save(new Job("Anonymization A"));
		jobRepository.save(new Job("Anonymization B"));
		jobRepository.save(new Job("Synthetization A"));
		jobRepository.save(new Job("Synthetization B"));
	}

	@Override
	public void run(String... args) throws Exception {
		ArgumentParser parser = ArgumentParsers.newFor("Deid").build()
											   .description("Provide deidentification services to conquery");
		parser.addArgument("--" + LOAD_DEMO_DATA_OPTION)
				.dest(LOAD_DEMO_DATA_OPTION)
				.action(Arguments.storeTrue())
				.help("Prefill database with demo data");

		try{
			final Namespace namespace = parser.parseArgs(args);
			handleArgs(namespace);
		}
		catch (ArgumentParserException e) {
			SpringApplication.exit(context, new ArgumentParsingErrorExitHook(parser, e));
		}
	}

	private void handleArgs(Namespace namespace) {
		if (namespace.getBoolean(LOAD_DEMO_DATA_OPTION)) {
			fillDemoData();
		}
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}



	@RequiredArgsConstructor
	private static class ArgumentParsingErrorExitHook implements ExitCodeGenerator, Ordered {

		private final ArgumentParser argumentParser;
		private final ArgumentParserException exception;

		@Override
		public int getExitCode() {
			argumentParser.handleError(exception);
			return 1;
		}

		@Override
		public int getOrder() {
			return Ordered.LOWEST_PRECEDENCE;
		}
	}
}
