import java.io.BufferedReader;
import java.io.IOException;

public class ControlConnectionListener extends Thread{
	BufferedReader in;
    String command;
    Boolean listenerHasLock;
    DataConnection dataConnection;
	
	public ControlConnectionListener(BufferedReader in) {
		this.in = in;
        this.command = "  ";
        listenerHasLock = true;
        dataConnection = null;

	}

	public void updateCommand(String command){
        this.command = command;
    }
	
	public void run() {
		String output;
		
		try {
		    if(in != null){
		        //System.out.println("buffer not null");
                while((output=in.readLine())!=null){
                   // System.out.println("stream available");
                    listenerHasLock = true;
                    System.out.println(output);
    				if(output.contains("220 ") || output.contains("226 ") || output.contains("230 ") || output.contains("250 ")
                            || output.contains("211 End")){

                        System.out.print("csftp> ");

                    } else if (output.contains("221 ")) {
                        System.exit(0);
                        return;
                    } else if (output.contains("550 ")) {
                        dataConnection.interrupt();


                        System.out.print("csftp> ");
                    } else if (output.matches(".*\\(\\d*,\\d*,\\d*,\\d*,\\d*,\\d*\\).*")) {
                        String[] pasvAdr = Utility.extractAdr(output);

                        if (dataConnection != null) {

                            //dataConnection.socket.close();
                        }
                        dataConnection = new DataConnection(pasvAdr[0], Integer.parseInt(pasvAdr[1]));
                        dataConnection.start();
                    }
                    //System.out.println("listener resting");
                    listenerHasLock = false;
                }
            }





		}catch(IOException e){
			System.err.println("0xFFFD Control connection I/O error, closing control connection");
			System.exit(1);
		}

	}
}
