package br.com.starwars.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.starwars.builder.PlanetBuilder;
import br.com.starwars.domain.Planet;
import br.com.starwars.exception.CustomAttributes;
import br.com.starwars.gateway.PlanetGateway;
import br.com.starwars.repository.PlanetRepository;
import br.com.starwars.service.PlanetService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({PlanetService.class, CustomAttributes.class})
public class PlanetControllerIntegrationTest {
	
	@MockBean
	private PlanetRepository repository;
	
	@MockBean
	private PlanetGateway gateway;
	

	@Autowired
	private WebTestClient webClient;
	
	
	@Test
	@DisplayName("Integration test find all planets with success")
	public void testFindAllWithSuccess() {
		
		Planet planet = new PlanetBuilder().withId(1).withName("Earth").build();
		BDDMockito.when(repository.findAll()).thenReturn(Flux.just(planet));
		
		webClient.get()
		.uri("/planets").exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.[0].id").isEqualTo(planet.getId())
		.jsonPath("$.[0].name").isEqualTo(planet.getName());
	}
	
	@Test
	@DisplayName("Integration test find By Id with success")
	public void testFindByIdWithSuccess() {
		
		Planet planet = new PlanetBuilder().withId(1).withName("Earth").build();
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));
		
		webClient.get()
		.uri("/planet/id/{id}", 1).exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.id").isEqualTo(planet.getId())
		.jsonPath("$.name").isEqualTo(planet.getName());
	}
	
	@Test
	@DisplayName("Integration test find By Id with Error")
	public void testFindByIdWithError() {
		
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());
		
		webClient.get()
		.uri("/planet/id/{id}", 1).exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("$.status").isEqualTo(404);
	}
	
	@Test
	@DisplayName("Integration test save with success")
	public void testSaveWithSuccess() {
		
		Planet planet = new PlanetBuilder().withId(1).withName("Earth").withClimate("spring").withTerrain("desert").build();
		BDDMockito.when(repository.save(ArgumentMatchers.any(Planet.class))).thenReturn(Mono.just(planet));
		
		webClient.post()
		.uri("/planet")
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(planet))
		.exchange()
		.expectStatus().isCreated()
		.expectBody()
		.jsonPath("$.id").isEqualTo(planet.getId())
		.jsonPath("$.name").isEqualTo(planet.getName());
	}
	
	@Test
	@DisplayName("Integration test save with error, name is empty")
	public void testSaveWithError_NameEmpty() {
		
		Planet planet = new PlanetBuilder().withClimate("spring").withTerrain("desert").build();
		
		webClient.post()
		.uri("/planet")
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(planet))
		.exchange()
		.expectStatus().isBadRequest()
		.expectBody()
		.jsonPath("$.status").isEqualTo(400);
	}
	

	@Test
	@DisplayName("Integration test save with error, climate is empty")
	public void testSaveWithError_ClimateEmpty() {
		
		Planet planet = new PlanetBuilder().withName("Earth").withTerrain("desert").build();
		
		webClient.post()
		.uri("/planet")
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(planet))
		.exchange()
		.expectStatus().isBadRequest()
		.expectBody()
		.jsonPath("$.status").isEqualTo(400);
	}
	
	@Test
	@DisplayName("Integration test save with error, terrain is empty")
	public void testSaveWithError_TerrainEmpty() {
		
		Planet planet = new PlanetBuilder().withName("Earth").withClimate("spring").build();
		
		webClient.post()
		.uri("/planet")
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(planet))
		.exchange()
		.expectStatus().isBadRequest()
		.expectBody()
		.jsonPath("$.status").isEqualTo(400);
	}
	
	@Test
	@DisplayName("Integration test delete By Id with success")
	public void testDeleteByIdWithSuccess() {
		
		Planet planet = new PlanetBuilder().withId(1).withName("Earth").build();
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(planet));
		BDDMockito.when(repository.delete(ArgumentMatchers.any(Planet.class))).thenReturn(Mono.empty());
		
		webClient.delete()
		.uri("/planet/{id}", 1).exchange()
		.expectStatus().isNoContent();
	}
	
	@Test
	@DisplayName("Integration test Delete with Error")
	public void testDeleteWithError() {
		
		BDDMockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.empty());
		
		webClient.delete()
		.uri("/planet/{id}", 1).exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("$.status").isEqualTo(404);
	}
	
}
