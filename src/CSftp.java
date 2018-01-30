import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class CSftp {
	final static int defaultPort = 21;
	private static String hostName;
	private static int portNumber;
	private static BufferedReader stdIn;
	private volatile static boolean retrMode = false;
	private volatile static String retrFileName;
	volatile static boolean inTransfer = false;
	public static void main(String[] args) {
		
		hostName = args[0];
		portNumber = Integer.parseInt(args[1]);
		stdIn = new BufferedReader(
				new InputStreamReader(System.in));
		
		try {
			switch(args.length) {
			case 1:
				new ControlConnection(hostName, defaultPort).start();
				break;
			case 2:
				
				new ControlConnection(hostName, portNumber).start();
				break;
			default:
				System.err.println("0xFFFF Processing error. Expected hostName and portNumber, but found > 2 or 0 arguments");
				System.exit(1);
			}
						
		}catch(UnknownHostException e){
			System.err.println("0xFFFC Control connection to "
					+ hostName+" on port "+ portNumber +" failed to open.");
            System.exit(1);	
		}catch(IOException e){
			System.err.println("0xFFFD Control connection I/O error, closing control connection.");
			System.exit(1);
		}

	}
	
	public static BufferedReader getStdIn() {
		return stdIn;
	}
	
	public static boolean getRetrMode() {
		return retrMode;
	}
	
	public static void enableRetr() {
		retrMode = true;
	}
	
	public static void disableRetr() {
		retrMode = false;
	}
	
	public static void setFileName(String str) {
		retrFileName = str;
	}
	
	public static String getFileName() {
		return retrFileName;
	}

}
