import java.sql.Date;

public class Jugador {

    int id;
    String nombre;
    String descripcion;
    String caracteristica;
    int edad;
    Date fechaIncorporacionLiga;

    
    public Jugador(String nombre, String descripcion, String caracteristica, int edad, Date fechaIncorporacionLiga) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristica = caracteristica;
        this.edad = edad;
        this.fechaIncorporacionLiga = fechaIncorporacionLiga;
    }
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getCaracteristica() {
        return caracteristica;
    }
    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public Date getFechaIncorporacionLiga() {
        return fechaIncorporacionLiga;
    }
    public void setFechaIncorporacionLiga(Date fechaIncorporacionLiga) {
        this.fechaIncorporacionLiga = fechaIncorporacionLiga;
    }

    

}
