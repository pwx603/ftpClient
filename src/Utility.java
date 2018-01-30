
public class Utility {
    public static String[] extractAdr(String str) {
        String[] adr = new String[2];
        str = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        String[] splitStr = str.split(",");

        StringBuilder sb = new StringBuilder();

        sb.append(splitStr[0]).append(".").append(splitStr[1]).append(".").append(splitStr[2]).append(".").append(splitStr[3]);
        adr[0] = sb.toString();
        adr[1] = String.valueOf(Integer.parseInt(splitStr[4]) * 256 + Integer.parseInt(splitStr[5]));

        return adr;
    }
    
    public static void echoClient(String str) {
    	System.out.println("--> " + str); 
    }
    
    public static void echoServer(String str) {
    	System.out.println("<-- " + str); 
    }
}
