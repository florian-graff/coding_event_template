package com.klosebros.kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class KataTemplateTest {

    @Test
    void emptyBreadcrump1() {
        var kataTemplate = new KataTemplate();
        var expected = "<a href=\"/\">HOME</a> : <a href=\"/\"></a> : <span class=\"active\"></span>";
        var given = "www.example.com/";
        var result = kataTemplate.bc(given);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void ignoreIndex() {
        var kataTemplate = new KataTemplate();
        var expected = "<a href=\"/\">HOME</a> : <a href=\"/\"></a> : <span class=\"active\"></span>";
        var given = "www.example.com/index";
        var result = kataTemplate.bc(given);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {".html", ".htm", ".asp", ".php"})
    void ignoreFileExts(String fileExt) {
        var kataTemplate = new KataTemplate();
        var expected = "<a href=\"/\">HOME</a> : <a href=\"/\"></a> : <span class=\"active\"></span>";
        var given = "www.example.com/index" + fileExt;
        var result = kataTemplate.bc(given);

        assertThat(result).isEqualTo(expected);
    }
}
