package Modelo;

import java.util.*;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.result.*;
import org.bson.Document;


public class BBDDMongo implements Funcionalidades {
    
    private String conexion;
    private String nombreBBDD;
    private MongoClient cliente;
    private MongoDatabase bbdd;
    private MongoCollection<Document> libro;
    private final HashMap<Integer, Libro> biblioteca;


    public BBDDMongo(String nombreBBDD) throws MongoException {
        this.conexion = "mongodb://localhost:27017";
        this.nombreBBDD = nombreBBDD;
        this.cliente = MongoClients.create(this.conexion);
        this.bbdd = this.cliente.getDatabase(this.nombreBBDD);
        this.libro = this.bbdd.getCollection("libro");
        this.biblioteca = leer();
    }

    @Override
    public HashMap<Integer,Libro> leer() {
        HashMap<Integer,Libro> aux = new HashMap<>();
        try (MongoCursor<Document> resultado = this.libro.find().iterator()) {
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                int isbn = documento.getInteger("isbn", 0);
                String titulo = documento.getString("titulo");
                String autor = documento.getString("autor");
                String editorial = documento.getString("editorial");
                String genero = documento.getString("genero");
                Libro libro = new Libro(isbn, titulo, autor, editorial, genero);
                aux.put(isbn, libro);
            }
        } catch (MongoException e) {
            System.err.println("Error: No se han podido leer los datos la base de datos '" + this.nombreBBDD + "'.");
            System.err.println(e.getMessage());
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer,Libro> datos) {
        try {
            MongoDatabase bbdd = this.cliente.getDatabase(this.nombreBBDD);
            MongoCollection<Document> coleccion = bbdd.getCollection("libro");
            coleccion.deleteMany(new Document()); 
            List<Document> listaDocumentos = new ArrayList<>();
            for (Libro libro : this.biblioteca.values()) {
                Document doc = new Document()
                    .append("isbn", libro.getIsbn())
                    .append("titulo", libro.getTitulo())
                    .append("autor", libro.getAutor())
                    .append("editorial", libro.getEditorial())
                    .append("genero", libro.getGenero());
                listaDocumentos.add(doc);
            }
            coleccion.insertMany(listaDocumentos);
            System.out.println("Los datos de la base de datos se han traspasado correctamente a la base de datos '" + nombreBBDD + "'.");
        } catch (MongoException e) {
            System.err.println("Error: No se ha podido establecer la conexión con la base de datos '" + nombreBBDD + "'.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: Ya existe un libro con el ISBN '" + isbn + "'.");
            return;
        }
        try {
            Document documento = new Document();
            documento.put("isbn", isbn);
            documento.put("titulo", libro.getTitulo());
            documento.put("autor", libro.getAutor());
            documento.put("editorial", libro.getEditorial());
            documento.put("genero", libro.getGenero());
            this.libro.insertOne(documento);
            this.biblioteca.put(libro.getIsbn(), libro);
            System.out.println("El libro con el ISBN '" + isbn + "'' se ha registrado correctamente.");
        } catch (MongoException e) {
            System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        try {
            Document documento = new Document();
            documento.put("isbn", isbn);
            DeleteResult borrado = this.libro.deleteOne(documento);
            this.biblioteca.remove(isbn);
            System.out.println("El libro con el ISBN '" + isbn + "' se ha borrado correctamente.");
            System.out.println("Documentos borrados: " + borrado.getDeletedCount() + ".");
        } catch (MongoException e) {
            System.err.println("Error: No se ha podido borrar el con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN " + isbn);
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
                this.libro.deleteOne(libroAntiguo);
                this.libro.insertOne(documentoNuevo);
            } else {
                this.libro.replaceOne(libroAntiguo, documentoNuevo);
            }
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
            System.out.println("El libro con el ISBN '" + isbn + "' se ha sustituido correctamente por el libro con el ISBN '" + libro.getIsbn() + "'.");
        } catch (MongoException e) {
            System.err.println("Error: No se ha podido modificar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    /*

    @Override
    public void buscar(int isbn) {
        try {
            Document filtro = new Document("isbn", isbn);
            Document documento = (Document) this.libro.find(filtro).first();
            if (documento == null) {
                System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
                return;
            }
            Libro libro = new Libro(
                documento.getInteger("isbn"),
                documento.getString("titulo"),
                documento.getString("autor"),
                documento.getString("editorial"),
                documento.getString("genero")
            );
            System.out.println("Libro encontrado con ISBN '" + isbn + "':");
            System.out.println(libro);
        } catch (MongoException e) {
            System.err.println("Error: No se ha podido buscar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void mostrar() {
        try (MongoCursor<Document> resultado = this.libro.find().iterator()) {
            if (!resultado.hasNext()) {
                System.out.println("No hay libros en la base de datos.");
                return;
            }
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                Libro libro = new Libro(
                    documento.getInteger("isbn"),
                    documento.getString("titulo"),
                    documento.getString("autor"),
                    documento.getString("editorial"),
                    documento.getString("genero")
                );
                System.out.println(libro);
            }
        } catch (MongoException e) {
            System.err.println("Error: No se han podido mostrar los libros en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    */

}