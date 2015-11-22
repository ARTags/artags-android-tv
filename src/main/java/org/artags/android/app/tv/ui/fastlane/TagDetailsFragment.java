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
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.artags.android.app.tv.Constants;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * TagDetailsFragment
 */
public class TagDetailsFragment extends DetailsFragment
{

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;
    private static final int ACTION_VIEW_TAG = 1;
    private static final int ACTION_LOCATION = 2;

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
            DetailsOverviewRow row = new DetailsOverviewRow(tags[0]);
            try
            {
                Bitmap poster = Picasso.with(getActivity())
                        .load(tags[0].getContentUrl())
                        .resize(dpToPx(DETAIL_THUMB_WIDTH, getActivity().getApplicationContext()),
                                dpToPx(DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext()))
//                        .centerCrop()
                        .centerInside()
                        .get();
                row.setImageBitmap(getActivity(), poster);
            }
            catch (IOException e)
            {
                Log.e(Constants.LOG_TAG, "Cannot load thumbnail for " + tags[0].getId(), e);
            }
            
            row.addAction(new Action(ACTION_VIEW_TAG, getResources().getString(
                    R.string.action_view_tag)));
            row.addAction(new Action(ACTION_LOCATION, getResources().getString(
                    R.string.action_location)));
            list.add(row);

            return list;

        }

        @Override
        protected void onPostExecute(List<DetailsOverviewRow> listRows)
        {
            ClassPresenterSelector ps = new ClassPresenterSelector();
            FullWidthDetailsOverviewRowPresenter dorPresenter
                    = new FullWidthDetailsOverviewRowPresenter(new DetailsDescriptionPresenter(getActivity()));
            // set detail background and style
            dorPresenter.setBackgroundColor(getResources().getColor(R.color.primary));
//            dorPresenter.setInitialState( FullWidthDetailsOverviewRowPresenter.STATE_SMALL );
//            lorPresenter.setInitialState( FullWidthDetailsOverviewRowPresenter.STATE_SMALL );
            dorPresenter.setOnActionClickedListener(new OnActionClickedListener() {
                @Override
                public void onActionClicked(Action action) {
                    if (action.getId() == ACTION_LOCATION) {
                        Intent intent = new Intent(getActivity(), LocationActivity.class);
                        intent.putExtra( Constants.EXTRA_TAG , (Serializable) mTag );
                        startActivity(intent);
                    }
                    else if (action.getId() == ACTION_VIEW_TAG) {
                        Intent intent = new Intent(getActivity(), ViewTagActivity.class);
                        intent.putExtra( Constants.EXTRA_TAG , (Serializable) mTag );
                        startActivity(intent);
                    }
                }
            });
            
            ps.addClassPresenter(DetailsOverviewRow.class, dorPresenter);

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
