package com.university.itis.utils;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LinkedDataUtils {

	/**
	 * Returns one string out of @strings which is most similar to @reference, according to normalized Levenshtein similarity.
	 * @param strings Set of string to be compared to @reference.
	 * @param reference Reference string to compare others to.
	 * @return Most similar string.
	 */
	public static String getMostSimilar(Collection<String> strings, String reference) {
		NormalizedLevenshtein l = new NormalizedLevenshtein();

		return strings.stream()
				.map(s -> new AbstractMap.SimpleEntry<String, Double>(s, l.similarity(s, reference)))
				.sorted((k1, k2) -> Double.compare(k2.getValue(), k1.getValue()))
				.findFirst()
				.get()
				.getKey();
	}

	public static Collection<String> uniqueCollection(Collection<String> c) {
		Map<String, Boolean> seen = new HashMap();
		Collection<String> uniqueCollection = new LinkedList<>();
		for (String o : c) {
			if (!seen.containsKey(o)) {
				seen.put(o, true);
				uniqueCollection.add(o);
			}
		}
		return uniqueCollection;
	}
}
