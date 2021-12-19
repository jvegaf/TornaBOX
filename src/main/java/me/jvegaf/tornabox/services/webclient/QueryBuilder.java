package me.jvegaf.tornabox.services.webclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    public static QueryDTO build(String[] strArgs) {
        //        joeski+un+congo
        var elements = sanitize(strArgs);
        QueryDTO qvo = new QueryDTO(elements);
        System.out.println("query builded: " + qvo.Value());
        return qvo;
    }

    private static List<String> sanitize(String[] qArgs) {
        var res = new ArrayList<String>();

        for (String qArg : qArgs) {
            var strArr = qArg.replaceAll("_", " ").trim().split(" ");
            res.addAll(Arrays.asList(strArr));
        }

        return res;
    }

}
