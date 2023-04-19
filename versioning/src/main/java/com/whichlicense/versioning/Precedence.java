package com.whichlicense.versioning;

public interface Precedence<T> {
    int LESS_THAN = -1;
    int EQUAL_TO = 0;
    int GREATER_THAN = 1;


    int precedence(T other);
}
