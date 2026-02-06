package modelo;

import javax.persistence.*;

@Entity
@Table(name = "grupo")
public class MusicBand {
	
	@Id
	private int id;
	private String name;
	
	public MusicBand(){
		
	}
	
	public MusicBand(String nombre){
		this.name = nombre;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
