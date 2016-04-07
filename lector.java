import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
public class lector{

File f;
public void programa(){
LogFilter lg = new LogFilter();
f=new File("C:\\Program Files\\Hearthstone\\Logs\\Zone.log");
try{
FileReader fr= new FileReader(f);
Scanner sc= new Scanner(fr);
int lineasprocesadas=0;
while(sc.hasNextLine()){
lg.Filter(sc.nextLine());
//lineasprocesadas+=1;
//System.out.println(lineasprocesadas);
}
//System.out.println(lineasprocesadas);
}
catch(Exception e){
System.out.println(e.getMessage());
}

}

public static void main(String [] args){
lector l = new lector();
l.programa();
}
}