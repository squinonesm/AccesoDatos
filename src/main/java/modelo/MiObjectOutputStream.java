package modelo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Sergio
 */
public class MiObjectOutputStream extends ObjectOutputStream {

    public MiObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public MiObjectOutputStream() throws IOException, SecurityException {
    }

    //Para que no escriba cabecera, util en caso de que ya haya algo escrito de antes
    @Override
    protected void writeStreamHeader() throws IOException {
    }

}
