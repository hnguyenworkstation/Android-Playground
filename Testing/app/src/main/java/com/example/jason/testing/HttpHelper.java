package com.example.jason.testing;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper class that retrieves HTML or text from an HTTP address and lets you specify two event handlers, a method
 * that is called on a separate thread to parse the HTML/text and a method that is called on the UI thread to
 * update the user interface
 * @param <T> The type of data that is the result of the HTML parsing is returned from the parser method to the
 *           UI updated method.
 */
public class HttpHelper<T> {
    /**
     * Specify an implementation for this callback in order to hande code in the background to parse the text
     * in the execute method and then code to handle updating the UI on the UI thread in the finish method.
     * @param <A> The type of data that execute returns and finish uses to update the UI
     */
    public interface Callback<A> {
        A execute(String html);
        void finish(A result);
    }

    /**
     * Read all HTML or text from the input stream using the specified text encoding
     * @param input The stream to read text from
     * @param encoding The encoding of the stream
     * @return All text read from the stream
     */
    private static String readAll(InputStream input, String encoding) {
        try {
            InputStreamReader reader = new InputStreamReader(input, encoding);
            StringBuilder result = new StringBuilder();
            char[] buffer = new char[4096];
            int len;
            while ((len = reader.read(buffer, 0, buffer.length)) > 0) {
                result.append(buffer, 0, len);
            }
            reader.close();
            return result.toString();
        }
        catch (IOException ignored) {
        }
        return null;
    }

    /**
     * Find out and return what type of text encoding is specified by the server
     * @param conn The opened HTTP connection to fetch the encoding for
     * @return The string name of the encoding. utf-8 is the default.
     */
    private static String getEncoding(HttpURLConnection conn) {
        String encoding = "utf-8";
        String contentType = conn.getHeaderField("Content-Type").toLowerCase();
        if (contentType.contains("charset=")) {
            int found = contentType.indexOf("charset=");
            encoding = contentType.substring(found + 8, contentType.length()).trim();
        }
        else if (conn.getContentEncoding() != null) {
            encoding = conn.getContentEncoding();
        }
        return encoding;
    }

    /**
     * Perform an HTTP GET network request to retrieve text from a remote web site or API. Specify a callback
     * to handle the text parsing and UI updating.
     * @param url The HTTP address to fetch text from
     * @param callback The event handlers that will be called by the helper method
     */
    public void get(final String url, final Callback<T> callback) {
        new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... voids) {
                try {
                    URL src = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection)src.openConnection();
                    String html = readAll(conn.getInputStream(), getEncoding(conn));
                    conn.disconnect();
                    return callback.execute(html);
                }
                catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(T result) {
                if (result != null) {
                    callback.finish(result);
                }
            }
        }.execute();
    }
}
