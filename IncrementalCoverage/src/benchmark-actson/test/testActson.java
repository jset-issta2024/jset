package test;

import coverage.SubjectExecutor;
import de.undercouch.actson.JsonEvent;
import de.undercouch.actson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class testActson extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        packagePrefix = "de.undercouch.actson";
        inputFileName = args[0];
        new testActson().wrapExecute();

    }

    @Override
    public void execute(String input) throws Throwable {

        byte[] json = input.getBytes(StandardCharsets.UTF_8);

        JsonParser parser = new JsonParser(StandardCharsets.UTF_8);

        int pos = 0; // position in the input JSON text
        int event; // event returned by the parser

        // feed the parser until it returns a new event
        while ((event = parser.nextEvent()) == JsonEvent.NEED_MORE_INPUT) {
            // provide the parser with more input
            pos += parser.getFeeder().feed(json, pos, json.length - pos);

            // indicate end of input to the parser
            if (pos == json.length) {
                parser.getFeeder().done();
            }
        }

    }
}
