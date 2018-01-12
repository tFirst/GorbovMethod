package com.method.gorbovmethod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.method.gorbovmethod.bean.User;
import com.method.gorbovmethod.common.state.StateMain;
import com.method.gorbovmethod.common.state.StateResult;
import com.method.gorbovmethod.connect.HttpRequestTaskGet;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class ResultsActivity extends AppCompatActivity {

	private static final String URL = "http://192.168.1.7:8080";

	private User user;
	private List<StateResult> stateResults;

	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		listView = findViewById(R.id.listResults);

		Init();
	}

	private void Init() {
		user = (User) getIntent().getSerializableExtra("user");

		String url;

		if (user.getRoleId() == 2) {
			url = URL +
					"/result" +
					"/getForUser" +
					"?userId=" + user.getUserId();
		} else {
			url = URL +
					"/result" +
					"/getAll";
		}

		StateMain stateMain = getDatas(url);

		if (stateMain != null) {
			if (stateMain.getErrorCode() != null &&
					stateMain.getErrorCode() != HttpStatus.GATEWAY_TIMEOUT.value()) {
				stateResults = stateMain.getResults();
			}
		}

		if (!stateResults.isEmpty()) {

			listView.setAdapter(new CustomListAdapter(this, stateResults));
		}
	}

	private StateMain getDatas(String url) {
		System.out.println(url);
		StateMain stateMain = null;
		try {
			stateMain = new HttpRequestTaskGet(url).execute().get();
			System.out.println(stateMain);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return stateMain;
	}

	@Override
	public void onBackPressed() {
		backToMain();
	}

	private void backToMain() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}
}
