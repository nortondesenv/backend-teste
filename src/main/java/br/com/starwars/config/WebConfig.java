package br.com.starwars.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
	
	
	@Value("${swapi.url}")
	private String swapiUrl;
	
	   @Bean
       public WebClient webClient(){

           WebClient webClient = WebClient.builder()
                   .baseUrl(swapiUrl)
                   .build();

           return webClient;
       }

}
