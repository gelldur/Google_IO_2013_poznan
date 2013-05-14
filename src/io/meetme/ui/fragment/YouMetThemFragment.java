package io.meetme.ui.fragment;

import io.meetme.MainActivity;
import io.meetme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class YouMetThemFragment extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflateLayout(inflater, container);
	}

	private View inflateLayout(LayoutInflater inflater, ViewGroup container) {
		View layout = inflater.inflate(R.layout.fragment_you_met_them, container,
				false);
		layout.findViewById(R.id.scan).setOnClickListener(this);
		return layout;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.scan:
			((MainActivity)getActivity()).startScanActivity();
			break;

		default:
			break;
		}
		
	}
}
