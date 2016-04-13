import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Calendar;
import java.util.ArrayList;

public class CreateDeckGUI extends JFrame implements ActionListener, MouseListener{

JButton TrackNewDeck, Save, Cancel;
GUI mainMenu;
JPanel test;
Connection connection;
Statement query;
CardList mySelectedCards;
Vector cartas;
JLabel numberOfCardsLabel;
String mySelectedClass;
JLabel nameOfDeckLabel;
JTextField nameOfDeckText;
String pathEditedDeck=null;

CreateDeckGUI(JFrame lastWindow, String selectedClass){
super("JTracker");
setLayout(null);
this.mainMenu=(GUI)lastWindow;
setSize(mainMenu.getSize());
setLocation(mainMenu.getLocation());

mySelectedClass=selectedClass;

TrackNewDeck = new JButton("Crear Mazo Detectado");
TrackNewDeck.setBounds(10,10,160, 30);
TrackNewDeck.addActionListener(this);
add(TrackNewDeck);

Save = new JButton("Guardar");
Save.setBounds(180, 10, 80, 30);
Save.addActionListener(this);
add(Save);

Cancel = new JButton("Cancelar");
Cancel.setBounds(270, 10, 90, 30);
Cancel.addActionListener(this);
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


Connection(); 
ResultSet resultado = null; 
try { 
resultado = query.executeQuery("Select c.cardId, n.name, c.rarity, c.cost from Cards as c inner join Names as n on (c.cardId=n.cardId) where (c.playerClass='"+selectedClass+"' or c.playerClass='') and lang='esMX' and cost>=0 and rarity!='' and collectible=1 order by c.playerClass desc, cost, name;"); 
}
catch (SQLException e) { 
System.out.println(e.getMessage()); 
}
cartas = new Vector();
try{

while(resultado.next()){
cartas.add( new Card(resultado.getString(1),resultado.getString(2), resultado.getInt(4), resultado.getString(3)));

}
}
catch(Exception e){
System.out.println("Fallo al crear objetos "+e.getMessage());
}
Vector dataNames = new Vector();
dataNames.add("Cartas");

JTable table = new JTable(new MyTableModel(cartas, dataNames));
table.setDefaultRenderer(JPanel.class, new DataRenderer());
table.setFillsViewportHeight(true);
JScrollPane scrollPaneTable = new JScrollPane(table);
scrollPaneTable.setBounds(450, 110, 300, 400);
add(scrollPaneTable);
table.addMouseListener(new MouseAdapter() {
  public void mouseReleased(MouseEvent e) {
    if (e.getClickCount() == 1) {
      JTable target = (JTable)e.getSource();
      int row = target.getSelectedRow();
      int column = target.getSelectedColumn();
		generateCard(row);
		updateNumberOfCardsLabel();
		//System.out.println("row "+ row +" col " + column);
      
    }
  }
});

mySelectedCards = new CardList(this);
JScrollPane scrollListSelectedCards = new JScrollPane(mySelectedCards);
 scrollListSelectedCards.setBounds(100, 110, 300, 400);
add(scrollListSelectedCards);

numberOfCardsLabel = new JLabel("0/30");
numberOfCardsLabel.setBounds(100, 520, 150, 30);
add(numberOfCardsLabel);

setVisible(true);
mainMenu.setVisible(false);
}

public void updateNumberOfCardsLabel(){
numberOfCardsLabel.setText(mySelectedCards.getCurrentNumberOfCards()+"/30");
}

private String generateFileName(){
String temp="";
if(pathEditedDeck==null || pathEditedDeck.equals("")){
temp="Decks/";
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
}
else{
temp+=pathEditedDeck;
}
return temp;
}


public void actionPerformed(ActionEvent e){
if(e.getSource()==TrackNewDeck){
System.out.println("funciona");
}
else if(e.getSource()==Save){
if(nameOfDeckText.getText().trim().length()>0){
int answer=-1;
if(mySelectedCards.getCurrentNumberOfCards()<30){
answer=JOptionPane.showConfirmDialog(null, "El deck no esta completo\n¿Deseas guardarlo?","salida",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
}
if (answer==JOptionPane.YES_OPTION || mySelectedCards.getCurrentNumberOfCards()==30){

File folder= new File("Decks/");
if(!folder.exists()){
folder.mkdir();
}
FileOutputStream fs;
ObjectOutputStream os;
try{
fs = new FileOutputStream(generateFileName());
os = new ObjectOutputStream(fs);
os.writeObject(new Deck(nameOfDeckText.getText(), mySelectedCards.getCurrentCards(), mySelectedClass));
os.close();
fs.close();
mainMenu.setSize(getSize());
mainMenu.setLocation(getLocation());
mainMenu.updateDecks();
mainMenu.setVisible(true);
setVisible(false);
dispose();
}
catch(Exception ex){
System.out.println(ex.getMessage());
}
finally{

}
}
}
else{
JOptionPane.showMessageDialog(null,"El nombre del deck no es valido","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
}
}
else if(e.getSource()==Cancel){
mainMenu.setSize(getSize());
mainMenu.setLocation(getLocation());
mainMenu.setVisible(true);
setVisible(false);
dispose();
}
}

public void mouseClicked(MouseEvent e){
System.out.println("click");
}
public void mouseExited(MouseEvent e){
}
public void mouseEntered(MouseEvent e){
}
public void mousePressed(MouseEvent e){
}
public void mouseReleased(MouseEvent e){
}

public void generateCard(int row){
	mySelectedCards.addCard((Card)cartas.elementAt(row));
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

class MyTableModel extends AbstractTableModel {
	
       Vector cards;
		 Vector names;
		 
		 public MyTableModel (Vector data, Vector desc){
		 super();
	 cards=data;
		
		 names=desc;
		 }
        public int getColumnCount() {
            return 1;
        }

        public int getRowCount() {
            return cards.size();
        }

        public String getColumnName(int col) {
            return  "Cartas";
        }

        public Object getValueAt(int row, int col) {
            return cards.elementAt(row);
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return JPanel.class;
				        }
		  }
public class DataRenderer extends JPanel
                           implements TableCellRenderer {
  
    public DataRenderer () {
        setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object panel,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
        
		  		  
		  Card a = (Card)panel;
        return a;
    }
}
}