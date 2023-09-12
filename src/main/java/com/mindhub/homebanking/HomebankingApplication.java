package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.CardType.*;
import static com.mindhub.homebanking.models.TransactionType.*;



@SpringBootApplication
public class HomebankingApplication {


	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		;
		return args -> {
/*
					Account account1 = new Account("VIN001",LocalDate.now(),5000);
					Account account2 = new Account("VIN002",LocalDate.now().plusDays(1),7500);
					Client client1 = new Client("Melba", "Morel","melba@melba.com",passwordEncoder.encode("Password1"));
					clientRepository.save(client1);
					client1.addAccount(account1);
					client1.addAccount(account2);
					accountRepository.save(account1);
					accountRepository.save(account2);
					clientRepository.save(client1);



					Account account3 = new Account( "VIN003",LocalDate.now(), (double) 10000);
					Client client2= new Client("Ricardo","Sanchez","ricardo@mindhub.com", passwordEncoder.encode("Password2"));
					clientRepository.save(client2);
					client2.addAccount(account3);
					accountRepository.save(account3);
					clientRepository.save(client2);

					Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));

					Transaction transaction1 = new Transaction(TransactionType.DEBIT, -1000.5, "Luz", LocalDateTime.now(), account1);
					transactionRepository.save(transaction1);
					account1.addTransaction(transaction1);
					Transaction transaction2 = new Transaction(TransactionType.DEBIT, -500.5, "Agua", LocalDateTime.now(),account1);
					transactionRepository.save(transaction2);
					account1.addTransaction(transaction2);
					Transaction transaction3 = new Transaction(TransactionType.CREDIT, 20000.00, "Factura Servicios", LocalDateTime.now(),account1);
					transactionRepository.save(transaction3);
					account1.addTransaction(transaction3);

					Transaction transaction4 = new Transaction(TransactionType.DEBIT, -552.65, "Luz", LocalDateTime.now(), account2);
					transactionRepository.save(transaction4);
					account2.addTransaction(transaction4);
					Transaction transaction5 = new Transaction(TransactionType.DEBIT, -1086.74, "Agua", LocalDateTime.now(),account2);
					transactionRepository.save(transaction5);
					account2.addTransaction(transaction5);
					Transaction transaction6 = new Transaction(TransactionType.CREDIT, 40000.33, "Factura Servicios", LocalDateTime.now(),account2);
					transactionRepository.save(transaction6);
					account2.addTransaction(transaction6);

					Transaction transaction7 = new Transaction(TransactionType.DEBIT, -500.00, "Luz", LocalDateTime.now(),account3);
					transactionRepository.save(transaction7);
					account3.addTransaction(transaction7);
					Transaction transaction8 = new Transaction(TransactionType.CREDIT, 10001.86, "Factura Servicios", LocalDateTime.now(),account3);
					transactionRepository.save(transaction8);
					account3.addTransaction(transaction8);
					Transaction transaction9 = new Transaction(TransactionType.DEBIT, -500.00, "Gas", LocalDateTime.now(),account3);
					transactionRepository.save(transaction9);
					Transaction transaction10 = new Transaction(TransactionType.CREDIT, 30000.76, "Factura Servicios", LocalDateTime.now(),account3);
					transactionRepository.save(transaction10);
					account3.addTransaction(transaction10);

					Loan loan1 = new Loan("Mortgage",500000, Arrays.asList(12, 24, 36, 48, 60));
					Loan loan2 = new Loan("Personal",100000, Arrays.asList(6,12,24));
					Loan loan3 = new Loan("Auto ",300000, Arrays.asList(6,12,24,36));
					loanRepository.save(loan1);
					loanRepository.save(loan2);
					loanRepository.save(loan3);

					ClientLoan clientLoan1 = new ClientLoan(400000, 60,client1, loan1);
					ClientLoan clientLoan2 = new ClientLoan(50000,12,client1,loan2);
					client1.addClientLoan(clientLoan1);
					client1.addClientLoan(clientLoan2);
					clientRepository.save(client1);
					clientLoanRepository.save(clientLoan1);
					clientLoanRepository.save(clientLoan2);
					clientRepository.save(client1);

					ClientLoan clientLoan3 = new ClientLoan(100000, 24,client2, loan2);
					ClientLoan clientLoan4 = new ClientLoan(200000,36,client2,loan3);
					client2.addClientLoan(clientLoan3);
					client2.addClientLoan(clientLoan4);
					clientRepository.save(client2);
					clientLoanRepository.save(clientLoan3);
					clientLoanRepository.save(clientLoan4);

					clientRepository.save(client2);


					cardRepository.save(new Card(CardType.DEBIT,GOLD, "1234 1234 1234 1234", 123, LocalDate.now().plusYears(5),LocalDate.now(),client1));
					cardRepository.save(new Card(CardType.CREDIT, TITANIUM , "4321 4321 4321 4321", 321, LocalDate.now().plusYears(5),LocalDate.now(),client1));
					cardRepository.save(new Card(CardType.CREDIT, SILVER , "5555 5555 4444 444", 987, LocalDate.now().plusYears(5),LocalDate.now(),client2));
*/
		};
	}
}
