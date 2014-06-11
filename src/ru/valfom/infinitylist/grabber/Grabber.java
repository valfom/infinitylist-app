package ru.valfom.infinitylist.grabber;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.valfom.infinitylist.video.Video;
import android.os.AsyncTask;

public class Grabber extends AsyncTask<Integer, Void, ArrayList<Video>> {
	
	private String url = "http://www.infinitylist.com/page/%d/";
	private int timeout = 60000;
	
	private OnGrabbedListener listener;

	public Grabber(OnGrabbedListener listener) {
		
		this.listener = listener;
	}                                                       
	
	private Document getSource(int pageNumber) {
		
		Document doc = null;
		
		try {
			
			doc = Jsoup.connect(String.format(url, pageNumber)).timeout(timeout).get();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return doc;
	}

	private ArrayList<Video> parseSource(Document doc) {
		
		Element elementPosts = doc.select("div.posts").first();
		Elements elementsVideo = elementPosts.select("div.blog-post");
		
		ArrayList<Video> videos = new ArrayList<Video>();
		
		for (Element video : elementsVideo) {
			
			Video v = new Video();
			
			String title = video.attr("data-post-title");
			v.setTitle(title);
			Element elementVideo = video.select("div.video").first();
			String attr = elementVideo.attr("data-encoded-embed-code");
			String[] parts = attr.split("[//]");
			String videoId = parts[4].split("[?]")[0];
			String url = "http://" + parts[2] + "/" + parts[3] + "/" + videoId;
			v.setUrl(url);

			videos.add(v);
		}
		
		return videos;
	}

	@Override
	protected ArrayList<Video> doInBackground(Integer... params) {
		
		int pageNumber = 1;
		
		if (params.length > 0) pageNumber = params[0]; 
		
		Document doc = getSource(pageNumber);
		
        return parseSource(doc);
	}
	
	protected void onPostExecute(ArrayList<Video> videos) {
   	 
		listener.updateList(videos);
    }
}
