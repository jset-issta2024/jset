package com.iheartradio.m3u8;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

abstract class BaseM3uParser implements IPlaylistParser {
    protected final M3uScanner mScanner;
    protected final Encoding mEncoding;

    BaseM3uParser(InputStream inputStream, Encoding encoding) {
        M3uScanner mScanner1;
        try {
            mScanner1 = new M3uScanner(inputStream, encoding);
        } catch (IOException e) {
            mScanner1 = null;
        }
        mScanner = mScanner1;
        mEncoding = encoding;
    }

    @Override
    public boolean isAvailable() {
        return mScanner.hasNext();
    }

    final void validateAvailable() throws EOFException {
        if (!isAvailable()) {
            throw new EOFException();
        }
    }
}
