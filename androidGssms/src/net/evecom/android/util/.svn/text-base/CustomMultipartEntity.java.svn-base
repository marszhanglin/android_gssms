/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
/**
 * 
 * √Ë ˆCustomMultipartEntity
 * @author Mars zhang
 * @created 2014-11-5 …œŒÁ10:46:01
 */
public class CustomMultipartEntity extends MultipartEntity {
    /**listener*/
    private final ProgressListener listener;

    public CustomMultipartEntity(final ProgressListener listener) {
        super();
        this.listener = listener;
    }

    public CustomMultipartEntity(final HttpMultipartMode mode, final ProgressListener listener) {
        super(mode);
        this.listener = listener;
    }

    public CustomMultipartEntity(HttpMultipartMode mode, final String boundary, final Charset charset,
            final ProgressListener listener) {
        super(mode, boundary, charset);
        this.listener = listener;
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, this.listener));
    }
    /**ProgressListener*/
    public static interface ProgressListener {
        void transferred(long num);
    }
    /**ProgressListener*/
    public static class CountingOutputStream extends FilterOutputStream {
        /**ProgressListener*/
        private final ProgressListener listener;
        /**ProgressListener*/
        private long transferred;

        public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            this.transferred += len;
            this.listener.transferred(this.transferred);
        }

        public void write(int b) throws IOException {
            out.write(b);
            this.transferred++;
            this.listener.transferred(this.transferred);
        }
    }

}
