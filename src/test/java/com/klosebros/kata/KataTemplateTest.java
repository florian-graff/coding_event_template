package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KataTemplateTest {

    @Test
    void emptyBreadcrump() {
        var kataTemplate = new KataTemplate();
        var expected ="<a href=\"/\">HOME</a> : <a href=\"//\"></a> : <span class=\"active\"></span>";
        var given = "www.example.com//";
        var result = kataTemplate.bc(given);

        assertThat(result).isEqualTo(expected);
    }
}
