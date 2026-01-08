	package pruebasMongo;

	import com.mongodb.client.MongoClients;
	import com.mongodb.client.MongoClient;
	import com.mongodb.client.MongoCollection;
	import com.mongodb.client.MongoCursor;
	import com.mongodb.client.MongoDatabase;
	import com.mongodb.client.result.DeleteResult;
	import com.mongodb.client.result.UpdateResult;

	import org.bson.Document;

	public class PruebaMongo {

		MongoCollection depositos;
		MongoCollection dispensadores;

		public PruebaMongo() {
			
			MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
			MongoDatabase database = mongoClient.getDatabase("maquinaRefrescos");
			depositos = database.getCollection("depositos");
			dispensadores = database.getCollection("dispensadores");
			
		}

		public void leerDatos() {

			MongoCursor resultado = depositos.find().iterator();

			System.out.println("\n VER DATOS \n");		
			
			while (resultado.hasNext()) {

				Document doc = (Document) resultado.next();
				System.out.println(doc.getString("nombre") + " - " + doc.getInteger("valor"));

			}

		}

		public void insercionPrueba() {

			System.out.println("\n PRUEBA INSERCION \n Nombre Prueba y valor 500 \n ");		
			
			Document nuevo = new Document();
			nuevo.put("nombre", "prueba");
			nuevo.put("valor", 500);

			depositos.insertOne(nuevo);

			System.out.println("\n INSERCION CORRECTA \n");		
			
		}
		
		public void insercionPrueba2() {

			System.out.println("\n PRUEBA INSERCION CON FIND DE OTRA COLECCION ");		
			
			Document busqueda = new Document();
			busqueda.put("clave", "cola");
			
			Document proyeccion = new Document();
			proyeccion.put("_id", 0);
			
			MongoCursor resultado = dispensadores.find(busqueda).projection(proyeccion).iterator();
			Document doc = (Document) resultado.next();

		
			Document nuevo = new Document();
			nuevo.put("valor", "50000");
			nuevo.put("nombre", "prueba insert find sin _id");
			nuevo.put("dispensadorIntruso", doc);

			depositos.insertOne(nuevo);

			System.out.println("\n INSERCION CORRECTA \n");		
			
		}

		public void pruebaBusqueda() {

			System.out.println("\n BUSQUEDA VALOR 500 \n");		
			
			Document searchQuery = new Document();
			searchQuery.put("valor", "500");

			MongoCursor resultado = depositos.find(searchQuery).iterator();

			while (resultado.hasNext()) {

				Document doc = (Document) resultado.next();
				System.out.println(doc.getString("nombre") + doc.getString("valor"));

			}

		}

		public void pruebaUpdate() {

			System.out.println("\n PRUEBA ACTUALIZACIÓN \n Nombre Prueba y valor nuevo 1000 \n ");
			Document filtro = new Document();
			filtro.put("nombre", "prueba");
			Document actualizacion = new Document();
			actualizacion.put("$set", new Document("valor", 1000));
			UpdateResult resultado = depositos.updateOne(filtro, actualizacion);
			System.err.println(resultado.getModifiedCount() + " MODIFICADOS \n");
			System.out.println("\n ACTUALIZACIÓN CORRECTA \n");	


		}

		public void pruebaBorrado() {

			System.out.println("\n PRUEBA BORRADO \n Nombre Prueba y valor 500 \n");

			Document busqueda = new Document();
			busqueda.put("valor", 500);

			DeleteResult borrado = depositos.deleteOne(busqueda);
			System.err.println(borrado.getDeletedCount() + " BORRADOS \n");
			System.out.println("\n BORRADO CORRECTO \n");	
		}

	}
