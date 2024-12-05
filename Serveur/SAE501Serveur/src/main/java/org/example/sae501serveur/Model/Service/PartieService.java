package org.example.sae501serveur.Model.Service;


import org.example.sae501serveur.Model.Entity.*;
import org.example.sae501serveur.Model.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartieService {
    @Autowired
    private PartieRepository partieRepository;
    @Autowired
    private JoueurRepository joueurRepository;
    @Autowired
    private LieuxRepository lieuxRepository;
    @Autowired
    private MonstreRepository monstreRepository;
    @Autowired
    private MonstreLieuxRepository monstreLieuxRepository;

    public List<Partie> getAllParties() {
        return partieRepository.findAll();
    }

    public Partie savePartie(Partie partie) {
        return partieRepository.save(partie);
    }

    public Partie getPartieById(Long id) {
        return partieRepository.findById(id).orElse(null);
    }
    public void deletePartie(Long id) {
        partieRepository.deleteById(id);
    }
    public Partie addJoueurBDD(Joueur joueur,Partie partie){
        partie.getJoueur().add(joueur);
        return partieRepository.save(partie);
    }
    public Partie getPartieNotFinishedByIdJoueur(Joueur joueur){
        return partieRepository.getPartieNotFinishedByIdJoueur(joueur.getId());
    }
    public List<Partie> getAllPartieNoFinished(){
        return partieRepository.getPartieNotFinished();
    }

    /**
     * Choix des lieux de manière aléatoires mais avec une utilisation de poids
     * afin de ne pas avoir une distance entre chaque lieux trop déséquilibré<br>
     * Pour choisir les lieux on utilise leur distance entre eux
     * que l'on passe dans une fonction inverse
     * afin d'obtenir un poids que l'on va retirer à la probalité d'obtenir le point<br>
     * Dans le cas on nous avons plusieurs points dont nous devons calculer la distance avec un futur candidat
     * on calcule la moyenne de son poids pour l'appliquer<br>
     * Ex : dans le cas on nous aurions un point (1,1) déjà sélectionné et que nous aurions un point (2,2) présent
     * le calcul de son poids nous donnerait
     * en utilisant la formule de la distance euclidienne
     * d=1,414
     * poids=1/1,414=0,70
     * probalité=1-0,7=0,3
     * @param longitude
     * @param latitude
     * @return
     */
    public Partie createPartie(double longitude,double latitude){
        Partie partie=new Partie();
        List<Lieux> lieux=lieuxRepository.getLieuxByPlayerPosition(latitude,longitude);
        List<Monstre> monstres=monstreRepository.findAll();
        List<MonstreLieux> lieuxPartie=new ArrayList<>();
        Random random=new Random();
        lieuxPartie.add(new MonstreLieux(monstres.get(random.nextInt(monstres.size())),
                lieux.get(random.nextInt(lieux.size()))));
        lieux.remove(lieuxPartie.get(0).getLieux());

        for (int i=0;i<6;i++){
            ArrayList<Lieux> lieuxPoids =new ArrayList<>() ;
            for(int j=0;j<lieux.size();j++){
                double poids=0;
                double probabilites;
                for (MonstreLieux monstreLieux : lieuxPartie) {
                    poids += 1 / (lieux.get(j).distance(monstreLieux.getLieux())/100 + 1);
                }
                probabilites=Math.round((1-poids/lieuxPartie.size())*100000000);
                for(int l=0;l<probabilites;l++){
                    lieuxPoids.add(lieux.get(j));
                }
            }
            random=new Random();
            lieuxPartie.add(new MonstreLieux(monstres.get(random.nextInt(monstres.size())),
                    lieuxPoids.get(random.nextInt(lieuxPoids.size()))));
            lieuxPartie.get(i).addPartie(partie);
            lieux.remove(lieuxPartie.get(i).getLieux());
            partie.addMonstreLieux(lieuxPartie.get(i));
            monstreLieuxRepository.save(lieuxPartie.get(i));
        }

        return partieRepository.save(partie);
    }
}

