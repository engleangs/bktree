package com.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WordList {
    public static List<String>list(String path) throws IOException {
        List<String> items =  Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        return items;
    }
}
