package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		;
		return(args -> {
					LocalDate date = LocalDate.now();
					Account account1 = new Account("VIN001",date,5000);
					Account account2 = new Account("VIN002",date.plusDays(1),7500);
					Client client1 = new Client("Melba", "Morel","melba@melba");
					clientRepository.save(client1);
					client1.addAccount(account1);
					client1.addAccount(account2);
					accountRepository.save(account1);
					accountRepository.save(account2);
					clientRepository.save(client1);



					Account account3 = new Account( " VIN003",date, (double) 10000);
					Client client2= new Client("Ricardo","Sanchez","ricardo@mindhub.com");
					clientRepository.save(client2);
					client2.addAccount(account3);
					accountRepository.save(account3);
					clientRepository.save(client2);

		});
	}
}
