package com.method.gorbovmethod;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.method.gorbovmethod.common.state.StateResult;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

	private List<StateResult> listData;
	private LayoutInflater layoutInflater;

	CustomListAdapter(Context aContext, List<StateResult> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(aContext);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.custom_list, null);
			holder = new ViewHolder();
			holder.countryNameView = convertView.findViewById(R.id.userName);
			holder.populationView = convertView.findViewById(R.id.userDatas);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		StateResult stateResult = this.listData.get(position);
		holder.countryNameView.setText("Пользователь - " + stateResult.getUserName());
		holder.populationView.setText(
				"Дата: " + stateResult.getDate().toString().split(" ")[0] +
				"\nВремя: " + stateResult.getDate().toString().split(" ")[1] +
				"\nЗатраченное время: " + stateResult.getResultTime() + "с " +
				"\nОценка переключения внимания: " + stateResult.getResultEvalAtt() + "%" +
				"\nОценка ошибок переключения внимания: " + stateResult.getResultEvalAttMis() + "%");

		return convertView;
	}

	static class ViewHolder {
		TextView countryNameView;
		TextView populationView;
	}

}
