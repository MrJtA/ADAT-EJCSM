import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;

public class Consultas {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> vuelos;
    
    public Consultas() {

        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("Aerolínia");
        vuelos = database.getCollection("vuelos");

    }

    public void Consulta1() {

        Document filtro = new Document();
        filtro.put("codigo", "IB708");

        Document valor = new Document();
        valor.put("destino", 1);

        MongoCursor<Document> resultado = vuelos.find(filtro).projection(valor).iterator();
        
        if(!resultado.hasNext()){
            System.out.println("No hay resultados");
        }

        System.out.println("\n----Vuelo IB708----\n");
        while (resultado.hasNext()) {
            
            Document doc = resultado.next();
            System.out.println("Destino : "+doc.getString("destino")+"\n");
        }
    }

    public void Consulta2() {
        
        Document filtro = new Document("codigo","IB706");
        Document proyeccion = new Document("vendidos",1).append("_id",0);

        MongoCursor<Document> resultado = vuelos.find(filtro).projection(proyeccion).iterator();

        while (resultado.hasNext()) {
            
            Document doc = resultado.next();
            List<Document> vendidos = doc.getList("vendidos", Document.class);

            if (vendidos==null||vendidos.isEmpty()){
                System.out.println("No se han vendido billetes todavía");
                return;
            }

            System.out.println("\n---Billetes vendidos---\n");

            for (Document v : vendidos) {
                System.out.println("-------------------------------------------");
                System.out.println("Asiento : "+v.getInteger("asiento"));
                System.out.println("DNI : "+v.getString("dni"));
                System.out.println("Apellido : "+v.getString("apellido"));
                System.out.println("Nombre : "+v.getString("nombre"));
                System.out.println("Código venta : "+v.getString("codigoVenta"));
                System.out.println("-------------------------------------------\n");
            }
        }
    }

    public void Consulta3() {

        Document filtro = new Document("codigo","IB706").append("vendidos.asiento", 2);
        Document proyeccion = new Document("vendidos.$",1).append("_id",0);

        Document resultado = vuelos.find(filtro).projection(proyeccion).first();
        List<Document> vendidos = resultado.getList("vendidos", Document.class);

        System.out.println("\n---Pasajero vuelo IB706---\n");
        System.out.println("Nombre : "+vendidos.get(0).getString("nombre")+"\n");

        /*
        for (Document v : vendidos) {
            System.out.println("\n---Pasajero vuelo IB706---\n");
            System.out.println("Nombre : "+v.getString("nombre")+"\n");
        }
        */
    }

    public void Consulta4() {
        
    }
}
