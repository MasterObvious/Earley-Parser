package earley_parser;

import java.util.ArrayList;
import java.util.List;

public class ChartEntry {
	String nonTerminal;
	String production;
	int progress;
	int start;
	int end;
	int id;
	List<ChartEntry> history;
	
	static int TotalId = 0;
	
	public ChartEntry(String nonTerminal, String production, int progress, int start, int end, List<ChartEntry> list){
		this.nonTerminal = nonTerminal;
		this.production = production;
		this.progress = progress;
		this.start = start;
		this.end = end;
		history = new ArrayList<>();
		if (list != null){
			history.addAll(list);
		}
	}

	public String getNonTerminal() {
		return nonTerminal;
	}
	
	public void setId(){
		this.id = TotalId++;
	}

	public String getProduction() {
		return production;
	}

	public int getProgress() {
		return progress;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	
	public List<ChartEntry> getHistory(){
		return history;
	}
	
	
	
	@Override
	public boolean equals(Object o){
		if (o instanceof ChartEntry){
			ChartEntry e = (ChartEntry) o;
			
			return this.nonTerminal.equals(e.nonTerminal) && this.production.equals(e.production) && this.progress == e.progress
					&& this.end == e.end && this.start == e.start && this.history.equals(e.history);
		}
		return false;
	}
	
	@Override
	public String toString(){
		String productionItem = id + ": " + nonTerminal + " ->";
		String[] productions = production.split(" ");
		
		for (int i = 0; i < progress; i++){
			productionItem = productionItem + " " + productions[i];
		}
		
		productionItem = productionItem + " •";
		
		for (int i = progress; i < productions.length; i++){
			productionItem = productionItem + " " + productions[i];
		}
		
		String ret = productionItem + " [" + start + "," + end + "] hist: ";
		
		
		
		if (history.size() == 0){
			return ret + "null";
		}else{
			for (ChartEntry c : history){
				ret += c.id + " ";
			}
			return ret;
		}
		
	}

}
