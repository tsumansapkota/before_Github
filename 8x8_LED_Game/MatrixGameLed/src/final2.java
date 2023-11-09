import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;

public class final2 {
	private SerialPort ports[];
	private SerialPort port ;
	private int chosenPort;
	private Scanner data;
	public OutputStream send;
	
	public void scanAndSelectPort(){
		ports= SerialPort.getCommPorts();
		System.out.println("Select a port:");
		int i = 1;
	    for(SerialPort port : ports) {
	        System.out.println(i++ + ". " + port.getSystemPortName());
	    }
	    Scanner s = new Scanner(System.in);
	    chosenPort = s.nextInt();
	    port = ports[chosenPort - 1];
	    s.close();
	    
	}
	public boolean portOpen(){
		if(port.openPort()) {
	        return true;
	    } else {
	       return false;
	    }
	}
	public void selectPort(String portName){
		ports= SerialPort.getCommPorts();
		int i=0;
		for(SerialPort port : ports) {
			System.out.println("." + port.getSystemPortName());
	        if(port.getSystemPortName().contains(portName)){
	        	this.port=ports[chosenPort=i];break;}
	        i++;
	    }

		
	}
	public void startPortReceiving(){
		port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		data = new Scanner(port.getInputStream());
	}
	public void startPortSending(){
	    port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		send=port.getOutputStream();
		port.setBaudRate(9600);
		port.setNumDataBits(8);
		port.setNumStopBits(1);
	}
	public void sendString(String in) throws IOException{
		in.concat("\n");
		send.write(in.getBytes());		
	}
	public void sendInt(short in) throws IOException{
		byte low=(byte) (in%256);
		System.out.println((int)low);
		byte high=(byte) (in>>>8);
		System.out.println((int)high);
		send.write(low);
		send.write(high);
		
	}
	public void sendByte(byte in) throws IOException{
		send.write(in);
		
	}
	public boolean incoming(){
		if (data.hasNext()) {
			return true;
		}
		else return false;
	}
	public String getStringData(){
		return data.nextLine();
	}
	public byte getByteData(){
		return data.nextByte();
	}
	public void close(){
		port.closePort();
	}

}
