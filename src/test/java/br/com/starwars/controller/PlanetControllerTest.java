package br.com.starwars.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.starwars.builder.PlanetBuilder;
import br.com.starwars.domain.Planet;
import br.com.starwars.service.PlanetService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class PlanetControllerTest {


	@InjectMocks
	private PlanetController controller;
	
	@Mock
	private PlanetService service;
	
	@Test
	@DisplayName("findAll with success")
	public void testFindAll() {

		Planet planet = new PlanetBuilder().withId(1).withName("Teste").build();

		BDDMockito.when(service.findAll()).thenReturn(Flux.just(planet));

		StepVerifier.create(controller.findAll()).expectSubscription().expectNext(planet).verifyComplete();

	}
	

	@Test
	@DisplayName("findById with success")
	public void testFindById() {

		Planet planet = new PlanetBuilder().withId(2).withName("Teste").build();

		BDDMockito.when(service.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));

		StepVerifier.create(controller.findById(2)).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("findByName with success")
	public void testFindByName() {

		Planet planet = new PlanetBuilder().withId(2).withName("Earth").build();

		BDDMockito.when(service.findByName(ArgumentMatchers.anyString())).thenReturn(Mono.just(planet));

		StepVerifier.create(controller.findByName("Earth")).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("Save with success")
	public void testSave() {

		Planet planet = new PlanetBuilder().withId(1).withName("Mars").withClimate("spring").withTerrain("desert").build();

		BDDMockito.when(service.save(planet)).thenReturn(Mono.just(planet));

		StepVerifier.create(controller.save(planet)).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	
	@Test
	@DisplayName("Delete with success")
	public void testDelete() {

		Planet planet = new PlanetBuilder().withId(1).withName("Mars").withClimate("spring").withTerrain("desert").build();
		
		BDDMockito.when(service.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));

		BDDMockito.when(service.delete(1)).thenReturn(Mono.empty());

		StepVerifier.create(controller.delete(1)).expectSubscription().verifyComplete();

	}
	
	
	
}
