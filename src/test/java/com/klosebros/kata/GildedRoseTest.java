package com.klosebros.kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @Test
    void shouldReturnNameSellinAndQuality() {
        Item[] items = new Item[] {new Item("foo", 5, 10)};
        GildedRose app = new GildedRose(items);
        assertThat(app.items[0].name).isEqualTo("foo");
        assertThat(app.items[0].sellIn).isEqualTo(5);
        assertThat(app.items[0].quality).isEqualTo(10);
    }

    @Test
    void shouldDecreaseByOne() {
        Item[] items = new Item[] {new Item("foo", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("foo");
        assertThat(app.items[0].sellIn).isEqualTo(4);
        assertThat(app.items[0].quality).isEqualTo(9);
    }

    @Test
    void shouldDecreaseByTwoAfterSellinPassed() {
        Item[] items = new Item[] {new Item("foo", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("foo");
        assertThat(app.items[0].sellIn).isEqualTo(-1);
        assertThat(app.items[0].quality).isEqualTo(8);
    }

    @Test
    void theQualityOfItemIsNeverNegative() {
        Item[] items = new Item[] {new Item("foo", 2, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("foo");
        assertThat(app.items[0].sellIn).isEqualTo(1);
        assertThat(app.items[0].quality).isEqualTo(0);
    }

    @Test
    void agedBrieShouldIncreaseInQuality() {
        Item[] items = new Item[] {new Item("Aged Brie", 12, 8)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Aged Brie");
        assertThat(app.items[0].sellIn).isEqualTo(11);
        assertThat(app.items[0].quality).isEqualTo(9);
    }

    @Test
    void agedBrieQualityShouldNotOverFifty() {
        Item[] items = new Item[] {new Item("Aged Brie", 15, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Aged Brie");
        assertThat(app.items[0].sellIn).isEqualTo(14);
        assertThat(app.items[0].quality).isEqualTo(50);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void agedBrieWithNegativeSellinQualityIncreasesByTwo(int sellin) {
        Item[] items = new Item[] {new Item("Aged Brie", sellin, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Aged Brie");
        assertThat(app.items[0].sellIn).isEqualTo(sellin - 1);
        assertThat(app.items[0].quality).isEqualTo(22);
    }

    @Test
    void sulfurasShouldNeverChangeQualityAndSellin() {
        Item[] items = new Item[] {new Item("Sulfuras, Hand of Ragnaros", 10, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Sulfuras, Hand of Ragnaros");
        assertThat(app.items[0].sellIn).isEqualTo(10);
        assertThat(app.items[0].quality).isEqualTo(20);
    }

    @Test
    void backstagePassesShouldIncreaseQualityByOneWhenSellinIsOver10() {
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 12, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(app.items[0].sellIn).isEqualTo(11);
        assertThat(app.items[0].quality).isEqualTo(21);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8, 9, 10})
    void backstagePassesShouldIncreaseQualityByTwoWhenSellinIsBetween10And5(int sellin) {
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", sellin, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(app.items[0].sellIn).isEqualTo(sellin - 1);
        assertThat(app.items[0].quality).isEqualTo(22);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void backstagePassesShouldIncreaseQualityByThreeWhenSellinIsBelow5(int sellin) {
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", sellin, 22)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(app.items[0].sellIn).isEqualTo(sellin - 1);
        assertThat(app.items[0].quality).isEqualTo(25);
    }

    @Test
    void backstagePassesShouldLooseQualityAfterConcert() {
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 0, 22)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(app.items[0].sellIn).isEqualTo(-1);
        assertThat(app.items[0].quality).isEqualTo(0);
    }

}