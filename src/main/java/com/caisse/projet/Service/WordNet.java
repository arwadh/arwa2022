package com.caisse.projet.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.stereotype.Service;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;


@Service
public class WordNet {

	private static ILexicalDatabase db = new NictWordNet();
	/*
	//available options of metrics
	private static RelatednessCalculator[] rcs = { new HirstStOnge(db),
	new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
	new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };
	*/
	private static double compute(String word1, String word2) {
	WS4JConfiguration.getInstance().setMFS(true);
	double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
	return s;
	}

	public static void main(String[] args) {
	String[] words = {"add", "get", "filter", "remove", "check", "find", "collect", "add"};

	for(int i=0; i<words.length-1; i++){
	for(int j=i+1; j<words.length; j++){
	double distance = compute(words[i], words[j]);
	System.out.println(words[i] +"–"  +  words[j] + "= " + distance);
	}
	
	
	}
	}
	public double test(List<String> word1,List<String>word2) {
		
		List<Double> list=new ArrayList<Double>();
		//Double d=0.0;
		for(int i=0; i<word1.size(); i++){
		for(int j=0; j<word2.size(); j++){
		double distance = compute(word1.get(i), word2.get(j));
		
		System.out.println(word1.get(i) +"–"  +  word2.get(j) + "= " + distance);
		//double d=(double)Math.round(distance * 100.0) / 100.0;
		//d+=distance;
		//System.out.println("la somme est" +d);
		list.add(distance);
		
		
		}}
		System.out.println(list);
		 int sum = list.stream().mapToInt(Double::intValue).sum();
		 System.out.println(sum);
		return sum;
		
	}
	}
	
	

	
