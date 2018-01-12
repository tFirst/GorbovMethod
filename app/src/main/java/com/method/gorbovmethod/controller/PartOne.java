package com.method.gorbovmethod.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.method.gorbovmethod.bean.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


public class PartOne extends AppCompatActivity {

	private static final Integer QUEUE_MAX_SIZE = 24;
	private static Integer COUNT_MISTAKES = 0;
	private static Integer STAGE_COUNT_MISTAKES = 0;

	private static Queue<Button> queueBlackButtons = new LinkedList<>();
	private static Queue<Button> queueRedButtons = new LinkedList<>();
	private static Queue<Button> generalQueue = new LinkedList<>();

	List<Button> blackButtons = new ArrayList<>();
	List<Button> redButtons = new ArrayList<>();

	private GridLayout gridLayout;
	private static DisplayMetrics displayMetrics;
	private static View.OnClickListener onClickListener;

	private static Set<Integer> indexesOfButtons = new HashSet<>();

	private static Long timeStart = System.currentTimeMillis();
	private static Long diffTime;
	private static Long timeDiffQuit = 0L;

	private static Long stage11Black = 0L;
	private static Long stage12Black = 0L;
	private static Long stage13Black = 0L;
	private static Long stage14Black = 0L;
	private static Long stage15Black = 0L;

	private static Integer stage11BlackMistakes = 0;
	private static Integer stage12BlackMistakes = 0;
	private static Integer stage13BlackMistakes = 0;
	private static Integer stage14BlackMistakes = 0;
	private static Integer stage15BlackMistakes = 0;

	private static Long stage11Red = 0L;
	private static Long stage12Red = 0L;
	private static Long stage13Red = 0L;
	private static Long stage14Red = 0L;
	private static Long stage15Red = 0L;

	private static Integer stage11RedMistakes = 0;
	private static Integer stage12RedMistakes = 0;
	private static Integer stage13RedMistakes = 0;
	private static Integer stage14RedMistakes = 0;
	private static Integer stage15RedMistakes = 0;

	private static Long startStage = System.nanoTime() / 1000000000;
	private static Long endStage;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testing);
		Init();
	}

	private void Init() {
		user = (User) getIntent().getSerializableExtra("user");

		gridLayout = findViewById(R.id.gridLayout);

		Display display = getWindowManager().getDefaultDisplay();
		displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		onClickListener = v -> checkClickedButton((Button) v);

		fillGrid();
	}

	private void fillGrid() {
		List<Button> allButtons = new ArrayList<>();

		int measure = displayMetrics.widthPixels / gridLayout.getColumnCount();

		int indexRed = 0, indexBlack = 0;

		for (int i = 0; i < QUEUE_MAX_SIZE; i++) {
			Button button = new Button(this);
			button.setLayoutParams(new ActionBar.LayoutParams(measure, measure));
			button.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
			button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
			button.setText("" + ++indexRed);
			button.setOnClickListener(onClickListener);
			redButtons.add(button);
		}

		// заполняем очередь красных кнопок в порядке убывания
		for (int i = QUEUE_MAX_SIZE - 1; i > -1; i--) {
			Button temp = redButtons.get(i);
			temp.setId(QUEUE_MAX_SIZE - 1 - i);
			queueRedButtons.add(temp);
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
			blackButtons.add(button);
			queueBlackButtons.add(button);
		}

		// заполняем основную очередь для первой части тестирования
		for (int i = 0; i < 2 * QUEUE_MAX_SIZE; i++) {
			if (i < QUEUE_MAX_SIZE) {
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

	@SuppressLint("ResourceType")
	void checkClickedButton(Button button) {
		System.out.println(generalQueue.peek().getText());
		if (generalQueue.peek() != button) {
			COUNT_MISTAKES++;
			STAGE_COUNT_MISTAKES++;
		} else {
			switch(button.getId()) {
				case 4:
					endStage = System.nanoTime() / 1000000000;
					if (blackButtons.contains(button)) {
						System.out.println((endStage - startStage));
						stage11Black = endStage - startStage;
						stage11BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage11Red = endStage - startStage;
						stage11RedMistakes = STAGE_COUNT_MISTAKES;
					}
					System.out.println(stage11Black + " " + stage11Red);
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 9:
					endStage = System.nanoTime() / 1000000000;
					if (blackButtons.contains(button)) {
						stage12Black = endStage - startStage;
						stage12BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage12Red = endStage - startStage;
						stage12RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 14:
					endStage = System.nanoTime() / 1000000000;
					if (blackButtons.contains(button)) {
						stage13Black = endStage - startStage;
						stage13BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage13Red = endStage - startStage;
						stage13RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 19:
					endStage = System.nanoTime() / 1000000000;
					if (blackButtons.contains(button)) {
						stage14Black = endStage - startStage;
						stage14BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage14Red = endStage - startStage;
						stage14RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 23:
					endStage = System.nanoTime() / 1000000000;
					if (blackButtons.contains(button)) {
						stage15Black = endStage - startStage;
						stage15BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage15Red = endStage - startStage;
						stage15RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
			}
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
		builder.setTitle("Первая часть тестирования завершена")
				.setMessage("Количество ошибок: " + COUNT_MISTAKES)
				.setCancelable(false)
				.setPositiveButton("Далее",
						(dialog, id) -> {
							dialog.cancel();
							nextPart();
						})
				.setNegativeButton("Выход",
						(dialog, id) -> {
							dialog.cancel();
							closeTest();
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
		clear();
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}

	private void nextPart() {
		clear();
		Intent intent = new Intent(this, PartTwo.class);
		intent.putExtra("timeFirstPart", (diffTime != null && timeDiffQuit != null) ? diffTime - timeDiffQuit : 0);
		intent.putExtra("mistakes", COUNT_MISTAKES);
		intent.putExtra("user", user);
		intent.putExtra("stage11Black", stage11Black);
		intent.putExtra("stage12Black", stage12Black);
		intent.putExtra("stage13Black", stage13Black);
		intent.putExtra("stage14Black", stage14Black);
		intent.putExtra("stage15Black", stage15Black);
		intent.putExtra("stage11Red", stage11Red);
		intent.putExtra("stage12Red", stage12Red);
		intent.putExtra("stage13Red", stage13Red);
		intent.putExtra("stage14Red", stage14Red);
		intent.putExtra("stage15Red", stage15Red);
		intent.putExtra("stage11BlackMistakes", stage11BlackMistakes);
		intent.putExtra("stage12BlackMistakes", stage12BlackMistakes);
		intent.putExtra("stage13BlackMistakes", stage13BlackMistakes);
		intent.putExtra("stage14BlackMistakes", stage14BlackMistakes);
		intent.putExtra("stage15BlackMistakes", stage15BlackMistakes);
		intent.putExtra("stage11RedMistakes", stage11RedMistakes);
		intent.putExtra("stage12RedMistakes", stage12RedMistakes);
		intent.putExtra("stage13RedMistakes", stage13RedMistakes);
		intent.putExtra("stage14RedMistakes", stage14RedMistakes);
		intent.putExtra("stage15RedMistakes", stage15RedMistakes);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		openQuitDialog();
	}

	private void openQuitDialog() {
		final Long timeQuitStart = System.currentTimeMillis();
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
		quitDialog
				.setTitle("Выход")
				.setMessage("Вы уверены, что хотите выйти в меню без сохранения результатов?");

		quitDialog.setPositiveButton("Да", (dialog, which) -> {
			dialog.cancel();
			closeTest();
		});

		quitDialog.setNegativeButton("Нет", (dialog, which) -> {
			Long timeQuitEnd = System.currentTimeMillis();
			timeDiffQuit = timeQuitEnd - timeQuitStart;
			dialog.cancel();
		});

		quitDialog.show();
	}

	private void clear() {
		COUNT_MISTAKES = 0;
		generalQueue.clear();
		indexesOfButtons.clear();
		queueBlackButtons.clear();
		queueRedButtons.clear();
		redButtons.clear();
		blackButtons.clear();
	}

	public void onClickEnd(View view) {
		nextPart();
	}
}
