package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriNomMusique implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o1.getNom().compareTo(o2.getNom()) != 0)
                return o1.getNom().compareTo(o2.getNom());
            else return o1.getArtiste().getNom().compareTo(o2.getArtiste().getNom());
        else if (o2.getNom().compareTo(o1.getNom()) != 0)
            return o2.getNom().compareTo(o1.getNom());
        else return o2.getArtiste().getNom().compareTo(o1.getArtiste().getNom());
    }
}
