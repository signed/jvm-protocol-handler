package com.github.signed.protocols.jvm;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class JvmStreamHandlerFactory implements URLStreamHandlerFactory {
    private MemoryDictionary dictionary;

    public JvmStreamHandlerFactory(MemoryDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if ("jvm".equals(protocol)) {
            return new Handler(dictionary);
        }
        return null;
    }
}
