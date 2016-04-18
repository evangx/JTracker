import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Random;
import java.util.Calendar;
public class DeckGUI extends JPanel{

private String nameDeck;
private String heroClass;
File fileDeck;
JButton delete, copy, edit, view, track;
DeckList deckList;
Deck deck;
GUI lastWindow;

public DeckGUI(File file, DeckList parent, GUI mainJFrame){
super();
lastWindow=mainJFrame;
deck=null;
fileDeck=file;
deckList=parent;
try{
FileInputStream fs= new FileInputStream(file);
ObjectInputStream os = new ObjectInputStream(fs);

deck = (Deck)os.readObject();
fs.close();
os.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}

nameDeck=deck.getNameDeck();
heroClass=deck.getSelectedClass();
setLayout(new BorderLayout());
setMaximumSize(new Dimension(600, 100));

JLabel nameLabel = new JLabel(nameDeck);

if(deck.getListOfCards().size()!=30){
nameLabel.setBackground(Color.RED);
}
nameLabel.setOpaque(true);
add(nameLabel, BorderLayout.CENTER);

delete = new JButton("Borrar");
delete.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
deleteDeck();
}
}
);

copy = new JButton("Copiar");
copy.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
copyDeck();
}
}
);

edit = new JButton("Editar");
edit.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
editDeck();
}
}
);

view = new JButton("Ver");
view.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){

}
}
);

track = new JButton("Track");
track.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
trackDeck();
}
}
);

JPanel buttons = new JPanel();
buttons.add(delete);
buttons.add(copy);
buttons.add(edit);
buttons.add(view);
buttons.add(track);
add(buttons, BorderLayout.SOUTH);

}

private void trackDeck(){
lastWindow.setCurrentDeck(fileDeck.getName());
new TrackDeckGUI((GUI)lastWindow, deck.getListOfCards());
}

private void editDeck(){

new CreateDeckGUI(lastWindow, heroClass, nameDeck, deck.getListOfCards(), "Decks/"+fileDeck.getName());
setVisible(false);
}

private void deleteDeck(){
fileDeck.delete();
deckList.remove(this);
deckList.updateUI();
}

private void copyDeck(){
String temp="Decks/";
char n;
Random rnd = new Random();
Calendar c1 = Calendar.getInstance();
temp+= c1.get(Calendar.YEAR);
temp+="-"+(c1.get(Calendar.MONTH)+1);
temp+="-"+c1.get(Calendar.DATE);
temp+="-"+c1.get(Calendar.HOUR_OF_DAY);
temp+="-"+c1.get(Calendar.MINUTE);
temp+="-"+c1.get(Calendar.SECOND);
temp+="-";
for (int i=0; i < 4 ; i++) {
n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
temp += n;
}
temp +=".deck";
Path FROM = Paths.get("Decks/"+fileDeck.getName());
Path TO = Paths.get(temp);
CopyOption[] options = new CopyOption[]{
StandardCopyOption.REPLACE_EXISTING,
StandardCopyOption.COPY_ATTRIBUTES
}; 
System.out.println("cadena creada: "+temp);
try{
Files.copy(FROM, TO, options);
File fTemp= new File(temp);
deckList.add(new DeckGUI(fTemp, deckList, lastWindow));
deckList.updateUI();
}
catch(Exception e){
System.out.println(e.getMessage());
}

}


}