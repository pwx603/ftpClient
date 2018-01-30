import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileOutput {
	
	private FileOutput() {
		
	}
	
    public static void fileTransfer(InputStream in, String fileName){
    	try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);){
    		int fileResponse = in.read();
    		
    		
    		CSftp.inTransfer = true;
        
  
            while(fileResponse != -1){
                fileOutputStream.write(fileResponse);
                fileResponse = in.read();
            }
        }catch(IOException e){
        	System.err.println("0x38E Access to local file "+ CSftp.getFileName() +" denied.");
        }
    }
}
