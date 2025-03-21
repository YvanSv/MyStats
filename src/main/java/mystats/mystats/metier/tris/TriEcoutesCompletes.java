package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriEcoutesCompletes implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o2.getNbEcoutesCompletes() - o1.getNbEcoutesCompletes() != 0)
                return o2.getNbEcoutesCompletes() - o1.getNbEcoutesCompletes();
            else return o1.getNbSkips() - o2.getNbSkips();
        else if (o1.getNbEcoutesCompletes() - o2.getNbEcoutesCompletes() != 0)
            return o1.getNbEcoutesCompletes() - o2.getNbEcoutesCompletes();
        else return o2.getNbSkips() - o1.getNbSkips();
    }
}
