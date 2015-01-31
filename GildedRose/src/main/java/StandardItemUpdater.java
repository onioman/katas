public class StandardItemUpdater extends  ItemUpdater {
    @Override
    public void update(Item item) {
        int currentSellIn = item.getSellIn();
        updateSellIn(item, currentSellIn);
        int currentQuality = item.getQuality();
        updateQuality(item, currentSellIn, currentQuality);
    }

    protected void updateQuality(Item item, int currentSellIn, int currentQuality) {
        if (currentQuality == 0) return;

        currentQuality -= degradationRate;
        if (currentSellIn == 0) {
            currentQuality -= degradationRate;
        }
        item.setQuality(currentQuality);
    }

    protected void updateSellIn(Item item, int currentSellIn) {
        if (currentSellIn > 0) {
            item.setSellIn(currentSellIn-1);
        }
    }

    protected int degradationRate = 1;
}
