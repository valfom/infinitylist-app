package ru.valfom.infinitylist.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.util.Log;

public class Grabber extends AsyncTask<Void, Void, String[]> {
	
	private String url = "http://www.infinitylist.com/";
	private int timeout = 60000;
	
	private OnGrabbedListener listener;

	public Grabber(OnGrabbedListener listener) {
		
		this.listener = listener;
	}                                                       
	
	private Document getSource() {
		
		Document doc = null;
		
		try {
			
			doc = Jsoup.connect(url).timeout(timeout).get();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return doc;
	}

	private String[] parseSource(Document doc) {
		
		Element elementPosts = doc.select("div.posts").first();
		Elements elementsVideo = elementPosts.select("div.blog-post");
		
		int i = 0;
		
		String[] videos = new String[50]; //TODO: change size
		
		for (Element video : elementsVideo) {
			
			Element elementVideo = video.select("div.video").first();
			String attr = elementVideo.attr("data-encoded-embed-code");
			String[] parts = attr.split("[//]");
			String videoId = parts[4].split("[?]")[0];
			String url = parts[2] + "/" + parts[3] + "/" + videoId;
			Log.d("LALA", url);
			
			videos[i] = url;
			
			i++;
		}
		
		return videos;
	}

	@Override
	protected String[] doInBackground(Void... params) {
		
		Document doc = getSource();
		
        return parseSource(doc);
	}
	
	protected void onPostExecute(String[] result) {
   	 
		listener.updateList(result);
    }
}
