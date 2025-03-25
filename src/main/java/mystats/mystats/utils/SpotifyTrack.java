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

    // Fonction pour envoyer la requête à l'API Spotify
    /* public static void startPlayback(Musique m) {
        try {
            // Créer l'URL de l'API Spotify pour commencer la lecture de la musique
            String urlString = "https://api.spotify.com/v1/me/player/play";
            URL url = new URL(urlString);

            // Créer une connexion HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + SpotifyAuth.getValidAccessToken());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Données JSON de la requête
            String jsonData = "{\"uris\":[\""+m.getURI()+"\"]}"; // Remplacer par l'URI de la musique que vous voulez jouer

            // Envoyer la requête avec les données JSON
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lire la réponse de l'API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("La musique a commencé à jouer !");
            } else {
                System.out.println("Erreur lors de la lecture de la musique. Code d'erreur: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    } */
}
