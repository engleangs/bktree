package com.demo;

import java.io.IOException;
import java.util.*;

public class BkTree {
    private static class Node {
        String word;
        Map<Integer,Node> children;
        public Node(String word){
            this.word = word;
            children = new TreeMap<>();
        }
        public void add(String word){
            int dis = LevensteinDistance.distance( word, this.word);
            Node child = children.get(dis);
            if( child != null) {
                child.add( word);
            }
            else {
                children.put( dis, new Node( word));
            }
        }
        public void query(String word,int threshold , Map<String,Integer>collection){
            int dis = LevensteinDistance.distance( word, this.word);
            if( dis <= threshold) {
                collection.put( this.word, dis);
            }
            for( int score = dis - threshold; score <= dis + threshold;score++){
                if( score > 0) {
                    Node child = children.get( score);
                    if( child !=null) {
                        child.query( word, threshold , collection);
                    }
                }
            }
        }
    }
    private Node root;
    public void add(String word){
        if( root == null) {
            root = new Node( word);
        }
        else {
            root.add( word);
        }
    }
    public List<WordResult> query(String word, int threshold){
        Map<String,Integer> found = new LinkedHashMap<>();
        root.query( word, threshold, found);
        List<WordResult> wordResults = new ArrayList<>();
        for(Map.Entry<String,Integer>w:found.entrySet()){
            WordResult wordResult = new WordResult(w.getValue(), w.getKey());
            wordResults.add( wordResult);
        }
        Collections.sort(wordResults, (o1, o2) -> {
            Integer i1 = o1.getScore();
            Integer i2 = o2.getScore();
            return i1.compareTo(i2);
        });
        return wordResults;
    }

    public static void main(String[] args) throws IOException {
        List<String> words = WordList.list("data/wordset.txt");
        BkTree bkTree = new BkTree();
        for(String w:words){
            bkTree.add(w);
        }
        String[] tests = {"សំ", "សួស្ត", "សំឡេ"};
        for(String st:tests) {
            System.out.println("similar to : "+st);
             List<WordResult> results =     bkTree.query(st,2);
             int i = 1;
             for(WordResult w:results){
                 System.out.println(" word : " +w.getWord() +" \t\t\t, distance : " + w.getScore());
                 if( i++ > 10){
                     break;
                 }
             }
            System.out.println("================================");

        }


    }


}
