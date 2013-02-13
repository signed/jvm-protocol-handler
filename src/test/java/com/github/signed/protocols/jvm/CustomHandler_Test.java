package com.github.signed.protocols.jvm;

import com.google.common.io.CharStreams;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomHandler_Test {
    private static final MemoryDictionary dictionary = new MemoryDictionary();

    @BeforeClass
    public static void registerPackageToSearchForCustomProtocolHandlers() throws Exception {
        JvmProtocolHandler.registerJvmProtocolHandler(dictionary);
    }

    @Test
    public void singleElement() throws Exception {
        registerUrl("thekey", "that is the content");
        assertThat(theContenReadFrom("jvm://thekey"), is("that is the content"));
    }

    @Test
    public void multipleUrlInDictionary() throws Exception {
        registerUrl("thekey", "that is the content");
        registerUrl("anotherKey", "another value");
        assertThat(theContenReadFrom("jvm://anotherKey"), is("another value"));
    }

    @Test(expected = FileNotFoundException.class)
    public void forResourceThatIsNotInTheDictionaryBecauseTheOpenJdkImplementationIgnoresThem() throws Exception {
        theContenReadFrom("jvm://notDeposed");
    }

    private String theContenReadFrom(String urlString) throws IOException {
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        return CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
    }

    private void registerUrl(String key, String content) {
        StringBuilder builder = new StringBuilder();
        builder.append(content);
        dictionary.depose(key, builder);
    }
}