package it.unipv.ingsw.blackmarket.dealers;

import it.unipv.ingsw.blackmarket.Briefcase;
import it.unipv.ingsw.blackmarket.Dealer;

import java.util.Random;

public class AlessandroOberti extends Dealer {

    private Random randomGenerator = new Random();

    public Briefcase exchangeBriefcase(int roundNo, int totRounds) {
        if (roundNo == 1)
            return Briefcase.FULL;
        else
            return Briefcase.EMPTY;
    }
}
