```
//create the printing service
TennisGameHistoryPrinterService tennisGameHistoryPrinterService = 
new TennisGameHistoryPrinterServiceImpl();

//process + print score states
tennisGameHistoryPrinterService.printScoreFromGameHistory("AABABBBAAA");
```
#this will show :

                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                A > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                A > Player A wins the game