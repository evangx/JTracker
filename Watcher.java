import java.io.File;

public class Watcher extends Thread{
boolean vigilar=true;
GUI jTracker;
Reader l;

public Watcher(GUI jTracker){
this.jTracker = jTracker;
l= new Reader(jTracker);
l.preparar();
}

public Watcher(){
}

public void run(){

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
Watcher w = new Watcher(null);
w.start();
}

}