package de.codecentric.mule.modules.assertobjectequals.internal;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ObjectTests extends AbstractConnectorTest {

    @Test
    public void allNull() throws Exception {
        aoec.compareObjects(null, null, false, false, null);
    }

    @Test
    public void streamListEmptyOptions() throws Exception {
        InputStream expected = string2Stream("[\"a\", \"b\", \"c\"]");
        List<String> actual = list("a", "b", "c");
        aoec.compareObjects(expected, actual, false, false, list());
    }

    @Test
    public void streamObjectEmptyOptions() throws Exception {
        InputStream expected = string2Stream("{\"a\": 1, \"b\": 2}");
        Map<Object, Object> actual = new LinkedHashMap<>();
        actual.put("b", 2);
        actual.put("a", 1);
        aoec.compareObjects(expected, actual, false, false, list());
    }

    @Test
    public void streamObjectCheckMapOrder() throws Exception {
        String expected = "{\"a\": 1, \"b\": 2}";
        Map<Object, Object> actual = new LinkedHashMap<>();
        actual.put("b", 2);
        actual.put("a", 1);
        expectNotEqual(string2Stream(expected), actual, false, true, list(), "at '', expect key a, actual b");
        expectNotEqual(expected, actual, false, false, list("CHECK_MAP_ORDER"), "at '', expect key a, actual b");
    }

    @Test
    public void streamObjectContainsOnly() throws Exception {
        String expected = "{\"a\": 1}";
        Map<Object, Object> actual = new LinkedHashMap<>();
        actual.put("b", 2);
        actual.put("a", 1);
        aoec.compareObjects(string2Stream(expected), actual, true, false, list());
        aoec.compareObjects(string2Stream(expected), actual, false, false, list("CONTAINS_ONLY_ON_MAPS"));
    }

    @Test
    public void jsonStringListEmptyOptions() throws Exception {
        String expected = "[\"a\", \"b\", \"c\"]";
        Object actual = list("a", "b", "c");
        aoec.compareObjects(expected, actual, false, false, list());
    }

    @Test
    public void jsonByteArrayObjectEmptyOptions() throws Exception {
        byte[] expected = "[\"a\", \"b\", \"c\"]".getBytes(StandardCharsets.UTF_8);
        Object actual = list("a", "b", "c");
        aoec.compareObjects(expected, actual, false, false, list());
    }

    @Test
    public void nullEmptyOptions() throws Exception {
        aoec.compareObjects(null, null, false, false, list());
    }

    @Test
    public void notEqualEmptyOptions() throws Exception {
        expectNotEqual("huhu", null, false, false, list(), "at '', expected huhu, actual is null");
    }

    @Test
    public void streamStream() throws Exception {
        String jsonString = "[\"a\", \"b\", \"c\"]";
        InputStream expected = string2Stream(jsonString);
        InputStream actual = string2Stream(jsonString);
        aoec.compareObjects(expected, actual, false, false, list());
    }

    private void expectNotEqual(Object expected, Object actual, boolean containsOnlyOnMaps, boolean checkMapOrder, List<String> pathOptions,
            String expectedMessage) throws Exception {
        try {
            aoec.compareObjects(expected, actual, containsOnlyOnMaps, checkMapOrder, pathOptions);
            Assert.fail("AssertionError missing!");
        } catch (AssertionError e) {
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }

    private List<String> list(String... entries) {
        return Arrays.asList(entries);
    }
}
