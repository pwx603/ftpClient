import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class ControlConnection extends ClientConnection {
	BufferedReader stdIn;
	public volatile static boolean passMode;
	
	public ControlConnection(String hostName, int portNumber) 
			throws UnknownHostException, IOException {
		super(hostName, portNumber);
		this.stdIn = new BufferedReader(
				new InputStreamReader(System.in));
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
		
	public void run() {
		try(
				PrintWriter out = 
					new PrintWriter(socket.getOutputStream(),true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()))
			){
				String inputLine = "";
				String command;
				
				ControlConnectionListener cc = new ControlConnectionListener(in);
				cc.start();
				
				
				while(true) {
					TimeUnit.MILLISECONDS.sleep(600);
					System.out.print("csftp> ");
					
					try {
						inputLine = stdIn.readLine();
					}catch(IOException e) {
						System.err.println("0xFFFE Input error while reading commands, terminating.");
						System.exit(1);
					}
					
					command = FtpProtocol.processCommand(inputLine);
					
					if(inputLine.equals("")||inputLine.charAt(0)=='#'||inputLine.split("\\s+").length > 2) {
						continue;
					}
					
					if(!passMode && command.equals("")) {
						System.err.println("0x001 Invalid Command");
						continue;
					}else {
						
						if(command.contains("PASV")&&command.contains("RETR")){
							
							String[] commandArgs = command.split(" ");
							
							out.println(commandArgs[0]);
							Utility.echoClient(commandArgs[0]);
							
							CSftp.enableRetr();
							out.println(commandArgs[1] + " " + commandArgs[2]);
							Utility.echoClient(commandArgs[1] + " " + commandArgs[2]);
							CSftp.setFileName(commandArgs[2]);
							
						}else if(command.contains("PASV")&&command.contains("LIST")){
							String[] commandArgs = command.split(" ");
							
							out.println(commandArgs[0]);
							Utility.echoClient(commandArgs[0]);
							out.println(commandArgs[1]);
							Utility.echoClient(commandArgs[1]);
						}else if(passMode){
							out.println("PASS " + inputLine);
							Utility.echoClient("PASS " + inputLine);
							disablePassMode();
						}else{
							out.println(command);
							Utility.echoClient(command);
						}
						
					}
					
					synchronized(in) {
						if(CSftp.getRetrMode()){
							in.wait();
						}
					}
				}					
			}catch(UnknownHostException e) {
				System.err.println("0xFFFC Control connection to " 
						+ hostName +" on port "
						+ Integer.toString(portNumber) 
						+ " failed to open.");
				System.exit(1);
			}catch(IOException e){
				System.err.println("0xFFFD Control connection I/O error, closing control connection.");
				System.exit(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static void enablePassMode(){
		passMode = true;
	}
	
	public static void disablePassMode(){
		passMode = false;
	}
}
