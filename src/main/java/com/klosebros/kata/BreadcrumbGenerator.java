package com.klosebros.kata;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BreadcrumbGenerator {

    record Url(String base, String path, String target){};

    public String generateBreadcrumb(String url, String separator){

        var a = url.split("/");
        var urlRecord = new Url(a[0], a[1], a[2]);

        var template = "<a href=\"/\">HOME</a> %s <a href=\"/%s/\">%s</a> %s <span class=\"active\">%s</span>";

        var cleanTarget = Optional.of(urlRecord.target).map(this::removeAnker).map(this::removeEnding).get();
        //var cleanPath=

        return template.formatted(separator, urlRecord.path, shortenPath(urlRecord.path), separator, cleanTarget.toUpperCase());
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

    private String shortenPath(String path){
        var list = List.of("the","of","in","from","by","with","and", "or", "for", "to", "at", "a");
        return Arrays.stream(path.split("-")).filter(s ->!list.contains(s))
                .map(s-> s.substring(0,1).toUpperCase())
                .collect(Collectors.joining());
    }
}
