public class StandardItemUpdater extends  ItemUpdater {
    @Override
    public void update(Item item) {
        int currentSellIn = item.getSellIn();
        updateSellIn(item, currentSellIn);
        int currentQuality = item.getQuality();
        updateQuality(item, currentSellIn, currentQuality);
    }

    private void updateQuality(Item item, int currentSellIn, int currentQuality) {
        currentQuality--;
        if (currentSellIn == 0) {
            currentQuality--;
        }
        if (currentQuality > 50) {
            currentQuality = 50;
        }
        item.setQuality(currentQuality);
    }

    private void updateSellIn(Item item, int currentSellIn) {
        if (currentSellIn > 0) {
            item.setSellIn(currentSellIn-1);
        }
    }

}
