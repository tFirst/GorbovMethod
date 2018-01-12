package com.method.gorbovmethod.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.method.gorbovmethod.Codes;
import com.method.gorbovmethod.MainActivity;
import com.method.gorbovmethod.R;
import com.method.gorbovmethod.bean.Result;
import com.method.gorbovmethod.bean.User;
import com.method.gorbovmethod.common.state.StateMain;
import com.method.gorbovmethod.connect.HttpRequestTaskPost;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class PartTwo extends AppCompatActivity {

	private static final String URL = "http://192.168.1.7:8080";

	private static final Integer QUEUE_MAX_SIZE = 24;
	private static Integer COUNT_MISTAKES = 0;
	private static Integer STAGE_COUNT_MISTAKES = 0;

	private static Queue<Button> queueBlackButtons = new LinkedList<>();
	private static Queue<Button> queueRedButtons = new LinkedList<>();
	private static Queue<Button> generalQueue = new LinkedList<>();

	private static List<Button> blackButtons = new ArrayList<>();
	private static List<Button> redButtons = new ArrayList<>();

	private GridLayout gridLayout;
	private static DisplayMetrics displayMetrics;
	private static View.OnClickListener onClickListener;

	private static Set<Integer> indexesOfButtons = new HashSet<>();

	private static Long timeStart = System.currentTimeMillis();
	private Long diffTime = 0L;
	private Long timeDiffQuit = 0L;
	private Long time;

	private static Long stage21Black = 0L;
	private static Long stage22Black = 0L;
	private static Long stage23Black = 0L;
	private static Long stage24Black = 0L;
	private static Long stage25Black = 0L;

	private static Integer stage21BlackMistakes = 0;
	private static Integer stage22BlackMistakes = 0;
	private static Integer stage23BlackMistakes = 0;
	private static Integer stage24BlackMistakes = 0;
	private static Integer stage25BlackMistakes = 0;

	private static Long stage21Red = 0L;
	private static Long stage22Red = 0L;
	private static Long stage23Red = 0L;
	private static Long stage24Red = 0L;
	private static Long stage25Red = 0L;

	private static Integer stage21RedMistakes = 0;
	private static Integer stage22RedMistakes = 0;
	private static Integer stage23RedMistakes = 0;
	private static Integer stage24RedMistakes = 0;
	private static Integer stage25RedMistakes = 0;

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

		COUNT_MISTAKES += getIntent().getIntExtra("mistakes", 0);
		gridLayout = findViewById(R.id.gridLayout);
		gridLayout.removeAllViews();

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
			temp.setId(QUEUE_MAX_SIZE - i);
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
		System.out.println(generalQueue.peek().getText());
		if (generalQueue.peek() != button) {
			COUNT_MISTAKES++;
			STAGE_COUNT_MISTAKES++;
		} else {
			endStage = System.nanoTime() / 1000000000;
			switch(button.getId()) {
				case 4:
					if (blackButtons.contains(button)) {
						stage21Black = endStage - startStage;
						stage21BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage21Red = endStage - startStage;
						stage21RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 9:
					if (blackButtons.contains(button)) {
						stage22Black = endStage - startStage;
						stage22BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage22Red = endStage - startStage;
						stage22RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 14:
					if (blackButtons.contains(button)) {
						stage23Black = endStage - startStage;
						stage23BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage23Red = endStage - startStage;
						stage23RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 19:
					if (blackButtons.contains(button)) {
						stage24Black = endStage - startStage;
						stage24BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage24Red = endStage - startStage;
						stage24RedMistakes = STAGE_COUNT_MISTAKES;
					}
					startStage = endStage;
					STAGE_COUNT_MISTAKES = 0;
					break;
				case 23:
					if (blackButtons.contains(button)) {
						stage25Black = endStage - startStage;
						stage25BlackMistakes = STAGE_COUNT_MISTAKES;
					} else {
						stage25Red = endStage - startStage;
						stage25RedMistakes = STAGE_COUNT_MISTAKES;
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
		time = (((diffTime - timeDiffQuit) +
				getIntent().getLongExtra("timeFirstPart", 0)) / 1000L);
		saveResults();
		builder.setTitle("Тестирование завершено")
				.setMessage("Количество ошибок: " + (COUNT_MISTAKES +
						"\nВремя прохождения: " + time +
						" секунд"))
				.setCancelable(false)
				.setPositiveButton("Начать заново",
						(dialog, id) -> {
							dialog.cancel();
							forBegin();
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
		gridLayout.removeAllViews();
		finish();
	}

	private void forBegin() {
		clear();
		Intent intent = new Intent(this, PartOne.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}

	private void saveResults() {
		Result result = new Result();
		result.setUserId(user.getUserId());
		result.setTestDate(new Timestamp(System.currentTimeMillis()));
		result.setResultTime(time);
		result.setResultEvalAtt(0);
		result.setResultEvalAttMis(0);

		result = setStageTimes(result);

		String url = URL +
				"/result" +
				"/save" +
				"?userId=" + user.getUserId();

		StateMain stateMain = getDatas(url, result);

		if (stateMain != null) {
			if (stateMain.getErrorCode() == Codes.SUCCESS) {
				Log.i("SAVE", "Results saved success");
			} else {
				Log.i("SAVE", "Results not saved");
			}
		} else {
			Log.i("STATE MAIN", "StateMain is null");
		}
	}

	private Result setStageTimes(Result result) {
		result.setStage11Black(getIntent().getLongExtra("stage11Black", 0));
		result.setStage11BlackMistakes(getIntent().getIntExtra("stage11BlackMistakes", 0));
		result.setStage12Black(getIntent().getLongExtra("stage12Black", 0));
		result.setStage12BlackMistakes(getIntent().getIntExtra("stage12BlackMistakes", 0));
		result.setStage13Black(getIntent().getLongExtra("stage13Black", 0));
		result.setStage13BlackMistakes(getIntent().getIntExtra("stage13BlackMistakes", 0));
		result.setStage14Black(getIntent().getLongExtra("stage14Black", 0));
		result.setStage14BlackMistakes(getIntent().getIntExtra("stage14BlackMistakes", 0));
		result.setStage15Black(getIntent().getLongExtra("stage15Black", 0));
		result.setStage15BlackMistakes(getIntent().getIntExtra("stage15BlackMistakes", 0));
		result.setStage11Red(getIntent().getLongExtra("stage11Red", 0));
		result.setStage11RedMistakes(getIntent().getIntExtra("stage11RedMistakes", 0));
		result.setStage12Red(getIntent().getLongExtra("stage12Red", 0));
		result.setStage12RedMistakes(getIntent().getIntExtra("stage12RedMistakes", 0));
		result.setStage13Red(getIntent().getLongExtra("stage13Red", 0));
		result.setStage13RedMistakes(getIntent().getIntExtra("stage13RedMistakes", 0));
		result.setStage14Red(getIntent().getLongExtra("stage14Red", 0));
		result.setStage14RedMistakes(getIntent().getIntExtra("stage14RedMistakes", 0));
		result.setStage15Red(getIntent().getLongExtra("stage15Red", 0));
		result.setStage15RedMistakes(getIntent().getIntExtra("stage15RedMistakes", 0));

		result.setStage21Black(stage21Black);
		result.setStage21BlackMistakes(stage21BlackMistakes);
		result.setStage22Black(stage22Black);
		result.setStage22BlackMistakes(stage22BlackMistakes);
		result.setStage23Black(stage23Black);
		result.setStage23BlackMistakes(stage23BlackMistakes);
		result.setStage24Black(stage24Black);
		result.setStage24BlackMistakes(stage24BlackMistakes);
		result.setStage25Black(stage25Black);
		result.setStage25BlackMistakes(stage25BlackMistakes);
		result.setStage21Red(stage21Red);
		result.setStage21RedMistakes(stage21RedMistakes);
		result.setStage22Red(stage22Red);
		result.setStage22RedMistakes(stage22RedMistakes);
		result.setStage23Red(stage23Red);
		result.setStage23RedMistakes(stage23RedMistakes);
		result.setStage24Red(stage24Red);
		result.setStage24RedMistakes(stage24RedMistakes);
		result.setStage25Red(stage25Red);
		result.setStage25RedMistakes(stage25RedMistakes);

		return result;
	}

	private StateMain getDatas(String url, Result result) {
		System.out.println(url);
		StateMain stateMain = null;
		try {
			stateMain = new HttpRequestTaskPost(url, result).execute().get();
			System.out.println(stateMain);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return stateMain;
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
		showAlertDialog();
	}
}
