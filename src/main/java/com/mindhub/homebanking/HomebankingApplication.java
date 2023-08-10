package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mindhub.homebanking.models.TransactionType.*;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
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


					Transaction transaction1 = new Transaction(DEBIT, -1000.5, "Luz", LocalDateTime.now(), account1);
					transactionRepository.save(transaction1);
					account1.addTransaction(transaction1);
					Transaction transaction2 = new Transaction(DEBIT, -500.5, "Agua", LocalDateTime.now(),account1);
					transactionRepository.save(transaction2);
					account1.addTransaction(transaction2);
					Transaction transaction3 = new Transaction(CREDIT, 20000.00, "Factura Servicios", LocalDateTime.now(),account1);
					transactionRepository.save(transaction3);
					account1.addTransaction(transaction3);

					Transaction transaction4 = new Transaction(DEBIT, -552.65, "Luz", LocalDateTime.now(), account2);
					transactionRepository.save(transaction4);
					account2.addTransaction(transaction4);
					Transaction transaction5 = new Transaction(DEBIT, -1086.74, "Agua", LocalDateTime.now(),account2);
					transactionRepository.save(transaction5);
					account2.addTransaction(transaction5);
					Transaction transaction6 = new Transaction(CREDIT, 40000.33, "Factura Servicios", LocalDateTime.now(),account2);
					transactionRepository.save(transaction6);
					account2.addTransaction(transaction6);

					Transaction transaction7 = new Transaction(DEBIT, -500.00, "Luz", LocalDateTime.now(),account3);
					transactionRepository.save(transaction7);
					account3.addTransaction(transaction7);
					Transaction transaction8 = new Transaction(CREDIT, 10001.86, "Factura Servicios", LocalDateTime.now(),account3);
					transactionRepository.save(transaction8);
					account3.addTransaction(transaction8);
					Transaction transaction9 = new Transaction(DEBIT, -500.00, "Gas", LocalDateTime.now(),account3);
					transactionRepository.save(transaction9);
					Transaction transaction10 = new Transaction(CREDIT, 30000.76, "Factura Servicios", LocalDateTime.now(),account3);
					transactionRepository.save(transaction10);
					account3.addTransaction(transaction10);


		});
	}
}
