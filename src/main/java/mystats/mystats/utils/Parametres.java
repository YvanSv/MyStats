package mystats.mystats.utils;

public class Parametres {
    private static Parametres params = null;
    // private int typeCalculSkip = 0;
    private float tauxPourEtreFull = 35;
    private float tauxPourEtreFullAvec = 35;

    // Type 0 = recherche du temps d'une musique par comparaison des temps d'écoutes sur plusieurs écoutes,
    // si 2 durée sont égales à la milliseconde alors ce temps est la durée totale.
    // Type 1 = temps en dur, exemple 30 secondes, toutes les écoutes de moins de 30 sont considérées comme des skips,
    // peu importe la durée totale de la musique.
    /*public void setTypeCalculSkip(int type) {
        typeCalculSkip = type;
    }
    public int getTypeCalculSkip() {
        return typeCalculSkip;
    }*/

    // Suivant le type de calcul des skips, considère que le toutes les écoutes allant au dessus de taux % de la durée
    // totale d'une musique est une écoute full.
    public void setTauxPourEtreFull(float taux) {
        this.tauxPourEtreFull = taux;
    }
    public float getTauxPourEtreFull() {
        return tauxPourEtreFull;
    }

    // Suivant le type de calcul des skips, considère que le toutes les écoutes allant au dessus de taux % de la durée
    // totale d'une musique est une écoute full même si l'utilisateur appuyé sur le bouton skip après le seuil défini.
    public void setTauxPourEtreFullAvec(float taux) {
        this.tauxPourEtreFullAvec = taux;
    }
    public float getTauxPourEtreFullAvec() {
        return tauxPourEtreFullAvec;
    }

    public static Parametres getInstance() {
        if (params == null) params = new Parametres();
        return params;
    }
}
