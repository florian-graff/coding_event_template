package com.klosebros.kata;

import io.vavr.collection.List;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KataTemplate {

    public static final List<String> EXTS = List.of(".html", ".htm", ".asp", ".php");

    public String bc(String url) {
        var splitedUrl = Arrays.stream(url.split("/")).collect(Collectors.toList());


        var lastIndex = splitedUrl.size() - 1;
        EXTS.forEach(ext -> {
            if (splitedUrl.getLast().contains(ext)) {
                splitedUrl.set(lastIndex, splitedUrl.getLast().replace(ext, ""));
            }

        });
        if (splitedUrl.get(lastIndex).equals("index")) {
            splitedUrl.remove(lastIndex);
        }

        if (splitedUrl.size() == 1) {
            return "<a href=\"/\">HOME</a> : <a href=\"/\"></a> : <span class=\"active\"></span>";
        }


        return "";
    }
}
