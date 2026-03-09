package es.uji.ei1027.sgOvi;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SgOviApplication {

	private static final Logger log =
			Logger.getLogger(SgOviApplication.class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(SgOviApplication.class).run(args);
	}
}
