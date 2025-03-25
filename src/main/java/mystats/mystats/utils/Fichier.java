package mystats.mystats.utils;

import mystats.mystats.metier.donnees.Ecoute;

import java.util.ArrayList;

public class Fichier {
    private final String nom,lien;
    private final ArrayList<Ecoute> lstEcoutes = new ArrayList<>();
    private int nbMusiques = 0, nbArtistes = 0, nbAlbums = 0;

    public Fichier(String nom, String lien) {
        this.nom = nom;
        this.lien = lien;
    }

    public String getNom() {
        return nom;
    }

    public String getLien() {
        return lien;
    }

    public void add(Ecoute e) {
        lstEcoutes.add(e);
    }

    public void addMusique() {
        nbMusiques++;
    }

    public void addArtiste() {
        nbArtistes++;
    }

    public void addAlbum() {
        nbAlbums++;
    }

    public int getNbEcoutes() {
        return lstEcoutes.size();
    }

    public int getNbEcoutesCompletes() {
        int cpt = 0;
        for (Ecoute e : lstEcoutes)
            if (e.getNature()) cpt++;
        return cpt;
    }

    public int getNbEcoutesSkips() {
        int cpt = 0;
        for (Ecoute e : lstEcoutes)
            if (!e.getNature()) cpt++;
        return cpt;
    }

    public int getNbMusiques() {
        return nbMusiques;
    }

    public int getNbArtistes() {
        return nbArtistes;
    }

    public int getNbAlbums() {
        return nbAlbums;
    }

    public int getTempsEcoute() {
        int res = 0;
        for(Ecoute e : lstEcoutes)
            res += e.getTempsEcoute();
        return res;
    }

    public boolean equals(Fichier f) {
        return f.getNom().equals(nom) && f.getLien().equals(lien);
    }
}
