import java.io.*;
import java.net.*;

public class CSftp {
    public static Boolean needPass = false;
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
                String[] splitCommands = command.split("\\s", 2);
                String commandArg1 = splitCommands[0];
                if(splitCommands.length == 2){

                    String commandArg2 = splitCommands[1];
                    if(needPass){
                        if(commandArg1.equals("pw")){
                            out.println("PASS "+commandArg2);
                        }
                    }else{
                        switch(commandArg1.toLowerCase()){
                            case "user":
                                out.println("USER "+commandArg2);
                                break;
                            case "pw":
                                if(needPass){
                                    out.println("PASS "+commandArg2);
                                }else{
                                    System.out.println("0x001 Invalid command");
                                    System.exit(1);
                                    return;
                                }
                                break;
                            case "get":
                                getCommand(out, in , commandArg2);
                                break;
                        }
                    }
                    printResponse(in);

                }
            }

        } catch (UnknownHostException e) {
            System.err.println("0xFFFC Control connection to "+hostName+" on port "+portNumber+" failed to open");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("0xFFFD Control connection I/O error, closing control connection ");
            System.exit(1);
        }
    }


    // Switches to passive mode and also retrieves data
    public static void getCommand(PrintWriter out, BufferedReader in, String commandArg2) throws IOException{
        out.println("PASV");
        printResponse(in);
        out.println("RETR "+commandArg2);
    }

    // prints and waits for the response from the server
    public static void printResponse(BufferedReader in) throws IOException {
        String serverResponse = in.readLine();
        while(serverResponse.matches("\\d\\d\\d-(.*)")){    //Checks for response code, matches digit digit digit
            if(serverResponse.contains("331"))
                needPass = true;
            System.out.println(serverResponse);
            serverResponse = in.readLine();
        }
        System.out.println(serverResponse);
    }
}