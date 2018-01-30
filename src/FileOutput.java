import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileOutput {
	
	private FileOutput() {
		
	}
	
    public static Boolean fileTransfer(InputStream in, String fileName) {
	    int fileResponse;
        FileOutputStream fileOutputStream;
        //System.out.println("in fileTransfer ");
        try {
            //System.out.println("trying fileTransfer ");
            fileResponse = in.read();
        }catch(IOException e){
            //System.out.println("Exited fileTransfer");    //debug
            return false;
        }

        try{
            fileOutputStream = new FileOutputStream(fileName);
            //System.out.println("Still here");    //debug
            while(fileResponse != -1){
                fileOutputStream.write(fileResponse);
                fileResponse = in.read();
            }
            //System.out.println("finished writing");    //debug
            CSftp.disableRetr();
            fileOutputStream.close();
            return true;
        }catch(IOException e){
            System.err.println("0x38E Access to local file "+ CSftp.getFileName() +" denied.");
            System.out.print("csftp> ");
            return false;
        }

    }
}
