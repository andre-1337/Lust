package com.andre1337.lust.utils;

public class Tuple<T1, T2> {
    public final T1 type1;
    public final T2 type2;

    public Tuple(T1 type1, T2 type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

    public T1 getFirst() {
        return this.type1;
    }

    public T2 getSecond() {
        return this.type2;
    }
}
