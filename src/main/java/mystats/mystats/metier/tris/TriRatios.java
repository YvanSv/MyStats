package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriRatios implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (Double.compare(o2.getRatio(), o1.getRatio()) != 0)
                return Double.compare(o2.getRatio(), o1.getRatio());
            else return o2.getNbEcoutesCompletes() - o1.getNbEcoutesCompletes();
        else if (Double.compare(o1.getRatio(), o2.getRatio()) != 0)
            return Double.compare(o1.getRatio(), o2.getRatio());
        else return o1.getNbEcoutesCompletes() - o2.getNbEcoutesCompletes();
    }
}
