package au.com.workingmouse.challenge.services;

import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

public class HttpsClient {

    public static BufferedReader request(String url) throws Exception {
        URL myUrl = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br;
    }
}
