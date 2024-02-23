package com.klosebros.kata;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KataTemplate {

    public String bc(String url) {
        var splitedUrl = Arrays.stream(url.split("/")).collect(Collectors.toList());


        if (splitedUrl.get(splitedUrl.size()-1).equals("index")) {
           splitedUrl.remove(splitedUrl.size()-1);
        }

        if (splitedUrl.size() == 1) {
            return "<a href=\"/\">HOME</a> : <a href=\"/\"></a> : <span class=\"active\"></span>";
        }



        return "";
    }
}
