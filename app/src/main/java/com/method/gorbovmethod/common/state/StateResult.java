package com.method.gorbovmethod.common.state;

import java.io.Serializable;
import java.sql.Timestamp;

public class StateResult implements Serializable {

	private String userName;
	private Timestamp date;
	private Long resultTime;
	private Integer resultEval;

	public StateResult() {
	}

	public String getUserName() {
		return userName;
	}

	public Timestamp getDate() {
		return date;
	}

	public Long getResultTime() {
		return resultTime;
	}

	public Integer getResultEval() {
		return resultEval;
	}

	@Override
	public String toString() {
		return "StateResult{" +
				"userName='" + userName + '\'' +
				", date=" + date +
				", resultTime=" + resultTime +
				", resultEval=" + resultEval +
				'}';
	}
}
