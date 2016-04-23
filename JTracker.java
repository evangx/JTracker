import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
public class JTracker{
static String configFilePath;
static File configFile;

public static void main(String [] args){
String os = System.getProperty("os.name");
os=os.toLowerCase();
if(os.indexOf("windows")!=-1){
os="Windows";
}
else if(os.indexOf("mac")!=-1){
os="Mac";
}
if(!existsConfigFile(os)){
createFile();
}
Configuration.createFile();
GUI programa= new GUI();
programa.setVisible(true);
}

private static boolean existsConfigFile(String operativeSystem){
if(operativeSystem.equals("Windows")){
configFilePath=System.getenv("LOCALAPPDATA");;
configFilePath+="/Blizzard/Hearthstone/";
}
else if(operativeSystem.equals("Mac")){
configFilePath="~/Library/Preferences/Blizzard/Hearthstone/";
}
configFilePath+="log.config";
configFile = new File(configFilePath);
return configFile.exists();
}

private static void createFile(){
try{
FileWriter fw = new FileWriter(configFile);
PrintWriter pw = new PrintWriter(fw);
pw.println("[Zone]");
pw.println("LogLevel=1");
pw.println("FilePrinting=true");
pw.println("ConsolePrinting=false");
pw.println("ScreenPrinting=false");
pw.println("Verbose=true");
pw.flush();
pw.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}
}

}
