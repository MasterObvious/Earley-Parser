package nonsence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class WordGeneration {
	
	static int LIST_LENGTH = 1000;
	static Random rand = new Random(System.currentTimeMillis());
	
	
	public static void main(String args[]) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("alice.txt"));
		
		char previous = '>';
		int nextInt = in.read();
		Map<Character, Map<Character, Integer>> counts = new HashMap<>();
		
		//counts of bigrams
		while(nextInt != -1){
			char next = (char) nextInt;
			if (next == ' '){
				incrementCount(previous, '<', counts);
				previous =  '>';
			}else{
				incrementCount(previous, next, counts);
				previous = next;
			}
			nextInt = in.read();
		}
		
		//create probability distribution
		Map<Character, Map<Character, Double>> distribution = new HashMap<>();
		
		for (char c : counts.keySet()){
			//sum up all occurrences
			int total = 0;
			for (char h : counts.get(c).keySet()){
				total += counts.get(c).get(h);
			}
			
			Map<Character, Double> distEntry = new HashMap<>();
			distribution.put(c, distEntry);
			//divide by total to get double
			for (char h : counts.get(c).keySet()){
				double prob = counts.get(c).get(h) / (double) total;
				distEntry.put(h, prob);
			}
			
		}
		
		//generate random words
		List<String> randomWordList = new ArrayList<>();
		
		for (int i = 0; i < LIST_LENGTH; i++){
			//generate first character
			String word = "";
			char current = generateChar(distribution.get('>'));
			
			//generate until end character
			while(current != '<'){
				word += current;
				current = generateChar(distribution.get(current));
			}
			
			randomWordList.add(word);
			
		}
		randomWordList = randomWordList.stream().distinct()
				.sorted((s1, s2) -> Double.compare(informationRate(s1, distribution), informationRate(s2, distribution)))
				.limit(10).collect(Collectors.toList());
		System.out.println(randomWordList);
	}
	
	
	public static void incrementCount(char prev, char next, Map<Character, Map<Character, Integer>> counts){
		Map<Character, Integer> entry;
		if (counts.containsKey(prev)){
			entry = counts.get(prev);
		}else{
			entry = new HashMap<>();
			counts.put(prev, entry);
		}
		
		if (entry.containsKey(next)){
			entry.put(next, entry.get(next) + 1);
		}else{
			entry.put(next, 1);
		}
	}
	
	public static char generateChar(Map<Character, Double> dist){
		double total = 0;
		double r = rand.nextDouble();
		
		for (char c : dist.keySet()){
			total += dist.get(c);
			if (r < total){
				return c;
			}
		}
		return 0;
	}
	
	public static double informationRate(String s, Map<Character, Map<Character, Double>> dist){
		char prev = '>';
		double totalInfo = 0;
		
		for (int i = 0; i < s.length(); i++){
			totalInfo += -1.0 * log2(dist.get(prev).get(s.charAt(i)));
			prev = s.charAt(i);
		}
		totalInfo += -1.0 * log2(dist.get(prev).get('<'));
		
		return totalInfo / s.length();
	}
	
	public static double log2(double d){
		return Math.log10(d) / Math.log10(2);
	}
	

}
