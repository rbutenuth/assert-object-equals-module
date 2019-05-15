package de.codecentric.mule.modules.assertobjectequals.internal;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.mule.runtime.api.lifecycle.InitialisationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AbstractConnectorTest {
    protected Operations aoec;

    @Before
    public void before() throws InitialisationException {
        aoec = new Operations();
    }

    @After
    public void after() {
        aoec = null;
    }

    protected InputStream string2Stream(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return new ByteArrayInputStream(bytes);
    }

    protected String stream2String(InputStream is) throws IOException {
        byte[] bytes = IOUtils.toByteArray(is);
        is.close();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
