package br.com.mjv.door.delegate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.mjv.door.model.TbAction;
import br.com.mjv.door.model.TbRfidReading;
import br.com.mjv.door.model.TbUser;


public class RfidDelegate {

	private EntityManager em;
	
	public RfidDelegate(){
		
		em = ConnectionManager.getInstance().getEntityManager();
		
	}

	public TbUser getUserByRfid(String rfid){
		
		Query q;
		
		q = em.createQuery("select t from TbUser t where t.nmIdRfid = ?1");
		
		q.setParameter(1, rfid);
		
		return (TbUser)q.getSingleResult();
		
	}
	
	public Object saveObject(Object object){
		
		Object returnValue;
		EntityTransaction et;
		
		et = em.getTransaction();
		
		et.begin();
		
		returnValue = em.merge(object);

		et.commit();
		
		return returnValue;
		
	}
	
	public void removeObject(Object object){
		
		
		em.getTransaction().begin();
		
		em.remove(object);

		em.getTransaction().commit();
		
	}
	
	public TbAction getActionByName(String name){
		
		Query q;
		
		q = em.createQuery("select t from TbAction t where t.nmAction = ?1");
		
		q.setParameter(1, name);
		
		return (TbAction)q.getSingleResult();

	}
	
	public TbRfidReading getLastReading(){
		
		Query q;
		
		q = em.createQuery("select t from TbRfidReading t order by t.dtReading desc");
		
		q.setMaxResults(1);
		
		return (TbRfidReading)q.getSingleResult();
		
	}
	
}
