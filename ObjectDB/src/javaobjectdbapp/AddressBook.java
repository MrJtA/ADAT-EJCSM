/*
 * Ejemplo extraido de https://www.developer.com/database/using-objectdb-in-java-projects/
 * Se han solucionado algunos pequeños errores (el codigo copiado tal cual no compilaba)
 * Las librerias se han obtenido de https://www.objectdb.com/download
 * Hay que descomprimir el zip y nos quedamos con los *.jar 
 */

package javaobjectdbapp;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/*
 * Esta clase es de la que se van a almacenar los objetos.
 */

@Entity
public class AddressBook implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String address;

	public AddressBook() {
	}

	public AddressBook(String n, String a) {
		this.name = n;
		this.address = a;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		this.address = a;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", this.name, this.address);
	}
}