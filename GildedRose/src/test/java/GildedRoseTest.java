import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}

    @Test
    public void oneDayStandardItem() {
        GildedRose.add(vest);
        passDays(1);
        assertEquals(vestInitialSellIn - 1, vest.getSellIn());
        assertEquals(vestInitialQuality-1, vest.getQuality());
    }


    @Test
    public void afterSellInStandardItem() {
        GildedRose.add(vest);
        passDays(vestInitialSellIn + 1);
        assertEquals(vestInitialQuality - vestInitialSellIn - 2, vest.getQuality());
    }

    @Test
    public void noNegativeQuality() {
        GildedRose.add(vest);
        passDays(vestInitialQuality+1);
        assertEquals(0, vest.getQuality());
    }

    @Test
    public void oneDayBrie() {
        GildedRose.add(brie);
        passDays(1);
        assertEquals(brieInitialSellIn-1, brie.getSellIn());
        assertEquals(brieInitialQuality+1, brie.getQuality());
    }

    @Test
    public void qualityNoGreaterThan50() {
        GildedRose.add(brie);
        passDays(51);
        assertEquals(50, brie.getQuality());
    }

    @Test
    public void oneDayAndManyDaysSulfuras() {
        GildedRose.add(sulfuras);
        passDays(1);
        assertEquals(sulfurasInitialSellIn, sulfuras.getSellIn());
        assertEquals(sulfurasInitialQuality, sulfuras.getQuality());
        passDays(20);
        assertEquals(sulfurasInitialSellIn, sulfuras.getSellIn());
        assertEquals(sulfurasInitialQuality, sulfuras.getQuality());
    }

    @Test
    public void backstage10daysBefore() {
        GildedRose.add(backstage);
        int daysPassed = 0;
        for( ; backstage.getSellIn() > 10;) {
            GildedRose.updateQuality();
            daysPassed++;
        }
        assertEquals(backstageInitialSellIn-daysPassed, backstage.getSellIn());
        assertEquals(backstageInitialQuality+daysPassed, backstage.getQuality());
    }

    @Test
    public void backstage5daysBefore() {
        GildedRose.add(backstage);
        int normalQualityDays = 0;
        for( ; backstage.getSellIn() > 10;) {
            GildedRose.updateQuality();
            normalQualityDays++;
        }

        int highQualityDays = 0;
        for( ; backstage.getSellIn() > 5;) {
            GildedRose.updateQuality();
            highQualityDays++;
        }
        assertEquals(backstageInitialSellIn-normalQualityDays-highQualityDays, backstage.getSellIn());
        assertEquals(backstageInitialQuality+normalQualityDays+2*highQualityDays, backstage.getQuality());
    }

    @Test
    public void backstage0daysBefore() {
        GildedRose.add(backstage);
        int normalQualityDays = 0;
        for( ; backstage.getSellIn() > 10;) {
            GildedRose.updateQuality();
            normalQualityDays++;
        }

        int highQualityDays = 0;
        for( ; backstage.getSellIn() > 5;) {
            GildedRose.updateQuality();
            highQualityDays++;
        }

        int veryHighQualityDays = 0;
        for( ; backstage.getSellIn() > 0;) {
            GildedRose.updateQuality();
            veryHighQualityDays++;
        }


        assertEquals(backstageInitialSellIn-normalQualityDays-highQualityDays-veryHighQualityDays, backstage.getSellIn());
        assertEquals(backstageInitialQuality+normalQualityDays+2*highQualityDays+3*veryHighQualityDays, backstage.getQuality());
    }

    @Test
    public void backstageAfterConcert() {
        GildedRose.add(backstage);
        passDays(backstageInitialSellIn+1);
        assertEquals(0, backstage.getQuality());
    }

    @Test
    public void sulfurasFixesQuality() {
        GildedRose.add(sulfuras);
        passDays(1);
        assertEquals(80, sulfuras.getQuality());
    }



    private void passDays(int days) {
        for(int d=0; d < days; d++) {
            GildedRose.updateQuality();
        }
    }

    @Before
    public void before() {
        vest = new Item("+5 Dexterity Vest", vestInitialSellIn, vestInitialQuality);
        brie = new Item("Aged Brie", brieInitialSellIn, brieInitialQuality);
        sulfuras = new Item("Sulfuras, Hand of Ragnaros", sulfurasInitialSellIn, sulfurasInitialQuality);
        backstage = new Item("Backstage passes to a TAFKAL80ETC concert", backstageInitialSellIn, backstageInitialQuality);
    }

    @After
    public void after() {
        GildedRose.clear();
    }

    int vestInitialSellIn = 10;
    int vestInitialQuality = 20;
    Item vest = new Item("+5 Dexterity Vest", vestInitialSellIn, vestInitialQuality);

    int brieInitialSellIn = 2;
    int brieInitialQuality = 0;
    Item brie = new Item("Aged Brie", brieInitialSellIn, brieInitialQuality);

    int sulfurasInitialSellIn = 0;
    int sulfurasInitialQuality = 80;
    Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", sulfurasInitialSellIn, sulfurasInitialQuality);

    int backstageInitialSellIn = 15;
    int backstageInitialQuality = 20;
    Item backstage = new Item("Backstage passes to a TAFKAL80ETC concert", backstageInitialSellIn, backstageInitialQuality);
}
