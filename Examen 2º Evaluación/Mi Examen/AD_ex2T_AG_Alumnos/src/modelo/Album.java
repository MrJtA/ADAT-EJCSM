package modelo;

public class Album {

	private int id;
	private String title;
	private MusicBand band;

	public Album() {
	}

    public Album(int id) {
        this.id = id;
    }
	
	public Album(int id, String titulo, MusicBand autor) {
		this.id = id;
		this.title = titulo;
		this.band = autor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MusicBand getBand() {
		return band;
	}

	public void setBand(MusicBand band) {
		this.band = band;
	}

}
