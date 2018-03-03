# Connect4

Programming exercise of Connect 4 game

## Building
* Compile
```
gradle compileJava
```

* Run tests
```
gradle test
```

* Create distribution
```
gradle distZip
```

## Running
* Unizp distribution package and run
```
./bin/connect4
```

### Command line arguments (experimental)
* `--random-ai`: Play against a bot selecting random moves
* `--mc-ai`: Play against a stronger bot (MonteCarlo rollouts)
* `--ai-only`: Watch two bots play against each other
