package br.com.starwars.builder;

import br.com.starwars.domain.Planet;

public class PlanetBuilder {

	private Planet planet;
	
	public PlanetBuilder() {
		planet = new Planet();
	}
	
	public PlanetBuilder withId(int id) {
		planet.setId(id);
		return this;
	}
	
	public PlanetBuilder withName(String name) {
		planet.setName(name);
		return this;
	}
	
	public PlanetBuilder withTerrain(String terrain) {
		planet.setTerrain(terrain);
		return this;
	}
	
	public PlanetBuilder withClimate(String climate) {
		planet.setClimate(climate);
		return this;
	}
	
	public PlanetBuilder withFilmsCounts(String[] films) {
		planet.setFilmsCount(films.length);
		return this;
	}
	
	public Planet build() {
		return planet;
	}
}
