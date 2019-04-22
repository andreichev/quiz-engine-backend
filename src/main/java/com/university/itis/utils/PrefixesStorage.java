package com.university.itis.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class PrefixesStorage {
	private Map<String, String> replaceMap;
	private Map<String, String> inverseReplaceMap;

	public PrefixesStorage(Map<String, String> replaceMapData) {
		setReplaceMap(replaceMapData);
	}

	private void setReplaceMap(Map<String, String> replaceMapData) {
		replaceMap = replaceMapData;
		inverseReplaceMap = replaceMap.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}

	public Map<String, String> getReplaceMap() {
		return replaceMap;
	}

	public Map<String, String> getInverseReplaceMap() {
		return inverseReplaceMap;
	}

	/* Shortens a long URI like http://dbpedia.org/Karlsruhe/ to dbr:Karlsruhe. */
	public String replace(String uri) {
		return getReplaceMap().keySet().stream().filter(k -> uri.contains(k)).findFirst().map(k -> uri.replace(k, replaceMap.get(k))).orElse(uri);
	}

	/* Expands a shorthand URI like dbr:Karlsruhe to http://dbpedia.org/Karlsruhe/ */
	public String replaceInverse(String shortName) {
		String f = getInverseReplaceMap().keySet().stream().filter(k -> shortName.contains(k)).findFirst().map(k -> shortName.replace(k, inverseReplaceMap.get(k))).orElse(shortName);
		return f;
	}


	public static String generatePrefixQueryString(Map<String, String> prefixes) {
		return prefixes.entrySet().stream()
				.map(e -> "PREFIX " + e.getValue() + " <" + e.getKey() + "> \n")
				.collect(Collectors.joining());
	}
}
