package com.example.wordleapplication.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.Random;

@Service
public class WordsService {

    private List<String> words;
    @PostConstruct
    private void loadWords() throws IOException {
        words = FileUtils.readLines(new File("src/main/resources/words.txt"));
    }

    public String getRandomWords() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}
