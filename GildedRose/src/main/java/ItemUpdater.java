public abstract class ItemUpdater {
    public static ItemUpdater picker(Item item) {
        String name = item.getName();
        if (name.contains("Aged Brie")) {
            return new BrieUpdater();
        } else if (name.contains("Sulfuras")) {
            return new SulfurasUpdater();
        } else {
            return new StandardItemUpdater();
        }
    }
    public abstract void update(Item item);
}
