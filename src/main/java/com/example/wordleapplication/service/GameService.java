package com.example.wordleapplication.service;

import com.example.wordleapplication.model.CharacterValue;
import com.example.wordleapplication.model.Game;
import com.example.wordleapplication.model.GameStatus;
import com.example.wordleapplication.model.GuessResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.wordleapplication.model.CharacterValue.*;
import static com.example.wordleapplication.model.GameStatus.IN_PROGRESS;
import static com.example.wordleapplication.model.GameStatus.WIN;
import static java.util.UUID.randomUUID;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(value = SCOPE_SINGLETON)
public class GameService {
    ConcurrentHashMap<String, Game> userGames = new ConcurrentHashMap<>();

    public String addGameToDataStore(String word){
        String userKey = randomUUID().toString();
        userGames.put(userKey,new Game(word,0));
        return userKey;
    }
    public GuessResponse submitGuess(String requestWord, String userToken){
        Game userGame = Optional.of(userGames.get(userToken)).orElseThrow(()->new RuntimeException("session doesnot exist"));
        if(userGame.getCurrentTries() > 5){
            throw new RuntimeException("User game is finished");
        }

        int currentTry = userGame.getCurrentTries();

        userGame.setCurrentTries(++currentTry);
        String userWord = userGame.getWord();

        Map<Character, CharacterValue> characterValueMap = new LinkedHashMap<>();
        if(userWord.equalsIgnoreCase(requestWord)){
            userGames.remove(userToken);
            return new GuessResponse(currentTry,requestWord, WIN, null);

        }else{
            for(int i= 0; i < userWord.length(); i++){
                if(userWord.charAt(i) == requestWord.charAt(i)){
                    characterValueMap.put(requestWord.charAt(i),CORRECT);
                }else if (userWord.contains(Character.toString(requestWord.charAt(i)))){
                    characterValueMap.put(requestWord.charAt(i), PRESENT_BUT_MISPLACED);
                }else{
                    characterValueMap.put(requestWord.charAt(i), NOT_PRESENT);
                }
            }

        }
        userGames.replace(userToken, userGame);
        return new GuessResponse(currentTry, "", IN_PROGRESS, characterValueMap);
    }

}
