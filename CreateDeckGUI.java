import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.*;


public class CreateDeckGUI extends JFrame{

JButton TrackNewDeck, Save, Cancel;
GUI mainMenu;
JLabel numberOfCardsLabel;
String mySelectedClass;
JLabel nameOfDeckLabel;
JTextField nameOfDeckText;

CreateDeckGUI(JFrame lastWindow, String selectedClass){
super("JTracker");
setLayout(null);
this.mainMenu=(GUI)lastWindow;
setSize(mainMenu.getSize());
setLocation(mainMenu.getLocation());

mySelectedClass=selectedClass;

TrackNewDeck = new JButton("Crear Mazo Detectado");
TrackNewDeck.setBounds(10,10,160, 30);

add(TrackNewDeck);

Save = new JButton("Guardar");
Save.setBounds(180, 10, 80, 30);

add(Save);

Cancel = new JButton("Cancelar");
Cancel.setBounds(270, 10, 90, 30);

add(Cancel);

nameOfDeckLabel = new JLabel("Nombre del deck: ");
nameOfDeckLabel.setBounds(10,50, 105, 30);
add(nameOfDeckLabel);

nameOfDeckText = new JTextField(30);
nameOfDeckText.setBounds(125, 50, 200, 30);
add(nameOfDeckText);

addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);

numberOfCardsLabel = new JLabel("0/30");
numberOfCardsLabel.setBounds(100, 520, 150, 30);
add(numberOfCardsLabel);

setVisible(true);
mainMenu.setVisible(false);
}

}