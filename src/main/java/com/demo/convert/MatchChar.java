package com.demo.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchChar {
    public static class Node{
        private List<String> matched;
        private Node next;
        private boolean end;
        public Node(List<String>matched){
            this.matched = matched;
        }
        public Node(List<String>matched,boolean end){
            this.matched = matched;
            this.end = end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }
    }
    private Node first;
    private Node last;
    private int size = 0;
    boolean isEnd(){
        return last.end;
    }

    public MatchChar(Node node){
        first = node;
        last = first;
        size++;
    }
    public MatchChar(List<String> matched) {
        this.first = new Node(matched);
        this.last = first;
        size++;
    }
    public List<String> getMatched() {
        return first.matched;
    }
    public void add(List<String>stringList){
        add(new Node(stringList));
    }
    public void add(Node node){
        last.next = node;
        last =last.next;
        size++;
    }
    public int size(){
        return size;
    }

    public List<String>getStrings(){
        List<String>result = new ArrayList<>();
        Node current = first;
        List<StringBuilder>sbList = new ArrayList<>();
        sbList.add(new StringBuilder());
        while (current!=null){
            List<StringBuilder>currentList = new ArrayList<>(sbList);
            sbList.clear();
            for(String s: current.matched){
                for(StringBuilder sb:currentList){
                    StringBuilder copy = new StringBuilder(sb);
                    copy.append(s);
                    sbList.add(copy);

                }
            }
            current = current.next;
        }

        for(StringBuilder sb:sbList){
            result.add( sb.toString());
        }
        return result;
    }
    public static void main(String[]args){
        MatchChar root = new MatchChar(Arrays.asList("a","b"));
        root.add(Arrays.asList("c","d"));
        root.add( Arrays.asList("e","f"));
        //expected : ace,acf,ade,adf,bce,bcf,bde,bdf
        List<String>result = root.getStrings();
        System.out.println("result : "+result);
    }
}
