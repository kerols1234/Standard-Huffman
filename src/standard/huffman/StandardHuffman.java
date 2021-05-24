/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standard.huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author kerols
 */
public class StandardHuffman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            comparsion("file.txt");
            decomparsion("comp.txt");
        } catch (IOException ex) {
            Logger.getLogger(StandardHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void comparsion(String fileName) throws IOException{
        HashMap<Character,Double> map = new HashMap<>();
        HashMap<Character,String> table = new HashMap<>();
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);	
        String str,data = "";
        double Size = 0;
        ArrayList<Node> arrayList = new ArrayList<>();
        while((str = br.readLine())!=null){
            for(int i = 0; i < str.length(); i++){
                if(map.containsKey(str.charAt(i))){
                    map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
                }else{
                    map.put(str.charAt(i), (double)1);
                }
            }
            data += str;
            Size += str.length();
        }
        br.close();
        fr.close();
        
        for(Entry<Character, Double> entry : map.entrySet()) {
            Character key = entry.getKey();
            double value = entry.getValue();
            
            map.put(key, Math.round((value / Size) * 1000) / 1000d);
            arrayList.add(new Node(entry.getKey(), entry.getValue()));
        }
        Collections.sort(arrayList);
        int s = ((arrayList.size() * 2) - 2);
        for(int i = 0; i < s; i += 2){
            arrayList.add(new Node((char)1,(arrayList.get(i).getPro()+arrayList.get(i+1).getPro()),arrayList.get(i),arrayList.get(i+1)));
            Collections.sort(arrayList);
        }   
        Collections.sort(arrayList);
        for(int i = arrayList.size()-1; i > -1 ; i--){
            Node l = arrayList.get(i).getLeft();
            Node r = arrayList.get(i).getRight();
            if(l != null){
                l.setCode(arrayList.get(i).getCode() + "0");
            }
            if(r != null){
                r.setCode(arrayList.get(i).getCode() + "1");
            }
        }
    
        for(int i = arrayList.size() - 1; i > -1; i--){
            if(arrayList.get(i).getCharacter() == (char)1)
                arrayList.remove(i);
            else{
                table.put(arrayList.get(i).getCharacter(), arrayList.get(i).getCode());
            }
        }
         
        String d = table.toString();
        d = d.replace('{', ' ');
        d = d.replace('}', ' ');
        d = d.trim();
        d = d.replace(", ", "\n");
        d += '\n';
        d += "!\n";
        
        for(int i = 0; i < data.length() ; i++){
            d += table.get(data.charAt(i));
        }
        
        FileWriter fileWriter = new FileWriter("comp.txt");
	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	
	bufferedWriter.write(d);
        
        bufferedWriter.close();
        fileWriter.close();
    }
    
    public static class Node implements Comparable{
        private String code;
        private char character;
        private double pro;
        private Node left, right;

        public Node( char character, double pro) {
            this.code = "";
            this.character = character;
            this.pro = pro;
            left = null;
            right = null;
        }

        public Node(char character, double pro, Node left, Node right) {
            this.character = character;
            this.pro = pro;
            this.left = left;
            this.right = right;
            this.code = "";
        }
        
        public String getCode() {
            return code;
        }

        public char getCharacter() {
            return character;
        }

        public double getPro() {
            return pro;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setCharacter(char character) {
            this.character = character;
        }

        public void setPro(double pro) {
            this.pro = pro;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public int compareTo(Object o) {
            double compareage= ((Node)o).getPro();
            char c = ((Node)o).getCharacter();
            if((this.pro - compareage) > 0)
                return  1;
            else if((this.pro - compareage) < 0)
                return -1;
            else{
                if((this.character - c) > 0){
                    return 1;
                }else if((this.character - c) < 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        }
        
        @Override
        public String toString() {
            return "[ code = " + code + ", character = " + character + ", propability = " + pro + "]\n";
        }
        
    }
    
    public static void decomparsion(String fileName) throws IOException {
        HashMap<String,Character> table = new HashMap<>();
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);	
        String str,data = "";
        ArrayList<Node> arrayList = new ArrayList<>();
        boolean flag = true;
        while((str = br.readLine())!=null){
            if(str.equalsIgnoreCase("!")){
                flag = false;
            }else if(flag){
                String[] x = str.split("=");
                table.put(x[1], x[0].charAt(0));
            }else{
                data += str;
            }
        }
        br.close();
        fr.close();
        
        String d = "";
        String tamp = "";
        for(int i = 0; i < data.length(); i++){
            tamp += data.charAt(i);
            if(table.containsKey(tamp)){
                d += table.get(tamp);
                tamp = "";
            }
        }
        FileWriter fileWriter = new FileWriter("decomp.txt");
	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	
	bufferedWriter.write(d);
        
        bufferedWriter.close();
        fileWriter.close();
    }
    
}
