package com.course.example.pointsbackend.points;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;
    private int length;
    private boolean palindrome = false;

    public String getData() {
        return myData;
    }

    public int getLength() {
        return length;
    }

    public boolean isPalindrome() {
        return palindrome;
    }

    public void setData(String data) {
        myData = data;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPalindrome(boolean palindrome) {
        this.palindrome = palindrome;
    }
}