package test;

import coverage.SubjectExecutor;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkType;
import org.nibor.autolink.Span;

import java.io.IOException;
import java.util.EnumSet;

public class testAutolinkJava extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.nibor.autolink";
        inputFileName = args[0];
        new testAutolinkJava().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {

        LinkExtractor linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
                .build();
        Iterable<Span> spans = linkExtractor.extractSpans(input);
        for(Span span : spans){
            String text = input.substring(span.getBeginIndex(), span.getEndIndex());
        }

    }
}
