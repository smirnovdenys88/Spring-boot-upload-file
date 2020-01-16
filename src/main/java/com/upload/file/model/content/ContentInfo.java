package com.upload.file.model.content;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ContentInfo {

    // According to the restriction on one video file 2GB, and segment size 4MB (about 512 segments).
    // We will use constant 1024.
    private static final int MAX_SEGMENTS_COUNT = 1024;
    private String originalVideoHash;

    private List<ContentSegment> videoSegments = new LinkedList<>();

    public ContentInfo() {
    }

    public ContentInfo(List<ContentSegment> videoSegments) {
        this.videoSegments = videoSegments;
    }

    public String getOriginalVideoHash() {
        return originalVideoHash;
    }

    public void setOriginalVideoHash(String originalVideoHash) {
        this.originalVideoHash = originalVideoHash;
    }

    public List<ContentSegment> getVideoSegments() {
        return videoSegments;
    }

    public int segmentCount() {
        return videoSegments.size();
    }

    public boolean add(ContentSegment contentSegment) {
        if (videoSegments.size() >= MAX_SEGMENTS_COUNT) {
            // TODO ideally this should not be handled here since there is no way to recover application if segment limit is reached
            // it should be handled on split and transcoding stage
            throw new RuntimeException("Cannot add more than " + MAX_SEGMENTS_COUNT + " segments");
        }
        return videoSegments.add(contentSegment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentInfo that = (ContentInfo) o;
        return Objects.equals(videoSegments, that.videoSegments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoSegments);
    }

    @Override
    public String toString() {
        return "ContentInfo{" +
                "videoSegments=" + videoSegments +
                '}';
    }
}
