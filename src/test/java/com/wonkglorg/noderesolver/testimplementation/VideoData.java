package com.wonkglorg.noderesolver.testimplementation;

public class VideoData {
    private String videoName;
    private String videoType;
    private String videoUrl;

    public VideoData(String videoName, String videoType, String videoUrl) {
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
