package com.upload.file.model.content;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class ContentSegment {
    private int segmentNumber;
    private int durationSeconds;
    private byte[] segmentHash;
    private String stringSegmentHash;
    private Quality quality;

    public ContentSegment() {
    }

    public ContentSegment(int segmentNumber, int durationSeconds, byte[] segmentHash, Quality quality) {
        this.segmentNumber = segmentNumber;
        this.durationSeconds = durationSeconds;
        this.segmentHash = segmentHash;
        this.quality = quality;
        this.stringSegmentHash = segmentHash.toString();
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    @JsonIgnore
    public byte[] getSegmentHash() {
        return segmentHash;
    }

    public String getStringSegmentHash() {
        return stringSegmentHash;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentSegment that = (ContentSegment) o;
        return segmentNumber == that.segmentNumber &&
                durationSeconds == that.durationSeconds &&
                Objects.equals(segmentHash, that.segmentHash) &&
                quality == that.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(segmentNumber, durationSeconds, segmentHash, quality);
    }

    @Override
    public String toString() {
        return "ContentSegment{" +
                "segmentNumber=" + segmentNumber +
                ", durationSeconds=" + durationSeconds +
                ", segmentHash=" + segmentHash +
                ", quality=" + quality +
                '}';
    }
}
