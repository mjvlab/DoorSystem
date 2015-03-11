package br.com.mjv.door.reader;

import java.util.logging.Logger;

import br.com.mjv.door.delegate.GpioDelegate;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

public class RfidReader
{
    // - Get an instance of Serial for COM interaction
    private final Serial serial = SerialFactory.createInstance();

    private final Logger fLogger=Logger.getLogger(this.getClass().getName());
    
    public RfidReader()
    {
        // - Change this to the COM port of your RFID reader
        //String comPort = "/dev/ttyUSB0";
    	String comPort = "/dev/ttyAMA0";
        
    	GpioDelegate.getInstance().startRfidReader();
    	
        // - Create and add a SerialDataListener
        serial.addListener(new SerialDataListener()
        {
            @Override
            public void dataReceived(SerialDataEvent event)
            {
            	
                HandleRfidReading reading;
                StringBuffer buffer;

                reading = new HandleRfidReading();
                buffer = new StringBuffer();

                // - Get byte array from SerialDataEvent
                byte[] data = event.getData().getBytes();
                
                // - Iterate byte array print a readable representation of each byte
                for ( int i=0; i < data.length; i++ )
                {
                	buffer.append(String.format("%x", data[i]));
                }
                
            	try {
            		
					reading.handleRfidReading(buffer.toString());
					
				} catch (Throwable t) {
					// TODO Auto-generated catch block
					t.printStackTrace();
				}

                // - Line break to represent end of data for this event
                System.out.println();
            }
        });

        fLogger.info("Listener configurado...");
        
        // - Attempt to open the COM port
        serial.open( comPort, 9600 );

        fLogger.info("Porta serial aberta. Pronto e aguardando...");

        while(true);

    }

    public static void main( String[] args )
    {
        new RfidReader();
    }
}