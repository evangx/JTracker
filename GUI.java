import javax.swing.JFrame;
import javax.swing.JMenuBar;
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


public GUI(){
super("JTracker");
setBounds(0,0, 800, 600);
setLayout(null);

JMenuBar jmb = new JMenuBar();
setJMenuBar(jmb);



CreateDeck = new JButton("Nuevo deck");
CreateDeck.setBounds(10, 10, 100, 30);
CreateDeck.addActionListener(this);
add(CreateDeck);



addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);

File f = new File("Decks/");
dl = new DeckList(this, f.listFiles());

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

public void actionPerformed(ActionEvent e){
if(e.getSource()== CreateDeck){
new SelectClassNewDeckGUI(this);
}
}

}