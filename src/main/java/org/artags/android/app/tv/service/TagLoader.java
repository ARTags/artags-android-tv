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

package org.artags.android.app.tv.service;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;
import org.artags.android.app.tv.model.CategorizedTags;

/**
 *
 * @author pierre
 */
public class TagLoader extends AsyncTaskLoader<List<CategorizedTags>>
{

    private List<CategorizedTags> mListCategories;
    private List<CategorizedTags> mListCategorizedTags;
    final PackageManager mPm;
//    PackageIntentReceiver mPackageObserver;

    public TagLoader(Context context, List<CategorizedTags> list )
    {
        super(context);
        mListCategories = list;
        mPm = getContext().getPackageManager();
    }

    @Override
    public List<CategorizedTags> loadInBackground()
    {
        mListCategorizedTags = new ArrayList<CategorizedTags>();
        for( CategorizedTags category : mListCategories )
        {
            CategorizedTags c = new CategorizedTags();
            c.setTitle( category.getTitle());
            c.setUrl( category.getUrl( ));
            c.setTags( TagService.getTagList( category.getUrl() ) );
            mListCategorizedTags.add(c);
        }
        return mListCategorizedTags;
    }
    
    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(List<CategorizedTags> listTags) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listTags != null) {
                onReleaseResources(listTags);
            }
        }
        List<CategorizedTags> oldTags = mListCategorizedTags;
        mListCategorizedTags = listTags;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(listTags);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldTags != null) {
            onReleaseResources(oldTags);
        }
    }

    @Override
    protected void onStartLoading()
    {
        if (mListCategorizedTags != null)
        {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mListCategorizedTags);
        }
/*
        // Start watching for changes in the app data.
        if (mPackageObserver == null)
        {
            mPackageObserver = new PackageIntentReceiver(this);
        }

        // Has something interesting in the configuration changed since we
        // last built the app list?
        boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());
*/
        if (takeContentChanged() || mListCategorizedTags == null )
        {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }
    /**
     * Handles a request to stop the Loader.
     */
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override public void onCanceled(List<CategorizedTags> listTags ) {
        super.onCanceled(listTags);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(listTags);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mListCategorizedTags != null) {
            onReleaseResources(mListCategorizedTags);
            mListCategorizedTags = null;
        }
/*
        // Stop monitoring for changes.
        if (mPackageObserver != null) {
            getContext().unregisterReceiver(mPackageObserver);
            mPackageObserver = null;
        }
*/
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<CategorizedTags> listTags ) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }

}
