package com.example.wordleapplication.controller;

import com.example.wordleapplication.model.CharacterValue;
import com.example.wordleapplication.model.GuessResponse;
import com.example.wordleapplication.service.GameService;
import com.example.wordleapplication.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class WordleController {

    private WordsService wordsService;
    private GameService gameService;

    @Autowired
    public WordleController(WordsService wordsService, GameService gameService) {
        this.wordsService = wordsService;
        this.gameService = gameService;
    }

    @GetMapping("/startGame")
    public String startGame() {
        String word = wordsService.getRandomWords();
        System.out.println(word);
        return gameService.addGameToDataStore(word);
    }

    @PostMapping("/submitGuess")
    public GuessResponse submitGuess(@RequestParam String guess, @RequestParam String userToken) {
        return gameService.submitGuess(guess, userToken);
    }

}
