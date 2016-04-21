import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.event.*;

public class SelectClassNewDeckGUI extends JFrame implements ActionListener{
JFrame lastWindow;
JButton Warrior, Shaman, Rouge, Paladin, Hunter, Druid, Warlock, Mage, Priest, Cancel;
JRadioButton isWild, isStandard;
JLabel deckTypeLabel;

public SelectClassNewDeckGUI(JFrame last){
super("JTracker");
setLayout(null);

lastWindow=last;
setLocation(lastWindow.getLocation());
setSize(lastWindow.getSize());

deckTypeLabel = new JLabel("tipo de deck: ");
deckTypeLabel.setBounds(120, 10 , 100, 30);
add(deckTypeLabel);

ButtonGroup deckType = new ButtonGroup();

isStandard = new JRadioButton ("Standard");
deckType.add(isStandard);
isStandard.setBounds(230, 10, 100, 30);
isStandard.setSelected(true);
add(isStandard);

isWild = new JRadioButton("Salvaje");
deckType.add(isWild);
isWild.setBounds(340, 10, 100, 30);
add(isWild);



Warrior = new JButton("Warrior");
Warrior.setBounds(50, 50, 150, 30);
Warrior.addActionListener(this);
add(Warrior);

Shaman = new JButton("Shaman");
Shaman.setBounds(250, 50, 150, 30);
Shaman.addActionListener(this);
add(Shaman);

Rouge = new JButton("Rouge");
Rouge.setBounds(450, 50, 150, 30);
Rouge.addActionListener(this);
add(Rouge);

Paladin = new JButton("Paladin");
Paladin.setBounds(50, 130, 150, 30);
Paladin.addActionListener(this);
add(Paladin);

Hunter = new JButton("Hunter");
Hunter.setBounds(250, 130, 150, 30);
Hunter.addActionListener(this);
add(Hunter);

Druid = new JButton("Druid");
Druid.setBounds(450, 130, 150, 30);
Druid.addActionListener(this);
add(Druid);


Warlock = new JButton("Warlock");
Warlock.setBounds(50, 210, 150, 30);
Warlock.addActionListener(this);
add(Warlock);

Mage = new JButton("Mage");
Mage.setBounds(250, 210, 150, 30);
Mage.addActionListener(this);
add(Mage);

Priest = new JButton("Priest");
Priest.setBounds(450, 210, 150, 30);
Priest.addActionListener(this);
add(Priest);

Cancel = new JButton("Cancelar");
Cancel.setBounds(250, 290, 150, 30);
Cancel.addActionListener(this);
add(Cancel);

addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(0);
}
}
);

setVisible(true);
lastWindow.setVisible(false);
} 

public void actionPerformed(ActionEvent e){
lastWindow.setSize(getSize());
lastWindow.setLocation(getLocation());

if(e.getSource()==Warrior){
new CreateDeckGUI(lastWindow, "Warrior", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Shaman){
new CreateDeckGUI(lastWindow, "Shaman", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Rouge){
new CreateDeckGUI(lastWindow, "Rouge", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Paladin){
new CreateDeckGUI(lastWindow, "Paladin", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Hunter){
new CreateDeckGUI(lastWindow, "Hunter", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Druid){
new CreateDeckGUI(lastWindow, "Druid", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Warlock){
new CreateDeckGUI(lastWindow, "Warlock", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Mage){
new CreateDeckGUI(lastWindow, "Mage", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Priest){
new CreateDeckGUI(lastWindow, "Priest", isStandard.isSelected());
setVisible(false);
dispose();
}
else if(e.getSource()==Cancel){
lastWindow.setVisible(true);
setVisible(false);
dispose();
}
}

}