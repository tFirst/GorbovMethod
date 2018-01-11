package com.method.gorbovmethod;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.method.gorbovmethod.bean.User;
import com.method.gorbovmethod.controller.PartOne;


public class MainActivity extends AppCompatActivity {

	private final String URL = "http://192.168.1.7:8080";

	private ConstraintLayout constraintLayout;
	private TextView textView;
	private Button buttonStartTest;
	private Button buttonInfo;
	private TextView textViewWelcome;
	private TextView textViewWelcomeText;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (User) getIntent().getSerializableExtra("user");

		constraintLayout = findViewById(R.id.infoLayout);
		textView = findViewById(R.id.textView);
		buttonStartTest = findViewById(R.id.buttonStartTest);
		buttonInfo = findViewById(R.id.buttonInfo);
		textViewWelcome = findViewById(R.id.textViewWelcome);
		textViewWelcome.setText(user.getName());
		textViewWelcomeText = findViewById(R.id.textViewWelcomeText);
	}

	public void onClickButtonStartTest(View view) {
		Intent intent = new Intent(this, PartOne.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public void onClickInfo(View view) {
		buttonInfo.setVisibility(View.INVISIBLE);
		buttonStartTest.setVisibility(View.INVISIBLE);
		textViewWelcome.setVisibility(View.INVISIBLE);
		textViewWelcomeText.setVisibility(View.INVISIBLE);
		textView.setText(R.string.testInfo);
		constraintLayout.setVisibility(View.VISIBLE);
	}

	public void onClickCloseInfo(View view) {
		constraintLayout.setVisibility(View.INVISIBLE);
		buttonStartTest.setVisibility(View.VISIBLE);
		buttonInfo.setVisibility(View.VISIBLE);
		textViewWelcome.setVisibility(View.VISIBLE);
		textViewWelcomeText.setVisibility(View.VISIBLE);
	}

	public void onClickViewResults(View view) {
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}
}
