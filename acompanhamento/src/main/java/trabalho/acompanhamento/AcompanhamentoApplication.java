package trabalho.acompanhamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AcompanhamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcompanhamentoApplication.class, args);
	}

}
