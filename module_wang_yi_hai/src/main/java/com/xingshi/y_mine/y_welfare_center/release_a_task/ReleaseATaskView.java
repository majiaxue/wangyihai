package com.xingshi.y_mine.y_welfare_center.release_a_task;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;

public interface ReleaseATaskView extends IView {
    void chooseTime(String time);

    void getType(int id);

    void loadAdapter(ReleaseATaskTabAdapter releaseATaskTabAdapter);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void showHeader(String bitmap, Uri uri);
}
