
public class FtpProtocol {
	private FtpProtocol () {		
	}
	
	public static String processCommand(String input){
        String output = "";
        String[] commandArgs = input.split("\\s+");
        
        switch(commandArgs.length) {
        	case 1:
        		output = handleOneArg(commandArgs);
        		break;
        	case 2:
        		output = handleTwoArgs(commandArgs);
        		break;
        	default:
        		System.out.println("0x002 Invalid number of arguments");
        		break;
        }
        
        return output;
    }
    
    private static String handleOneArg(String[] commandArgs){
    	if(commandArgs[0].equalsIgnoreCase("quit")) {
    		return "QUIT";
    	}else if(commandArgs[0].equalsIgnoreCase("features")){
    		return "FEAT";
    	}else if(commandArgs[0].equalsIgnoreCase("dir")){
    		return "PASV LIST";
    	}else if(commandArgs[0].equalsIgnoreCase("")) {
    		return "";
    	}
    	return "";
    }
    
    private static String handleTwoArgs(String[] commandArgs) {
    	if(commandArgs[0].equalsIgnoreCase("user")) {
    		return "USER " + commandArgs[1]; 
    	}else if(commandArgs[0].equalsIgnoreCase("pw")) {
    		return "PASS " + commandArgs[1];
    	}else if(commandArgs[0].equalsIgnoreCase("get")) {
    		return "PASV RETR " + commandArgs[1];
    	}else if(commandArgs[0].equalsIgnoreCase("cd")) {
    		return "CWD " + commandArgs[1];
    	}else if(commandArgs[0].equalsIgnoreCase("retr")){
    		return "RETR " + commandArgs[1];
    	}
    	return "";
    }

}
