package com.example.tarea3.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//objeto comic serializable con sus constructore y metodos getter y setter
public class Comic{

    @SerializedName("num")
    @Expose
    private int num;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("img")
    @Expose
    private String img;

        public Comic(int num, String day, String month, String year, String title, String img) {
            this.num = num;
            this.day = day;
            this.month = month;
            this.year = year;
            this.title = title;
            this.img = img;
        }

        public Comic(int num, String day, String month, String year, String title) {
            this.num = num;
            this.day = day;
            this.month = month;
            this.year = year;
            this.title = title;
         }

        public Comic() {
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
}
