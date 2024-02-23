package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BreadcrumbGeneratorTest {

    @Test
    void basicTest(){
        BreadcrumbGenerator breadcrumbGenerator = new BreadcrumbGenerator();
        var url = "www.baseurl.com/basic/example";
        var separator = ":";
        var expected =  "<a href=\"/\">HOME</a> : <a href=\"/basic/\">BASIC</a> : <span class=\"active\">EXAMPLE</span>";

        assertThat(breadcrumbGenerator.generateBreadcrumb(url,separator)).isEqualTo(expected);
    }
}
