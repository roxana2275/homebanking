package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		;
		return(args -> {
			Client client1 = new Client("Melba","Morel","melba@mindhub.com");
			clientRepository.save(client1);

			Client client2= new Client("Ricardo","Sanchez","ricardo@mindhub.com");
			clientRepository.save(client2);


		});
	}
}
