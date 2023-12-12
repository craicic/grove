package org.motoc.gamelibrary.technical;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Row {
    private final List<String> values;

    public Row() {
        this.values = new ArrayList<>();
    }

    public void addValue(String column) {
        this.values.add(column);
    }

    @Override
    public String toString() {
        return "Row{" +
               "values=" + values +
               '}';
    }
}