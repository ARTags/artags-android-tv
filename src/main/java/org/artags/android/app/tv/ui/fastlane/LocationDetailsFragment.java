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

package org.artags.android.app.tv.ui.fastlane;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.util.Log;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.artags.android.app.tv.Constants;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * LocationDetailsFragment
 */
public class LocationDetailsFragment extends DetailsFragment
{

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;
    private static final int ACTION_BACK = 1;

    private DetailRowBuilderTask mRowBuilderTask;
    private BackgroundHelper bgHelper;
    private Tag mTag;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mTag = (Tag) getActivity().getIntent().getSerializableExtra(Constants.EXTRA_TAG);
        (mRowBuilderTask = new DetailRowBuilderTask()).execute(mTag);
        bgHelper = new BackgroundHelper(getActivity());
        bgHelper.prepareBackgroundManager();
        bgHelper.updateBackground(mTag.getContentUrl());

    }

    @Override
    public void onStop()
    {
        mRowBuilderTask.cancel(true);
        bgHelper.release();
        super.onStop();
    }

    private class DetailRowBuilderTask extends AsyncTask<Tag, Integer, List<DetailsOverviewRow>>
    {

        @Override
        protected List<DetailsOverviewRow> doInBackground(Tag... tags)
        {
            List<DetailsOverviewRow> list = new ArrayList<DetailsOverviewRow>(); 

            // Build location row
            LocationOverviewRow row = new LocationOverviewRow(tags[0]);
            String locationMapUrl = tags[0].getLocationUrl();
            Log.d( Constants.LOG_TAG, locationMapUrl );
            try
            {
                Bitmap poster = Picasso.with(getActivity())
                        .load( locationMapUrl )
                        .resize(dpToPx(DETAIL_THUMB_WIDTH, getActivity().getApplicationContext()),
                                dpToPx(DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext()))
                        .centerCrop()
                        .get();
                row.setImageBitmap(getActivity(), poster);
            }
            catch (IOException e)
            {
                Log.e(Constants.LOG_TAG, "Cannot load thumbnail for " + tags[0].getId(), e);
            }

            row.addAction(new Action(ACTION_BACK, getResources().getString(
                    R.string.action_back)));
            
            list.add(row);
            return list;

        }

        @Override
        protected void onPostExecute(List<DetailsOverviewRow> listRows)
        {
            ClassPresenterSelector ps = new ClassPresenterSelector();
            FullWidthDetailsOverviewRowPresenter lorPresenter
                    = new FullWidthDetailsOverviewRowPresenter(new LocationDescriptionPresenter(getActivity()));
            // set detail background and style
            lorPresenter.setBackgroundColor(getResources().getColor(R.color.primary));
//            dorPresenter.setInitialState( FullWidthDetailsOverviewRowPresenter.STATE_SMALL );
//            lorPresenter.setInitialState( FullWidthDetailsOverviewRowPresenter.STATE_SMALL );
            lorPresenter.setOnActionClickedListener(new OnActionClickedListener() {
                @Override
                public void onActionClicked(Action action) {
                    if (action.getId() == ACTION_BACK) {
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
            ps.addClassPresenter(LocationOverviewRow.class , lorPresenter);

            /**
             * bonus code for adding related items to details fragment *
             */
            // <START>
            ArrayObjectAdapter adapter = new ArrayObjectAdapter(ps);
            for( DetailsOverviewRow row : listRows )
            {
                adapter.add(row);
            }
            setAdapter(adapter);

        }

    }

    // Utility method for converting dp to pixels
    public static int dpToPx(int dp, Context ctx)
    {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
