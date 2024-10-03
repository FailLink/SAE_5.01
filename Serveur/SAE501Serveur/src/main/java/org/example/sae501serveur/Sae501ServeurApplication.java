package org.example.sae501serveur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;

@SpringBootApplication
public class Sae501ServeurApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Sae501ServeurApplication.class, args);
	}

}
