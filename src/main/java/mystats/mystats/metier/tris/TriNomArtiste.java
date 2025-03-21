package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriNomArtiste implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o1.getArtiste().getNom().compareTo(o2.getArtiste().getNom()) != 0)
                return o1.getArtiste().getNom().compareTo(o2.getArtiste().getNom());
            else return Double.compare(o2.getRatio(), o1.getRatio());
        else if (o2.getArtiste().getNom().compareTo(o1.getArtiste().getNom()) != 0)
            return o2.getArtiste().getNom().compareTo(o1.getArtiste().getNom());
        else return Double.compare(o1.getRatio(), o2.getRatio());
    }
}
