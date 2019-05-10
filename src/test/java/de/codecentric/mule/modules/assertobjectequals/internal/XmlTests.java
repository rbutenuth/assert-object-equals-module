package de.codecentric.mule.modules.assertobjectequals.internal;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class XmlTests extends AbstractConnectorTest {

    @Test
    public void xmlEqual() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b></b></a>";
        InputStream expected = string2Stream(xml);
        InputStream actual = string2Stream(xml);
        aoec.compareXml(expected, actual, "NORMALIZE_WHITESPACE");
    }

    @Test
    public void xmlNotEqualDueToComment() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b></b></a>";
        String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b><!-- Hello, world! --></b></a>";
        expectNotEqualXml(expected, actual, "NORMALIZE_WHITESPACE",
                "Expected child nodelist length '0' but was '1' - comparing <b...> at /a[1]/b[1] to <b...> at /a[1]/b[1]");
    }

    @Test
    public void xmlNotEqualDueToWhitespace() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>abba</b></a>";
        String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>ab ba</b></a>";
        expectNotEqualXml(expected, actual, "NORMALIZE_WHITESPACE",
                "Expected text value 'abba' but was 'ab ba' - comparing <b ...>abba</b> at /a[1]/b[1]/text()[1] to <b ...>ab ba</b> at /a[1]/b[1]/text()[1]");
    }

    @Test
    public void xmlEqualDueToIgnoreAllWhitespace() throws Exception {
        // "normalize": Tab and space are equal
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>ab ba</b></a>";
        String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>ab\tba</b></a>";
        aoec.compareXml(expected, actual, "NORMALIZE_WHITESPACE");
    }

    @Test
    public void xmlEqualIgnoreComments() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b></b></a>";
        String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b><!-- Hello, world! --></b></a>";
        aoec.compareXml(expected, actual, "IGNORE_COMMENTS");
    }

    @Test
    public void xmlEqualIgnoreWhitespace() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>abba</b></a>";
        String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b>abba </b></a>";
        aoec.compareXml(expected, actual, "IGNORE_WHITESPACE");
    }

    private void expectNotEqualXml(Object expected, Object actual, String options, String expectedMessage) throws Exception {
        try {
            aoec.compareXml(expected, actual, options);
            Assert.fail("AssertionError missing!");
        } catch (AssertionError e) {
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
}
