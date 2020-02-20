package com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.interfaces;

import com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.model.Film;

import java.util.ArrayList;

public interface LoadTVsCallback {
    void preExecute();
    void postExecute(ArrayList<Film> tvShowItems);
}
