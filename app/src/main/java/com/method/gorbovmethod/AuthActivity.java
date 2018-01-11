package com.method.gorbovmethod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.method.gorbovmethod.bean.User;
import com.method.gorbovmethod.common.state.StateMain;
import com.method.gorbovmethod.connect.HttpRequestTaskGet;

import org.springframework.http.HttpStatus;

import java.util.concurrent.ExecutionException;

public class AuthActivity extends AppCompatActivity {

	private String filename = "login.gorb";
	private static String URL = "http://192.168.1.7:8080";

	private User user;
	private Boolean isOnline = true;

	private Button auth;
	private EditText login;
	private EditText password;
	private Button register;
	private Button guest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth);
		Init();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		user = (User) savedInstanceState.getSerializable("user");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable("userId", user);
	}

	private void Init() {
		auth = findViewById(R.id.buttonAuth);
		auth.setEnabled(false);
		login = findViewById(R.id.login);
		password = findViewById(R.id.password);
		register = findViewById(R.id.buttonRegister);
		register.setEnabled(false);
		guest = findViewById(R.id.buttonGuest);

		addListeners();
	}

	private void addListeners() {
		login.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				if (password.getText().length() != 0) {
					auth.setEnabled(true);
					register.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		password.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				if (login.getText().length() != 0) {
					auth.setEnabled(true);
					register.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
	}

	public void auth(View view) {
		System.out.println(password.getText());
		String url = URL +
				"/auth" +
				"?name=" + login.getText() +
				"&password=" + password.getText();

		StateMain stateMain = getDatas(url);

		if (stateMain != null) {
			if (stateMain.getErrorCode() != HttpStatus.GATEWAY_TIMEOUT.value()) {
				if (stateMain.getErrorCode() == Codes.USER_NOT_EXIST) {
					auth.setEnabled(false);
					register.setBackgroundColor(getResources().getColor(R.color.colorGreen));
					guest.setBackgroundColor(getResources().getColor(R.color.colorGreen));
					Toast.makeText(this,
							"Такого пользователя не существует. Зарегистрируйтесь или войдите как Гость.",
							Toast.LENGTH_SHORT).show();
				} else if (stateMain.getErrorCode() == Codes.INVALID_PASSWORD) {
					Toast.makeText(this,
							"Неправильный логин или пароль",
							Toast.LENGTH_SHORT).show();
				}
				if (stateMain.getUser() != null) {
					user = stateMain.getUser();
					goToMainActivity();
				}
			} else {
				Toast.makeText(this,
						"Нет соединения с сервером",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private StateMain getDatas(String url) {
		System.out.println(url);
		StateMain stateMain = null;
		try {
			stateMain = new HttpRequestTaskGet(url).execute().get();
			System.out.println(stateMain);
		} catch (InterruptedException | ExecutionException e) {
			Toast.makeText(this,
					"Нет соединения с сервером",
					Toast.LENGTH_SHORT).show();
		}

		return stateMain;
	}

	private void goToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("user", user);
		intent.putExtra("isOnline", isOnline);
		startActivity(intent);
		finish();
	}

	public void onClickRegister(View view) {
		StateMain stateMain = null;

		String urlNew = URL +
				"/auth" +
				"/register" +
				"?name=" + login.getText() +
				"&password=" + password.getText();

		stateMain = getDatas(urlNew);

		if (stateMain.getErrorCode() != HttpStatus.GATEWAY_TIMEOUT.value()) {
			if (stateMain.getErrorCode() != Codes.USER_ALREADY_EXIST) {
				if (stateMain.getUser() != null) {
					user = stateMain.getUser();
					goToMainActivity();
				}
			} else {
				Toast.makeText(this,
						"Такой пользователь уже существует",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this,
					"Нет соединения с сервером",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void onClickGuest(View view) {
	}
}
