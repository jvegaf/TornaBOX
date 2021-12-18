package me.jvegaf.tornabox.services.webclient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {



    @Test
    void tryABuildQueryStringOneString() {
        var input = new ArrayList<String>();
        input.add("joeski_un_congo ");

        var result = QueryBuilder.build(input);
        assertEquals(result, "joeski+un+congo");
    }

    @Test
    void tryABuildQueryStringCoupleStrings() {
        var input = new ArrayList<String>();
        input.add("joeski " );
        input.add(" un_congo " );


        var result = QueryBuilder.build(input);
        assertEquals(result, "joeski+un+congo");
    }
}