package soundcloud.example.com.soundcloudapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GMAsync extends AsyncTask<String, Void, Double[]> {
    public static String token = "";
    private String vin;

    public GMAsync(String vin) {
        this.vin = vin;
    }

    protected void onPreExecute() {
        // Perform setup - runs on user interface thread
    }

    protected Double[] doInBackground(String... strings) {
        try{
            String webPage = "https://developer.gm.com/api/v1/oauth/access_token?grant_type=client_credentials";
            String authStringEnc = "authStringEnc";

            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            urlConnection.setRequestProperty("Accept", "application/json");
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            token = result.substring(result.indexOf("access_token") + 15, result.indexOf("expires_in") - 4);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }


        try{
            StringBuilder urlBuilder = new StringBuilder("https://developer.gm.com/api/v1/account/vehicles/{vin}/requests".replace("{vin}", URLEncoder.encode(vin, "UTF-8")));
            urlBuilder.append("?");
            urlBuilder.append(URLEncoder.encode("offset","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("limit","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("units","UTF-8") + "=" + URLEncoder.encode("METRIC", "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            BufferedReader rd;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println("sb:" + sb);

            double latitude = Double.parseDouble(sb.substring(sb.indexOf("<lat>")+5,sb.indexOf("</lat>")));
            double longitude = Double.parseDouble(sb.substring(sb.indexOf("<long>")+6,sb.indexOf("</long>")));
            Double[] arr = {latitude,longitude};
            return arr;
        }catch(Exception e){
            Double[] arr = new Double[2];
            arr[0] = 0.0;
            arr[1] = 0.0;
            return arr;
        }
    }

    protected void onProgressUpdate(String[] values) {
        // Update user with progress bar or similar - runs on user interface thread
    }

    protected void onPostExecute(int result) {
        // Update user interface
    }
}
