import java.util.ArrayList;
import java.util.List;


public class GildedRose {

	private static List<Item> items = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        System.out.println("OMGHAI!");
        loadDefaultItems();
        updateQuality();
    }

    public static void add(Item item) {
        initItems();
        items.add(item);
    }

    public static void clear() {
        if (items != null) {
            items.clear();
            items = null;
        }
    }

    private static void initItems() {
        if (items == null) {
            items = new ArrayList<Item>();
        }
    }

    public static void loadDefaultItems() {
        items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));
    }


    public static void updateQuality() {
        for (int i = 0; i < items.size(); i++) {
            Item current  = items.get(i);
            ItemUpdater updater = ItemUpdater.picker(current);
            updater.update(current);
        }
    }
}