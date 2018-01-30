import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class DataConnection extends ClientConnection{
	public DataConnection(String hostName, int portNumber) 
			throws UnknownHostException, IOException {
		super(hostName, portNumber);
	}
	
	public void run() {
		try(
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()))
			){
			
			String output;
			
			if(CSftp.getRetrMode()) {
				FileOutput.fileTransfer(socket.getInputStream(), CSftp.getFileName());	

			}else{
				while((output=in.readLine())!=null){
					System.out.println(output);
				}	
			}
			
			socket.close();	
		}catch(UnknownHostException e) {
			System.err.println("0x3A2 Data transfer connection to " 
					+ hostName + " on port " + portNumber 
					+ " failed to open.");
			System.exit(1);
		}catch(IOException e){
			System.err.println("asdfasadf0x3A7 Data transfer connection I/O error, closing data connection.");
			System.exit(1);
		}
	}
}
