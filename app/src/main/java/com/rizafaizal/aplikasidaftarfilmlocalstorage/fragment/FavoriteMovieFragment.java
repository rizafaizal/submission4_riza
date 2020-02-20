package com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rizafaizal.aplikasidaftarfilmlocalstorage.R;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.db.MovieHelper;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.interfaces.LoadMoviesCallback;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.helper.MovieMappingHelper;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.adapter.MoviesAdapter;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.model.Film;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback {

    private MoviesAdapter adapter;
    private ProgressBar progressBarMoviesFavorit;
    private MovieHelper movieHelper;

    private ArrayList<Film> items = new ArrayList<>();
    private RecyclerView rvMovies;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        rvMovies = view.findViewById(R.id.rc_movies_favorite);
        rvMovies.setHasFixedSize(true);

        progressBarMoviesFavorit = view.findViewById(R.id.progressbarMovieFavorite);

        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();


        showRecyclerView();

        new FavoriteMovieFragment.LoadMoviesAsync(movieHelper, this).execute();

        return view;
    }

    private void showRecyclerView() {
        adapter = new MoviesAdapter();
        adapter.notifyDataSetChanged();
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        progressBarMoviesFavorit.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Film> movies) {
        progressBarMoviesFavorit.setVisibility(View.INVISIBLE);
        adapter.setData(movies);
        items.addAll(movies);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMoviesAsync  extends AsyncTask<Void, Void, ArrayList<Film>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
            Cursor dataCursor = weakMovieHelper.get().queryAll();
            return MovieMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Film> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

}
