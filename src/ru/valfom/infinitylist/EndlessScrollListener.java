package ru.valfom.infinitylist;

import ru.valfom.infinitylist.grabber.Grabber;
import ru.valfom.infinitylist.grabber.OnGrabbedListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    
    private OnGrabbedListener listener;

    public EndlessScrollListener(OnGrabbedListener listener) {
    	
    	this.listener = listener;
    }
    
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    	
        if (loading) {
        	
            if (totalItemCount > previousTotal) {
            	
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
        	
            new Grabber(listener).execute(currentPage + 1);
            
            loading = true;
        }
    }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}
}