package com.andre1337.lust.utils;

public record Tuple<T1, T2>(T1 type1, T2 type2) {

    public T1 getFirst() {
        return this.type1;
    }

    public T2 getSecond() {
        return this.type2;
    }
}
