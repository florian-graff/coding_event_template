package com.klosebros.kata;

import java.util.Arrays;

public class KataTemplate {

    public String bc(String url) {
        var splitedUrl = Arrays.stream(url.split("/")).toList();

        if (splitedUrl.size() == 1) {
            return "<span class=\"active\">HOME</span>";
        }

        return "";
    }
}
