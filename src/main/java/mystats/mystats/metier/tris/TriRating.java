package mystats.mystats.metier.tris;

import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.utils.Filtre;

import java.util.Comparator;

public class TriRating  implements Comparator<Donnee> {
    @Override
    public int compare(Donnee o1, Donnee o2) {
        if (Filtre.getInstance().getMethodeTri() > 0)
            if (Double.compare(o2.getRating(), o1.getRating()) != 0)
                return Double.compare(o2.getRating(), o1.getRating());
            else return o2.getTempsEcoute() - o1.getTempsEcoute();
        else if (Double.compare(o1.getRating(), o2.getRating()) != 0)
            return Double.compare(o1.getRating(), o2.getRating());
        else return o1.getTempsEcoute() - o2.getTempsEcoute();
    }
}
