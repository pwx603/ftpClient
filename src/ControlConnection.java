import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class ControlConnection extends ClientConnection {
	BufferedReader stdIn;
	String hostName;
	Integer portNumber;
	
	public ControlConnection(String hostName, int portNumber) 
			throws UnknownHostException, IOException {
        super(hostName, portNumber);
	    this.hostName = hostName;
	    this.portNumber = portNumber;

		this.stdIn = CSftp.getStdIn();
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

				ControlConnectionListener ccListener = new ControlConnectionListener(in);
				ccListener.start();
                //System.out.println("waiting for input");

                //System.out.println("don't have lock");

				while(true) {
				    //while(ccListener.listenerHasLock == false){

                        //System.out.print("csftp> ");

                        try{
                            inputLine = stdIn.readLine();
                        }catch(IOException e){
                            System.err.println("0xFFFE Input error while reading commands, terminating.");
                            System.exit(1);
                            return;
                        }
                    //}


					command = FtpProtocol.processCommand(inputLine);
                   // System.out.println("Command translated: "+command);
                    ccListener.updateCommand(command);
                    //System.out.println("Command translated updated: "+command);
                    if(command.equals(" ")){
                        System.err.println("0x002 Invalid number of arguments");
                        System.out.print("csftp> ");
                        continue;
                    }else
					if(command.equals("")) {
						System.err.println("0x001 Invalid Command");
                        System.out.print("csftp> ");
						continue;
					}else {
						//System.out.println("Client: " + inputLine);
						//System.out.println("what's the command: " + command);

						if(command.contains("PASV")&&command.contains("RETR")){

							String[] commandArgs = command.split(" ");

							out.println(commandArgs[0]);

							CSftp.enableRetr();
							out.println(commandArgs[1] + " " + commandArgs[2]);
							CSftp.setFileName(commandArgs[2]);

						}else if(command.contains("PASV")&&command.contains("LIST")){
							String[] commandArgs = command.split(" ");
                            CSftp.disableRetr();
							out.println(commandArgs[0]);
							out.println(commandArgs[1]);
						}else {
							out.println(command);
							//printResponse(in);
						}

					}
					continue;
			}
				//socket.close();
					
			}catch(UnknownHostException e) {
				System.err.println("0xFFFC Control connection to "+hostName+" on port "+portNumber.toString()+" failed to open.");
				System.exit(1);
				return;
			}catch(IOException e){
				System.err.println("0xFFFD Control connection I/O error, closing control connection.");
				System.exit(1);
				return;
			}
	}
	

  

}
