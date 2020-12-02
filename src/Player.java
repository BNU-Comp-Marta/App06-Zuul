/**
 *
 */
public class Player
{
    private final String name;
    private int score;
    private int count;

    /**
     * Added another comment
     * @param name
     */
    public Player(String name)
    {
        this.name = name;
        score = 0;
    }

    /**
     *
     */
    public void print()
    {
        System.out.println("Player : " + name);
        System.out.println();
    }
}
