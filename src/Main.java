/**
 * Based on the Collosal Cave Adventure game
 */
import java.util.Random;
import java.util.Scanner;
public class Main
{
    private static Game zuul;

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        zuul = new Game();
        //system variables
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        //game variables
        String[] enemies ={"wolf", "Skeleton", "Zombie", "Wraith", "knight", "ancient knight","dragon","gargoyle","Steel king of smite" };
        int maxEnemyHealth= 50;
        int EnemyAttackDamage= 20;
    }
}
