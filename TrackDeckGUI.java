import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrackDeckGUI extends JFrame implements ActionListener, ItemListener{
GUI lastWindow;
CardList mySelectedCards;
Connection connection;
Statement query;
JButton Back, iLostButton, iWonButton;
JRadioButton manualDetectionButton, autoDetectionButton;




public TrackDeckGUI(GUI lastWindow, ArrayList<String> receivedCards){
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
try{
connection.close();
}
catch(Exception e){
System.out.println(e.getMessage()); 
}


Watcher trackDeck = new Watcher(lastWindow);
trackDeck.start();
lastWindow.getLogFilter().setView(mySelectedCards);

Back = new JButton("Regresar");
Back.setBounds(270, 10, 90, 30);
Back.addActionListener(this);
add(Back);

iWonButton = new JButton("Gane");
iWonButton.setBounds(100, 520, 100, 30);
iWonButton.addActionListener(this);
add(iWonButton);

iLostButton = new JButton("Me rendi");
iLostButton.setBounds(100, 550, 100, 30);
iLostButton.addActionListener(this);
add(iLostButton);

ButtonGroup bg = new ButtonGroup();
manualDetectionButton = new JRadioButton("Deteccion manual");
manualDetectionButton.setBounds(310, 50, 150, 30);
bg.add(manualDetectionButton);
manualDetectionButton.addItemListener(this);
add(manualDetectionButton);
manualDetectionButton.setSelected(true);

autoDetectionButton = new JRadioButton("Deteccion automatica");
autoDetectionButton.setBounds(150,50, 150, 30);
bg.add(autoDetectionButton);
autoDetectionButton.addItemListener(this);
add(autoDetectionButton);
//autoDetectionButton.setEnabled(false);



addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);


setVisible(true);
lastWindow.setVisible(false);
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
else if(e.getSource()==iLostButton){
if(lastWindow.getLogFilter().getOpposingHero()>0){
lastWindow.getLogFilter().setGameOver(false);
}
else{
JOptionPane.showMessageDialog(null,"No se detecto una partida","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
}
}
else if(e.getSource()==iWonButton){
if(lastWindow.getLogFilter().getOpposingHero()>0){
lastWindow.getLogFilter().setGameOver(true);
}
else{
JOptionPane.showMessageDialog(null,"No se detecto una partida","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
}
}

}

public void itemStateChanged(ItemEvent e){
if(e.getStateChange()==1){
if(e.getItem()==manualDetectionButton){
iWonButton.setVisible(true);
iLostButton.setVisible(true);
}
else{
iWonButton.setVisible(false);
iLostButton.setVisible(false);
System.out.println("It'll be added in the future");
}
}
}

}