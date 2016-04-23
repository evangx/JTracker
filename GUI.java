import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.*;
import java.io.File;


public class GUI extends JFrame implements ActionListener{

JTextArea ta;
Watcher logtracker;
LogFilter lg;
JButton CreateDeck;
DeckList dl;
private String currentDeck="";
private String currentProfile="Default";
private String currentCardLang="enUS";
Menu menuBar;

public GUI(){
super("JTracker");
setBounds(0,0, 800, 630);
setLayout(null);

currentProfile=Configuration.getLastProfile();
currentCardLang=Configuration.getCardLang();

menuBar = new Menu(this, this);
setJMenuBar(menuBar);

CreateDeck = new JButton("Nuevo deck");
CreateDeck.setBounds(10, 10, 100, 30);
CreateDeck.addActionListener(this);
add(CreateDeck);



addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}

public void windowActivated(WindowEvent e){
setJMenuBar(menuBar);
}
}
);

File f = new File("Decks/");
dl = new DeckList((GUI)this, f.listFiles());

JScrollPane scrollListOfDecks = new JScrollPane(dl);
scrollListOfDecks.setBounds(50,50, 400, 500);
add(scrollListOfDecks);
}

public void updateDecks(){
dl.updateDecks();
}

public void setLogFilter(LogFilter filt){
lg=filt;
}

public LogFilter getLogFilter(){
return lg;
}

public String getCurrentDeck(){
return currentDeck;
}

public void setCurrentDeck(String currentDeck){
this.currentDeck=currentDeck;
}

public String getCurrentProfile(){
return currentProfile;
}

public void setCurrentProfile(String currentProfile){
this.currentProfile=currentProfile;
Configuration.setCurrentProfile(currentProfile);
}

public String getCurrentCardLang(){
return currentCardLang;
}

public void setCurrentCardLang(String currentCardLang){
this.currentCardLang=currentCardLang;
Configuration.setCardLang(currentCardLang);
}

public Menu getCurrentMenuBar(){
return menuBar;
}

public void actionPerformed(ActionEvent e){
if(e.getSource()== CreateDeck){
new SelectClassNewDeckGUI(this);
}
}




}