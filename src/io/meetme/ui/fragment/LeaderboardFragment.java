package io.meetme.ui.fragment;

import io.meetme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeaderboardFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflateLayout(inflater, container);
	}

	private View inflateLayout(LayoutInflater inflater, ViewGroup container) {
		View layout = inflater.inflate(R.layout.fragment_they_met_you, container,
				false);
		return layout;
	}
}
