package it.unipv.ingsw.blackmarket.dealers;

import it.unipv.ingsw.blackmarket.Briefcase;
import it.unipv.ingsw.blackmarket.Dealer;
import it.unipv.ingsw.blackmarket.Exchange;

public class Dorigo extends Dealer {
    @Override
    public Briefcase exchangeBriefcase(int roundNo, int totRounds) {
        if(roundNo==1) return Briefcase.FULL;
        else return Briefcase.EMPTY;
    }

    /*
     è indubbiamente conviente essere sempre e solo un falso,
    sicuramente non mi interessa vedere quella prima per
    decidere se essere onesto o disonesto.
    */

}
