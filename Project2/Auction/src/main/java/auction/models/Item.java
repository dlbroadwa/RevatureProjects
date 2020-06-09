package auction.models;

import java.util.Objects;

/**
 * Represents an item being auctioned.
 */
public class Item {
    private int itemID = 0;
    private String name = "";
    private String description = "";

    public Item() {}

    /**
     * Constructs an item with the given name and description.
     * This is the constructor you usually want to call; the database will fill in the item ID
     * once you insert this item into it.
     * @param name the name of the item
     * @param desc a description for the item
     */
    public Item(String name, String desc) {
        if (name != null)
            this.name = name;
        if (desc != null)
            this.description = desc;
    }

    /**
     * Constructs an item with the given ID, name, and description.
     * This is probably not the constructor you want to call. If this item is being inserted
     * into a database, then the DAO method for this class will ignore the item ID you set here
     * and replace it with its own from the database.
     * @param itemID the ID of the item
     * @param name the name of the item
     * @param desc a description for the item
     */
    public Item(int itemID, String name, String desc) {
        this(name, desc);
        this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                description.equals(item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
