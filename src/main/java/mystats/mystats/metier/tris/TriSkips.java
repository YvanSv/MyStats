package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriSkips implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o2.getNbSkips() - o1.getNbSkips() != 0)
                return o2.getNbSkips() - o1.getNbSkips();
            else return o1.getNbEcoutesCompletes() - o2.getNbEcoutesCompletes();
        else if (o1.getNbSkips() - o2.getNbSkips() != 0)
            return o1.getNbSkips() - o2.getNbSkips();
        else return o2.getNbEcoutesCompletes() - o1.getNbEcoutesCompletes();
    }
}
