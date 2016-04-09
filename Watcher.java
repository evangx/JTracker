import java.io.File;

public class Watcher{
boolean vigilar=true;

public void programa(){
Reader l= new Reader();
l.preparar();
while(vigilar){
l.programa();
try{
Thread.sleep(1000);
}
catch(Exception e){
System.out.println(e.getMessage());
}
}
}


public static void main(String [] args){
Watcher w = new Watcher();
w.programa();
}

}