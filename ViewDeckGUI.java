import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewDeckGUI extends JFrame implements ActionListener, ItemListener{
GUI lastWindow;
CardList mySelectedCards;
JButton Back;
Connection connection;
Statement query;
int[][] matches;
JLabel deckInformationLabel;
int gamesPlayed;
JRadioButton thisMonthButton, allTimeButton;
String thisMonthInformation, allTimeInformation;


public ViewDeckGUI(GUI lastWindow, ArrayList<String> receivedCards, String deckFileName){
super("JTracker");
this.lastWindow=lastWindow;
setSize(lastWindow.getSize());
setLocation(lastWindow.getLocation());
setLayout(null);


mySelectedCards = new CardList(false);
JScrollPane scrollListSelectedCards = new JScrollPane(mySelectedCards);
scrollListSelectedCards.setBounds(100, 110, 300, 400);
add(scrollListSelectedCards);


Connection(); 
ResultSet resultado = null; 
for(int i=0; i<receivedCards.size(); i++){
try { 
resultado = query.executeQuery("Select c.cardId, n.name, c.rarity, c.cost from Cards as c inner join Names as n on (c.cardId=n.cardId) where lang='esMX' and cost>=0 and rarity!='' and collectible=1 and c.cardId='"+receivedCards.get(i)+"';"); 
mySelectedCards.addCard( new Card(resultado.getString(1),resultado.getString(2), resultado.getInt(4), resultado.getString(3)));
}
catch (SQLException e) { 
System.out.println(e.getMessage()); 
}
}
matches= new int[9][2];

deckFileName="";
thisMonthInformation="";
allTimeInformation="";
allTimeInformation=updateInformation(Record.getAll(deckFileName));
thisMonthInformation=updateInformation(Record.getLastMonth(deckFileName));

deckInformationLabel= new JLabel("");
deckInformationLabel.setText(allTimeInformation);
deckInformationLabel.setBounds( 425, 110, 300, 300);
add(deckInformationLabel);

Back = new JButton("Regresar");
Back.setBounds(270, 10, 90, 30);
Back.addActionListener(this);
add(Back);

thisMonthButton= new JRadioButton("Este Mes", false);
allTimeButton= new JRadioButton("Todo",true);
ButtonGroup bg= new ButtonGroup();
bg.add(thisMonthButton);
bg.add(allTimeButton);
allTimeButton.setBounds(50,50,100,30);
allTimeButton.addItemListener(this);
add(allTimeButton);
thisMonthButton.setBounds(150,50,100,30);
thisMonthButton.addItemListener(this);
add(thisMonthButton);


addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);


setVisible(true);
lastWindow.setVisible(false);
}

public void restartMatches(){
for (int i=0;i<matches.length;i++){
for (int j=0;j<matches[0].length;j++){
matches[i][j]=0;
}
}
}


public String updateInformation(ResultSet records){
gamesPlayed=0;
int victories=0;
String deckInformation="";
restartMatches();
try{
while(records.next()){
if(records.getString(2).equals("true")){
matches[records.getInt(1)-1][0]=records.getInt(3);
victories+=records.getInt(3);
}
else{
matches[records.getInt(1)-1][1]=records.getInt(3);
}
gamesPlayed+=records.getInt(3);
}
}
catch(Exception e){
System.out.println(e.getMessage());
}
deckInformation="<html><body>Juegos: "+gamesPlayed+" WinRatio: "+getWinRatio(victories, gamesPlayed-victories)+"%<br>";
deckInformation+="Warrior"+updateLabel(1);
deckInformation+="Shaman	"+updateLabel(2);
deckInformation+="Rogue		"+updateLabel(3);
deckInformation+="Paladin"+updateLabel(4);
deckInformation+="Hunter	"+updateLabel(5);
deckInformation+="Druid		"+updateLabel(6);
deckInformation+="Warlock"+updateLabel(7);
deckInformation+="Mage		"+updateLabel(8);
deckInformation+="Priest"+updateLabel(9);
deckInformation+="</body></html>";
return deckInformation;
}

public String updateLabel(int info){
info=info-1;
return " Victorias: "+ matches[info][0]+ " Derrotas: "+matches[info][1]+" WinRatio: "+getWinRatio(matches[info][0],matches[info][1])+"%<br>";
}

public float getWinRatio(int victories, int defeats){
int totalGames=victories+defeats;
int winRatio=0;
if(totalGames>0){
winRatio=(victories*100)/(victories+defeats);
}
else{
winRatio=0;
}
return winRatio;
}

public void Connection(){
try { 
Class.forName("org.sqlite.JDBC"); 
}
catch (ClassNotFoundException e) { 
System.out.println("error al cargar driver "+e.getMessage()); 
} 
try { 
connection = DriverManager.getConnection("jdbc:sqlite:cards.db"); 
query = connection.createStatement(); 
}
catch (SQLException e) { 
System.out.println("error al hacer conexion "+e.getMessage()); 
} 
}

public void actionPerformed(ActionEvent e){
if(e.getSource()==Back){
lastWindow.setSize(getSize());
lastWindow.setLocation(getLocation());
lastWindow.updateDecks();
lastWindow.setVisible(true);
setVisible(false);
dispose();
}
}


public void itemStateChanged(ItemEvent e){
if(e.getStateChange()==1){
if(e.getItem()==thisMonthButton){
deckInformationLabel.setText(thisMonthInformation);
}
else if(e.getItem()==allTimeButton){
deckInformationLabel.setText(allTimeInformation);
}
}
}

}