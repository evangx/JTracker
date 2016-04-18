import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class CardList extends JPanel{
 
private ArrayList currentCards; 
private int numberOfCards=0;
private boolean addListeners=true;
CreateDeckGUI createDeckWindow;

public CardList(CreateDeckGUI cDW){
super();
createDeckWindow=cDW;
currentCards=new ArrayList();
setLayout(new GridLayout(31,1));

}

public CardList(){
super();
currentCards=new ArrayList();
setLayout(new GridLayout(31,1));

}

public CardList(boolean addListeners){
super();
currentCards=new ArrayList();
setLayout(new GridLayout(31,1));
this.addListeners=addListeners;
}

public int exist(String id){
int temp=-1;
for(int i=0; i<currentCards.size();i++){
if(id.equals((currentCards.get(i)))){
temp=i;
//System.out.println("Copia existente");
}
}
return temp;
}

private int getIndexToInsert(Card newCard){
int startValue=-1;
int lastValue=-1;
int defValue=-1;
boolean modif=false;
Card temp;
for(int i=0; i<currentCards.size();i++){
temp = (Card)getComponent(i);
if(newCard.getCost()>temp.getCost()){
startValue=i+1;
}
else if(newCard.getCost()<temp.getCost() && lastValue==-1){
lastValue=i;
if(startValue==-1){
startValue=lastValue;
}
}
}
if(startValue!=-1 && lastValue==-1){
lastValue=currentCards.size()-1;
modif=true;
}
else if(lastValue==-1 && startValue==-1){
startValue=0;
lastValue=currentCards.size()-1;
modif=true;
}
if(lastValue==startValue && lastValue!=-1 && !modif){
defValue=lastValue;
}
else{
for (int i=startValue; i<=lastValue; i++){
temp = (Card) getComponent(i);
if(newCard.getName().compareTo(temp.getName())>0 && newCard.getCost()==temp.getCost()){
defValue=i+1;
}
}
if(defValue==-1){
defValue=startValue;
}
}
return defValue;
}

public void addCard(Card newCard){
if(numberOfCards>=0 && numberOfCards<30){
int exist= exist(newCard.getId());
if(exist<0){
if(currentCards.size()==0){
insertCard(newCard,0);
currentCards.add(0, newCard.getId());
}
else{
int insertAt=getIndexToInsert(newCard);
insertCard(newCard, insertAt);
currentCards.add(insertAt, newCard.getId());
}
}
else{
Card temp= (Card)getComponent(exist);
if(temp.getAmount()==1 && !temp.getRarity().equals("Legendary")){
numberOfCards+=1;
}
temp.addCopy();

}

updateUI();
}
}

private void insertCard(Card newCard, int index){
Card temp = new Card(newCard.getId(), newCard.getName(), newCard.getCost(), newCard.getRarity());
if(addListeners){
temp.addMouseListener( new MouseAdapter(){


public void mouseReleased(MouseEvent e){
Card tem= (Card)e.getSource();
if(tem.getAmount()==2){
numberOfCards-=1;
createDeckWindow.updateNumberOfCardsLabel();
tem.deleteCopy();

//System.out.println("copia borrada");
}
else{
remove(tem);
numberOfCards-=1;
createDeckWindow.updateNumberOfCardsLabel();
currentCards.remove(currentCards.indexOf(tem.getId()));

updateUI();
//System.out.println("carta borrada");
}
//System.out.println("cantidad restante"+numberOfCards);
}
}
);
}
numberOfCards+=1;
add(temp, index);
//System.out.println("cantidad insertada"+numberOfCards);
}

public int getCurrentNumberOfCards(){
return numberOfCards;
}

public ArrayList getCurrentCards(){
ArrayList tem=new ArrayList();
Card temp;
for(int i=0; i<currentCards.size();i++){
temp = (Card)getComponent(i);
for(int j=0; j<temp.getAmount();j++){
tem.add(temp.getId());
}
}
return tem;
}

public void cardPlayed(String idCardPlayed){
Card temp;
for(int i=0; i<currentCards.size();i++){
temp=(Card)getComponent(i);
if(temp.getId().equals(idCardPlayed)){
temp.playCopy();
}
}
}

public void cardRecovered(String idCardRecovered){
Card temp;
for(int i=0; i<currentCards.size();i++){
temp=(Card)getComponent(i);
if(temp.getId().equals(idCardRecovered)){
temp.unplayCopy();
}
}
}

public void restartList(){
Card temp;
for(int i=0; i<currentCards.size();i++){
temp=(Card)getComponent(i);
temp.restoreCard();
}
}


public static void main(String [] args){
JFrame a= new JFrame();
CardList cl = new CardList();
Card c = new Card("a", "Entomb", 6, "Rare");
//Card d = new Card("e", "Antomb", 6, "Rare");
a.setSize(500,500);

a.add(cl);
a.setVisible(true);
cl.addCard(c);
cl.addCard(new Card("b", "texto de b", 3, "Rare"));
cl.addCard(new Card("c", "Xntomb", 6, "Rare"));
cl.addCard(new Card("d", "Yntomb", 6, "Rare"));
cl.addCard(new Card("e", "Wntomb", 6, "Rare"));
cl.addCard(new Card("f", "trampa", 7, "Rare"));
cl.addCard(new Card("g", "Zntomb", 6, "Rare"));
//cl.addCard(d);
Scanner sc= new Scanner(System.in);
String x, y;
while(true){
x=sc.nextLine();
y= sc.nextLine();
int z=Integer.parseInt(sc.nextLine());
cl.addCard(new Card(x, y,(int) z, "Rare"));
}

}

}