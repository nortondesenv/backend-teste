package br.com.starwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.starwars.domain.Planet;
import br.com.starwars.gateway.PlanetGateway;
import br.com.starwars.repository.PlanetRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetService {
	
	@Autowired
	private PlanetRepository repository;
	
	@Autowired
	private PlanetGateway gateway;
	
	@Cacheable("planets")
	public Flux<Planet> findAll(){
		return repository.findAll();
	}
	
	public Mono<Planet> findById(int id){
		return repository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Planet with ID = " + id + " not found")));
	}
	
	public Mono<Planet> findByName(String name){
		return repository.findByName(name)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Planet with NAME = " + name + " not found")));
	}

	
	public Mono<Planet> save(Planet planet) {
		
		
//		Mono<Planet> mono = save
//				.flatMap(repository::save)
//				.zipWith(gateway.getFilmsCount(planet))
//				.map((tuple) -> new PlanetBuilder()
//					.withId(tuple.getT1().getId())
//					.withName(tuple.getT1().getClimate())
//					.withClimate(tuple.getT1().getClimate())
//					.withTerrain(tuple.getT1().getTerrain())
//					.withFilmsCounts(tuple.getT2().getFilms())
//					.build())
//				.flatMap(repository::save);
		

		return repository.save(planet);
	}
	
	
	public Mono<Void> delete(int id) {
		return findById(id)
				.flatMap(repository::delete);
	}
	

}
