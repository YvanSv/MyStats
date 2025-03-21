package mystats.mystats.utils;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class SpotifyTrack {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String API_TRACK_URL = "https://api.spotify.com/v1/tracks/";
    private static final String API_ARTIST_URL = "https://api.spotify.com/v1/artists/";

    public static String getTrackImage(String trackId, String accessToken) {
        Request request = request(API_TRACK_URL,trackId,accessToken);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                // Accéder à l'URL de l'image (par exemple, la première image)
                return json.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public static String getTrackArtist(String trackId, String accessToken) {
        Request request = request(API_TRACK_URL,trackId,accessToken);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                // Accéder à l'ID de l'artiste
                return json.getJSONArray("artists").getJSONObject(0).getString("id");
            }
            System.out.println(response);
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public static String getArtistImage(String trackId, String accessToken) {
        String artistID = getTrackArtist(trackId,accessToken);
        Request request = request(API_ARTIST_URL,artistID,accessToken);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                // Accéder à l'image de l'artiste
                return json.getJSONArray("images").getJSONObject(0).getString("url");
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    private static Request request(String url, String id, String accessToken) {
        return new Request.Builder().url(url+id).addHeader("Authorization", "Bearer " + accessToken).build();
    }
}
