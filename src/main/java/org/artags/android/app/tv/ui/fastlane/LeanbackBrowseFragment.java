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

import org.artags.android.app.tv.model.CategorizedTags;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.View;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.artags.android.app.tv.Constants;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * LeanbackBrowseFragment
 */
public class LeanbackBrowseFragment extends BrowseFragment implements LoaderManager.LoaderCallbacks
{

    private BackgroundHelper bgHelper;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init()
    {

        setBrandColor(getResources().getColor(R.color.primary));
        setBadgeDrawable(getResources().getDrawable(R.drawable.artags_badge));

        updateAdapter(new ArrayList<CategorizedTags>());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        getLoaderManager().initLoader(0, null, this);

        bgHelper = new BackgroundHelper(getActivity());
        bgHelper.prepareBackgroundManager();

    }

    void updateAdapter(List<CategorizedTags> listCategories)
    {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        int position = 0;
        for (CategorizedTags category : listCategories)
        {
            HeaderItem headerItem = new HeaderItem(position++, category.getTitle());
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
            for (Tag tag : category.getTags())
            {
                listRowAdapter.add(tag);
            }
            rowsAdapter.add(new ListRow(headerItem, listRowAdapter));

        }
        setAdapter(rowsAdapter);
    }

    @Override
    public void onStop()
    {
        bgHelper.release();
        super.onStop();
    }

    protected OnItemViewClickedListener getDefaultItemViewClickedListener()
    {
        return new OnItemViewClickedListener()
        {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder2, Row row)
            {
                Tag tag = (Tag) o;
                Log.i( Constants.LOG_TAG, "Tag clicked : " + tag.getTitle() );
                Intent intent = new Intent(getActivity(), TagDetailsActivity.class);
                intent.putExtra( Constants.EXTRA_TAG , (Serializable) tag);
                startActivity(intent);
            }
        };
    }

    protected OnItemViewSelectedListener getDefaultItemSelectedListener()
    {

        return new OnItemViewSelectedListener()
        {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                    RowPresenter.ViewHolder rowViewHolder, Row row)
            {
                if (item instanceof Tag)
                {
                    bgHelper.setBackgroundUrl(((Tag) item).getContentUrl());
                    bgHelper.startBackgroundTimer();
                }

            }
        };
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle)
    {
        Log.d(Constants.LOG_TAG, "LeanbackBrowseFragment : TagLoader started");
        return new org.artags.android.app.tv.service.TagLoader(getContext(), Config.getCategories());
    }

    @Override
    public void onLoadFinished(Loader loader, Object data)
    {
        List<CategorizedTags> list = (List<CategorizedTags>) data;
        updateAdapter(list);
    }

    @Override
    public void onLoaderReset(Loader loader)
    {
    }

}
