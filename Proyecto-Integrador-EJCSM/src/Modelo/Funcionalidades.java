package Modelo;

import java.util.HashMap;

public interface Funcionalidades {

    public HashMap<Integer,Libro> leer();
    public void guardar(HashMap<Integer,Libro> datos);
    public void insertar(Libro libro);
    public void modificar(int isbn, Libro libro);
    public void borrar(int isbn);
    public void restablecer();

}