import java.io.BufferedReader;
import java.io.IOException;

public class ControlConnectionListener extends Thread{
	BufferedReader in;
	
	public ControlConnectionListener(BufferedReader in) {
		this.in = in;
	}
	
	public void run() {
		String output;
		
		try {
			
			while((output=in.readLine())!=null){
					if(output.contains("421")) {
						Utility.echoServer("\n" + output);
						System.exit(1);
					}
					
					Utility.echoServer(output);
					if(output.matches(".*\\(\\d*,\\d*,\\d*,\\d*,\\d*,\\d*\\).*")) {
						String[] pasvAdr = Utility.extractAdr(output);
						
						new DataConnection(pasvAdr[0], Integer.parseInt(pasvAdr[1])).start();
					}
	
					if(output.contains("226 Transfer complete.")) {
							CSftp.disableRetr();
							synchronized(in) {
								in.notify();
							}
					}
						
					if(output.contains("550")) {
						CSftp.disableRetr();
						synchronized(in) {
							in.notify();
						}
					}
					
					if(output.contains("331")) {
						ControlConnection.enablePassMode();

					}
					
					if(output.contains("221")) {
						System.exit(0);
					}
					
					if(output.contains("530")) {
						synchronized(in) {
							in.notify();
						}
					}
					
			}
			
		}catch(IOException e){
			System.err.println("0xFFFD Control connection I/O error, closing control connection");
			System.exit(1);
		}

	}
}
