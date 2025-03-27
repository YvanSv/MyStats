package mystats.mystats.metier;

import mystats.mystats.metier.donnees.*;
import mystats.mystats.metier.tris.*;
import mystats.mystats.utils.Date;
import mystats.mystats.utils.Fichier;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.Parametres;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    private static DataReader dr;
    private final ArrayList<Fichier> fichiers = new ArrayList<>();
    private final ArrayList<Ecoute> historique = new ArrayList<>();
    private final ArrayList<Artiste> selection = new ArrayList<>();
    private final ArrayList<Musique> playlist = new ArrayList<>();
    private final ArrayList<Album> liste = new ArrayList<>();
    private final ArrayList<Ecoute> tmpHistorique = new ArrayList<>();

    public ArrayList<Fichier> getFichiers() {
        return fichiers;
    }

    public void addFichier(Fichier f) {
        for (Fichier f2 : fichiers)
            if (f.equals(f2)) return;
        this.fichiers.add(f);
        lireFichier(f);
        creerMusiques();
        setNature(tmpHistorique);
        System.out.println("Fichier " + f.getNom() + " chargé.");
    }

    public void removeFichier(Fichier f) {
        fichiers.remove(f);

        /*historique.clear();
        selection.clear();
        playlist.clear();
        tmpHistorique.clear();
        for (Fichier f2 : fichiers)
            lireFichier(f2);
        creerMusiques();
        setNature(tmpHistorique);*/
        for (Ecoute e : f) {
            historique.remove(e);
            e.getMusique(e.getNom()).remove(e);
        }
        System.out.println("Fichier " + f.getNom() + " supprimé.");
    }

    public void setNature(ArrayList<Ecoute> lst) {
        for (Ecoute ec : lst) {
            for (Musique musique : playlist) {
                if (musique.getArtiste().getNom().equals(ec.getArtiste().getNom()) && musique.getNom().equals(ec.getNom())) {
                    ec.setNature(!(ec.getDuree() < musique.getDuree() * Parametres.getInstance().getTauxPourEtreFull() / 100));
                    break;
                }
            }
        }
        tmpHistorique.clear();
    }

    private void lireFichier(Fichier fichier) {
        Ecoute ecoute = new Ecoute();
        String ligne;
        try {
            InputStream inputStream = new FileInputStream(fichier.getLien());
            Scanner sc = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while (sc.hasNextLine()) {
                ligne = sc.nextLine();
                if (ligne.contains("{")) ecoute = new Ecoute();
                else if (ligne.contains("},")) {
                    tmpHistorique.add(ecoute);
                    fichier.add(ecoute);
                }
                else if (ligne.contains(":")) {
                    switch (getAttribut(ligne)) {
                        case "endTime" -> ecoute.setDate(new Date(getValue(ligne)));
                        case "ts" -> ecoute.setDate(new Date(getValue(ligne).replace('T',' ').split(":")[0]+":"+getValue(ligne).split(":")[1]));
                        case "artistName", "master_metadata_album_artist_name" -> ajouterArtiste(ecoute,getValue(ligne));
                        case "trackName", "master_metadata_track_name" -> ecoute.setNom(getValue(ligne));
                        case "msPlayed" -> ecoute.setDuree(Integer.parseInt(getValue(ligne)));
                        case "ms_played" -> ecoute.setDuree(Integer.parseInt(getValue(ligne).split(",")[0]));
                        case "master_metadata_album_album_name" -> ajouterAlbum(ecoute,getValue(ligne));
                        case "spotify_track_uri" -> ecoute.setUri(getValue(ligne));
                    }
                }
            }
            sc.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String getAttribut(String ligne) {
        String debut = ligne.split(":")[0];
        return debut.split("\"")[1];
    }

    private String getValue(String ligne) {
        String fin = ligne.split(": ")[1];
        if (fin.contains("\""))
            return fin.split("\"")[1];
        else return fin.split(",")[0];
    }

    private void ajouterArtiste(Ecoute ecoute, String nom_artiste) {
        boolean trouve = false;
        for (Artiste a : selection)
            if (a.getNom().equals(nom_artiste)) {
                ecoute.setArtiste(a);
                trouve = true;
                break;
            }

        if (!trouve) {
            Artiste nouvel_artiste = new Artiste(nom_artiste);
            selection.add(nouvel_artiste);
            fichiers.get(fichiers.size()-1).addArtiste();
            ecoute.setArtiste(nouvel_artiste);
        }
    }

    private void ajouterAlbum(Ecoute ecoute, String nom_album) {
        boolean trouve = false;
        for (Album a : liste)
            if (a.getNom().equals(nom_album) && a.getArtiste().getNom().equals(ecoute.getArtiste().getNom())) {
                ecoute.setAlbum(a);
                trouve = true;
                break;
            }

        if (!trouve) {
            Album nouvel_album = new Album(nom_album,ecoute.getArtiste());
            liste.add(nouvel_album);
            fichiers.get(fichiers.size()-1).addAlbum();
            ecoute.setAlbum(nouvel_album);
        }
    }

    private void creerMusiques() {
        for (Ecoute ecoute : tmpHistorique) {
            boolean trouve = false;
            for (Musique musique : playlist) {
                if (ecoute.deLaMusique(musique)) {
                    musique.add(ecoute);
                    trouve = true;
                    break;
                }
            }

            if (!trouve) {
                Musique nouvelle_musique = new Musique(ecoute.getNom(),ecoute.getArtiste(),ecoute.getAlbum(),ecoute.getUri());
                playlist.add(nouvelle_musique);
                nouvelle_musique.add(ecoute);
                fichiers.get(fichiers.size()-1).addMusique();
                ecoute.getArtiste().add(nouvelle_musique);
                /*if (ecoute.getArtiste().getImage() == null)
                    ecoute.getArtiste().setImage();*/
                ecoute.getAlbum().add(nouvelle_musique);
            }
            historique.add(ecoute);
        }
    }

    public ArrayList<Artiste> filtrerArtistes() {
        ArrayList<Artiste> res = new ArrayList<>(selection);
        for (Artiste a : selection)
            if (!Filtre.getInstance().valide(a))
                res.remove(a);
        switch (Math.abs(Filtre.getInstance().getMethodeTri())) {
            case 1 -> res.sort(new TriSkips());
            case 2 -> res.sort(new TriRatios());
            case 3 -> res.sort(new TriNomArtiste());
            case 5 -> res.sort(new TriEcoutes());
            case 6 -> res.sort(new TriEcoutesCompletes());
            case 8 -> res.sort(new TriTempsEcoute());
        }
        return res;
    }

    public ArrayList<Musique> filtrerMusiques() {
        ArrayList<Musique> res = new ArrayList<>(playlist);
        for (Musique m : playlist)
            if (!Filtre.getInstance().valide(m))
                res.remove(m);
        switch (Math.abs(Filtre.getInstance().getMethodeTri())) {
            case 1 -> res.sort(new TriSkips());
            case 2 -> res.sort(new TriRatios());
            case 3 -> res.sort(new TriNomArtiste());
            case 4 -> res.sort(new TriNomMusique());
            case 5 -> res.sort(new TriEcoutes());
            case 6 -> res.sort(new TriEcoutesCompletes());
            case 8 -> res.sort(new TriTempsEcoute());
        }
        return res;
    }

    public ArrayList<Ecoute> filtrerEcoutes() {
        ArrayList<Ecoute> res = new ArrayList<>(historique);
        for (Ecoute e : historique)
            if (!Filtre.getInstance().valide(e))
                res.remove(e);
        switch (Math.abs(Filtre.getInstance().getMethodeTri())) {
            case 3 -> res.sort(new TriNomArtiste());
            case 4 -> res.sort(new TriNomMusique());
        }
        return res;
    }

    public ArrayList<Album> filtrerAlbums() {
        ArrayList<Album> res = new ArrayList<>(liste);
        for (Album a : liste)
            if (!Filtre.getInstance().valide(a))
                res.remove(a);
        switch (Math.abs(Filtre.getInstance().getMethodeTri())) {
            case 1 -> res.sort(new TriSkips());
            case 2 -> res.sort(new TriRatios());
            case 3 -> res.sort(new TriNomArtiste());
            case 5 -> res.sort(new TriEcoutes());
            case 6 -> res.sort(new TriEcoutesCompletes());
            case 7 -> res.sort(new TriNomAlbum());
            case 8 -> res.sort(new TriTempsEcoute());
        }
        return res;
    }

    public ArrayList<Ecoute> getHistorique() {
        return historique;
    }

    public Musique getTopMusiqueEcoutes() {
        Musique res = new Musique("tmp",null,null, null);
        for (Musique m : playlist)
            if (m.getNbEcoutes() > res.getNbEcoutes())
                res = m;
        return res;
    }

    public Artiste getTopArtisteEcoutes() {
        Artiste res = new Artiste("");
        for (Artiste a : selection)
            if (a.getNbEcoutes() > res.getNbEcoutes())
                res = a;
        return res;
    }

    public Album getTopAlbumEcoutes() {
        Album res = new Album("", new Artiste(""));
        for (Album a : liste)
            if (a.getNbEcoutes() > res.getNbEcoutes())
                res = a;
        return res;
    }

    public Musique getTopMusiqueEcoutesCompletes() {
        Musique res = new Musique("tmp",null,null, null);
        for (Musique m : playlist)
            if (m.getNbEcoutesCompletes() > res.getNbEcoutesCompletes())
                res = m;
        return res;
    }

    public Artiste getTopArtisteEcoutesCompletes() {
        Artiste res = new Artiste("");
        for (Artiste a : selection)
            if (a.getNbEcoutesCompletes() > res.getNbEcoutesCompletes())
                res = a;
        return res;
    }

    public Album getTopAlbumEcoutesCompletes() {
        Album res = new Album("", new Artiste(""));
        for (Album a : liste)
            if (a.getNbEcoutesCompletes() > res.getNbEcoutesCompletes())
                res = a;
        return res;
    }

    public Musique getTopMusiqueSkips() {
        Musique res = new Musique("tmp",null,null, null);
        for (Musique m : playlist)
            if (m.getNbSkips() > res.getNbSkips())
                res = m;
        return res;
    }

    public Artiste getTopArtisteSkips() {
        Artiste res = new Artiste("");
        for (Artiste a : selection)
            if (a.getNbSkips() > res.getNbSkips())
                res = a;
        return res;
    }

    public Album getTopAlbumSkips() {
        Album res = new Album("", new Artiste(""));
        for (Album a : liste)
            if (a.getNbSkips() > res.getNbSkips())
                res = a;
        return res;
    }

    public int getNbEcoutesCompletes() {
        int res = 0;
        for (Ecoute e : historique)
            if (e.getNature())
                res++;
        return res;
    }

    public int getNbEcoutes() {
        return historique.size();
    }

    public int getNbArtistes() {
        return selection.size();
    }

    public int getNbMusiques() {
        return playlist.size();
    }

    public int getNbAlbums() {
        return liste.size();
    }

    public int getNbSkips() {
        int res = 0;
        for (Ecoute e : historique)
            if (!e.getNature())
                res++;
        return res;
    }

    public String getPourcentEcoutesCompletes() {
        return String.format("%2.2f",(double)(getNbEcoutesCompletes()) / historique.size() * 100);
    }

    public String getPourcentSkips() {
        return String.format("%2.2f",(double)(getNbSkips()) / historique.size() * 100);
    }

    public void clear() {
        fichiers.clear();
        tmpHistorique.clear();
        historique.clear();
        selection.clear();
        playlist.clear();
        System.out.println("Suppression des fichiers terminée.");
    }

    private DataReader() {}
    public static DataReader getInstance() {
        if (dr == null) dr = new DataReader();
        return dr;
    }
}
