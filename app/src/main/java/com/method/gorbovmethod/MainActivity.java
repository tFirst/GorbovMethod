package com.method.gorbovmethod;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.method.gorbovmethod.controllers.PartOne;


public class MainActivity extends AppCompatActivity {

	private ConstraintLayout constraintLayout;
	private TextView textView;
	private Button buttonStartTest;
	private Button buttonInfo;
	private TextView textViewTime;
	private TextView textViewTimeText;

	private static final String DATABASE_NAME = "tests.db";
	private static final String DATABASE_TABLE = "tests_table";
	private static final String DATABASE_CREATE =
			"create table if not exists " + DATABASE_TABLE + " ( _id integer primary key autoincrement," +
					"time INTEGER NOT NULL," +
					"mistakes INTEGER NOT NULL);";
	private static SQLiteDatabase myDatabase;

	private void createDatabase() {
		myDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		myDatabase.execSQL(DATABASE_CREATE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createDatabase();

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

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setBestTime();
	}

	private void setBestTime() {
		Cursor cursor = myDatabase.rawQuery("SELECT * FROM tests_table ORDER BY time ASC, mistakes DESC", new String[]{});
		System.out.println(cursor.getCount());
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			textViewTime.setText(cursor.getLong(1) + " секунд");
		} else {
			textViewTime.setText("Результата нет");
		}
	}

	public static SQLiteDatabase getDatabase() {
		return myDatabase;
	}
}
