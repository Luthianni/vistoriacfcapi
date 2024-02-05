package br.gov.ce.detran.vistoriacfcapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("br.gov.ce.detran.vistoriacfcapi.entity")
public class VistoriaCfcApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VistoriaCfcApiApplication.class, args);
	}

}
