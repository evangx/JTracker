import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Menu extends JMenuBar implements ActionListener, ItemListener{
JMenuItem newProfileMenuItem;
JFrame mainWindow;
JMenuItem defaultProfileMenuItem;
JMenu profilesMenu, deleteProfileMenu;
Connection connection;
Statement query;
ArrayList profiles;
GUI lastWindow;

public Menu(JFrame mainWindow, GUI lastWindow){
super();
this.mainWindow=mainWindow;
this.lastWindow=lastWindow;
profilesMenu = new JMenu("Profiles");
add(profilesMenu);
defaultProfileMenuItem = new JMenuItem("Default");
profilesMenu.add(defaultProfileMenuItem);
updateProfiles();
}

public void updateProfiles(){
profilesMenu.removeAll();
defaultProfileMenuItem.addActionListener(this);
profilesMenu.add(defaultProfileMenuItem);

deleteProfileMenu = new JMenu("Delete Profile");

Connection();
ResultSet resultado= null;

JMenuItem temp;
profiles=new ArrayList();
try{
resultado = query.executeQuery("Select name from Profiles where oid>1");
while(resultado.next()){
profiles.add(resultado.getString(1));
temp=new JMenuItem(resultado.getString(1));
temp.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
JMenuItem menuTemp=(JMenuItem) e.getSource();
lastWindow.setCurrentProfile(menuTemp.getText());
}
}
);
profilesMenu.add(temp);
temp=new JMenuItem(resultado.getString(1));
temp.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
JMenuItem menuTemp=(JMenuItem) e.getSource();
int answer=JOptionPane.showConfirmDialog(null, "Se Borrara el perfil: "+menuTemp.getText()+"\n¿Deseas continuar?","eliminar",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
if(answer==JOptionPane.YES_OPTION){
Connection();
try{
query.execute("delete from Profiles where name='"+menuTemp.getText()+"';");
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
if(menuTemp.getText().equals(lastWindow.getCurrentProfile())){
lastWindow.setCurrentProfile("Default");
}
updateProfiles();
}
}
});
deleteProfileMenu.add(temp);
}
}
catch(Exception e){
System.out.println(e.getMessage());
}
finally{
try{
connection.close();
}
catch(Exception e){
System.out.println(e.getMessage());
}
}
profilesMenu.addSeparator();
newProfileMenuItem = new JMenuItem("New Profile");
newProfileMenuItem.addActionListener(this);
profilesMenu.add(newProfileMenuItem);
profilesMenu.addSeparator();

profilesMenu.add(deleteProfileMenu);
}


public void actionPerformed(ActionEvent e){
if(e.getSource()==newProfileMenuItem){;
new NewProfileGUI(mainWindow, this, profiles);
}
else if(e.getSource()==defaultProfileMenuItem){
lastWindow.setCurrentProfile("Default");
}
}

public void itemStateChanged(ItemEvent e){
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