public abstract class ItemUpdater {
    public static ItemUpdater picker(Item item) {
        return new StandardItemUpdater();
    }
    public abstract void update(Item item);
}
