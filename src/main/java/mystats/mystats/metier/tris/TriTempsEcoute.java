package mystats.mystats.metier.tris;

import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriTempsEcoute implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (o2.getTempsEcoute() - o1.getTempsEcoute() != 0)
                return o2.getTempsEcoute() - o1.getTempsEcoute();
            else return o1.getNbEcoutes() - o2.getNbEcoutes();
        else if (o1.getTempsEcoute() - o2.getTempsEcoute() != 0)
            return o1.getTempsEcoute() - o2.getTempsEcoute();
        else return o2.getNbEcoutes() - o1.getNbEcoutes();
    }
}