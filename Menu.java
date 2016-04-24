import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Menu extends JMenuBar implements ActionListener, ItemListener{
JMenuItem newProfileMenuItem, changeHearthstonePath;
JFrame mainWindow;
JMenuItem defaultProfileMenuItem;
JMenu profilesMenu, deleteProfileMenu;
Connection connection;
Statement query;
ArrayList profiles;
GUI lastWindow;
JMenu cardLangs;

public Menu(JFrame mainWindow, GUI lastWindow){
super();
this.mainWindow=mainWindow;
this.lastWindow=lastWindow;

profilesMenu = new JMenu("Profiles");
add(profilesMenu);

defaultProfileMenuItem = new JMenuItem("Default");
profilesMenu.add(defaultProfileMenuItem);
updateProfiles();

JMenu languageMenu = new JMenu("Language");
add(languageMenu);

cardLangs = new JMenu("Cards");
languageMenu.add(cardLangs);

createListOfCardLangs(Configuration.getCardLang());

JMenu configurationMenu = new JMenu("Configuration");
add(configurationMenu);

changeHearthstonePath = new JMenuItem("Hearthstone path");
changeHearthstonePath.addActionListener(this);
configurationMenu.add(changeHearthstonePath);


}

private void createListOfCardLangs(String selectedLang){
if(selectedLang.equals("") || selectedLang==null){
selectedLang="enUS";
}
Connection();
ResultSet resultado= null;

ButtonGroup group = new ButtonGroup();

try{
resultado = query.executeQuery("Select DISTINCT(lang) from Names");
JRadioButtonMenuItem submenuTemp;
while(resultado.next()){
submenuTemp = new JRadioButtonMenuItem (resultado.getString(1));
if(submenuTemp.getText().equals(selectedLang)){
submenuTemp.setSelected(true);
}
submenuTemp.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
JRadioButtonMenuItem sbmTemp=(JRadioButtonMenuItem) e.getSource();
lastWindow.setCurrentCardLang(sbmTemp.getText());
}
});
group.add(submenuTemp);
cardLangs.add(submenuTemp);
}
}
catch(Exception e){
System.out.println(e.getMessage());
}
finally{
try{
connection.close();
}
catch(Exception ex){
System.out.println(ex.getMessage());
}
}

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
else if(e.getSource()==changeHearthstonePath){
String path = Configuration.getHearthstonePath();
String newPath="";
newPath=JOptionPane.showInputDialog("Ingrese el directiorio de\ninstalacion de hearthstone",path);
System.out.println(path);
System.out.println(newPath);
if(newPath!=null){
if(!path.equals(newPath) && newPath.length()>0){
Configuration.setHearthstonePath(newPath);
}
}
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