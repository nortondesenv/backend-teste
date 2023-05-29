package br.com.starwars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.starwars.domain.Planet;
import br.com.starwars.service.PlanetService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@ResponseBody
public class PlanetController {
	
	
	@Autowired
	private PlanetService service;
	
	@GetMapping("/planets")
	@ResponseStatus(HttpStatus.OK)
	public Flux<Planet> findAll(){
		return service.findAll();
	}
	
	
	@GetMapping("/planet/id/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Planet> findById(@PathVariable int id){
		return service.findById(id);
	}
	
	
	@GetMapping("/planet/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Planet> findByName(@PathVariable String name){
		return service.findByName(name);
	}
	
	@PostMapping("/planet")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Planet> save(@Valid @RequestBody Planet planet){
		return service.save(planet);
	}
	
	@DeleteMapping("/planet/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable int id){
		return service.delete(id);
	}
	

}
