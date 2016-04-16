import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrackDeckGUI extends JFrame implements ActionListener{
GUI lastWindow;
CardList mySelectedCards;
Connection connection;
Statement query;
JButton Back;



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

Watcher trackDeck = new Watcher(lastWindow);
trackDeck.start();
lastWindow.getLogFilter().setView(mySelectedCards);

Back = new JButton("Regresar");
Back.setBounds(270, 10, 90, 30);
Back.addActionListener(this);
add(Back);

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
}
}