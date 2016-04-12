import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;

public class Card extends JPanel{

private String nameCard;
private String cardId;
private  int costCard;
private String rarityCard;
private int amountCard;
private JLabel amountLabel;

public Card(String id, String name,  int cost, String rarity){
super();
cardId=id;
nameCard=name;
costCard=cost;
rarityCard=rarity;
amountCard=1;
setLayout(new BorderLayout());

JLabel costLabel = new JLabel(costCard +"");
if(rarity.equals("Common")){
costLabel.setBackground(Color.GRAY);
}
else if(rarity.equals("Rare")){
costLabel.setBackground(Color.BLUE);

}
else if(rarity.equals("Epic")){
costLabel.setBackground(Color.MAGENTA);
}
else if(rarity.equals("Legendary")){
costLabel.setBackground(Color.YELLOW);
}
costLabel.setOpaque(true);
add(costLabel, BorderLayout.WEST);


JLabel nameLabel = new JLabel(nameCard);
add(nameLabel, BorderLayout.CENTER);
amountLabel = new JLabel("");
add(amountLabel, BorderLayout.EAST);
}



public String getId(){
return cardId;
}

public String getName(){
return nameCard;
}

public int getCost(){
return costCard;
}

public String getRarity(){
return rarityCard;
}

public int getAmount(){
return amountCard;
}

public void addCopy(){
if(!rarityCard.equals("Legendary") && amountCard==1){
amountCard+=1;
updateAmount();
}
}

public void deleteCopy(){
if(amountCard==2){
amountCard-=1;
updateAmount();
}
}

private void updateAmount(){
if(amountCard==1){
amountLabel.setText("");
}
else{
amountLabel.setText("2");
}
}

public void playCopy(){
if(amountCard==2){
if(amountLabel.isVisible()){
amountLabel.setVisible(false);
}
else{
setVisible(false);
}
}
else{
setVisible(false);
}
}

public void unplayCopy(){
if(isVisible()){
if(amountCard==2){
amountLabel.setVisible(true);
}
}
else{
setVisible(true);
}
}

public void restoreCard(){
amountLabel.setVisible(true);
setVisible(true);
}

public static void main (String [] args){
JFrame a= new JFrame();
Card c = new Card("a", "Entomb", 6, "Rare");

a.setSize(500,500);

a.add(c);
a.setVisible(true);
}

}