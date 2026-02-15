package Modelo;

import Vista.Vista;
import java.util.*;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.result.*;
import org.bson.Document;


public final class BBDDMongo implements Funcionalidades {
    
    private String conexion;
    private String nombreBBDD;
    private MongoClient cliente;
    private MongoDatabase bbdd;
    private MongoCollection<Document> coleccion;
    private final HashMap<Integer, Libro> biblioteca;
    private final Vista vista;

    public BBDDMongo(String nombreBBDD) {
        this.conexion = "mongodb://localhost:27017";
        this.nombreBBDD = nombreBBDD;
        this.cliente = MongoClients.create(this.conexion);
        this.bbdd = this.cliente.getDatabase(this.nombreBBDD);
        this.coleccion = this.bbdd.getCollection("libro");
        this.vista = new Vista();
        this.vista.conexionBBDD(nombreBBDD);
        this.biblioteca = leer();
    }

    @Override
    public HashMap<Integer,Libro> leer() {
        HashMap<Integer,Libro> aux = new HashMap<>();
        try (MongoCursor<Document> resultado = this.coleccion.find().iterator()) {
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                int isbn = documento.getInteger("isbn", 0);
                String titulo = documento.getString("titulo");
                String autor = documento.getString("autor");
                String editorial = documento.getString("editorial");
                String genero = documento.getString("genero");
                Libro libro = new Libro(isbn, titulo, autor, editorial, genero);
                if (aux.containsKey(isbn)) {
					this.vista.errorLibroRepetido(isbn);
				} else {
					aux.put(isbn, libro);
				}
            }
        } catch (MongoException e) {
            this.vista.errorLecturaBBBDD(this.nombreBBDD);
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer, Libro> datos) {
        try {
            this.coleccion.deleteMany(new Document()); 
            List<Document> listaDocumentos = new ArrayList<>();
            for (Libro libro : datos.values()) {
                Document documento = new Document()
                    .append("isbn", libro.getIsbn())
                    .append("titulo", libro.getTitulo())
                    .append("autor", libro.getAutor())
                    .append("editorial", libro.getEditorial())
                    .append("genero", libro.getGenero());
                listaDocumentos.add(documento);
            }
            if (!listaDocumentos.isEmpty()) {
                this.coleccion.insertMany(listaDocumentos);
            }
            this.biblioteca.clear();
            this.biblioteca.putAll(datos);
            this.vista.guardadoBBDD(this.nombreBBDD);
        } catch (MongoException e) {
            this.vista.errorGuardadoBBDD(this.nombreBBDD);
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroExistente(isbn);
            return;
        }
        try {
            Document documento = new Document();
            documento.put("isbn", isbn);
            documento.put("titulo", libro.getTitulo());
            documento.put("autor", libro.getAutor());
            documento.put("editorial", libro.getEditorial());
            documento.put("genero", libro.getGenero());
            this.coleccion.insertOne(documento);
            this.biblioteca.put(libro.getIsbn(), libro);
            this.vista.insercionLibro(isbn);
        } catch (MongoException e) {
            this.vista.errorInsercionLibro(isbn);
        }
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        try {
            Document documento = new Document();
            documento.put("isbn", isbn);
            this.coleccion.deleteOne(documento);
            this.biblioteca.remove(isbn);
            this.vista.borradoLibro(isbn);
        } catch (MongoException e) {
            this.vista.errorBorradoLibro(isbn);
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        try {
            Document libroAntiguo = new Document("isbn", isbn);
            Document documentoNuevo = new Document()
                    .append("isbn", libro.getIsbn())
                    .append("titulo", libro.getTitulo())
                    .append("autor", libro.getAutor())
                    .append("editorial", libro.getEditorial())
                    .append("genero", libro.getGenero());

            if (isbn != libro.getIsbn()) {
                this.coleccion.deleteOne(libroAntiguo);
                this.coleccion.insertOne(documentoNuevo);
            } else {
                this.coleccion.replaceOne(libroAntiguo, documentoNuevo);
            }
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
            this.vista.modificacionLibro(isbn, libro.getIsbn());
        } catch (MongoException e) {
            this.vista.errorModificacionLibro(isbn);
        }
    }

    @Override
    public void restablecer() {
        try {
            this.coleccion.deleteMany(new Document());
            this.biblioteca.clear();
            this.vista.restablecerLibros();
        } catch (MongoException e) {
            this.vista.errorRestablecerLibros();
        }
    }

    /*

    @Override
    public void buscar(int isbn) {
        try {
            Document filtro = new Document("isbn", isbn);
            Document documento = (Document) this.libro.find(filtro).first();
            if (documento == null) {
                this.vista.libroInexistente(isbn);
                return;
            }
            Libro libro = new Libro(
                documento.getInteger("isbn"),
                documento.getString("titulo"),
                documento.getString("autor"),
                documento.getString("editorial"),
                documento.getString("genero")
            );
            this.vista.buscar(this.biblioteca, isbn);
        } catch (MongoException e) {
            this.vista.errorBuscadoLibro(isbn);
        }
    }

    @Override
    public void mostrar() {
        try (MongoCursor<Document> resultado = this.libro.find().iterator()) {
            if (!resultado.hasNext()) {
                this.vista.errorVacio();
                return;
            }
            HashMap<Integer,Libro> aux = new HashMap<>();
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                int isbn = documento.getInteger("isbn");
                Libro libro = new Libro(
                    isbn,
                    documento.getString("titulo"),
                    documento.getString("autor"),
                    documento.getString("editorial"),
                    documento.getString("genero")
                );
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                    return;
                }
                aux.put(isbn, libro);
            }
            this.vista.mostrar(aux);
        } catch (MongoException e) {
            this.vista.errorMuestraLibros();
        }
    }

    */

}