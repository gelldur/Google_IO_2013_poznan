package io.meetme.adapter;

import io.meetme.R;
import io.meetme.ui.fragment.LeaderboardFragment;
import io.meetme.ui.fragment.TheyMetYouFragment;
import io.meetme.ui.fragment.YouMetThemFragment;

import java.util.Locale;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipePagerAdapter extends FragmentPagerAdapter {

	private static final int PAGE_COUNT = 3;
	public static final int PAGE_THEY_MET_YOU = 0;
	public static final int PAGE_YOU_MET_THEM = 1;
	public static final int PAGE_LEADERBOARD = 2;

	private final Activity activity;

	public SwipePagerAdapter(Activity feedActivity,
			FragmentManager fragmentManager) {
		super(fragmentManager);
		activity = feedActivity;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = getPageFragment(position);
		return fragment;
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case PAGE_THEY_MET_YOU:
			return activity.getString(R.string.tab_they_met_you).toUpperCase(l);
		case PAGE_YOU_MET_THEM:
			return activity.getString(R.string.tab_you_met_them).toUpperCase(l);
		case PAGE_LEADERBOARD:
			return activity.getString(R.string.tab_leaderboard).toUpperCase(l);
		}
		return null;
	}

	private Fragment getPageFragment(int position) {
		switch (position) {
		case PAGE_THEY_MET_YOU:
			return new TheyMetYouFragment();
		case PAGE_YOU_MET_THEM:
			return new YouMetThemFragment();
		case PAGE_LEADERBOARD:
			return new LeaderboardFragment();
		}
		return null;
	}
}