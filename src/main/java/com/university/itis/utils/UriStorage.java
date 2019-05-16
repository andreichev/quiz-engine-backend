package com.university.itis.utils;

import java.util.List;
import java.util.Map;

public class UriStorage {
    private Map<String, String> classes;
    private List<String> blackList;

    public UriStorage(Map<String, String> classes, List<String> blackList) {
        this.classes = classes;
        this.blackList = blackList;
    }

    public Map<String, String> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, String> classes) {
        this.classes = classes;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }
}
