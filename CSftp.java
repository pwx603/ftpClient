import java.io.*;
import java.net.*;

public class FTPClient {
    public static void main() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("csftp> ");

        String hostName = userInput.readLine();
        int portNumber = Integer.parseInt(args[1]);

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
                }*/
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}