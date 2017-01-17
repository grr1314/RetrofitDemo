package com.fragment.admin.retrofitdemo.model;

/**
 * Created by admin on 2017/1/17.
 */
public class Rating {
    private String average;
    private String stars;
    private String min;
    private String max;

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
