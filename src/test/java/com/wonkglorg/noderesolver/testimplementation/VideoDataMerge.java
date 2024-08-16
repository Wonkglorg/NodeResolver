package com.wonkglorg.noderesolver.testimplementation;

import com.wonkglorg.noderesolver.nodes.LocalMergeNode;

public class VideoDataMerge<T extends VideoData> extends LocalMergeNode<T> {
    private VideoData videoData = new VideoData("videoNameModified", "videoTypeModified", "videoUrlModified");

    public VideoDataMerge(Class<T> inputType) {
        super(inputType);
    }

    @Override
    protected T merge(T input) {
        input.setVideoName(videoData.getVideoName());
        input.setVideoType(videoData.getVideoType());
        return input;
    }
}
