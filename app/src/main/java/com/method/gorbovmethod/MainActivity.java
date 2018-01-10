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

import com.method.gorbovmethod.controller.PartOne;


public class MainActivity extends AppCompatActivity {

	private final String URL = "http://192.168.1.7:8080";

	private ConstraintLayout constraintLayout;
	private TextView textView;
	private Button buttonStartTest;
	private Button buttonInfo;
	private TextView textViewTime;
	private TextView textViewTimeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		constraintLayout = findViewById(R.id.infoLayout);
		textView = findViewById(R.id.textView);
		buttonStartTest = findViewById(R.id.buttonStartTest);
		buttonInfo = findViewById(R.id.buttonInfo);
		textViewTime = findViewById(R.id.textViewTime);
		textViewTimeText = findViewById(R.id.textViewTimeText);
	}

	public void onClickButtonStartTest(View view) {
		Intent intent = new Intent(this, PartOne.class);
		startActivity(intent);
		finish();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public void onClickInfo(View view) {
		buttonInfo.setVisibility(View.INVISIBLE);
		buttonStartTest.setVisibility(View.INVISIBLE);
		textViewTime.setVisibility(View.INVISIBLE);
		textViewTimeText.setVisibility(View.INVISIBLE);
		textView.setText(R.string.testInfo);
		constraintLayout.setVisibility(View.VISIBLE);
	}

	public void onClickCloseInfo(View view) {
		constraintLayout.setVisibility(View.INVISIBLE);
		buttonStartTest.setVisibility(View.VISIBLE);
		buttonInfo.setVisibility(View.VISIBLE);
		textViewTimeText.setVisibility(View.VISIBLE);
		textViewTime.setVisibility(View.VISIBLE);
	}

	private void setBestTime() {

	}
}
