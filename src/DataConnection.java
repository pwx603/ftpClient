import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class DataConnection extends ClientConnection{
    String hostName;
    Integer portNumber;


	public DataConnection(String hostName, int portNumber) 
			throws UnknownHostException, IOException {
		super(hostName, portNumber);
		this.hostName = hostName;
		this.portNumber = portNumber;
	}

	
	public void run() {
       //while(running == true) {
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))
            ) {

                //System.out.println("Successfully connected to dataConnection");

                String output;

                if (CSftp.getRetrMode()) {

                    FileOutput.fileTransfer(socket.getInputStream(), CSftp.getFileName());

                    socket.close();

                } else {
                    while ((output = in.readLine()) != null) {
                        System.out.println(output);
                    }
                }
                socket.close();
            } catch (UnknownHostException e) {
                System.err.println("0x3A2 Data transfer connection to " + hostName + " on port " + portNumber + " failed to open.");

            } catch (IOException e) {
                System.err.println(e);
                System.err.println("0x3A7 Data transfer connection I/O error, closing data connection.");

            }


    }
}
