import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.*;
import java.io.File;


public class GUI extends JFrame{

Watcher logtracker;
LogFilter lg;
JButton CreateDeck;


public GUI(){
super("JTracker");
setBounds(0,0, 800, 600);
setLayout(null);

JMenuBar jmb = new JMenuBar();
setJMenuBar(jmb);



CreateDeck = new JButton("Nuevo deck");
CreateDeck.setBounds(10, 10, 100, 30);

add(CreateDeck);



addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);


}








}