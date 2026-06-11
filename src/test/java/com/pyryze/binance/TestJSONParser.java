package com.pyryze.binance;

import com.pyryze.binance.connector.client.utils.JSONParser;
import org.json.JSONException;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class TestJSONParser {
    private final String mockJson = "{\"key1\":\"value1\", \"key2\":2}";
    private final int value2 = 2;

    @Test
    public void testGetJSONStringValue() {
        assertEquals("value1", JSONParser.getJSONStringValue(mockJson, "key1"));
    }

    @Test
    public void testGetJSONIntValue() {
        assertEquals(value2, JSONParser.getJSONIntValue(mockJson, "key2"));
    }

    @Test
    public void testGetJSONStringValueThrowException() {
        assertThrows(JSONException.class, () -> JSONParser.getJSONStringValue(mockJson, "InvalidKey"));
    }

    @Test
    public void testGetJSONIntValueThrowException() {
        assertThrows(JSONException.class, () -> JSONParser.getJSONIntValue(mockJson, "InvalidKey"));
    }
}
