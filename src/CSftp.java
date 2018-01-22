import java.io.*;
import java.net.*;

public class CSftp {
    public static void main(String[] args) throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String hostName = args[0];;
        int portNumber;
        if(args.length == 2){
            portNumber = Integer.parseInt(args[1]);
        }else if (args.length == 1){
            portNumber = 21;
        }else {
            System.out.println("0x002 Incorrect number of arguments");
            System.exit(1);
            return;
        }

        try (
             Socket kkSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()))
        ) {

            System.out.println(in.readLine());
            String serverResponse;
            while(true){
                System.out.print("csftp> ");
                //serverResponse = in.readLine();
                /*if(serverResponse == "\\421\\s"){
                    System.out.println(serverResponse);
                    System.exit(1);
                    return;
                }*/
                String command = userInput.readLine();
                String[] splitCommands = command.split("\\s");

                if(splitCommands.length == 2){
                    if(splitCommands[0].equals("user")){
                        out.println("USER "+splitCommands[1]);
                    }
                }
                //Todo: This doesn't work yet
                serverResponse = in.readLine();
                while(serverResponse.equals("230-\\.")){
                    System.out.println(serverResponse);
                    serverResponse = in.readLine();
                }


            }

        } catch (UnknownHostException e) {
            System.err.println("0xFFFC Control connection to "+hostName+" on port "+portNumber+" failed to open");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("0xFFFD Control connection I/O error, closing control connection ");
            System.exit(1);
        }



        /*
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            int x = 10;
            while ((fromServer = in.readLine()) != null && (x > 0)) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = "Who's there?";
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
                x--;
                //fromUser = stdIn.readLine();
                /*if(fromUser == null){
                    fromUser = "Who's there?";
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }else
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
        */
    }
}