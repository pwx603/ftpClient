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
        		if (validCom(commandArgs)){
        		    output = " ";

                } else {
                    output = "";
                }
        		break;
        }
        
        return output;
    }
    private static Boolean validCom(String[] commandArgs){
        if(commandArgs[0].equalsIgnoreCase("quit")|| commandArgs[0].equalsIgnoreCase("features")|| commandArgs[0].equalsIgnoreCase("dir")||
                commandArgs[0].equalsIgnoreCase("user")|| commandArgs[0].equalsIgnoreCase("pw")||commandArgs[0].equalsIgnoreCase("get")||
                commandArgs[0].equalsIgnoreCase("cd")){
                return true;
        }else return false;
    }
    
    private static String handleOneArg(String[] commandArgs){
    	if(commandArgs[0].equalsIgnoreCase("quit")) {
    		return "QUIT";
    	}else if(commandArgs[0].equalsIgnoreCase("features")){
    		return "FEAT";
    	}else if(commandArgs[0].equalsIgnoreCase("dir")){
    		return "PASV LIST";
    	}else if(commandArgs[0].equalsIgnoreCase("user") || (commandArgs[0].equalsIgnoreCase("pw")) ||
                (commandArgs[0].equalsIgnoreCase("get")) || (commandArgs[0].equalsIgnoreCase("cd"))){

            System.out.println("Wtf 1");
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
    	}else if(commandArgs[0].equalsIgnoreCase("quit") || (commandArgs[0].equalsIgnoreCase("features")) ||
                (commandArgs[0].equalsIgnoreCase("dir"))){

    	    System.out.println("Wtf");
            return " ";

        }else{

            return "";
        }
    }
    

}
