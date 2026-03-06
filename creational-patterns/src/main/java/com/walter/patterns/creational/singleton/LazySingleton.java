package com.walter.patterns.creational.singleton;
public final class LazySingleton {
    private static volatile LazySingleton instance;
    private final String note;
    private LazySingleton(String note) {
        this.note = note;
    }
    public static LazySingleton getInstance() {
        return getInstance("default");
    }
    public static LazySingleton getInstance(String note) {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton(note);
                }
            }
        }
        return instance;
    }
    public String getNote() {
        return note;
    }
}
