import java.sql.*;
public class Record{
static Connection connection;
static Statement query;
static ResultSet rs;
public static void createRecord(String deck, int opposingHero, boolean isWinner, String profile){
Connection();
try{
query.execute("insert into Matches values('"+deck+"', "+ opposingHero+", '"+ isWinner + "', date('now'), (select oid from Profiles where name='"+profile+"'));");
}
catch(Exception e){
System.out.println(e.getMessage());
}
}

public static ResultSet getAll(String deck){
Connection();
try{
rs=query.executeQuery("Select opposingHero, isWinner, COUNT(isWinner) from Matches where deck='"+deck+"' group by opposingHero, isWinner;");
}
catch(Exception e){
System.out.println(e.getMessage());
}
return rs;
}

public static ResultSet getLastMonth(String deck){
Connection();
try{
//rs=query.executeQuery("Select opposingHero, isWinner, COUNT(isWinner) from Matches where deck='"+deck+"' and date>date('now', '-1 month') group by opposingHero, isWinner;");
rs=query.executeQuery("Select opposingHero, isWinner, COUNT(isWinner) from Matches where deck='"+deck+"' and date>date('now', 'start of month') group by opposingHero, isWinner;");
}
catch(Exception e){
System.out.println(e.getMessage());
}
return rs;
}

public static void Connection(){
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