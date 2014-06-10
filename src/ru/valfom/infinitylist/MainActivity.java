package ru.valfom.infinitylist;

import ru.valfom.infinitylist.grabber.Grabber;
import ru.valfom.infinitylist.grabber.OnGrabbedListener;
import ru.valfom.infinitylist.player.Player;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.container, ListViewFragment.newInstance(position + 1))
        .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section_latest_videos);
                break;
            case 2:
                mTitle = getString(R.string.title_section_weeks_hyped);
                break;
            case 3:
                mTitle = getString(R.string.title_section_months_hyped);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Fragment containing list view.
     */
    public static class ListViewFragment extends ListFragment implements OnGrabbedListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ListViewFragment newInstance(int sectionNumber) {
        	
        	ListViewFragment fragment = new ListViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            
            return fragment;
        }

        @Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			
			super.onListItemClick(l, v, position, id);
			
			TextView tv = (TextView) v.findViewById(android.R.id.text1);
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tv.getText().toString())));
			
			Intent intent = new Intent(getActivity(), Player.class);
			intent.putExtra("url", tv.getText().toString());
			startActivity(intent);
		}

		public ListViewFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            return rootView;
        }
        
        @Override
        public void onAttach(Activity activity) {
        	
            super.onAttach(activity);
            
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {

			super.onViewCreated(view, savedInstanceState);
			
			getListView().setOnScrollListener(new EndlessScrollListener(this));
			
			new Grabber(this).execute();
		}

		@Override
		public void updateList(String[] result) {

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		              android.R.layout.simple_list_item_1, result);
			setListAdapter(adapter);
		}
    }
}
