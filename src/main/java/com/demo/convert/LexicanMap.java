package com.demo.convert;

import com.demo.BkTree;
import com.demo.WordList;
import com.demo.WordResult;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LexicanMap {
    private static final Map<String, List<String>>MAP = new HashMap<>();
    public LexicanMap() throws IOException {
        List<String> lines= Files.readAllLines(Paths.get("data/l_map.txt"));
        for(String line:lines){
            if(line.length()==0){
                continue;
            }
            String[] l = line.split(" ",-1);
            String en = l[0];
            List<String>kh = new ArrayList<>();
            for(int i=1;i<l.length;i++){
                if(l[i].length()==0){
                    continue;
                }
                String[]ch = l[i].split(",");
                kh.addAll( Arrays.asList(ch));
            }

            MAP.put(en, kh);
        }

    }
    public List<String>convert(String lazyEn){
        //nhom
        //tov
        //hnambay
        List<String>result = new ArrayList<>();
        List<MatchChar>matchCharList = new ArrayList<>();
        convert(matchCharList,lazyEn);
        for(MatchChar matchChar:matchCharList){
            List<String>strList = matchChar.getStrings();
            result.addAll(strList);
        }
        return result;
    }

    static class WordItem{
        private StringBuilder sb;
        private int index;
        private int length;
        public WordItem(StringBuilder sb,int index){
            this.sb = sb;
            this.index = index;
            this.length = sb.length();
        }

    }

    public  void convert(List<MatchChar>result ,String lazyEnglish){
        StringBuilder p;
        WordItem wordItem = new WordItem( new StringBuilder(lazyEnglish),0);

        Queue<WordItem>queue = new LinkedList<>();
        queue.offer(wordItem);
        while (!queue.isEmpty()) {
            WordItem w = queue.poll();
            p = w.sb;
            List<MatchChar.Node>matcheNodes = new ArrayList<>();
            while (p.length() > 0) {
                if (MAP.containsKey(p.toString())) {
                    MatchChar.Node node = new MatchChar.Node( MAP.get(p.toString()));
                    int index = w.index + p.length();
                    matcheNodes.add( node);
                    if( index<lazyEnglish.length()){
                        StringBuilder sb = new StringBuilder(lazyEnglish.substring(index));
                        queue.offer(new WordItem(sb,index));
                    }
                    if( p.length() == w.length){
                        node.setEnd(true);
                    }
                }
                p.setLength(p.length() - 1);//decrement the length of the string
            }
            for (MatchChar.Node node : matcheNodes) {
                if (result.size() == 0) {
                    result.add(new MatchChar(node));
                }
                else {
                    for(MatchChar m : result){
                        if(m.isEnd()){
                            continue;
                        }
                        m.add(node);
                    }
                }
            }
        }

    }

    public static void main(String[]args) throws IOException {
        LexicanMap lexicanMap = new LexicanMap();
        List<String>result = lexicanMap.convert("tov");
        System.out.println("result : "+result);
        System.out.println(lexicanMap.convert("sala"));
        List<String> words = WordList.list("data/wordset.txt");
        BkTree bkTree = new BkTree();
        for(String w:words){
            bkTree.add(w);
        }
        for(String st:lexicanMap.convert("tos")) {
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
