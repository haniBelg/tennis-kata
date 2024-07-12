- lunch programatically :

```
        // this is the class that glues all our services to deliver the needed method.
        GameRunner gameRunner = new GameRunner();
        // this is the method that recieves game history and prints the scores.
        gameRunner.printGameScoresFromHistory("AABABBBAAA");
```

> this will print :

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

- lunch with command line :
  > mvn clean install

> java -jar ./target/tennis-kata-1.0-SNAPSHOT.jar AABBABABBB

                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                B > Player A: 30 / Player B: 30
                A > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                B > Player B wins the game

☝️this uses [TennisGameApplication.java](https://github.com/haniBelg/tennis-kata/blob/master/src/main/java/kata/TennisGameApplication.java) as main class entrypoint
