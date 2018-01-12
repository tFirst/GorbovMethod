package com.method.gorbovmethod.common.state;

import java.io.Serializable;
import java.sql.Timestamp;

public class StateResult implements Serializable {

	private String userName;
	private Timestamp date;
	private Long resultTime;
	private Integer resultEvalAtt;
	private Integer resultEvalAttMis;

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

	public Integer getResultEvalAtt() {
		return resultEvalAtt;
	}

	public Integer getResultEvalAttMis() {
		return resultEvalAttMis;
	}

	@Override
	public String toString() {
		return "StateResult{" +
				"userName='" + userName + '\'' +
				", date=" + date +
				", resultTime=" + resultTime +
				", resultEvalAtt=" + resultEvalAtt +
				", resultEvalAttMis=" + resultEvalAttMis +
				'}';
	}
}
