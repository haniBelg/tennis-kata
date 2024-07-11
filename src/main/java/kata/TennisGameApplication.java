package kata;

import kata.tennis.services.TennisGameHistoryPrinterService;
import kata.tennis.services.impl.TennisGameHistoryPrinterServiceImpl;

/**
 * Hello world!
 *
 */
public class TennisGameApplication {
    public static void main(String[] args) {
        TennisGameHistoryPrinterService tennisGameHistoryPrinterService = new TennisGameHistoryPrinterServiceImpl();
        tennisGameHistoryPrinterService.printScoreFromGameHistory(args[0]);
    }
}
