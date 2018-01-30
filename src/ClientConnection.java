import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class ClientConnection extends Thread{
	protected Socket socket = null;
	
	public ClientConnection(String hostName, int portNumber)
			throws UnknownHostException, IOException{
		super("ClientConnection");
		this.socket = new Socket(hostName, portNumber);
		
	}
	
	public abstract void run();
}
