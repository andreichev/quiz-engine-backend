package com.university.itis.utils;

import java.util.Map;

public class ClassesStorage {
    private Map<String, Integer> classes;

    public ClassesStorage(Map<String, Integer> classes) {
        this.classes = classes;
    }

    public Map<String, Integer> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, Integer> classes) {
        this.classes = classes;
    }
}
