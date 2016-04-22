public class AutoTrackLogFilter{
LogFilter lf;
String profileToTrack;

public AutoTrackLogFilter(LogFilter lf, String profileToTrack){
System.out.println("tracking:"+ profileToTrack);
this.lf=lf;
this.profileToTrack=profileToTrack;
}

public void Filter (String log){
if(log.length()>0){
if(log.indexOf("TAG_CHANGE Entity="+profileToTrack+" tag=PLAYSTATE value=WON")!=-1 || log.indexOf("TAG_CHANGE Entity="+profileToTrack+" tag=PLAYSTATE value=LOST")!=-1){
System.out.println("fin del juego");
if(log.indexOf("WON")!=-1){
System.out.println("victoria detectada");
lf.setGameOver(true);
}
else{
System.out.println("derrota detectada");
lf.setGameOver(false);
}
}
}
}
}