package Modelo;

import Vista.Vista;
import java.io.*;
import java.util.*;
import java.net.*;
import org.json.*;

public final class BBDDPHP implements Funcionalidades {

    private final String urlServidor = "http://localhost/Servidor-Proyecto-Integrador-EJCSM/Api.php";
    private final String nombreBBDD;
    private final HashMap<Integer, Libro> biblioteca;
    private final Vista vista;

    public BBDDPHP() {
        this.nombreBBDD = "Servidor PHP";
        this.vista = new Vista();
        this.biblioteca = leer();
        this.vista.conexionBBDD(this.nombreBBDD);
    }

    private JSONObject enviarPeticion(String json) {
        try {
            URL url = new URL(urlServidor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }
            StringBuilder respuesta = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    respuesta.append(linea);
                }
            }
            return new JSONObject(respuesta.toString());
        } catch (Exception e) {
            // Si hay un error de conexión o formato
            return new JSONObject().put("estado", "error").put("mensaje", e.getMessage());
        }
    }

    @Override
    public HashMap<Integer, Libro> leer() {
        HashMap<Integer, Libro> aux = new HashMap<>();
        JSONObject respuesta = enviarPeticion("{\"accion\":\"leer\"}");
        if (respuesta.optString("estado").equals("ok")) {
            JSONArray array = respuesta.getJSONArray("libros");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                
                int isbn = obj.getInt("isbn");
                Libro libro = new Libro(
                    isbn,
                    obj.getString("titulo"),
                    obj.getString("autor"),
                    obj.getString("editorial"),
                    obj.getString("genero")
                );
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                } else {
                    aux.put(isbn, libro);
                }
            }
        } else {
            this.vista.errorLecturaBBBDD(this.nombreBBDD);
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer, Libro> datos) {

    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroExistente(isbn);
            return;
        }
        JSONObject json = new JSONObject();
        json.put("accion", "insertar");
        json.put("isbn", isbn);
        json.put("titulo", libro.getTitulo());
        json.put("autor", libro.getAutor());
        json.put("editorial", libro.getEditorial());
        json.put("genero", libro.getGenero());
        JSONObject respuesta = enviarPeticion(json.toString());
        if (respuesta.optString("estado").equals("ok")) {
            this.biblioteca.put(isbn, libro);
            this.vista.insercionLibro(isbn);
        } else {
            this.vista.errorInsercionLibro(isbn);
        }
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        JSONObject json = new JSONObject();
        json.put("accion", "borrar");
        json.put("isbn", isbn);
        JSONObject respuesta = enviarPeticion(json.toString());
        if (respuesta.optString("estado").equals("ok")) {
            this.biblioteca.remove(isbn);
            this.vista.borradoLibro(isbn);
        } else {
            this.vista.errorBorradoLibro(isbn);
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        JSONObject json = new JSONObject();
        json.put("accion", "modificar");
        json.put("isbn", libro.getIsbn());
        json.put("titulo", libro.getTitulo());
        json.put("autor", libro.getAutor());
        json.put("editorial", libro.getEditorial());
        json.put("genero", libro.getGenero());
        JSONObject respuesta = enviarPeticion(json.toString());
        if (respuesta.optString("estado").equals("ok")) {
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
            this.vista.modificacionLibro(isbn, libro.getIsbn());
        } else {
            this.vista.errorModificacionLibro(isbn);
        }
    }

    @Override
    public void restablecer() {
        JSONObject respuesta = enviarPeticion("{\"accion\":\"restablecer\"}");
        if (respuesta.optString("estado").equals("ok")) {
            this.biblioteca.clear();
            this.vista.restablecerLibros();
        } else {
            this.vista.errorRestablecerLibros();
        }
    }

}