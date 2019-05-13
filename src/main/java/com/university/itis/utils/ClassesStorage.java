package com.university.itis.utils;

import java.util.Map;

public class ClassesStorage {
    private Map<String, String> classes;

    public ClassesStorage(Map<String, String> classes) {
        this.classes = classes;
    }

    public Map<String, String> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, String> classes) {
        this.classes = classes;
    }
}
