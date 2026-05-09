package accesodatos_alumnos;

import com.mongodb.MongoException;
import com.mongodb.client.*;

import modelo.MusicBand;

import org.bson.Document;

import java.util.List;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.w3c.dom.DocumentType;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class IntermediarioMongo {
	
	/*
	 * Crear atributos si es necesario
	 */

	private String conexion;
    private String nombreBBDD;
    private MongoClient cliente;
    private MongoDatabase bbdd;
    private MongoCollection<Document> documentoGrupo;
	
	/*
	 * Constructor. Se puede utilizar para crear la conexión
	 */
	
	public IntermediarioMongo() {
		this.conexion = "mongodb://localhost:27017";
        this.nombreBBDD = "examen";
        this.cliente = MongoClients.create(this.conexion);
        this.bbdd = this.cliente.getDatabase(this.nombreBBDD);
        this.documentoGrupo = this.bbdd.getCollection("grupos");
	}
	
	/*
	 * Formato de salida para leerInfoGrupos
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Extremoduro 
		ID: 2 - Mecano
		--------------------------------------------------------------
	
	 *
	 * Formato de salida avanzado (mostrando también albumes)
	 * 
	 	--------------------------------------------------------------
	 	
		ID: 1 - Extremoduro: Pedrá, Historias prohibidas, Deltoya
		ID: 2 - Mecano: Mecano: Ailala, Grandes éxitos
		
		--------------------------------------------------------------		
		
	 * 
	 * 
	 */

	public void leerInfoGrupos() {

        try (MongoCursor<Document> resultado = this.documentoGrupo.find().iterator()) {
			System.out.println("--------------------------------------------------------------");
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                String id = documento.getString("id");
                String nombre = documento.getString("nombre");
				Document proyeccion=new Document("albumes",1).append("_id",0);
				System.out.print("ID: " + id + " - " + nombre + ": ");
				MongoCursor<Document>lista = documentoGrupo.find(documento).projection(proyeccion).iterator();
				while (lista.hasNext()) {
					Document doc=lista.next();
					List<Document>albumes=doc.getList("albumes", Document.class);
					for (Document a : albumes) {				
						System.out.print(a.getString("tituloAlbum") + ", ");
					}					
				}
				System.out.println();
            }
			System.out.println("--------------------------------------------------------------");
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }

	}

	
	/*
	 * Insertar grupo
	 */
	
	public void insertarGrupo(String nombregrupo) {
		MusicBand grupo = new MusicBand(nombregrupo);
		int id = (int) (Math.random()*10000) + 67;
		grupo.setId(id);
        try {
            Document documento = new Document();
            documento.put("id", id);
            documento.put("nombre", nombregrupo);
            this.documentoGrupo.insertOne(documento);
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }

	}

	/*
	 * Formato de salida para buscar grupo por nombre (ej LIKE Dire)
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Dire Straits
		ID: 2 - Directos al mar
		--------------------------------------------------------------
	
	 *
	 * Formato de salida avanzado (mostrando también albumes)
	 * 
	 	--------------------------------------------------------------
	 	
		ID: 1 - Dire Straits: Alchemy, Making movies
		ID: 2 - Directos al mar: Uno de prueba
		
		--------------------------------------------------------------		
		
	 * 
	 * 
	 */

	public void buscarGrupoXNombre(String nombre) {
		try {
            Document filtro = new Document("nombre", nombre);
            Document documento = (Document) this.documentoGrupo.find(filtro).first();
            if (documento == null) {
                System.out.println("Error: No existe ningún grupo con ese nombre.");
                return;
            }
			String id = documento.getString("id");
			System.out.print("ID: " + id + " - " + nombre + ": ");
			Document proyeccion=new Document("albumes",1).append("_id",0);
			MongoCursor<Document>lista = documentoGrupo.find(documento).projection(proyeccion).iterator();
			while (lista.hasNext()) {
				Document doc=lista.next();
				List<Document>albumes=doc.getList("albumes", Document.class);
				for (Document a : albumes) {				
					System.out.print(a.getString("tituloAlbum") + ", ");
				}			
			}
			System.out.println();
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }
	}

    public void addAlbum(String nombreGrupo, String album) {

		/* 
		try {
            Document filtro = new Document("nombre", nombreGrupo);
            Document documento = (Document) this.documentoGrupo.find(filtro).first();
            if (documento == null) {
                System.out.println("Error: No existe ningún grupo con ese nombre.");
                return;
            }
			Document proyeccion=new Document("albumes",1).append("_id",0);
			MongoCursor<Document>lista = documentoGrupo.find(documento).projection(proyeccion).iterator();
			while (lista.hasNext()) {
				Document doc=lista.next();
				List<Document>albumes=doc.getList("albumes", Document.class);
				for (Document a : albumes) {				
					System.out.print(a.getString("tituloAlbum") + ", ");
				}			
			}
			System.out.println("Album añadido");
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }
			*/

    }
	
	


}
