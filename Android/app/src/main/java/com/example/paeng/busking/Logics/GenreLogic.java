package com.example.paeng.busking.Logics;

import com.example.paeng.busking.Common.Constants;

public class GenreLogic {
    int bin_genre;

    public GenreLogic(int bin_genre) {
        this.bin_genre = bin_genre;
    }

    public void get_genre(String[] result) {
        result = new String[Constants.max_genre];
        int bin_target = Constants.max_binary_genre;
        for(int i = 0; i < Constants.max_genre; i++) {
            if(this.bin_genre >= bin_target) {
                this.bin_genre -= bin_target;
                result[i] = Constants.genres[i];
            } else {
                result[i] = null;
            }
        }
    }
}
