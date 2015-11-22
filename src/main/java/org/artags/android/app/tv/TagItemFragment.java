/* Copyright (c) 2010-2015 ARTags Project owners (see http://www.artags.org)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.artags.android.app.tv;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import java.util.List;
import org.artags.android.app.tv.service.TagLoader;
import org.artags.android.app.tv.model.CategorizedTags;
import org.artags.android.app.tv.model.Tag;
import org.artags.android.app.tv.ui.fastlane.Config;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TagItemFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static final String ARG_CATEGORY = "category";


    private OnFragmentInteractionListener mListener;

    private GridView mGridView;

    public static TagItemFragment newInstance( int category ) {
        TagItemFragment fragment = new TagItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY, category );
        fragment.setArguments(args);
        Log.d( Constants.LOG_TAG, "TagItemFragment created for category " + Constants.CATEGORIES[category] );
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TagItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videoitem_list, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Tag tag = (Tag) parent.getAdapter().getItem(position);
                ((OnFragmentInteractionListener) getActivity()).onFragmentInteraction( tag );
            }

        });
        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.d(TagItemFragment.class.getName(), "onAttach Called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        Log.d( Constants.LOG_TAG, "TagItemFragment : TagLoader started");
        Loader loader = new TagLoader( getContext(), Config.getCategories() ); 
        loader.startLoading();
        return loader;

    }

    
    
    @Override
    public void onLoadFinished(Loader loader, Object data)
    {
        if( data instanceof List )
        {
            List<CategorizedTags> list = (List<CategorizedTags>) data;
            int category = getArguments().getInt(ARG_CATEGORY);
            Log.d( Constants.LOG_TAG, "TagItemFragment : TagLoader finished. Number of tags fetched : " + list.get(category).getTags().size() );
            ListAdapter listAdapter = new TagListAdapter( getContext() , list.get(category).getTags() );
            mGridView.setAdapter( listAdapter );
            mGridView.setVisibility(View.VISIBLE);
            mGridView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onLoaderReset(Loader loader)
    {
       
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Tag tag);
    }
    
}
