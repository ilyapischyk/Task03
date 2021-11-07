package com.pischyk.task3.builder;

import com.pischyk.task3.entity.Gem;
import com.pischyk.task3.exception.GemException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGemBuilder {
    protected Set<Gem> gems;

    public AbstractGemBuilder() {
        gems = new HashSet<>();
    }

    public Set<Gem> getGems() {
        return gems;
    }

    public abstract void buildSetGems(String filename) throws GemException;
}
