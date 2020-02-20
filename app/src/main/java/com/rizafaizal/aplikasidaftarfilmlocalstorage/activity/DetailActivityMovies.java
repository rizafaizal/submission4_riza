package com.rizafaizal.aplikasidaftarfilmlocalstorage.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.R;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.db.MovieHelper;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.model.Film;

import static com.rizafaizal.aplikasidaftarfilmlocalstorage.db.DatabaseContract.MovieColoumns.OVERVIEW_MV;
import static com.rizafaizal.aplikasidaftarfilmlocalstorage.db.DatabaseContract.MovieColoumns.POSTER_MV;
import static com.rizafaizal.aplikasidaftarfilmlocalstorage.db.DatabaseContract.MovieColoumns.RELEASE_MV;
import static com.rizafaizal.aplikasidaftarfilmlocalstorage.db.DatabaseContract.MovieColoumns.SCORE_MV;
import static com.rizafaizal.aplikasidaftarfilmlocalstorage.db.DatabaseContract.MovieColoumns.TITLE_MV;


public class DetailActivityMovies extends AppCompatActivity implements View.OnClickListener {
    ImageView imgFilm;
    TextView txtJudulFilm, txtReleaseFilm, txtScoreFilm, txtDescFilm;
    private ProgressBar progressBar;
    private Button btnFavorite;


    public static final String EXTRA_MOVIE = "extra_movie";

    MovieHelper movieHelper;
    int action;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        imgFilm = findViewById(R.id.img_photo_detail);
        txtJudulFilm = findViewById(R.id.txt_judul_detail);
        txtReleaseFilm = findViewById(R.id.txt_release_detail);
        txtScoreFilm = findViewById(R.id.txt_score_detail);
        txtDescFilm = findViewById(R.id.txt_desc_detail);
        progressBar = findViewById(R.id.progressBar_detail);
        btnFavorite = findViewById(R.id.btn_favMovies_detail);
        btnFavorite.setOnClickListener(this);

        showLoading(true);

        Film films = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (films != null) {
            showLoading(false);
            txtJudulFilm.setText(films.getTitle());
            txtReleaseFilm.setText(films.getRelease());
            txtScoreFilm.setText(films.getScore());
            txtDescFilm.setText(films.getOverview());
            Glide.with(this)
                    .load(films.getPoster())
                    .apply(new RequestOptions().override(950, 900))
                    .into(imgFilm);

            movieHelper = MovieHelper.getInstance(getApplicationContext());
            movieHelper.open();

            String movieTitle = films.getTitle();
            Log.d("test", "onCreate: "+movieTitle+movieHelper.getOne(movieTitle));

            if (movieHelper.getOne(movieTitle)){
                //delete
                btnFavorite.setText(getString(R.string.favorite_delete));
                btnFavorite.setBackgroundColor(Color.RED);
                action = 0;
            } else if (!movieHelper.getOne(movieTitle)){
                //insert
                btnFavorite.setText(getString(R.string.favorite_add));
                btnFavorite.setBackgroundColor(Color.BLUE);
                action = 1;
            }
        }

        //Nama Bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.bar_detail_film));
        ab.setSubtitle(films.getTitle());
        ab.setDisplayHomeAsUpEnabled(true);
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        Film movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (action==1){

            ContentValues values = new ContentValues();
            values.put(TITLE_MV, movie.getTitle());
            values.put(RELEASE_MV, movie.getRelease());
            values.put(OVERVIEW_MV, movie.getOverview());
            values.put(SCORE_MV, movie.getScore());
            values.put(POSTER_MV, movie.getPoster());
            long result = MovieHelper.insert(values);

            btnFavorite.setText(getString(R.string.favorite_delete));
            btnFavorite.setBackgroundColor(Color.RED);
            action = 0;

            if (result > 0) {
                Toast.makeText(DetailActivityMovies.this, getString(R.string.favorite_success)+" "+getString(R.string.favorite_add), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivityMovies.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(DetailActivityMovies.this, getString(R.string.favorite_failed)+" "+getString(R.string.favorite_add), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivityMovies.this, MainActivity.class);
                startActivity(intent);
            }

        }else if(action == 0){

            long result = movieHelper.deleteByTitle(movie.getTitle());
            if (result > 0) {
                Toast.makeText(DetailActivityMovies.this, getString(R.string.favorite_success)+" "+getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivityMovies.this, MainActivity.class);
                startActivity(intent);
                action = 1;
                btnFavorite.setText(getString(R.string.favorite_add));
                btnFavorite.setBackgroundColor(Color.BLUE);
            } else {
                Toast.makeText(DetailActivityMovies.this, getString(R.string.favorite_failed)+" "+getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivityMovies.this, MainActivity.class);
                startActivity(intent);
            }

        }

    }
}
