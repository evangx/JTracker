import java.io.RandomAccessFile;
public class Reader{

RandomAccessFile f = null;
long indice_actual=0;
LogFilter lf;
GUI jTracker;
boolean isAutoTracking=false;
String profileToTrack;
AutoTrackLogFilter autoTrackLogFilter;
Watcher watcher;
String path="";

public Reader(GUI jTracker){
this.jTracker=jTracker;
}

public Reader(LogFilter lf, String profileToTrack){
this.lf=lf;
isAutoTracking=true;
this.profileToTrack=profileToTrack;
}

public void preparar(){
path=Configuration.getHearthstonePath();
path+="Logs\\";
if(!isAutoTracking){
lf= new LogFilter(jTracker);
}
else{
autoTrackLogFilter = new AutoTrackLogFilter(lf, profileToTrack);
}
try{
if(!isAutoTracking){
path+="Zone.log";
f=new RandomAccessFile(path, "r");
}
else{
path+="Power.log";
f=new RandomAccessFile(path, "r");
}
indice_actual=f.length();
System.out.println("original size"+ indice_actual);
}
catch(Exception e){
System.out.println(e.getMessage());
}
}

public void programa(){
try{
f=new RandomAccessFile(path, "r");
if(indice_actual<f.length()){
f.seek(indice_actual);
while(f.getFilePointer()<f.length()){
if(!isAutoTracking){
lf.Filter(f.readLine());
}
else{
autoTrackLogFilter.Filter(f.readLine());
}
}

indice_actual=f.length();
}
else if(indice_actual==f.length()){

}
else if (indice_actual>f.length()){
System.out.println("algo raro paso"+ f.length()+" "+indice_actual);
indice_actual=0;
}
}
catch(Exception e){
System.out.println(e.getMessage());
}
}




}