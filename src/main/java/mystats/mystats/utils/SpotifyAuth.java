package mystats.mystats.utils;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Base64;

public class SpotifyAuth {
    private static final String CLIENT_ID = "074ca6ac424b4475aa544a69cb0f8e3e";
    private static final String CLIENT_SECRET = "59e8ced3168e43f99466f36df9640626";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static String accessToken;
    private static long expirationTime;

    /*public static String getAccessToken() {
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                return json.getString("access_token");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    // Méthode pour obtenir un nouvel access token
    private static String getAccessToken() {
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                accessToken = json.getString("access_token");
                int expiresIn = json.getInt("expires_in"); // Durée de vie du token en secondes

                // Calcul de l'heure d'expiration du token
                expirationTime = System.currentTimeMillis() / 1000 + expiresIn;
                return accessToken;
            } else {
                System.out.println("Erreur lors de la récupération du token d'accès");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour obtenir un access token valide (et en redemander un si nécessaire)
    public static String getValidAccessToken() {
        long currentTime = System.currentTimeMillis() / 1000; // Temps en secondes

        // Si le token est expiré ou n'existe pas, en redemander un
        if (accessToken == null || currentTime >= expirationTime) {
            return getAccessToken();
        }

        // Sinon, retourner le token actuel encore valide
        return accessToken;
    }
}
