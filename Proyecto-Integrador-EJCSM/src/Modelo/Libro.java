package Modelo;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "libro")
public class Libro implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private int isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String genero;

    public Libro() {
    }

    public Libro(int isbn, String titulo, String autor, String editorial, String genero) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
    }

    public int getIsbn() {
        return this.isbn;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return this.isbn + ", " + this.titulo + ", " + this.autor + ", " + this.editorial + ", " + this.genero;
    }

}