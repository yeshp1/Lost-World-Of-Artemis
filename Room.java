import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;    // stores exits of this room.
    public HashMap<String,Integer> items=new HashMap<String,Integer>();    //stores items of this room
    private Character character=new Character();
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }
    
    /** 
    * Retrieves the weight of the item
    * @param item The weight of the item to be retrieved
    * @return returns the weight of the item in room
    */
    public int returnWeight(String item)
    {
        return items.get(item);    
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /** 
    * Adds the item to the room (items HashMap)
    * @param item the name of the item
    * @param weight the weight of the item
    */
    public void setItem (String item, int weight) 
    {
        items.put(item, weight);
    }
    
    /**
     * Display items in each room
     * @return returns all the items
     */
    public String getItems()
    {
        String returnString = "Items: ";
        Set<String> keys = items.keySet();
        for(String itemr : keys) {
            returnString += " " + "[" + itemr + "]";
        }
        return returnString;
    }  
    
    /**
     * @return The short description of the room
     * @(the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return (description + ".\n" + getExitString());
    }
    
    /**
     * Return a description of the exits and Items of the room
     */
    public String getDescription()
    {
        return getExitString() + "\n" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + "["+ exit + "]";
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
}

