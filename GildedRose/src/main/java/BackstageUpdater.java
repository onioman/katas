/**
 * Created by onio on 2/1/15.
 */
public class BackstageUpdater  extends StandardItemUpdater {
    protected void updateQuality(Item item, int currentSellIn, int currentQuality) {
        currentQuality++;
        if (currentSellIn <= 10) {
            currentQuality++;
        }
        if (currentSellIn <= 5) {
            currentQuality++;
        }
        if (currentSellIn == 0 && currentSellIn == item.getSellIn()) {
            currentQuality = 0;
        }
        if (currentQuality > 50) {
            currentQuality = 50;
        }
        item.setQuality(currentQuality);
    }
}
