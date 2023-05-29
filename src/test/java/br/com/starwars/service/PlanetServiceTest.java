package br.com.starwars.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import br.com.starwars.builder.PlanetBuilder;
import br.com.starwars.domain.Planet;
import br.com.starwars.gateway.PlanetGateway;
import br.com.starwars.repository.PlanetRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class PlanetServiceTest {
	
	
	
	@InjectMocks
	private PlanetService service;
	
	@Mock
	private PlanetRepository repository;
	
	@Mock
	private PlanetGateway gateway;
	
	
	
	@Test
	@DisplayName("findAll with success")
	public void testFindAll() {

		Planet planet = new PlanetBuilder().withId(1).withName("Teste").build();

		BDDMockito.when(repository.findAll()).thenReturn(Flux.just(planet));

		StepVerifier.create(service.findAll()).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("findById with success")
	public void testFindById() {

		Planet planet = new PlanetBuilder().withId(2).withName("Teste").build();

		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));

		StepVerifier.create(service.findById(2)).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("findById with error")
	public void testFindById_WithError() {

		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());

		StepVerifier.create(service.findById(2)).expectSubscription().expectError(ResponseStatusException.class).verify();

	}
	
	@Test
	@DisplayName("findByName with success")
	public void testFindByName() {

		Planet planet = new PlanetBuilder().withId(2).withName("Earth").build();

		BDDMockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Mono.just(planet));

		StepVerifier.create(service.findByName("Earth")).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("findByName with error")
	public void testFindByName_WithError() {

		BDDMockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

		StepVerifier.create(service.findByName("Earth")).expectSubscription().expectError(ResponseStatusException.class).verify();

	}
	
	@Test
	@DisplayName("Save with success")
	public void testSave() {

		Planet planet = new PlanetBuilder().withId(1).withName("Mars").withClimate("spring").withTerrain("desert").build();
		
		BDDMockito.when(repository.save(planet)).thenReturn(Mono.just(planet));

		StepVerifier.create(service.save(planet)).expectSubscription().expectNext(planet).verifyComplete();

	}
	
	@Test
	@DisplayName("Delete with success")
	public void testDelete() {

		Planet planet = new PlanetBuilder().withId(1).withName("Mars").withClimate("spring").withTerrain("desert").build();
		
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));

		BDDMockito.when(repository.delete(planet)).thenReturn(Mono.empty());

		StepVerifier.create(service.delete(1)).expectSubscription().verifyComplete();

	}
	
	@Test
	@DisplayName("Delete with error")
	public void testDelete_WithError() {
		
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());

		StepVerifier.create(service.delete(1)).expectSubscription().expectError(ResponseStatusException.class).verify();


	}

}
