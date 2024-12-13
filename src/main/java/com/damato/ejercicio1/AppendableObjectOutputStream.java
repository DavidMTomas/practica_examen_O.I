package com.damato.ejercicio1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendableObjectOutputStream extends ObjectOutputStream
{

    public AppendableObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    protected AppendableObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }

    @Override
    protected void writeStreamHeader() throws IOException
    {
    }

}

