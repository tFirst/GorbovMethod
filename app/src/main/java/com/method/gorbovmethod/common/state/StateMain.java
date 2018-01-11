package com.method.gorbovmethod.common.state;

import com.method.gorbovmethod.bean.Result;
import com.method.gorbovmethod.bean.User;

import java.io.Serializable;
import java.util.List;

public class StateMain implements Serializable {

	private Integer errorCode;
	private List<StateResult> results;
	private User user;
	private Result result;

	public Integer getErrorCode() {
		return errorCode;
	}

	public List<StateResult> getResults() {
		return results;
	}

	public User getUser() {
		return user;
	}

	public Result getResult() {
		return result;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "StateMain{" +
				"errorCode=" + errorCode +
				", results=" + results +
				", user=" + user +
				", result=" + result +
				'}';
	}
}
