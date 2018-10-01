package earley_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
	
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		
		
		String text = input.nextLine();
		
		List<ChartEntry> chart = new ArrayList<>();
		ChartEntry init = new ChartEntry("S", "NP VP", 0, 0, 0, null);
		chart.add(init);
		init.setId();
		
		Grammar g = new Grammar();
		g.initGrammar();
		List<String> phrase = new ArrayList<>();
		phrase.add("NP");
		phrase.add("VP");
		phrase.add("PP");
		
		String[] words = text.split(" ");
		
		int index = 0;
		
		while(index < chart.size()){
			ChartEntry c = chart.get(index++);
			System.out.println(c);
			String[] production = c.getProduction().split(" ");
			
			if (c.getProgress() < production.length){
				String nextSymbol = production[c.getProgress()];
				
				//predict step
				if (phrase.contains(nextSymbol)){
					for (String s : g.getProductions(nextSymbol)){
						ChartEntry newEntry = new ChartEntry(nextSymbol, s, 0, c.getEnd(), c.getEnd(), null);
						boolean isUnique = true;
						
						for (ChartEntry e : chart){
							if (e.equals(newEntry)){
								isUnique = false;
							}
						}
						if (isUnique && newEntry.getStart() < words.length){
							newEntry.setId();
							chart.add(newEntry);
						}
					}
				}else{
					//scan step
					for (String s : g.getProductions(nextSymbol)){
						if (c.getEnd() < words.length && words[c.getEnd()].equals(s)){
							ChartEntry newEntry = new ChartEntry(nextSymbol, s, 1, c.getStart(), c.getEnd() + 1, null);
							boolean isUnique = true;
							
							for (ChartEntry e : chart){
								if (e.equals(newEntry)){
									isUnique = false;
								}
							}
							if (isUnique && newEntry.getStart() < words.length){
								newEntry.setId();
								chart.add(newEntry);
							}
						}
					}
				}
			}else{
				//complete step
				for (int i = 0; i < index; i++){
					ChartEntry toComplete = chart.get(i);
					String[] p = toComplete.getProduction().split(" ");
					if (toComplete.getProgress() < p.length){
						if (p[toComplete.getProgress()].equals(c.getNonTerminal()) && toComplete.getEnd() == c.getStart()){
							List<ChartEntry> hist = new ArrayList<>(toComplete.getHistory());
							hist.add(c);
							ChartEntry newEntry = new ChartEntry(toComplete.getNonTerminal(), toComplete.getProduction(), toComplete.getProgress() + 1, toComplete.getStart(), c.getEnd(), hist);
							boolean isUnique = true;
							
							for (ChartEntry e : chart){
								if (e.equals(newEntry)){
									isUnique = false;
								}
							}
							if (isUnique && newEntry.getStart() < words.length){
								newEntry.setId();
								chart.add(newEntry);
							}
						}
					}
				}
			}
		}
		int totalDerivations = 0;
		for (ChartEntry c : chart){
			if (c.getNonTerminal().equals("S") && c.getStart() == 0 && c.getEnd() == words.length){
				totalDerivations++;
			}
		}
		
		System.out.println("In total there are " + totalDerivations + " derivations");
		
	}

}
