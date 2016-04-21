public class LogFilter{

boolean inGame=false;
boolean inMulligan=false;
int numMulligan=0;
boolean firstDraw=false;
boolean MulliganStarted=false;
GUI jTracker;
CardList mySelectedCards;
int opposingHero;

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
else if(log.indexOf("] zone from FRIENDLY PLAY (Hero) -> FRIENDLY GRAVEYARD")!=-1 || log.indexOf("] zone from OPPOSING PLAY (Hero) -> OPPOSING GRAVEYARD")!=-1){
System.out.print("Someone lose: ");
getName(log);
Record.createRecord(jTracker.getCurrentDeck(),opposingHero,getGameOver(log));
}
}
}
else{
/*
if(log.indexOf("] zone from  -> OPPOSING PLAY (Hero)")!=-1 || log.indexOf("] zone from  -> FRIENDLY PLAY (Hero)")!=-1){
if(log.indexOf("] zone from  -> OPPOSING PLAY (Hero)")!=-1){
System.out.println("Enemy hero: "+getOpposingHeroClass(log));
opposingHeroDetected=true;
}
if(opposingHeroDetected){
inGame=true;
System.out.println("Inicia juego");
inMulligan=true;
System.out.println("En Mano: ");
}
}
*/
if(log.indexOf("] zone from  -> OPPOSING PLAY (Hero)")!=-1){
opposingHero=getOpposingHeroClass(log);

inGame=true;
System.out.println("Inicia juego");
inMulligan=true;
System.out.println("En Mano: ");

}
}
}
}

//D 23:05:34.3540708 ZoneChangeList.ProcessChanges() - id=2 local=False [name=Savannah Highmane id=54 zone=HAND zonePos=0 cardId=EX1_534 player=2] zone from FRIENDLY DECK -> FRIENDLY HANDprivate void getName(String log){
private String getName(String log){
if(log.indexOf(" [name=")!=-1 && log.indexOf(" id=")!=-1){
log=log.substring(log.indexOf(" [name=")+7, log.length());
log=log.substring(0, log.indexOf(" id="));
System.out.println(log);
}
return log;
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
//[Zone] ZoneChangeList.ProcessChanges() - id=1 local=False [name=Uther Lightbringer id=36 zone=PLAY zonePos=0 cardId=HERO_04 player=2] zone from  -> OPPOSING PLAY (Hero)

//D 23:05:34.3540708 ZoneChangeList.ProcessChanges() - id=37 local=False [name=Uther Lightbringer id=4 zone=GRAVEYARD zonePos=0 cardId=HERO_04 player=1] zone from FRIENDLY PLAY (Hero) -> FRIENDLY GRAVEYARD
//D 22:50:43.6898022 ZoneChangeList.ProcessChanges() - id=65 local=False [name=Rexxar id=66 zone=GRAVEYARD zonePos=0 cardId=HERO_05 player=2] zone from OPPOSING PLAY (Hero) -> OPPOSING GRAVEYARD
private boolean getGameOver(String log){
boolean isWinner=true;
if(log.indexOf("] zone from FRIENDLY PLAY (Hero) -> FRIENDLY GRAVEYARD")!=-1){
isWinner=false;
}
getGameOver();
return isWinner;
}

private void getGameOver(){
inGame=false;
mySelectedCards.restartList();
}

private int getOpposingHeroClass(String log){
int heroClass=0;
String opposingHero="";
/*
Warrior	1
Shaman	2
Rogue		3
Paladin	4
Hunter	5
Druid		6
Warlock	7
Mage		8
Priest	9
*/
opposingHero=getName(log);
if(opposingHero.equals("Garrosh Hellscream")){
heroClass=1;
}
else if(opposingHero.equals("Thrall")){
heroClass=2;
}
else if(opposingHero.equals("Valeera Sanguinar")){
heroClass=3;
}
else if(opposingHero.equals("Uther Lightbringer")){
heroClass=4;
}
else if(opposingHero.equals("Rexxar")){
heroClass=5;
}
else if(opposingHero.equals("Malfurion Stormrage")){
heroClass=6;
}
else if(opposingHero.equals("Gul'dan")){
heroClass=7;
}
else if(opposingHero.equals("Jaina Proudmoore")){
heroClass=8;
}
else if(opposingHero.equals("Anduin Wrynn")){
heroClass=9;
}

return heroClass;
}

public int getOpposingHero(){
return opposingHero;
}

public void setGameOver(boolean isWinner){
getGameOver();
Record.createRecord(jTracker.getCurrentDeck(),opposingHero, isWinner);

}

}

