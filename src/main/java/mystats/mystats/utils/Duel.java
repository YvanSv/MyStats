package mystats.mystats.utils;

import mystats.mystats.VueArbres;
import mystats.mystats.metier.donnees.Donnee;

import java.util.List;

public class Duel<T> {
    private final Arbre.Node<T> node;
    private final T[] donnees;
    private static VueArbres vue;

    public Duel(Arbre.Node<T> node, int nb) {
        this.node = node;
        donnees = (T[]) new Object[nb];
    }

    public Duel(Arbre.Node<T> node, List<T> lst) {
        this.node = node;
        donnees = (T[]) new Object[lst.size()];
        for (int i = 0; i < lst.size(); i++)
            donnees[i] = lst.get(i);
    }

    public Arbre.Node<T> getNode() { return node; }

    public String getNom() {
        return node.getNom();
    }

    public T getDonnee(int index) {
        return donnees[index];
    }

    public int size() {
        return donnees.length;
    }

    public boolean possible() {
        for (T t : donnees)
            if (t == null) return false;
        return true;
    }

    public void setChoix(T t) {
        if (!node.getNom().equals("Finale")) node.getParent().setDonneeDuel(node.getNumero(), t);
        else vue.champion((Donnee)t);
    }

    public void setDonnee(int num, T donnee) {
        donnees[num] = donnee;
    }

    public String toString() {
        StringBuilder sRet = new StringBuilder();
        for (T t : donnees)
            if (t != null) sRet.append(t).append(" ");
        return sRet.toString();
    }

    public static void setVue(VueArbres v) {
        vue = v;
    }
}
