package it.unipv.ingsw.blackmarket;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import it.unipv.ingsw.blackmarket.dealers.CoinFlipDealer;

public class Market {
    public static final int VALUE_FOR_BUYER = 14;
    public static final int VALUE_FOR_SELLER = 10;
    private final static Logger LOGGER = Logger.getLogger(Market.class.getName());
    private List<Dealer> dealers = new ArrayList<>();

    public void populateMarket() throws InstantiationException, IllegalAccessException {
        dealers = new ArrayList<>();

        // Search all the descendants of the Dealer base class.
        Reflections reflections = new Reflections("it.unipv.ingsw");
        Set<Class<? extends Dealer>> subTypes;
        subTypes = reflections.getSubTypesOf(Dealer.class);

        // Add all descendants of Dealer except 'CoinFlipDealer' if they are in an odd number.
        for (Class<? extends Dealer> cls : subTypes)
            if (subTypes.size() % 2 == 0 || !cls.getName().equals(CoinFlipDealer.class.getName()))
                dealers.add(cls.newInstance());

        LOGGER.info(dealers.size() + " dealers created");
    }

    private String makeExchangeSummary(Dealer dealer1, List<Briefcase> history1, int reward1,
                                       Dealer dealer2, List<Briefcase> history2, int reward2) {
        StringBuilder historyStr = new StringBuilder();
        for (int i =0; i < history1.size() && i < history2.size(); i++) {
            if (i > 0)
                historyStr.append("  ");
            historyStr.append(history1.get(i) == Briefcase.FULL ? '$' : '.');
            historyStr.append("");
            historyStr.append(history2.get(i) == Briefcase.FULL ? '$' : '.');
        }

        return String.format("%21s/%-21s %s %4d/%-4d", dealer1.getName(), dealer2.getName(), historyStr.toString(), reward1, reward2);
    }

    private int computeReward(Briefcase dealerCase, Briefcase otherCase) {
        int loss = (dealerCase == Briefcase.FULL ? VALUE_FOR_SELLER : 0);
        int gain = (otherCase == Briefcase.FULL ? VALUE_FOR_BUYER : 0);
        return gain - loss;
    }

    private void exchange(Dealer firstDealer, Dealer secondDealer, int rounds) {
        int firstReward = 0;
        int secondReward = 0;
        List<Briefcase> firstHistory = new ArrayList<>();
        List<Briefcase> secondHistory = new ArrayList<>();
        for (int round = 0; round < rounds; round++) {
            Briefcase firstCase = firstDealer.exchangeBriefcase(secondHistory, rounds);
            Briefcase secondCase = secondDealer.exchangeBriefcase(firstHistory, rounds);
            firstReward += computeReward(firstCase, secondCase);
            secondReward += computeReward(secondCase, firstCase);
            firstHistory.add(firstCase);
            secondHistory.add(secondCase);
        }
        firstDealer.addToBalance(firstReward);
        secondDealer.addToBalance(secondReward);
        LOGGER.info(makeExchangeSummary(firstDealer, firstHistory, firstReward, secondDealer, secondHistory, secondReward));
    }

    private void simulateDay(int rounds) {
        Collections.shuffle(dealers);
        for (int i = 0; i < dealers.size() - 1; i += 2)
            exchange(dealers.get(i), dealers.get(i + 1), rounds);
    }

    public void simulateSeason(int days, int roundsPerDay) {
        for (int day = 0; day < days; day++) {
            LOGGER.info("Day " + (day + 1) + " of " + days);
            simulateDay(roundsPerDay);
        }
    }

    public void sortDealers() {
        dealers.sort((dealer1, dealer2) -> (dealer2.getBalance() - dealer1.getBalance()));
    }

    public List<Dealer> getDealers() {
        return dealers;
    }
}