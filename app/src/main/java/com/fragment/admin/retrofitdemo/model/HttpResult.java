package com.fragment.admin.retrofitdemo.model;

/**
 * Created by admin on 2017/1/11.
 */
public class HttpResult<T> {
    private int count;
    private int start;
    private int total;
    private String title;
    private T subjects;

    public HttpResult() {
    }

    public HttpResult(int count, int start, int total, String title, T subjects) {
        this.count = count;
        this.start = start;
        this.total = total;
        this.title = title;
        this.subjects = subjects;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != subjects) {
            sb.append(" subjects:" + subjects.toString());
        }
        return sb.toString();
    }
}
