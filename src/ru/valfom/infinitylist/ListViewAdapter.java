package ru.valfom.infinitylist;

import java.util.ArrayList;

import ru.valfom.infinitylist.image.loader.ImageLoader;
import ru.valfom.infinitylist.video.Video;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater inflater;
	ArrayList<Video> videos;

	ListViewAdapter(Context context, ArrayList<Video> videos) {
		
		this.context = context;
		this.videos = videos;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		
		if (view == null)
			view = inflater.inflate(R.layout.item, parent, false);

		Video video = (Video) getItem(position);

		TextView tvTitle = ((TextView) view.findViewById(R.id.tvTitle));
		tvTitle.setText(video.getTitle());
		
		TextView tvUrl = ((TextView) view.findViewById(R.id.tvUrl));
		tvUrl.setText(video.getUrl());
		
		// Loader image - will be shown before loading image
        int loader = R.drawable.ic_launcher;
         
        ImageView ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
         
        ImageLoader imgLoader = new ImageLoader(context);
         
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(video.getPreviewUrl(), loader, ivThumbnail);

		return view;
	}
}
