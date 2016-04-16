public class LogFilter{

boolean inGame=false;
boolean inMulligan=false;
int numMulligan=0;
boolean firstDraw=false;
boolean MulliganStarted=false;
GUI jTracker;
CardList mySelectedCards;

public LogFilter(GUI jTracker){
this.jTracker=jTracker;
jTracker.setLogFilter(this);
}

public void TrackNewDeck(boolean value){
if(value){
}
else{

}
}

public void setView(CardList mySelectedCards){
this.mySelectedCards=mySelectedCards;
}

public void Filter(String log){
if(log.length()>0){
if(inGame){
if(inMulligan){
/*
if(log.indexOf("] zone from  -> FRIENDLY HAND")!=-1){
getName(log);
}
*/
if(log.indexOf(" START waiting for [name=")!=-1 && log.indexOf(" zone=HAND ")!=-1 && !MulliganStarted){
getName(log);
mySelectedCards.cardPlayed(getId(log));
}
else if(log.indexOf("m_id=1 END waiting for zone OPPOSING PLAY (Hero Power)") != -1){
MulliganStarted=true;
}
else if(log.indexOf("] zone from FRIENDLY DECK -> FRIENDLY HAND")!=-1){
if(numMulligan==0){
firstDraw=true;
}
else if(numMulligan == 1 && !firstDraw){
firstDraw=true;
}
System.out.print("Nueva carta: ");
getName(log);
mySelectedCards.cardPlayed(getId(log));
}
else if(log.indexOf("] zone from FRIENDLY HAND -> FRIENDLY DECK")!=-1){
System.out.print("Se va: ");
getName(log);
mySelectedCards.cardRecovered(getId(log));
}
//D 17:17:31.1954577 ZoneChangeList.ProcessChanges() - processing index=5 change=powerTask=[power=[type=TAG_CHANGE entity=[id=3 cardId= name=JcWayland] tag=MULLIGAN_STATE value=WAITING] complete=False] entity=JcWayland srcZoneTag=INVALID srcPos= dstZoneTag=INVALID dstPos=
else if(log.indexOf("] tag=MULLIGAN_STATE value=WAITING] complete=False] entity=") != -1){
numMulligan++;
if(firstDraw){
System.out.println("Mulligan Completo");
inMulligan=false;
firstDraw=false;
MulliganStarted=false;
}
else if(numMulligan==2){
System.out.println("Mulligan Completo");
inMulligan=false;
firstDraw=false;
MulliganStarted=false;
}
}
}
else{
if(log.indexOf("] zone from FRIENDLY DECK -> FRIENDLY HAND")!=-1){
getName(log);
mySelectedCards.cardPlayed(getId(log));
}
else if(log.indexOf("] zone from FRIENDLY DECK -> FRIENDLY SECRET")!=-1){
System.out.print("secreto puesto: ");
getName(log);
mySelectedCards.cardPlayed(getId(log));
}
else if(log.indexOf("] zone from FRIENDLY DECK -> FRIENDLY GRAVEYARD")!=-1){
System.out.print("Card destroyed: ");
getName(log);
mySelectedCards.cardPlayed(getId(log));
}
}
}
else{
if(log.indexOf("] zone from  -> OPPOSING PLAY (Hero)")!=-1 || log.indexOf("] zone from  -> FRIENDLY PLAY (Hero)")!=-1){
inGame=true;
System.out.println("Inicia juego");
inMulligan=true;
System.out.println("En Mano: ");
}
}
}
}

//D 23:05:34.3540708 ZoneChangeList.ProcessChanges() - id=2 local=False [name=Savannah Highmane id=54 zone=HAND zonePos=0 cardId=EX1_534 player=2] zone from FRIENDLY DECK -> FRIENDLY HANDprivate void getName(String log){
private void getName(String log){
if(log.indexOf(" [name=")!=-1 && log.indexOf(" id=")!=-1){
log=log.substring(log.indexOf(" [name=")+7, log.length());
log=log.substring(0, log.indexOf(" id="));
System.out.println(log);
}
}
//D 23:05:34.3540708 ZoneChangeList.ProcessChanges() - id=2 local=False [name=Savannah Highmane id=54 zone=HAND zonePos=0 cardId=EX1_534 player=2] zone from FRIENDLY DECK -> FRIENDLY HAND
private String getId(String log){
if(log.indexOf(" [name=")!=-1 && log.indexOf(" id=")!=-1){
log=log.substring(log.indexOf(" cardId=")+8, log.length());
log=log.substring(0, log.indexOf(" player="));
System.out.println(log);
}
return(log);
}
}

