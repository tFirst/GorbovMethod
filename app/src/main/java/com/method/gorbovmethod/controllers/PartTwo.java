package com.method.gorbovmethod.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.method.gorbovmethod.MainActivity;
import com.method.gorbovmethod.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


public class PartTwo extends AppCompatActivity {

	private static final Integer QUEUE_MAX_SIZE = 24;
	private static Integer COUNT_MISTAKES = 0;

	private static Queue<Button> queueBlackButtons = new LinkedList<>();
	private static Queue<Button> queueRedButtons = new LinkedList<>();
	private static Queue<Button> generalQueue = new LinkedList<>();

	private GridLayout gridLayout;
	private static DisplayMetrics displayMetrics;
	private static View.OnClickListener onClickListener;

	private static Set<Integer> indexesOfButtons = new HashSet<>();

	private static Long timeStart = System.currentTimeMillis();
	private Long diffTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing);
		Init();
	}

	private void Init() {
		gridLayout = findViewById(R.id.gridLayout);
		System.out.println(gridLayout.getContext());

		Display display = getWindowManager().getDefaultDisplay();
		displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkClickedButton((Button) v);
			}
		};

		fillGrid();
	}

	private void fillGrid() {
		List<Button> allButtons = new ArrayList<>();
		List<Button> redButtons = new ArrayList<>();

		int measure = displayMetrics.widthPixels / gridLayout.getColumnCount();

		int indexRed = 0, indexBlack = 0;
		System.out.println(measure);

		for (int i = 0; i < QUEUE_MAX_SIZE; i++) {
			Button button = new Button(this);
			button.setId(i);
			button.setLayoutParams(new ActionBar.LayoutParams(measure, measure));
			button.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
			button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
			button.setText("" + ++indexRed);
			button.setOnClickListener(onClickListener);
			redButtons.add(button);
		}

		// заполняем очередь красных кнопок в порядке убывания
		for (int i = QUEUE_MAX_SIZE - 1; i > -1; i--) {
			queueRedButtons.add(redButtons.get(i));
		}

		// заполняем очередь черных кнопок в порядке возрастания
		for (int i = 0; i < QUEUE_MAX_SIZE; i++) {
			Button button = new Button(this);
			button.setId(i);
			button.setLayoutParams(new ActionBar.LayoutParams(measure, measure));
			button.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
			button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack));
			button.setText("" + ++indexBlack);
			button.setOnClickListener(onClickListener);
			queueBlackButtons.add(button);
		}

		// заполняем основную очередь для второй части тестирования
		for (int i = 0; i < 2 * QUEUE_MAX_SIZE; i++) {
			if (i % 2 == 1) {
				Button temp = queueBlackButtons.remove();
				generalQueue.add(temp);
				allButtons.add(temp);
			} else {
				Button temp = queueRedButtons.remove();
				generalQueue.add(temp);
				allButtons.add(temp);
			}
		}

		for (int i = 0; i < 2 * QUEUE_MAX_SIZE; i++) {
			gridLayout.addView(allButtons.get(getIndexForGridLayout()));
		}
	}

	void checkClickedButton(Button button) {
		if (generalQueue.peek() != button) {
			COUNT_MISTAKES++;
		} else {
			button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
			button.setEnabled(false);
			generalQueue.remove();
			if (generalQueue.isEmpty()) {
				Long timeEnd = System.currentTimeMillis();
				diffTime = timeEnd - timeStart;
				showAlertDialog();
			}
		}
	}

	public void showAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Тестирование завершено")
				.setMessage("Количество ошибок: " +
						(COUNT_MISTAKES + getIntent().getIntExtra("mistakes", 0)) +
						"\nВремя прохождения: " +
						((diffTime + getIntent().getLongExtra("timeFirstPart", 0)) / 1000L) +
						" секунд")
				.setCancelable(false)
				.setPositiveButton("Начать заново",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								forBegin();
							}
						})
				.setNegativeButton("Выход",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								closeTest();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private Integer getIndexForGridLayout() {
		Random random = new Random();
		Integer temp = random.nextInt(2 * QUEUE_MAX_SIZE);
		return indexesOfButtons.add(temp) ? temp : getIndexForGridLayout();
	}

	private void closeTest() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void forBegin() {
		Intent intent = new Intent(this, PartOne.class);
		startActivity(intent);
	}
}
