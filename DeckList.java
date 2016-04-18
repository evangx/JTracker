import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.BoxLayout;
import java.util.ArrayList;
public class DeckList extends JPanel{

ArrayList<File> decks;
File[] listOfDecks;
GUI parent;

public DeckList(GUI parent, File[] list){
super();
listOfDecks=list;
this.parent=parent;
createListOfDecks();
}

private void createListOfDecks(){
decks=new ArrayList<File>();
for(File deck: listOfDecks){
decks.add(deck);
}
setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
for(int i=0; i<decks.size(); i++){
add(new DeckGUI(decks.get(i), this,  parent));
}

}

public void updateDecks(){
removeAll();
File temp= new File("Decks/");
listOfDecks=temp.listFiles();
createListOfDecks();
}



public static void main(String [] args){
JFrame a= new JFrame();
File f= new File ("Decks/");
//DeckList dl = new DeckList(a, f.listFiles());
a.setSize(500,500);
//JScrollPane scrollListSelectedCards = new JScrollPane(dl);

//a.add(scrollListSelectedCards);
a.setVisible(true);


}

}