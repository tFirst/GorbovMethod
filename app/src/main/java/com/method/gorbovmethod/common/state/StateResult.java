package com.method.gorbovmethod.common.state;

import com.method.gorbovmethod.bean.Result;
import com.method.gorbovmethod.bean.User;

import java.sql.Timestamp;

public class StateResult {

	private String userName;
	private Timestamp date;
	private Double resultTime;
	private Integer resultEval;

	public StateResult(Result result, User user) {
		this.userName = user.getName();
		this.date = result.getTestDate();
		this.resultTime = result.getResultTime();
		this.resultEval = result.getResultEval();
	}
}
