import java.util.Random;
/**
 *
 * This class holds all the information related to the monster in the game 
 *
 * @author (Yeshvanth Prabakar)
 * @version (23/11/2017)
 */
public class Monster
{ 
Random random=new Random();

 /** 
  * Print out the description for the Monster
  */
public void getMonsterDescription()
{
    System.out.println("A wild human like figure approaches you, you realise that all trace of humanity is gone it is a... monster");    
}

/** 
  * Probability that the monster spawns
  * @return true if monster spawns, false otherwise
  */
public boolean spawn()
{
    int randomNum=random.nextInt(6);
    if(randomNum==1)
    {      
        return true;
    }
    return false;
}

/** 
  * Probability that the monster kills the player
  * @return true if monster kills player, false otherwise
  */
public boolean probabilityKilled(int upperLimitRandom)
{
    int randomNum=random.nextInt(upperLimitRandom);
    if(randomNum==1)
    {      
        return true;
    }
    return false;
}
}