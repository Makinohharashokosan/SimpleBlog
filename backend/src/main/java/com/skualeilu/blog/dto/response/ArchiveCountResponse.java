package com.skualeilu.blog.dto.response;
public class ArchiveCountResponse {
    private int year;
    private int month;
    private long count;
    public ArchiveCountResponse(int year, int month, long count) { this.year = year; this.month = month; this.count = count; }
    // Getters
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public long getCount() { return count; }
}