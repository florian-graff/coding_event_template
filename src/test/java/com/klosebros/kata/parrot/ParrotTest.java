package com.klosebros.kata.parrot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParrotTest {

    @Test
    void getSpeedOfEuropeanParrot() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.EUROPEAN, 0, 0, false);
        assertThat(parrot.getSpeed()).isEqualTo(12.0);
    }

    @Test
    void getSpeedOfAfricanParrot_With_One_Coconut() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.AFRICAN, 1, 0, false);
        assertThat(parrot.getSpeed()).isEqualTo(3.0);
    }

    @Test
    void getSpeedOfAfricanParrot_With_Two_Coconuts() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.AFRICAN, 2, 0, false);
        assertThat(parrot.getSpeed()).isZero();
    }

    @Test
    void getSpeedOfAfricanParrot_With_No_Coconuts() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.AFRICAN, 0, 0, false);
        assertThat(parrot.getSpeed()).isEqualTo(12.0);
    }

    @Test
    void getSpeedNorwegianBlueParrot_nailed() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.NORWEGIAN_BLUE, 0, 1.5, true);
        assertThat(parrot.getSpeed()).isZero();
    }

    @Test
    void getSpeedNorwegianBlueParrot_not_nailed() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.NORWEGIAN_BLUE, 0, 1.5, false);
        assertThat(parrot.getSpeed()).isEqualTo(18.0);
    }

    @Test
    void getSpeedNorwegianBlueParrot_not_nailed_high_voltage() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.NORWEGIAN_BLUE, 0, 4, false);
        assertThat(parrot.getSpeed()).isEqualTo(24.0);
    }

    @Test
    void getCryOfEuropeanParrot() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.EUROPEAN, 0, 0, false);
        assertThat(parrot.getCry()).isEqualTo("Sqoork!");
    }

    @Test
    void getCryOfAfricanParrot() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.AFRICAN, 1, 0, false);
        assertThat(parrot.getCry()).isEqualTo("Sqaark!");
    }

    @Test
    void getCryOfNorwegianBlueHighVoltage() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.NORWEGIAN_BLUE, 0, 4, false);
        assertThat(parrot.getCry()).isEqualTo("Bzzzzzz");
    }

    @Test
    void getCryOfNorwegianBlueNoVoltage() {
        Parrot parrot = Parrot.createParrot(ParrotTypeEnum.NORWEGIAN_BLUE, 0, 0, false);
        assertThat(parrot.getCry()).isEqualTo("...");
    }
}