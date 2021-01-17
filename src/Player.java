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

        System.out.println(health);
    }
    int health = 100;
    int AttackDamage = 35;

    public class Item {

        private String note;
        private String itemId;
        private int quantity;
        private String description;


        public void add() {
            this.quantity++;
        }
        }


        public String getName() {
            return this.name;
        }
}
