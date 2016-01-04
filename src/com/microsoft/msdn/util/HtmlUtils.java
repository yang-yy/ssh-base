package com.microsoft.msdn.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;


public final class HtmlUtils {

    @Autowired
    @Qualifier("httpClient")
    private CloseableHttpClient httpClient;

    public void downloadHtml(String url, String fileName) throws Exception {
        this.downloadHtml(url, true, fileName);
    }

    public void downloadResource(String url, String fileName) throws Exception {
        this.downloadHtml(url, false, fileName);
    }

    private void downloadHtml(String url, boolean isText, final String outFile) throws Exception {
        HttpGet httpGetter = null;
        HttpResponse response = null;
        BufferedReader readStream = null;
        ReadableByteChannel resChannel = null;
        try {
            httpGetter = new HttpGet(url);
            response = httpClient.execute(httpGetter);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (isText) {
                    readStream = new BufferedReader(Channels.newReader(Channels.newChannel(response.getEntity().getContent()), "UTF-8"));
                    writeToFile(readStream, outFile);
                } else {
                    resChannel = Channels.newChannel(response.getEntity().getContent());
                    writeToFile(resChannel, outFile);
                }
            } else
                throw new Exception(response.getStatusLine().toString());
        } finally {
            try {
                if (readStream != null) readStream.close();
            } catch (Exception e) {
            }
            try {
                if (resChannel != null) resChannel.close();
            } catch (Exception e) {
            }
            try {
                if (httpGetter != null) httpGetter.abort();
            } catch (Exception e) {
            }

            readStream = null;
            resChannel = null;
            response = null;
            httpGetter = null;
        }
    }

    private void writeToFile(final BufferedReader netStream, String fileName) throws Exception {
        File destFile = new File(fileName);
        BufferedWriter bw = null;
        String line = null;

        if (netStream == null) return;
        if (destFile.exists()) {
            if (destFile.isDirectory()) return;
            destFile.deleteOnExit();
        }
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8"));
            do {
                line = netStream.readLine();
                if (line == null) break;
                bw.write(line);
                bw.newLine();
                bw.flush();
            } while (true);
        } finally {
            if (bw != null) bw.close();
            bw = null;
        }
    }

    private void writeToFile(final ReadableByteChannel netStream, String fileName) throws Exception {
        File destFile = new File(fileName);
        if (netStream == null) return;
        if (destFile.exists()) {
            if (destFile.isDirectory()) return;
            destFile.deleteOnExit();
        }

        FileChannel fc = null;
        try {
            fc = new FileOutputStream(destFile).getChannel();
            fc.transferFrom(netStream, 0, Long.MAX_VALUE);
        } finally {
            if (fc != null) fc.close();
        }
    }
}
