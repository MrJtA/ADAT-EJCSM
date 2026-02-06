import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class Vuelos {
    
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> vuelos;

    public Vuelos() {

        mongoClient=MongoClients.create("mongodb://localhost:27017");
        database=mongoClient.getDatabase("aerolinia");
        vuelos=database.getCollection("vuelos");
    }

    public void Query1() {

        Document filtro=new Document("codigo","IB706");
        Document proyeccion=new Document("_id",0).append("destino",1);

        MongoCursor<Document>resultado=vuelos.find(filtro).projection(proyeccion).iterator();

        while (resultado.hasNext()) {
            
            Document doc=resultado.next();

            System.out.println("\nDestino : "+doc.getString("destino"));
        }
    }

    public void Query2() {

        Document filtro=new Document("codigo","IB706");
        Document proyeccion=new Document("vendidos",1).append("_id",0);

        MongoCursor<Document>resultado=vuelos.find(filtro).projection(proyeccion).iterator();

        while (resultado.hasNext()) {
            
            Document doc=resultado.next();
            List<Document>vendidos=doc.getList("vendidos", Document.class);

            for (Document v : vendidos) {
                
                System.out.println("---------------------------------------");
                System.out.println("Asiento : "+v.getInteger("asiento"));
                System.out.println("DNI : "+v.getString("dni"));
                System.out.println("Apellido : "+v.getString("apellido"));
                System.out.println("Nombre : "+v.getString("nombre"));
                System.out.println("---------------------------------------");
            }
            
        }
    }

    public void Query2version() {

        Document filtro=new Document("codigo","IB706");
        Document proyeccion=new Document("vendidos",1).append("_id",0);

        Document resultado=vuelos.find(filtro).projection(proyeccion).first();

        List<Document>vendidos=resultado.getList("vendidos", Document.class);

        for (Document v : vendidos) {
                
                System.out.println("\n---------------------------------------");
                System.out.println("Asiento : "+v.getInteger("asiento"));
                System.out.println("DNI : "+v.getString("dni"));
                System.out.println("Apellido : "+v.getString("apellido"));
                System.out.println("Nombre : "+v.getString("nombre"));
                System.out.println("---------------------------------------");
            }
    }

    public void Query3() {

        Document filtro=new Document("codigo","IB706").append("vendidos.asiento", 2);
        Document proyeccion=new Document("vendidos.nombre.$",1).append("_id",0);

        Document resultado=vuelos.find(filtro).projection(proyeccion).first();

        List<Document>vendidos=resultado.getList("vendidos", Document.class);

        System.out.println("\nNombre : "+vendidos.get(0).getString("nombre"));
    }

    public void QueryPrueba() {

        Document filtro=new Document("plazas_disponibles",new Document("$gt",10));
        Document proyeccion=new Document("_id",0).append("origen", 1).append("destino", 1);

        MongoCursor<Document>resultado=vuelos.find(filtro).projection(proyeccion).iterator();

        while (resultado.hasNext()) {
            
            Document doc=resultado.next();

            System.out.println("\nOrigen : "+doc.getString("origen"));
            System.out.println("Destino : "+doc.getString("destino"));
        }
    }

    public void Query4() {

        Document filtro=new Document("destino","MURCIA");

        Document billete=new Document("asiento",2)
        .append("dni", "33344455G")
        .append("apellido", "Rodriguez")
        .append("nombre", "Fabio");

        Document update=new Document("$inc",new Document("plazas_disponibles",-1))
        .append("$pull", new Document("asientos_disponibles",2))
        .append("$push", new Document("vendidos",billete));

        UpdateResult resultado=vuelos.updateOne(filtro, update);

        if (resultado.getModifiedCount()>0) {
            System.out.println("\nSe ha añadido el billete");
        }else {
            System.out.println("\nNo se ha podido añadir el billete");
        }

        System.out.println("Concidencias : "+resultado.getMatchedCount());
    }

    public void Query5() {

        Document filtro=new Document("destino","MURCIA").append("vendidos.asiento", 2);

        Document campos=new Document("vendidos.$.dni","6666G")
        .append("vendidos.$.apellido", "Licea")
        .append("vendidos.$.nombre", "Fabio");

        Document update=new Document("$set",campos);

        UpdateResult resultado=vuelos.updateOne(filtro,update);

        if (resultado.getModifiedCount()>0) {
            System.out.println("\nSe ha cambiado los datos del billete");
        }else {
            System.out.println("\nNo se ha podido realizar los cambios en el billete");
        }

        System.out.println("Condiciones : "+resultado.getMatchedCount());
    }

    public void Query6() {

        Document filtro=new Document("destino","MURCIA")
        .append("vendidos.asiento",2);

        Document delete=new Document("asiento",2);

        Document update=new Document("$inc",new Document("plazas_disponibles",+1))
        .append("$push",new Document("asientos_disponibles",2))
        .append("$pull", new Document("vendidos",delete));

        UpdateResult resultado=vuelos.updateOne(filtro, update);

        if (resultado.getModifiedCount()>0) {
            System.out.println("\nSe ha cancelado la compra del billete");
        }else {
            System.out.println("\nNo se ha podido cancelar la compra del billete");
        }

        System.out.println("Condiciones : "+resultado.getMatchedCount());
    }
}
