package com.example.Bet365Odds;

import com.example.Bet365Odds.service.Bet365ApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bet365OddsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Bet365OddsApplication.class, args);

		Thread t = new Thread(new Bet365ApiService());
		t.start();
	}

}
