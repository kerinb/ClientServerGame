package com.kerinb.servergamechallenge;

import java.util.ArrayList;

public class Move {
    // Creating an object of ArrayList
    static ArrayList<Move> Data
            = new ArrayList<Move>();
    int number;
    String name;
    Move(int number, String name)
    {
        // This keyword refers to parent instance itself
        this.number = number;
        this.name = name;
    }
}
