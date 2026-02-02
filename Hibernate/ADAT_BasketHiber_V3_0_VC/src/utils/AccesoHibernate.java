package utils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.hibernate.*;

import modelo.Equipo;
import modelo.Jugador;
import modelo.JugadorPosicion;
import modelo.Posicion;

public class AccesoHibernate {

	Session session;

	public AccesoHibernate() {

		HibernateUtil util = new HibernateUtil();
		session = util.getSession();

	}

	public void borrarDatos() {
		System.out.println("Inicio Borrado");

		session.beginTransaction();

		TypedQuery q = session.createQuery("delete from JugadorPosicion");
		q.executeUpdate();
		q = session.createQuery("delete from Posicion");
		q.executeUpdate();
		q = session.createQuery("delete from Jugador");
		q.executeUpdate();
		q = session.createQuery("delete from Equipo");
		q.executeUpdate();

		session.getTransaction().commit();
		System.out.println("Fin Borrado");
	}

	public void consultaEquipo() {
		System.out.println("Inicio Consulta Simple Equipos");

		TypedQuery<Equipo> q = session.createQuery("from Equipo");
		List<Equipo> results = q.getResultList();

		Iterator equiposIterator = results.iterator();

		while (equiposIterator.hasNext()) {
			Equipo team = (Equipo) equiposIterator.next();

			System.out.println("		Id: " + team.getId() + " - Nombre: " + team.getNombre());

			Set<Jugador> jugadoresEquipo = team.getConjuntoJugadores();

			if(jugadoresEquipo!=null && jugadoresEquipo.size()>0 ){
				Iterator<Jugador> jugadoresIterator = jugadoresEquipo.iterator();
				System.out.println("				Lista de jugadores (" + jugadoresEquipo.size() +")");
				while (jugadoresIterator.hasNext()) {
					Jugador miJ = jugadoresIterator.next();
					Set<JugadorPosicion> pos = miJ.getPosiciones();
					System.out.println("				" + miJ.getNombre() + " Num posiciones " + pos.size());
				}				
			}else {
				System.out.println("				Equipo sin jugadores");
			}

		}

		System.out.println("Fin Consulta Equipos");
	}

	public void consultaJugadorPosicion() {
		System.out.println("Inicio Consulta");

		TypedQuery<JugadorPosicion> q = session.createQuery("select jp from JugadorPosicion jp order by jp.j.numero");
		List<JugadorPosicion> results = q.getResultList();

		Iterator<JugadorPosicion> jpIterator = results.iterator();
		int idJug;
		int auxId = 0;
		while (jpIterator.hasNext()) {
			JugadorPosicion jp = jpIterator.next();
			Jugador j = jp.getJ();
			idJug = j.getIdJugador();
			if (idJug != auxId) {
				System.out
						.println("(" + j.getNumero() + ") " + j.getNombre() + " (" + j.getEquipo().getNombre() + ") ");
			}
			System.out
					.println("		Posicion: " + jp.getP().getPosicion() + " - numero de Veces: " + jp.getNumVeces());
			auxId = idJug;

		}

		System.out.println("Fin Consulta");
	}

	public void insertarDatosPrueba() {
		System.out.println("Inicio Inserción Masiva");

		Equipo e1 = new Equipo();
		e1.setNombre("NIGHT TEAM");

		Equipo e2 = new Equipo();
		e2.setNombre("DREAM TEAM");

		Jugador j1 = new Jugador();
		j1.setNombre("Paco");
		j1.setNumero(5);
		j1.setEquipo(e1);

		Jugador j2 = new Jugador();
		j2.setNombre("Juan");
		j2.setNumero(6);
		j2.setEquipo(e1);

		Jugador j3 = new Jugador();
		j3.setNombre("Ana");
		j3.setNumero(7);
		j3.setEquipo(e1);

		Jugador j4 = new Jugador();
		j4.setNombre("Luis");
		j4.setNumero(8);
		j4.setEquipo(e1);

		Jugador j5 = new Jugador();
		j5.setNombre("Lola");
		j5.setNumero(9);
		j5.setEquipo(e1);

		Posicion p1 = new Posicion();
		p1.setPosicion("Pivot");
		p1.setDescripcion("Taponar");

		Posicion p2 = new Posicion();
		p2.setPosicion("Ala-Pivot");
		p2.setDescripcion("Rebotes y puntos");

		Posicion p3 = new Posicion();
		p3.setPosicion("Alero");
		p3.setDescripcion("De todo un poco");

		Posicion p4 = new Posicion();
		p4.setPosicion("Escolta");
		p4.setDescripcion("Tirador");

		Posicion p5 = new Posicion();
		p5.setPosicion("Base");
		p5.setDescripcion("Dirigir");

		JugadorPosicion jp1 = new JugadorPosicion();
		jp1.setJ(j1);
		jp1.setP(p1);
		jp1.setNumVeces(2);

		JugadorPosicion jp2 = new JugadorPosicion();
		jp2.setJ(j1);
		jp2.setP(p2);
		jp2.setNumVeces(3);

		JugadorPosicion jp3 = new JugadorPosicion();
		jp3.setJ(j2);
		jp3.setP(p2);
		jp3.setNumVeces(1);

		JugadorPosicion jp4 = new JugadorPosicion();
		jp4.setJ(j4);
		jp4.setP(p4);
		jp4.setNumVeces(4);

		JugadorPosicion jp5 = new JugadorPosicion();
		jp5.setJ(j5);
		jp5.setP(p5);
		jp5.setNumVeces(4);

		JugadorPosicion jp6 = new JugadorPosicion();
		jp6.setJ(j3);
		jp6.setP(p3);
		jp6.setNumVeces(2);

		JugadorPosicion jp7 = new JugadorPosicion();
		jp7.setJ(j3);
		jp7.setP(p4);
		jp7.setNumVeces(2);

		// Utilizamos hibernate para guardar objetos

		session.beginTransaction();

		session.save(e1);
		session.save(e2);

		session.save(j1);
		session.save(j2);
		session.save(j3);
		session.save(j4);
		session.save(j5);

		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);

		session.save(jp1);
		session.save(jp2);
		session.save(jp3);
		session.save(jp4);
		session.save(jp5);
		session.save(jp6);
		session.save(jp7);

		session.getTransaction().commit();

		System.out.println("Fin Inserción Masiva");
	}

	public void cerrarSesion() {

		session.close();

	}

}
