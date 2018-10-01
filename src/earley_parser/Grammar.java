package earley_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
	
	private Map<String, List<String>> grammar = new HashMap<>();
	
	public void initGrammar(){
		List<String> productions;
		
		//S -> NP VP
		productions = new ArrayList<>();
		productions.add("NP VP");
		grammar.put("S", productions);
		
		//NP -> N PP | N
		productions = new ArrayList<>();
		productions.add("N");
		productions.add("N PP");
		grammar.put("NP", productions);
		
		//PP -> P NP
		productions = new ArrayList<>();
		productions.add("P NP");
		grammar.put("PP", productions);
		
		//VP -> VP PP| V VP| V NP| V
		productions = new ArrayList<>();
		productions.add("V");
		productions.add("V NP");
		productions.add("V VP");
		productions.add("VP PP");
		grammar.put("VP", productions);
		
		//N -> can | fish | rivers | December
		productions = new ArrayList<>();
		productions.add("can");
		productions.add("fish");
		productions.add("rivers");
		productions.add("December");
		productions.add("they");
		grammar.put("N", productions);
		
		//P -> in
		productions = new ArrayList<>();
		productions.add("in");
		grammar.put("P", productions);
		
		//V -> can | fish
		productions = new ArrayList<>();
		productions.add("can");
		productions.add("fish");
		grammar.put("V", productions);
		
	}
	
	public List<String> getProductions(String nonterminal){
		return grammar.get(nonterminal);
	}

}
