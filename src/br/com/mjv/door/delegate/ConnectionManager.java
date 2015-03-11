package br.com.mjv.door.delegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionManager {

	private EntityManager em;
	private static ConnectionManager instance;
	
	public static ConnectionManager getInstance(){
		
		if (instance == null){
			instance = new ConnectionManager();
		}
		
		return instance;
		
	}
	
	private ConnectionManager(){
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DoorSystem");

		em = emf.createEntityManager();

	}
	
	public EntityManager getEntityManager(){
		return em;
	}
	
}
