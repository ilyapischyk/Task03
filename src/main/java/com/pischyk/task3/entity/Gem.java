package com.pischyk.task3.entity;

import java.time.LocalDate;

public abstract class Gem {

    private String origin;
    private VisualParameters parameters = new VisualParameters();
    private double value;
    private LocalDate date;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public VisualParameters getParameters() {
        return parameters;
    }

    public void setParameters(VisualParameters parameters) {
        this.parameters = parameters;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gem gem = (Gem) o;
        return Double.compare(gem.value, value) == 0 &&
                origin.equals(gem.origin) &&
                parameters.equals(gem.parameters) &&
                date.equals(gem.date);
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = 37 * result + (origin == null ? 0 : origin.hashCode());
        result = 37 * result + parameters.hashCode();
        long longBits = Double.doubleToLongBits(value);
        result = 37 * result + (int) (longBits - (longBits >>> 32));
        result = 37 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Gem{");
        builder.append("origin=").append(origin);
        builder.append(", parameters=").append(parameters);
        builder.append(", value=").append(value);
        builder.append(", date=").append(date);
        builder.append('}');
        return builder.toString();
    }
}
