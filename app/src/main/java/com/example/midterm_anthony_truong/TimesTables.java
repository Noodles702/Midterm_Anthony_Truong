package com.example.midterm_anthony_truong;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TimesTables {
    private static final Set<Integer> generatedNumbers = new LinkedHashSet<>();

    private TimesTables() {}
    public static void addNumber(int n) {
        generatedNumbers.add(n);
    }

    public static List<Integer> getNumbers() {
        return new ArrayList<>(generatedNumbers);
    }
}
