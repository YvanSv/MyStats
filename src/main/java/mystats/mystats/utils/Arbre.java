package mystats.mystats.utils;

import mystats.mystats.VueArbres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Arbre<T> {
    private static Arbre instance;
    private Node<T> root;
    private VueArbres vue;
    private int numDuel;
    private int nbDuels;
    private final List<Duel<T>> previous = new ArrayList<>();

    public static Arbre getInstance() {
        if (instance == null) instance = new Arbre<>();
        return instance;
    }

    private Arbre() {}

    public Arbre init(VueArbres v, int nbDonneesParDuel, List<T> lst) {
        vue = v;
        Duel.setVue(v);

        root = new Node<>(0,null, nbDonneesParDuel);
        root.nom = "Finale";
        root.build(lst, nbDonneesParDuel);

        numDuel = 0;
        nbDuels = (lst.size()-1)/(nbDonneesParDuel-1);
        previous.clear();
        return this;
    }

    public static class Node<T> {
        private final int num;
        private String nom;
        private final Node<T> parent;
        private Duel<T> duel;
        private final Node<T>[] children;

        private Node(int numero, Node<T> parent, int nbEnfants) {
            num = numero;
            this.parent = parent;
            if (parent != null)
                switch (parent.nom) {
                    case "Finale" -> nom = "Demi-finale";
                    case "Demi-finale" -> nom = "Quart de finale";
                    case "Quart de finale" -> nom = "8eme de finale";
                    case "8eme de finale" -> nom = "16eme de finale";
                    case "16eme de finale" -> nom = "32eme de finale";
                    case "32eme de finale" -> nom = "64eme de finale";
                    case "64eme de finale" -> nom = "128eme de finale";
                    case "128eme de finale" -> nom = "256eme de finale";
                }
            children = new Node[nbEnfants];
        }

        private void build(List<T> lst, int nbDonneesParDuel) {
            if (lst.size() >= nbDonneesParDuel && lst.size() < 2 * nbDonneesParDuel) {
                duel = new Duel<>(this,lst);
                return;
            }

            // On découpe la liste en nbDonnesParDuel parties
            List<List<T>> subLists = splitList(lst,nbDonneesParDuel);
            // On crée son propre duel (vide) et on construit les enfants
            duel = new Duel<>(this,nbDonneesParDuel);

            for (int i = 0; i < nbDonneesParDuel; i++) {
                Node<T> fils = new Node<>(i,this, nbDonneesParDuel);
                children[i] = fils;
                fils.build(subLists.get(i),nbDonneesParDuel);
            }
        }

        private List<List<T>> splitList(List<T> lst, int nbDonneesParDuel) {
            List<List<T>> result = new ArrayList<>();
            int totalElements = lst.size();
            int elementsPerSubList = totalElements / nbDonneesParDuel;
            int remainder = totalElements % nbDonneesParDuel;

            int index = 0;
            for (int i = 0; i < nbDonneesParDuel; i++) {
                int subListSize = elementsPerSubList + (i < remainder ? 1 : 0); // Distribuer l'excédent
                result.add(new ArrayList<>(lst.subList(index, index + subListSize)));
                index += subListSize;
            }

            return result;
        }

        public void setDonneeDuel(int index, T donnee) {
            duel.setDonnee(index,donnee);
        }

        public Node<T> getParent() {
            return parent;
        }

        public Node<T>[] getChildren() { return children; }

        public Duel<T> getRandomDuel() {
            if (duel.possible()) return duel;

            List<Node<T>> lst = new ArrayList<>(List.of(children));
            Collections.shuffle(lst);

            Duel<T> ret = null;
            for (Node<T> enfant : lst)
                if (duel.getDonnee(enfant.getNumero()) == null) {
                    ret = enfant.getRandomDuel();
                    break;
                }

            return ret;
        }

        public int getNumero() {
            return num;
        }

        public String getNom() {
            return nom;
        }
    }

    public void next(Duel<T> previous) {
        this.previous.add(previous);
        numDuel++;
        vue.setDuel(root.getRandomDuel(), "Duel " + numDuel + " / " + nbDuels);
    }

    public void other() {
        vue.setDuel(root.getRandomDuel(), "Duel " + numDuel + " / " + nbDuels);
    }

    public void previous() {
        Duel<T> duel = previous.get(previous.size()-1);
        previous.remove(duel);
        vue.setDuel(duel, "Duel " + --numDuel + " / " + nbDuels);
    }

    public Duel<T> getPrevious() {
        return previous.get(previous.size()-1);
    }
}
