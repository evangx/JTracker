import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class NewProfileGUI extends JFrame implements ActionListener{

JTextField profileName, battletagName;
JButton ok, cancel;
Connection connection;
Statement query;
Menu menu;
ArrayList profiles;

public NewProfileGUI(JFrame mainWindow, Menu menu, ArrayList profiles){
super();
this.menu=menu;
this.profiles=profiles;
setLocation(mainWindow.getWidth()/2-200,mainWindow.getHeight()/2-50);
setLayout(new GridLayout(3,2));
getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
JLabel profileNameLabel = new JLabel("Name");
add(profileNameLabel);
profileName = new JTextField();
add(profileName);
JLabel battletagNameLabel = new JLabel ("BattleTag");
add(battletagNameLabel);
battletagName = new JTextField();
add(battletagName);
ok = new JButton("Aceptar");
ok.addActionListener(this);
add(ok);
cancel = new JButton("Cancelar");
cancel.addActionListener(this);
add(cancel);
setUndecorated(true);
setSize(400,100);
setVisible(true);
}

public void actionPerformed(ActionEvent e){
if(e.getSource()==cancel){
dispose();
}
else if(e.getSource()==ok){
if(profileName.getText().length()>0 && battletagName.getText().length()>0){
if(!profiles.contains(profileName.getText())){
Connection();
try{
query.executeUpdate("insert into Profiles values ('"+profileName.getText()+"', '"+battletagName.getText()+"');");
}
catch(Exception ex){
System.out.println(ex.getMessage());
}
finally{
try{
connection.close();
}
catch(Exception ex){
System.out.println(ex.getMessage());
}
}
menu.updateProfiles();
dispose();
}
else{
JOptionPane.showMessageDialog(null,"El perfil ya existe","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
}
}
else{
JOptionPane.showMessageDialog(null,"Todos los campos deben ser completados","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
}
}
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

}