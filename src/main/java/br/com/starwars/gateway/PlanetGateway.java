package br.com.starwars.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.starwars.domain.Planet;
import br.com.starwars.model.response.PlanetResponse;
import reactor.core.publisher.Mono;

@Service
public class PlanetGateway {

	@Autowired
	private WebClient webClient;

	public Mono<PlanetResponse> getFilmsCount(Planet planet) {

		Mono<PlanetResponse> response = webClient.get().uri("/" + planet.getId()).retrieve()
				.bodyToMono(PlanetResponse.class);

		return response;
	}

}
