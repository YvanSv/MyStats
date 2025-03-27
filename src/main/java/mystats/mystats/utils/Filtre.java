package mystats.mystats.utils;

import mystats.mystats.metier.donnees.*;

public class Filtre {
    private static final Filtre filtre = new Filtre();
    public static final int TYPE_MUSIQUE = 0;
    public static final int TYPE_ARTISTE = 1;
    public static final int TYPE_ECOUTE = 2;
    public static final int TYPE_ALBUM = 3;
    private int type = 0;
    private int methodeTri = 0;
    private int nbEcoutesCompletesMin = -1;
    private int nbEcoutesCompletesMax = -1;
    private int nbSkipMin = -1;
    private int nbSkipMax = -1;
    private int nbEcoutesMin = -1;
    private int nbEcoutesMax = -1;
    private double ratioMin = -1;
    private double ratioMax = -1;
    private String artiste = "";
    private String musique = "";
    private String album = "";
    private Boolean nature = null;
    private Date dateDebut = null;
    private Date dateFin = null;

    public static Filtre getInstance() {
        return filtre;
    }

    public void clear() {
        type = methodeTri = 0;
        nbEcoutesMin = nbEcoutesMax = -1;
        nbEcoutesCompletesMin = nbEcoutesCompletesMax = -1;
        nbSkipMin = nbSkipMax = -1;
        ratioMin = ratioMax = -1;
        artiste = musique = album = "";
        nature = null;
        dateDebut = dateFin = null;
    }

    public void setType(int num) {
        type = num;
    }
    public void setMethodeTri(int num) {
        if (methodeTri == num) methodeTri *= -1;
        else methodeTri = num;
    }
    public void setNbEcoutesCompletesMin(int nb) { nbEcoutesCompletesMin = nb; }
    public void setNbEcoutesCompletesMax(int nb) { nbEcoutesCompletesMax = nb; }
    public void setNbEcoutesMin(int nb) { nbEcoutesMin = nb; }
    public void setNbEcoutesMax(int nb) { nbEcoutesMax = nb; }
    public void setNbSkipMin(int nb) { nbSkipMin = nb; }
    public void setNbSkipMax(int nb) { nbSkipMax = nb; }
    public void setRatioMin(double ratio) { ratioMin = ratio; }
    public void setRatioMax(double ratio) { ratioMax = ratio; }
    public void setArtiste(String a) { artiste = a; }
    public void setMusique(String a) { musique = a; }
    public void setAlbum(String a) { album = a; }
    public void setNature(Boolean b) { nature = b; }
    public void setDateDebut(Date d) { dateDebut = d; }
    public void setDateFin(Date d) { dateFin = d; }

    public int getType() { return type; }
    public int getMethodeTri() { return methodeTri; }
    public int getNbEcoutesCompletesMin() { return nbEcoutesCompletesMin; }
    public int getNbEcoutesCompletesMax() { return nbEcoutesCompletesMax; }
    public int getNbEcoutesMin() { return nbEcoutesMin; }
    public int getNbEcoutesMax() { return nbEcoutesMax; }
    public int getNbSkipMin() { return nbSkipMin; }
    public int getNbSkipMax() { return nbSkipMax; }
    public double getRatioMin() { return ratioMin; }
    public double getRatioMax() { return ratioMax; }
    public String getArtiste() { return artiste; }
    public String getMusique() { return musique; }
    public Boolean getNature() { return nature; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }

    public boolean valide(Artiste a) {
        return (artiste.equals("") || a.getNom().toLowerCase().contains(artiste.toLowerCase())) && valide((Donnee) a);
    }

    public boolean valide(Musique m) {
        return ((artiste.equals("") || m.getArtiste().getNom().toLowerCase().contains(artiste.toLowerCase())) &&
                ((album.equals("")) || m.getAlbum().getNom().toLowerCase().contains(album.toLowerCase()))) &&
                valide((Donnee) m);
    }

    public boolean valide(Album a) {
        return (artiste.equals("") || a.getArtiste().getNom().toLowerCase().contains(artiste.toLowerCase())) && valide((Donnee) a);
    }

    public boolean valide(Ecoute e) {
        if (nature != null && nature != e.getNature()) return false;
        if (!musique.equals("") && !e.getNom().equals(musique)) return false;
        if (dateDebut != null && e.getDate().avant(dateDebut) && !dateDebut.memeDate(e.getDate())) return false;
        if (dateFin   != null && !e.getDate().avant(dateFin)  && !dateFin  .memeDate(e.getDate())) return false;
        if (!album.equals("") && !album.equals(e.getAlbum().getNom())) return false;
        return artiste.equals("") || e.getArtiste().getNom().toLowerCase().contains(artiste.toLowerCase());
    }

    private boolean valide(Donnee d) {
        if (nbEcoutesMin != -1 && nbEcoutesMin > d.getNbEcoutes()) return false;
        if (nbEcoutesMax != -1 && nbEcoutesMax < d.getNbEcoutes()) return false;
        if (nbEcoutesCompletesMin != -1 && nbEcoutesCompletesMin > d.getNbEcoutesCompletes()) return false;
        if (nbEcoutesCompletesMax != -1 && nbEcoutesCompletesMax < d.getNbEcoutesCompletes()) return false;
        if (nbSkipMin != -1 && nbSkipMin > d.getNbSkips()) return false;
        if (nbSkipMax != -1 && nbSkipMax < d.getNbSkips()) return false;
        if (ratioMin != -1 && ratioMin > d.getRatio()) return false;
        return ratioMax == -1 || !(ratioMax < d.getRatio());
    }

    public String toString() {
        String type, tri;
        if (this.type == 0) type = "Titres";
        else type = "Artistes";
        if (methodeTri == 0) tri = "Ecoutes";
        else if (methodeTri == 1) tri = "Skips";
        else tri = "Ratio";
        return  "type = " + type + "\ntri = " + tri + "\necoutesMin = " + nbEcoutesCompletesMin + "\necoutesMax = " + nbEcoutesCompletesMax + "\n" +
                "skipsMin = " + nbSkipMin + "\nskipsMax = " + nbSkipMax + "\nratioMin = " + ratioMin + "\nratioMax = " + ratioMax;
    }
}