package br.com.starwars.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("planet")
public class Planet {

	@Id
	private Integer id;

	@NotNull(message ="Name cannot be null")
	@NotEmpty(message ="Name cannot be empty")
	private String name;

	@NotNull(message ="Climate cannot be null")
	@NotEmpty(message ="Climate cannot be empty")
	private String climate;

	@NotNull(message ="Terrain cannot be null")
	@NotEmpty(message ="Terrain cannot be empty")
	private String terrain;
	
	private int filmsCount;
	
	public int getFilmsCount() {
		return filmsCount;
	}

	public void setFilmsCount(int filmsCount) {
		this.filmsCount = filmsCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

}
