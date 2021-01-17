public class Item {

    String description;

    //constructor
    public Item(String newdescription) {
        description = newdescription;
    }

    public String getDescription() {
        return description;
    }

    public String getlongdescription() {
        return "It says" + description;
    }
}



