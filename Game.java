/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room nextRoom, previousRoom, roomRandomiser;
    private Random random;
    private Monster monster;
    private Character character;
    //ArrayList to store a variety of phrases which suggest there is no door in that direction
    private ArrayList<String> doorOptions;
    //ArrayList to store items carried by player
    private Room sea, mainHall, stellasGarden, crematorium, emergencyAccess, fountainOfHeritage, clinic, aleCorner, pointPrometheus, artemisRecords, fortKeller, submarine;
    private ArrayList<Room> randomRoom;
    private String item, itemsCarried;
    private String commandWord, secondWord, thirdWord;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        character = new Character();
        doorOptions=new ArrayList<String>();
        randomRoom=new ArrayList<Room>();
        parser = new Parser();
        monster=new Monster();
        random = new Random();
        createRooms();
        createItems();
        addRooms();
        exitVariety();
        createCharacter();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        sea = new Room("you are in the pacific ocean, nothing but water");
        
        mainHall = new Room("you have entered Artemis's main hall");
        crematorium = new Room("you are in the Crematorium");
        stellasGarden = new Room("you are in Stella's-Garden");
        
        emergencyAccess = new Room("you are in the emergency access room");
        fountainOfHeritage = new Room("A beautiful Ventian themed fountain: the Fountain of Heritage");
        
        aleCorner = new Room("you are in the Ale Corner, nothing but scrap bottles of wine and empty barrels");
        clinic = new Room("you have entered the clinic");
        pointPrometheus = new Room("you are in pointPrometheus");
        artemisRecords = new Room("you are in Artemis's record room, an archive of all data that went into building this place");
        fortKeller = new Room("you are in fort keller");
        submarine = new Room("You are in the submarine... you need to collect the 3 fuse components to turn on the power and escape");
        // initialise room exits, all rooms in one floor are grouped together
        
        sea.setExit("north", mainHall);  
        mainHall.setExit("north", crematorium);
        mainHall.setExit("down", emergencyAccess);
        mainHall.setExit("up", aleCorner);

        crematorium.setExit("west", stellasGarden);
        crematorium.setExit("south", mainHall);
        stellasGarden.setExit("east", crematorium);
        
        aleCorner.setExit("west", pointPrometheus);
        aleCorner.setExit("east", clinic);
        aleCorner.setExit("south", artemisRecords);
        aleCorner.setExit("down", mainHall);
        clinic.setExit("west", aleCorner);
        artemisRecords.setExit("north", aleCorner);
        pointPrometheus.setExit("east", aleCorner);
        pointPrometheus.setExit("south", fortKeller);
        fortKeller.setExit("north", pointPrometheus);
        
        emergencyAccess.setExit("east", fountainOfHeritage);
        emergencyAccess.setExit("up", mainHall);
        emergencyAccess.setExit("north", submarine);
        fountainOfHeritage.setExit("west", emergencyAccess);
        submarine.setExit("south", emergencyAccess);

        currentRoom = sea;  // start game outside
    }
     
    /**
     * Create all the characters in game
     */
    private void createCharacter()
    {
        character.createCharacter("mysteriousMan", "cloakOfShadows");
    }
    
    /**
     * Store all the created rooms in an randomRoom ArrayList
     */
    private void addRooms()
    {
       randomRoom.add(sea);
       randomRoom.add(mainHall);
       randomRoom.add(stellasGarden);
       randomRoom.add(emergencyAccess);
       randomRoom.add(fountainOfHeritage);
       randomRoom.add(clinic);
       randomRoom.add(aleCorner);
       randomRoom.add(pointPrometheus);
       randomRoom.add(artemisRecords);
       randomRoom.add(fortKeller);
    }
    
    /**
     * Create all the items in each room.
     */
    private void createItems()
    {
        addRooms();
        mainHall.setItem("Key",10);  
        mainHall.setItem("Elongated-Table",500);
        crematorium.setItem("HealthKit",20);  
    
        fortKeller.setItem("SecondFuseComponent",15);
        artemisRecords.setItem("ThirdFuseComponent",15);
        artemisRecords.setItem("ArchivedFilesofArtemis",1000);
        clinic.setItem("HealthKit",20); 
    
        emergencyAccess.setItem("FirstFuseComponent",15);
        fountainOfHeritage.setItem("Fountain",50000);
        crematorium.setItem("HealthKit",20); 
        
        //randomly generates the items to appear in different rooms
        
        roomRandomiser=randomRoom.get(roomNumberGenerator(randomRoom.size()));
        roomRandomiser.setItem("ancestralCoin",40);
        roomRandomiser=randomRoom.get(roomNumberGenerator(randomRoom.size()));
        roomRandomiser.setItem("Wrench",70);
        roomRandomiser=randomRoom.get(roomNumberGenerator(randomRoom.size()));
        roomRandomiser.setItem("Hammer",30);
        roomRandomiser=randomRoom.get(roomNumberGenerator(randomRoom.size()));
        roomRandomiser.setItem("Sword",90);
        roomRandomiser=randomRoom.get(roomNumberGenerator(randomRoom.size()));
        roomRandomiser.setItem("Cigarette",90);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Artemis, an underwater Utopia that went south after a pandemic struck the city");
        System.out.println("The city is filled with mutated monsters of all kinds. Your plane has just crashed in the midst of the sea and in the distance you see an entrance to the city Artemis. It is your job to find a way out of here and make it home");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        this.commandWord = command.getCommandWord(); //first word entered by user 
        this.secondWord = command.getSecondWord(); //secondword entered by user
        this.thirdWord = command.getThirdWord();
        this.item = currentRoom.getItems();
        this.itemsCarried = character.displayCarryItems(); 
        character.displayCarryItems(); 
                    
        // implementations of user commands:
        
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        if (commandWord.equals("help")) 
        {
            printHelp();
        }
        
        if (commandWord.equals("go")) 
        {   
            //monster has chance of appearing/killing player everytime player enters new room
            if(monsterAppearance(command)==true)
            {
                wantToQuit = quit(command);    
            }
            goRoom(command);
        }
        
        if(commandWord.equals("back"))
        {
            directionBack(); 
        }
        
        if(commandWord.equals("select"))
        {
            selectItem();
        }
        
        if(commandWord.equals("use"))
        {
            useItem();
        }
        
        if (commandWord.equals("quit")&&command.getSecondWord()!=null) {
            wantToQuit = quit(command);
            System.out.println("Thank you for playing.  Good bye.");
        } 
        
        if(commandWord.equals("drop"))
        { 
            dropItem();        
        }
           
        if (win()==true) {
            wantToQuit = quit(command);
            System.out.println("Congrats you have Escaped Artemis and are making your way back home!");
        }
                
        getCloakOfShadows(command);
        
        return wantToQuit;
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost at sea. You are alone. You see a massive building-like structure in front of you");
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
        
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {    
        itemsCarried = character.displayCarryItems();
        item = currentRoom.getItems();
        String direction = command.getSecondWord();
        String directionBack = command.getCommandWord();
        
        if(command.hasSecondWord()&&(commandWord.equals("go"))) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if(commandWord.equals("back"))
        {
            nextRoom = currentRoom.getExit(directionBack);
        }
        
        //teleporter room
        
        if(nextRoom==fountainOfHeritage)
        {
            System.out.println("you have entered the fountain of heritage, you feel the presence of the ancestors, the energy from the fountain has teloported you to another room");
            int randomNumber=random.nextInt(randomRoom.size());
            nextRoom=randomRoom.get(randomNumber);
            currentRoom = nextRoom;
        }
        
        if(nextRoom==mainHall||previousRoom==mainHall)
        {
            System.out.println("A " + character.getCharacter("mysteriousMan") + " in a robe appears, he appears to have something to say, type 'give ancestralCoin mysteriousMan' to give him the coin in exchange for a suprise");   
        }

        //dont enter submarine if character doesnt have key
        if((nextRoom==submarine)&&(!character.hasKey()))
        {
            System.out.println("You must collect the key to open the submarine door");    
        }
        else
        {
            if (nextRoom == null) {
                int randomNumber = random.nextInt(6);
                System.out.println(doorOptions.get(randomNumber));
            }
            else {
                previousRoom = currentRoom; //stores current room for back command
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                System.out.println(currentRoom.getItems());
                System.out.println(character.displayCarryItems());
                System.out.println("Weight: "+character.currentWeight()+"units"); 
            }
        }
    }
    
    /**
     * Implementation of the Monster that tries to kill the player and end game
     */
    public Boolean monsterAppearance(Command command)
    {
        boolean wantToQuit = false;
        if(itemsCarried.contains("cloakOfShadows"))
        {   //monster spawns and kills player with lower probability if they have cloak
            if((monster.spawn()==true)&&(monster.probabilityKilled(10)==true))
            {
                System.out.println("Monster has killed you thanks for playing");
                wantToQuit = quit(command);    
            }
            else if(monster.spawn()==true&&(monster.probabilityKilled(10)==false))
            {
                System.out.println("A monster appears but you have escaped it!");
                goRoom(command);
            }   
        }
        else
        {   //monster has normal kill and spawn chance if cloak is not present
            if((monster.spawn()==true)&&(monster.probabilityKilled(6)==true))
            {
                System.out.println("Monster has killed you thanks for playing");
                wantToQuit = quit(command);    
            }
                else if(monster.spawn()==true&&(monster.probabilityKilled(6)==false))
            {
                System.out.println("A monster appears but you have escaped it!");
            }
        }       
        return wantToQuit;
    }
        
    /**
     * Returns the player to the previous room and prints descriptions
     */
    private void directionBack()
    {
        currentRoom=previousRoom;
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.getItems());
        System.out.println(character.displayCarryItems());
        System.out.println("Weight: "+character.currentWeight()+"units"); 
    }
    
    /** 
     * Implementation of the 'select' commandWord
     */
    public void selectItem()
    {
        if(secondWord==null||!(item.contains(secondWord)))
        {
            System.out.println("That is not a valid item");
        }           
        else if(item.contains(secondWord)&&(character.currentWeight()<=150))
        {
            if((currentRoom.returnWeight(secondWord))>150) //if item is greater than 150 units don't pick up item
            {
                System.out.println("You can't pick up that item");    
            }
            else if((character.currentWeight()+currentRoom.returnWeight(secondWord))>150) //if your inventory exceeds 150 units or you were to pick up an item that would cause your inventory to exceed 50 units don't pick up item
            {
                System.out.println("Your inventory is too heavy to carry that item");     
            }
            else
            {
                character.carryItem(secondWord); //add selected item to the itemsCarried LinkedList
                character.addWeight(currentRoom.returnWeight(secondWord)); //add weight of item
                System.out.println("Weight: "+character.currentWeight()+"units");
                System.out.println(character.displayCarryItems()); 
            } 
        }
    }
    
    /** 
     * Implementation of the 'use' commandWord
     */
    public void useItem()
    {
        if(secondWord==null)
        {
            System.out.println("You cannot use that item");
        }
        else if(secondWord.equals("Cigarette")&&(itemsCarried.contains("Cigarette")))
        {
            System.out.println("You smoke a cigarette and release some tension");
        }
    }
    
    /** 
     * Implementation of the 'drop' commandWord
     */
    public void dropItem()
    {
        if(itemsCarried.contains(secondWord)&&item.contains(secondWord))
        {
            character.removeItem(secondWord);    //remove selected item from the itemsCarried ArrayList
            character.removeWeight(currentRoom.returnWeight(secondWord)); //remove weight of item
            System.out.println("Weight: "+character.currentWeight()+"units");
            System.out.println(character.displayCarryItems()); 
        }
        else 
        {
            System.out.println("you cant drop that item in a different room");    
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {   
        if(win()==true)
        {
            return true;    //end game if character has won
        }
        
        if((monster.probabilityKilled(4)==true))
        { 
            return true;   //end game if player is killed
        }
        
        if(command.getCommandWord().equals("quit")&&command.getSecondWord()==null) 
        {
            System.out.println("Quit what?");
            return false;
        }
        else 
        {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * Check whether the game is ready to end
     * If in submarine and character has collected key and fusecomponents return true, false otherwise
     */
    private boolean win()
    {
        if((currentRoom==submarine)&&(character.hasEscaped()))
        {
            return true;    
        }    
        return false;
    }
    
    /**
     * command words.
     */
    public void getCloakOfShadows(Command command)
    {
        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        if((commandWord.equals("give"))&&(currentRoom==mainHall)&&(command.getSecondWord()==null||(command.getThirdWord()==null)))
        {
            System.out.println("The mysteriousMan does not understand what you are trying to do");
        }
        else if((commandWord.equals("give"))&&(secondWord.equals("ancestralCoin"))&&(thirdWord.equals("mysteriousMan"))&&(!(itemsCarried.contains("ancestralCoin"))))
        {
            System.out.println("you do not have the ancestralCoin item in your inventory");
        }
        else if((commandWord.equals("give"))&&(secondWord.equals("ancestralCoin"))&&(thirdWord.equals("mysteriousMan"))&&(itemsCarried.contains("ancestralCoin")))
        {
            String cloakOfShadows = character.getCharacterItem("mysteriousMan");
            character.carryItem(cloakOfShadows);  
            System.out.println("The mysteriousMan gives you a cloak of shadows which reduces the chance that you can be seen by monsters");
            character.removeItem("ancestralCoin"); 
            character.addWeight(50);
            System.out.println("Weight: "+character.currentWeight()+"units");
            System.out.println(character.displayCarryItems()); 
        }
    }
    
    /**
     *  Gives a variety of exit responses to the player.
     */
    private void exitVariety()
    {
        doorOptions.add("There is no door");
        doorOptions.add("Nothing but a wall in front of you");
        doorOptions.add("There is no space to exit");
        doorOptions.add("That is not an exit");
        doorOptions.add("You can't head in that direction");
        doorOptions.add("You gaze at the ceiling in bewilderment");   
    }
    
     /**
     * Generates a random number
     * @param upperLimit Numbers are generaterd from 0 till upperLimit (excluded)
     * @return returns the randomly generated number
     */
    public int roomNumberGenerator(int upperLimit)
    {
        int randomNumber=random.nextInt(upperLimit);   
        return randomNumber;
    }
}
