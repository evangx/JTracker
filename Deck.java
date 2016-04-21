import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable{

private String nameDeck;
private ArrayList listCards;
private String selectedClass;
private boolean isStandard;

public String getNameDeck(){
return nameDeck;
}

public void setNameDeck(String name){
nameDeck=name;
}

public ArrayList getListOfCards(){
return listCards;
}

public void setListOfCards(ArrayList newListOfCards){
listCards= newListOfCards;
}

public String getSelectedClass(){
return selectedClass;
}

public void setSelectedClass(String newSelectedClass){
selectedClass=newSelectedClass;
}

public boolean isStandard(){
return isStandard;
}

public void setStandard(boolean isStandard){
this.isStandard=isStandard;
}

public Deck(){
}

public Deck(String name, ArrayList cards, String heroClass, boolean isStandard){
nameDeck=name;
listCards=cards;
selectedClass=heroClass;
this.isStandard=isStandard;
}

}