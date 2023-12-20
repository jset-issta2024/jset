package coverage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author pwy
 */
public class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {

    }
}
