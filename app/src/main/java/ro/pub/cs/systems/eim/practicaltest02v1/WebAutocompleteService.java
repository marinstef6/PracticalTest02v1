package ro.pub.cs.systems.eim.practicaltest02v1;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebAutocompleteService {

    public static String getSuggestions(String prefix) {
        try {
            String urlStr = "https://suggestqueries.google.com/complete/search?client=chrome&q="
                    + java.net.URLEncoder.encode(prefix, "UTF-8");

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray json = new JSONArray(sb.toString());
            JSONArray suggestions = json.getJSONArray(1);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < suggestions.length(); i++) {
                result.append(suggestions.getString(i));
                if (i < suggestions.length() - 1) {
                    result.append(",");
                }
            }

            return result.toString();

        } catch (Exception e) {
            return "";
        }
    }
}

