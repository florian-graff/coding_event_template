package com.klosebros.kata;

import java.util.Optional;
import java.util.stream.Stream;

public class BreadcrumbGenerator {

    record Url(String base, String path, String target){};

    public String generateBreadcrumb(String url, String separator){

        var a = url.split("/");
        var urlRecord = new Url(a[0], a[1], a[2]);

        var template = "<a href=\"/\">HOME</a> %s <a href=\"/%s/\">%s</a> %s <span class=\"active\">%s</span>";

        var cleanTarget = Optional.of(urlRecord.target).map(this::removeAnker).map(this::removeEnding).get();

        return template.formatted(separator, urlRecord.path, urlRecord.path.toUpperCase(), separator, cleanTarget.toUpperCase());
    }

    private String removeAnker(String target) {
        return Stream.of("#", "?")
                .filter(target::contains)
                .map(s -> target.substring(0, target.indexOf(s)))
                .findFirst()
                .orElse(target);
    }

    private String removeEnding(String target){
        return Stream.of(".php",".htm", ".html", ".asp")
                .filter(target::endsWith)
                .map(s -> target.split("\\.")[0])
                .findFirst()
                .orElse(target);
    }
}
