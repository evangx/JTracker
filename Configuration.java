import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class Configuration{
private static Properties properties;
private static OutputStream os;
private static InputStream is;

public static void createFile(){
File f = new File("Configuration.properties");
if(!f.exists()){
setDefaultPath();
setCurrentProfile("Default");
}
}

public static String getLastProfile(){
String profile="Default";
profile=getProperty("Profile");
return profile;
}
       
public static void setCurrentProfile(String profile){
setProperty("Profile", profile);
}

public static String getHearthstonePath(){
return getProperty("Path");
}

private static void setDefaultPath(){
String path="";
if(System.getProperty("os.name").indexOf("Windows")!=-1){
path+="C:\\Program Files";
if(!System.getProperty("os.arch").equals("x86")){
path+=" x86";
}
path+="\\Hearthstone\\";
}
else{
}
setProperty("Path", path);
}

private static void setProperty(String property, String value){
properties = new Properties();
File f = new File("Configuration.properties");
try{
if(f.exists()){
is = new FileInputStream("Configuration.properties");
properties.load(is);
}
}
catch(Exception e){
System.out.println(e.getMessage());
}
finally{
try{
is.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}
}
try{
os = new FileOutputStream("Configuration.properties");
properties.setProperty(property, value);
properties.store(os, null);
}
catch(Exception e){
System.out.println(e.getMessage());
}
finally{
try{
os.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}
}
}

private static String getProperty(String property){
properties = new Properties();
String value="";
try{
is = new FileInputStream("Configuration.properties");
properties.load(is);
value=properties.getProperty(property);
}
catch(Exception e){
System.out.println(e.getMessage());
}
finally{
try{
is.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}
}
return value;
}

}