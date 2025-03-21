package mystats.mystats.metier.tris;


import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriEcoutes implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o2.getNbEcoutes() - o1.getNbEcoutes() != 0)
                return o2.getNbEcoutes() - o1.getNbEcoutes();
            else return (int) (o2.getRatio() - o1.getRatio());
        else if (o1.getNbEcoutes() - o2.getNbEcoutes() != 0)
            return o1.getNbEcoutes() - o2.getNbEcoutes();
        else return (int) (o1.getRatio() - o2.getRatio());
    }
}
