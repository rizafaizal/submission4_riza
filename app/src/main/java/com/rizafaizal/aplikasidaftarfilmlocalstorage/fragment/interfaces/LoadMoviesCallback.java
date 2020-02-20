package com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.interfaces;

import com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.model.Film;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<Film> movies);
}
