public class BrieUpdater extends StandardItemUpdater {
    @Override
    protected void updateQuality(Item item, int currentSellIn, int currentQuality) {
        currentQuality++;
        if (currentQuality > 50) {
            currentQuality = 50;
        }
        item.setQuality(currentQuality);
    }
}
