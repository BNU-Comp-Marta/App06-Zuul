import java.util.ArrayList;
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
 * 
 * Modified and extended by Derek and Andrei
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Timer timer;
    ArrayList<Item> Inventory = new ArrayList<Item>();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        timer = new Timer(15, -1, 5);
        play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room Cave, Crypt, Dark_Forest, Village, Dragon_keep, Valley_of_souls, Castle, Castle_interior, Castle_f1, Castle_f2, Castle_f3, end;

        // create the rooms
        Cave = new Room("In a cold, dark cave and you can hear the revolting sounds of the undead coming from the east. In your hand you hold a torch which lights the way. You can see a strange path with a light in the west ");
        Crypt = new Room(" in a dimly lit corridor as you see strange markings on the wall and bones scattered around the floor and the smell of rot is sickening");
        Dark_Forest = new Room("in the huntsman's forest where and are cold to the bone and afraid of whats to come, in the west you see an odd light...");
        Valley_of_souls = new Room("you can hear the souls of the lost nearby");
        Dragon_keep = new Room("listening to a loud breathing noise as you enter and see the burnt corpse of a soldier with his sword still in hand...and the low warning growl of a dragon");
        Village = new Room("in a surprising safe haven from all the suffering you find a cosy little settlement, with peace and tranquility, indeed a rare sight. You find a note on the floor");
        Castle = new Room("outside the tallest structure you have ever seen, with crumbling brick, moss everywhere and a large door");
        Castle_interior = new Room("terrified ad the door slams shut behind you and you find yourself trapped in this ancient castle with no means of escape, you find the only way is forward now, but there is an ancient sword glistening in the distance");
        Castle_f1 = new Room("standing in the castle's 1st floor, you hear whispers and realise you are doomed with no way out");
        Castle_f2 = new Room("on The second floor of the castle evokes different feelings as you know in your heart you are close to something");
        Castle_f3 = new Room("at the final floor of the castle and the end of your journey, or is it.");
        end = new Room("Congratulations, you have reached the end of the game");
        // initialise room exits
        Cave.setExit("east", Crypt);
        Crypt.setExit("south", Dark_Forest);
        Village.setExit("west", Castle);

        Cave.setExit("west", Dragon_keep);

        Dragon_keep.setExit("east", Cave);

        Dragon_keep.setExit("north", Village);

        Crypt.setExit("north", Valley_of_souls);

        Valley_of_souls.setExit("south", Crypt);

        Dark_Forest.setExit("north", Crypt);
        Dark_Forest.setExit("west", Village);
        Village.setExit("east", Dark_Forest);
        Castle.setExit("east", Village);
        Castle.setExit("north", Castle_interior);

        Castle_interior.setExit("north", Castle_f1);
        Castle_f1.setExit("north", Castle_f2);

        Castle_f2.setExit("north", Castle_f3);

        Castle_f3.setExit("end", end);

        Crypt.setExit("west", Cave);

        int weight1 = 5;
        int weight2 = 1;
        int weight3 = 4;
        int weight4 = 10;
        int weight5 = 2;
        int weight6 = 30;

        int maxweight = 40;
        int playerweight = 0;

        if ((playerweight) <= maxweight) {
            System.out.println("You can take this item");

        } else if ((playerweight) > maxweight) {
            System.out.println("You are overencumbered and move very slow");
        }


        currentRoom = Cave;  // start game in cave
        //items to be interacted with
        Inventory.add(new Item("Torch"));
        Village.setItem(new Item("Note"));
        Valley_of_souls.setItem(new Item("Crystal"));
        Castle_interior.setItem(new Item("Sword"));
        Castle_f1.setItem(new Item("Key"));
        Castle_f2.setItem(new Item("Armour"));


        Village.useItem(new Item(":You must go to the castle, but beware of the darkness within" + "\n" + weight2 + "g"));
        Valley_of_souls.useItem(new Item(" an odd crystal, with a faint sound echoing within" + "\n" + weight3 + "g"));
        Castle_interior.useItem(new Item("an old rusty sword but an odd energy permeates through this weapon" + "\n" + weight4 + "g"));
        Castle_f1.useItem(new Item("forgotten old key to unlock answers or more questions..." + "\n" + weight5 + "g"));
        Castle_f2.useItem(new Item("old armour which once was worn by the true king" + "\n" + weight6 + "g"));
    }


    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Thank you for playing.  see you later...");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the dark and twisted World of Zuul!");
        System.out.println("This land is a new, incredibly dark text based game, good luck, you will need it!");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("You have " + timer + " moves to win.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());


    }


    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean Wanttoquit = false;
        boolean updateTimer = true;


        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("invalid command, tip add go before you type a direction e.g go west... or a capital letter for Inventory");
                updateTimer = false;
                break;

            case HELP:
                printHelp();
                updateTimer = false;
                break;

            case GO:
                Wanttoquit = goRoom(command);
                break;

            case Inventory:
                printInventory();
                printMaxweight();
                updateTimer = false;
                break;

            case get:
                getItem(command);
                updateTimer = false;
                break;

            case drop:
                dropItem(command);
                updateTimer = false;
                break;

            case moves:
                System.out.println("You have " + timer + " moves left...");
                updateTimer = false;
                break;

            case QUIT:
                Wanttoquit = quit(command);
                updateTimer = false;
                break;
        }
        if (updateTimer) {
            timer.updateTimer();
            if (timer.hasExpired())
                System.out.println("Your time has run out! - you have failed and let the darkness destroy you");
            Wanttoquit = false;
        } else if (timer.isLow()) {
            System.out.println("Hurry, time is running low and the darkness approaches");
            System.out.println("You have " + timer + " moves left!");
        }
        return Wanttoquit;
    }

    private void printMaxweight() {
        System.out.println(40 +"g total");
    }


    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop..
            System.out.println("drop what?");
            return;
        }
        {


        }

        String item = command.getSecondWord();

        //stores item in inventory
        Item newItem = null;
        int index = 0;
        for (int i = 0; i < Inventory.size(); i++) {
            if (Inventory.get(i).getDescription().equals(item)) {
                newItem = Inventory.get(i);
                index = i;
            }

        }
        if (newItem == null) {
            System.out.println("You do not have that item in your inventory");
        } else {
            Inventory.remove(index);
            currentRoom.setItem(new Item(item));
            System.out.println("dropped:" + item);
        }
    }

    private void printInventory() {
        String output = "";
        for (int i = 0; i < Inventory.size(); i++) {
            output += Inventory.get(i).getDescription() + " ";

        }
        System.out.println("You are carrying");
        System.out.println(output);
    }

    private void getItem(Command command) {

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what?");
            return;
        }
        {


        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("There is no such item");
        } else {
            Inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Received:" + item);
        }


    }


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("alone in the world");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) {

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to get.
            System.out.println("Go where?");
            return false;

        }
        {

        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("you are blocked by a wall!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if (currentRoom == new Room("end")) {
                System.out.println(" saved, congratulations, you have escaped the world of Zuul and won!");
                return true;
            }
        }
        return false;
    }






    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
