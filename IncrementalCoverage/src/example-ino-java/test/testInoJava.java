package test;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testInoJava extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.amazon.ion";
        inputFileName = args[0];

        new testInoJava().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        IonWriter jsonWriter = IonTextWriterBuilder.json().withPrettyPrinting().build(stringBuilder);
        IonReader reader = IonReaderBuilder.standard().build(input);
        jsonWriter.writeValues(reader);
    }
}
