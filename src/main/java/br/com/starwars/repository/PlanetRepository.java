package br.com.starwars.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.starwars.domain.Planet;
import reactor.core.publisher.Mono;

public interface PlanetRepository extends ReactiveCrudRepository<Planet, Integer>{

	Mono<Planet> findByName(String name);

}
