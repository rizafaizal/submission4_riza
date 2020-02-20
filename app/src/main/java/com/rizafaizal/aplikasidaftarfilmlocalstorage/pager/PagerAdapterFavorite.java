package com.rizafaizal.aplikasidaftarfilmlocalstorage.pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rizafaizal.aplikasidaftarfilmlocalstorage.R;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.FavoriteMovieFragment;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.FavoriteTVShowFragment;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.MoviesFragment;
import com.rizafaizal.aplikasidaftarfilmlocalstorage.fragment.TvshowFragment;

public class PagerAdapterFavorite extends FragmentPagerAdapter {

    private final Context mContext;

    public PagerAdapterFavorite(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[] {
            R.string.tab_favorite_movie,
            R.string.tab_favorite_tvshow
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;
            case 1:
                fragment = new FavoriteTVShowFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

}
