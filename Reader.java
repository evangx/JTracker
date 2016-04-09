import java.io.RandomAccessFile;
public class Reader{

RandomAccessFile f = null;
long indice_actual=0;
LogFilter lf;



public void preparar(){

lf= new LogFilter();
try{
f=new RandomAccessFile("C:\\Program Files\\Hearthstone\\Logs\\Zone.log", "r");
indice_actual=f.length();
System.out.println("original size"+ indice_actual);
}
catch(Exception e){
System.out.println(e.getMessage());
}
}

public void programa(){

try{
f=new RandomAccessFile("C:\\Program Files\\Hearthstone\\Logs\\Zone.log", "r");
if(indice_actual<f.length()){
f.seek(indice_actual);
while(f.getFilePointer()<f.length()){
lf.Filter(f.readLine());
System.out.println("ciclo");
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