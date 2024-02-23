package com.klosebros.kata;

import java.util.stream.Stream;

public class BreadcrumbGenerator {

    record Url(String base, String path, String target){};

    public String generateBreadcrumb(String url, String separator){

        var a = url.split("/");
        var urlRecord = new Url(a[0], a[1], a[2]);

        var template = "<a href=\"/\">HOME</a> %s <a href=\"/%s/\">%s</a> %s <span class=\"active\">%s</span>";

        return template.formatted(separator, urlRecord.path, urlRecord.path.toUpperCase(), separator, removeEnding(urlRecord.target).toUpperCase());
    }

    private String removeEnding(String target){
        return Stream.of(".php",".htm", ".html", ".asp")
                .filter(target::endsWith)
                .map(s -> target.split("\\.")[0])
                .findFirst()
                .orElse(target);
    }


}
