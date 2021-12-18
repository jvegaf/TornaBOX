package me.jvegaf.tornabox.services.webclient;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    public static String build(List<String> strArgs) {
        //        joeski+un+congo
        var rList = sanitize(strArgs);
        return StringUtils.join(rList.toArray(),"+");
    }

    private static List<String> sanitize(List<String> qArgs) {
        var res = new ArrayList<String>();

        for (String qArg : qArgs) {
            var strArr = qArg.replaceAll("_", " ").trim().split(" ");
            res.addAll(Arrays.asList(strArr));
        }

        return res;
    }

}
