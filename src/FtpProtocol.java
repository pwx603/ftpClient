public class FtpProtocol {
    public static String processCommand(String input){
        String output = "";
        String[] commandArgs = input.split(" ");

        switch(commandArgs.length) {
        	case 1:
        		output = handleOneArg(commandArgs);
        		break;
        	case 2:
        		output = handleTwoArgs(commandArgs);
        		break;
        	default:
        		output = " ";
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
    	}else if(commandArgs[0].equalsIgnoreCase("user") || (commandArgs[0].equalsIgnoreCase("pw")) ||
                (commandArgs[0].equalsIgnoreCase("get")) || (commandArgs[0].equalsIgnoreCase("cd")) ||
                (commandArgs[0].equalsIgnoreCase("retr"))){

    	    return " ";
        }else{
            return "";
        }
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
    	}else if(commandArgs[0].equalsIgnoreCase("quit") || (commandArgs[0].equalsIgnoreCase("features")) ||
                (commandArgs[0].equalsIgnoreCase("dir"))){

            return " ";

        }else{
            return "";
        }
    }
    

}
