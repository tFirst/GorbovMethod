package com.method.gorbovmethod.bean;


import java.sql.Timestamp;

public class Result {

    private Integer id;
    private Integer userId;
    private Timestamp testDate;
    private Double resultTime;
    private Integer resultEval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getTestDate() {
        return testDate;
    }

    public void setTestDate(Timestamp testDate) {
        this.testDate = testDate;
    }

    public Double getResultTime() {
        return resultTime;
    }

    public void setResultTime(Double resultTime) {
        this.resultTime = resultTime;
    }

    public Integer getResultEval() {
        return resultEval;
    }

    public void setResultEval(Integer resultEval) {
        this.resultEval = resultEval;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", userId=" + userId +
                ", testDate=" + testDate +
                ", resultTime=" + resultTime +
                ", resultEval=" + resultEval +
                '}';
    }
}
