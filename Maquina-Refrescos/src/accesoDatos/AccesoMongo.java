package accesoDatos;

import java.util.HashMap;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoMongo implements I_Acceso_Datos {
	
    private MongoClient cliente;
    private MongoDatabase bbdd;
	private MongoCollection<Document> depositos;
	private MongoCollection<Document> dispensadores;

	public AccesoMongo() {
		this.cliente = MongoClients.create("mongodb://localhost:27017");
		this.bbdd = this.cliente.getDatabase("Maquina_refrescos");
		this.depositos = this.bbdd.getCollection("depositos");
		this.dispensadores = this.bbdd.getCollection("dispensadores");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> listaDepositos = new HashMap<>();
        try {

            MongoCursor<Document> resultado = this.depositos.find().iterator();
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                String nombre = documento.getString("nombre");
                int valor = documento.getInteger("valor");
                int cantidad = documento.getInteger("cantidad");
                Deposito deposito = new Deposito(nombre, valor, cantidad);
                listaDepositos.put(deposito.getValor(), deposito);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
		return listaDepositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> listaDispensadores = new HashMap<>();
        try {
            MongoCursor<Document> resultado = this.dispensadores.find().iterator();
            while (resultado.hasNext()) {
                Document documento = resultado.next();
                String clave = documento.getString("clave");
                String nombre = documento.getString("nombre");
                int precio = documento.getInteger("precio");
                int cantidad = documento.getInteger("cantidad");
                Dispensador dispensador=new Dispensador(clave, nombre, precio, cantidad);
                listaDispensadores.put(dispensador.getClave(), dispensador);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return listaDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean aux = true;
		for (Deposito deposito : depositos.values()) {
			try {
				Document filtro = new Document("valor", deposito.getValor());
				Document actualizacion = new Document("$set", new Document("cantidad", deposito.getCantidad()));
				UpdateResult resultado = this.depositos.updateOne(filtro, actualizacion);
			} catch (Exception e) {
                System.err.println(e.getMessage());
				aux = false;
			}
		}
		return aux;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean aux = true;
		for (Dispensador dispensador : dispensadores.values()) {
			try {
				Document filtro = new Document("clave", dispensador.getClave());
				Document actualizacion = new Document("$set", new Document("cantidad",dispensador.getCantidad()));
				UpdateResult resultado = this.dispensadores.updateOne(filtro,actualizacion);
			} catch (Exception e) {
                System.err.println(e.getMessage());
				aux = false;
			}
		}
		return aux;
	}

}