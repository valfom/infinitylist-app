package ru.valfom.infinitylist.grabber;

import java.util.ArrayList;

import ru.valfom.infinitylist.video.Video;

public interface OnGrabbedListener {

	public void updateList(ArrayList<Video> videos);
}
