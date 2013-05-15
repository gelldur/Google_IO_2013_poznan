package io.meetme.ui.fragment;

import io.meetme.MeetMeApplication;
import io.meetme.R;
import io.meetme.database.DatabaseManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class LeaderboardFragment extends Fragment implements OnClickListener {

	private PointsLoader pointsLoader;
	private Thread threadPointSender;

	/**
	 * [{"username": "Delfin", "points": 190, "id": "934503aq123312"},
	 * {"username": "Ivan", "points": 100, "id": "903aqwe312"}, {"username":
	 * "VaIer", "points": 20, "id": "123aqwe312"}]
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflateLayout(inflater, container);
	}

	private View inflateLayout(LayoutInflater inflater, ViewGroup container) {
		View layout = inflater.inflate(R.layout.fragment_leaderboard,
				container, false);

		return layout;
	}

	@Override
	public void onResume() {
		super.onResume();

		pointsLoader = new PointsLoader();
		pointsLoader.execute();

	}

	@Override
	public void onPause() {
		super.onPause();
		pointsLoader.cancel(true);
	}

	private class PointsLoader extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			DatabaseManager databaseManager = DatabaseManager.getInstance();

			return databaseManager.getPoints();
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}

	}

	@Override
	public void onClick(View v) {

		if (threadPointSender == null || threadPointSender.isAlive() == false) {
			threadPointSender = new Thread(new MeetMeApplication.StatSender());
			threadPointSender.start();
		} else {
			Toast.makeText(getActivity(), "Yeah i'm sending...",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (threadPointSender != null) {
			threadPointSender.interrupt();
			threadPointSender = null;
		}
	}
}
