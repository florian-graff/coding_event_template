package com.klosebros.kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BreadcrumbGeneratorTest {
    @ParameterizedTest
    @ValueSource(strings={"",".php",".htm", ".html", ".asp", "?Test", "#Test"})
    void testBreadcumbGeneration(String ending){
        BreadcrumbGenerator breadcrumbGenerator = new BreadcrumbGenerator();
        var url = "www.baseurl.com/basic/example" + ending;
        var separator = ":";
        var expected =  "<a href=\"/\">HOME</a> : <a href=\"/basic/\">BASIC</a> : <span class=\"active\">EXAMPLE</span>";

        assertThat(breadcrumbGenerator.generateBreadcrumb(url,separator)).isEqualTo(expected);

    }

    @ParameterizedTest
    @ValueSource(strings={"mysite.com/very-long-url-to-make-a-silly-yet-meaningful-example/example.asp"})
    void testBreadcumbGenerationWithLongPath(String url){
        BreadcrumbGenerator breadcrumbGenerator = new BreadcrumbGenerator();
        var separator = ":";
        var expected =  "<a href=\"/\">HOME</a> : <a href=\"/very-long-url-to-make-a-silly-yet-meaningful-example/\">VLUMSYME</a> : <span class=\"active\">EXAMPLE</span>";

        assertThat(breadcrumbGenerator.generateBreadcrumb(url,separator)).isEqualTo(expected);

    }
}
