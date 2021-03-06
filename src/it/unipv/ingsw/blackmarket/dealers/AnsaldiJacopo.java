package it.unipv.ingsw.blackmarket.dealers;

import it.unipv.ingsw.blackmarket.Briefcase;
import it.unipv.ingsw.blackmarket.Dealer;
import it.unipv.ingsw.blackmarket.Exchange;

import java.text.BreakIterator;
import java.util.ArrayList;

public class AnsaldiJacopo extends Dealer {
    //ANSALDI 437472
    private ArrayList<Briefcase> previousCase;
    public AnsaldiJacopo() {
        previousCase = new ArrayList<>();
    }
    @Override
    public Briefcase exchangeBriefcase(int roundNo, int totRounds) {
       return Briefcase.EMPTY;
    }

    @Override
    public void exchangeResult(Exchange exchange, int roundNo, int totRounds) {
        previousCase.add(exchange.secondBriefcase());
    }
}
