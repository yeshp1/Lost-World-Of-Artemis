
/**
 *
 * This class holds all the information related to the characters in the game. 
 * The characters include the player and additional non-player characters that are created.
 * 
 * @author (Yeshvanth Prabakar)
 * @version (23/11/2017)
 */
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

public class Character
{
    private int weight=0;
    private List<String> itemsCarried = new LinkedList<String>();
    private HashMap<String, String> characters= new HashMap<String, String>(); 
    
    /** 
    * Adds item to the itemsCarried LinkedList;
    * @param item The item being stored in the inventory.
    */
    public void carryItem(String item)
    {
        itemsCarried.add(item);   
    }
    
    /** 
    * Removes item from the itemsCarried LinkedList;
    * @param item The item being removed from the inventory.
    */
    public void removeItem(String item)
    {
        itemsCarried.remove(item);
    }
    
    /** 
    * Displays all items in inventory
    * @return returns the String of all the items in the inventory
    */
    public String displayCarryItems()
    {
        String returnString="Inventory: ";
        for(String item: itemsCarried)
        {
            returnString+=" "+"["+item+"]";    
        }   
        return returnString;
    }
    
    /** 
    * Stores the created characters in a HashMap
    * @param character name of the character
    * @param item items carried by character
    */
    public void createCharacter(String character, String item)
    {
       characters.put(character, item); 
    }
    
    /** 
    * Returns the  item carried by a specific character
    * @param character the name of the specific character
    * @return returns the item carried by character if character is present in HashMap, null otherwise
    */
     public String getCharacterItem(String character)
    {
        Set<String> keys=characters.keySet();
        for(String characterx:keys)
        {
            if(characterx.equals(character))
            {
                return characters.get(characterx);
            }
        }
        return null;
    }
    
    /** 
    * Returns the specific character
    * @param character the name of the specific character
    * @return returns the name of character if present, null otherwise
    */
    public String getCharacter(String character)
    {
        Set<String> keys=characters.keySet();
        for(String characterx:keys)
        {
            if(characterx.equals(character))
            {
                return characterx;
            }
        }
        return null;
    }
    
    /** 
    * Checks whether all 3 fusecomponents are present in players inventory
    * @return true if player has all 3 fusecomponent items, false otherwise
    */
    public Boolean hasFuseComponents()
    {
        for(String item: itemsCarried)
        {
            if(item.equals("FirstFuseComponent")&&(item.equals("SecondFuseComponent"))&&(item.equals("ThirdFuseComponent")))   
            {
                return true;    
            }
        } 
        return false;
    }
    
    /** 
    * Checks whether key is present in players inventory
    * @return true if player has key, false otherwise
    */
    public Boolean hasKey()
    {
        for(String item: itemsCarried)
        {
            if(item.equals("Key"))   
            {
                return true;    
            }
        } 
        return false;
    }
    
    /** 
    * Checks whether player has all items to escape
    * @return true if they does, false otherwise
    */
    public Boolean hasEscaped()
    {
        String items=displayCarryItems();
        if((items.contains("Key"))&&(items.contains("FirstFuseComponent"))&&(items.contains("SecondFuseComponent"))&&(items.contains("SecondFuseComponent")))   
        {
            return true;    
        }
        return false;
    }
    
    /** 
    * Adds weight of the item to the inventory
    * @param weight the weight to be added to the inventory
    */
    public void addWeight(int weight)
    {
        this.weight+=weight;    
    }
    
    /** 
    * Displays weight of inventory
    * @return returns the weight of the inventory
    */
    public int currentWeight()
    {
        return weight;
    }
    
    /** 
    * Subtracts weight of the from the inventory
    * @param weight the weight to be subtracted from the inventory
    */
    public void removeWeight(int weight)
    {
    this.weight-=weight;    
    }
    
    
}
