package com.method.gorbovmethod.connect;

import android.os.AsyncTask;
import android.util.Log;

import com.method.gorbovmethod.common.state.StateMain;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpRequestTaskGet extends AsyncTask<Void, Void, StateMain> {

	// запрос на сервер
	private String url;

	public HttpRequestTaskGet(String url) {
		this.url = url;
	}

	// передача запроса на сервер
	@Override
	protected StateMain doInBackground(Void... params) {
		System.out.println(url);
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			return restTemplate.getForObject(url, StateMain.class);
		} catch (Exception e) {
			Log.e("HttpRequestTaskGet", "Нет подключения к серверу", e);
			StateMain state = new StateMain();
			state.setErrorCode(HttpStatus.GATEWAY_TIMEOUT.value());
			return state;
		}
	}
}
