package it.unipv.ingsw.blackmarket;

import java.util.List;

// TODO: command line options
// TODO: javadoc

public class BlackMarket {

    static private void printRanking(List<Dealer> dealers) {
        for (int i = 0; i < dealers.size(); i++) {
            System.out.printf("%2d) %21s %+6d\n", i + 1, dealers.get(i).getName(), dealers.get(i).getBalance());
            // System.out.println((i + 1) + ")" + dealers.get(i).getName() + ":\t" + dealers.get(i).getBalance());
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");

        Market market = new Market();
        try {
            for (int i = 0; i < 10; i++)
            market.populateMarket();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int days = 7;
        int roundsPerDay = 10;

        market.simulateSeason(days, roundsPerDay);
        market.sortDealers();
        System.out.println("FINAL RANKING");
        printRanking(market.getDealers());
    }
}