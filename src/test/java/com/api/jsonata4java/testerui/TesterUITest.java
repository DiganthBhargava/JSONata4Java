package com.api.jsonata4java.testerui;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.json.JsonMapper;

public class TesterUITest {

    TesterUI testerUi;

    @Before
    public void setUp() throws IOException {
        if (!isOnWindows()) {
            return;
        }
        testerUi = new TesterUI();
    }

    @Test
    public void testXmlToJson() throws IOException {
        if (!isOnWindows()) {
            return;
        }
        assertEquals(minifyJson(TesterUI.readFile(Paths.get("src/test/resources/exerciser/xmladdress.json"))),
            minifyJson(testerUi
                .xmlToJson(TesterUI.readFile(Paths.get("src/test/resources/exerciser/xmladdress.xml")))));
    }

    public static String minifyJson(final String in) {
        final StringWriter sw = new StringWriter();
        JsonMapper mapper = JsonMapper.builder().build();
        try (final JsonGenerator gen = mapper.createGenerator(sw)) {
            final JsonParser parser = mapper.createParser(in);
            while (parser.nextToken() != null) {
                gen.copyCurrentEvent(parser);
            }
        }
        return sw.getBuffer().toString();
    }

    private boolean isOnWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
