package br.com.mjv.door.reader;

import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import com.pi4j.io.gpio.PinState;

import br.com.mjv.door.delegate.GpioDelegate;
import br.com.mjv.door.delegate.RfidDelegate;
import br.com.mjv.door.model.TbAction;
import br.com.mjv.door.model.TbRfidReading;
import br.com.mjv.door.model.TbUser;
import br.com.mjv.speechlab.SpeechThread;

public class HandleRfidReading {

	private String ACTION_NON_AUTHORIZED = "non_authorized_access";
	private String ACTION_ADMIN_USER = "admin_user";
	private String ACTION_CREATE_USER = "create_user";
	private String ACTION_OPEN_DOOR = "open_door";
	
	private final Logger fLogger=Logger.getLogger(this.getClass().getName());

	public synchronized void handleRfidReading(String rfid){
		
		RfidDelegate delegate;
		TbRfidReading tbRfidReading, lastRecording;
		TbUser tbUser;
		TbAction tbAction;
		SpeechThread thread;
		
		tbUser = null;
		
		//PRIMEIRO LOG RFID DETECTADO E PROCURANDO NA BASE DE DADOS
		
		fLogger.info("Rfid detectado: '" + rfid + "'");
		
		delegate = new RfidDelegate();
		
		try {
			lastRecording = delegate.getLastReading();
		} catch (NoResultException e1) {
			lastRecording = null;
			
		}
		
		try {
			
			tbUser = delegate.getUserByRfid(rfid);
			
			//SEGUNDO LOG USUARIO ENCONTRADO NA BASE DE DADOS
			
			fLogger.info("Usuario com rfid = '" + rfid + "' foi encontrado na base de dados. Nome: '" + tbUser.getNmUser() + "'");
			
			
			// Se o usuario existe...

			if (tbUser.getFgIsMasterUser() == 1){
					
				tbAction = delegate.getActionByName(ACTION_ADMIN_USER);
				
				//TERCEIRO LOG USUARIO ADMINISTRADOR ENCONTRADO
				fLogger.info("Usuario com rfid = '" + rfid + "' e um administrador...");
				
				//ABRINDO PORTA APIS ACENDER LEDS AZUL E VERDE
				
				try {
					
				
					GpioDelegate.getInstance().changeGreenLedState(PinState.HIGH);
					GpioDelegate.getInstance().changeBlueLedState(PinState.HIGH);
					//GpioDelegate.getInstance().changeDoorState(PinState.HIGH);
					
					Thread.sleep(500);
					
					GpioDelegate.getInstance().changeGreenLedState(PinState.LOW);
					GpioDelegate.getInstance().changeBlueLedState(PinState.LOW);
					//GpioDelegate.getInstance().changeDoorState(PinState.LOW);

					Thread.sleep(500);

					GpioDelegate.getInstance().changeGreenLedState(PinState.HIGH);
					GpioDelegate.getInstance().changeBlueLedState(PinState.HIGH);
					
					Thread.sleep(500);

					GpioDelegate.getInstance().changeGreenLedState(PinState.LOW);
					GpioDelegate.getInstance().changeBlueLedState(PinState.LOW);
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					
				}
			
			} else {
				
				tbAction = delegate.getActionByName(ACTION_OPEN_DOOR);
				
				//ACENDENDO LED AZUL INDICANDO ACESSO NEGADO AQUELE RFID
				
			}

			tbRfidReading = new TbRfidReading();
			
			tbRfidReading.setDtReading(new Date());
			tbRfidReading.setTbUser(tbUser);
			tbRfidReading.setNmIdRfid(rfid);
			tbRfidReading.setTbAction(tbAction);
			
			delegate.saveObject(tbRfidReading);
			
			//QUARTO LOG ABERTURA DE PORTA AUTORIZADA

			fLogger.info("Abertura de porta autorizada para '" + tbUser.getNmUser() + "'");
			
			//thread = new SpeechThread("WELCOME", tbUser.getNmUser());
			
			//thread.run();

			try {
				
				GpioDelegate.getInstance().changeGreenLedState(PinState.HIGH);
				GpioDelegate.getInstance().changeDoorState(PinState.HIGH);
				
				Thread.sleep(500);
				
				GpioDelegate.getInstance().changeDoorState(PinState.LOW);
				
				Thread.sleep(500);

				GpioDelegate.getInstance().changeGreenLedState(PinState.LOW);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			// Se usuario nao existe...
			
		} catch (NoResultException e) {
			
			if (lastRecording != null && lastRecording.getTbAction().getNmAction().equals(ACTION_ADMIN_USER)){
				
				tbUser = new TbUser();

				tbAction = delegate.getActionByName(ACTION_CREATE_USER);
				
				tbUser.setDtCraeate(new Date());
				tbUser.setFgIsMasterUser(0);
				tbUser.setNmIdRfid(rfid);
				tbUser.setNmUser("Visitante");
				
				tbUser = (TbUser)delegate.saveObject(tbUser);
				
				tbRfidReading = new TbRfidReading();
				
				tbRfidReading.setDtReading(new Date());
				tbRfidReading.setTbUser(tbUser);
				tbRfidReading.setNmIdRfid(rfid);
				tbRfidReading.setTbAction(tbAction);
				
				delegate.saveObject(tbRfidReading);
				
				//QUINTO LOG USUARIO CADASTRADO COM SUCESSO

				fLogger.info("Usuario com rfid = '" + rfid + "' foi cadastrado na base de dados com o nome: '" + tbUser.getNmUser() + "'");

				//thread = new SpeechThread("WELCOME", tbUser.getNmUser());
				
				//thread.run();

				try {
					
				
					GpioDelegate.getInstance().changeGreenLedState(PinState.HIGH);
					GpioDelegate.getInstance().changeDoorState(PinState.HIGH);
					Thread.sleep(500);
					
					GpioDelegate.getInstance().changeGreenLedState(PinState.LOW);
					Thread.sleep(500);
					
					GpioDelegate.getInstance().changeGreenLedState(PinState.HIGH);
					Thread.sleep(500);
				
					GpioDelegate.getInstance().changeGreenLedState(PinState.LOW);
					GpioDelegate.getInstance().changeDoorState(PinState.LOW);
				
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}
				
			} else {
				
				tbAction = delegate.getActionByName(ACTION_NON_AUTHORIZED);

				tbRfidReading = new TbRfidReading();

				tbRfidReading.setDtReading(new Date());
				tbRfidReading.setNmIdRfid(rfid);
				tbRfidReading.setTbAction(tbAction);
				
				delegate.saveObject(tbRfidReading);
				
				//SEXTO LOG ABERTURA NÌO AUTORIZADA PARA USUARIO
				
				fLogger.info("Abertura de porta NAO autorizada para usuario com rfid = '" + rfid + "'");
				
				//thread = new SpeechThread("UNKOWN", tbUser.getNmUser());
				
				//thread.run();
				
			try {
				GpioDelegate.getInstance().changeBlueLedState(PinState.HIGH);
				
				Thread.sleep(1000);
				
				GpioDelegate.getInstance().changeBlueLedState(PinState.LOW);
				
			} catch (InterruptedException e1) {
				e.printStackTrace();
			}

			}
			
		}

	}
	
}
