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

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.util.BlurTransform;

/**
 * Created by anirudhd on 11/3/14.
 */
public class BackgroundHelper
{

    private final Handler mHandler = new Handler();

    private final Runnable mUpdateBackgroundAction = new Runnable()
    {
        @Override
        public void run()
        {
            if (mBackgroundURL != null)
            {
                updateBackground(mBackgroundURL);
            }
        }
    };

    private Activity mActivity;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private String mBackgroundURL;

    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;

    public BackgroundHelper(Activity mActivity)
    {
        this.mActivity = mActivity;
    }

    private long BACKGROUND_UPDATE_DELAY = 200L;

    public void prepareBackgroundManager()
    {
        mBackgroundManager = BackgroundManager.getInstance(mActivity);
        mBackgroundManager.attach(mActivity.getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(mBackgroundManager);
        mDefaultBackground = mActivity.getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    public void release()
    {
        mHandler.removeCallbacksAndMessages(null);
        mBackgroundManager.release();
    }

    public void setBackgroundUrl(String backgroundUrl)
    {
        this.mBackgroundURL = backgroundUrl;
        scheduleUpdate();
    }

    static class PicassoBackgroundManagerTarget implements Target
    {

        BackgroundManager mBackgroundManager;

        public PicassoBackgroundManagerTarget(BackgroundManager backgroundManager)
        {
            this.mBackgroundManager = backgroundManager;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom)
        {
            this.mBackgroundManager.setBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable drawable)
        {
            this.mBackgroundManager.setDrawable(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable)
        {
            // Do nothing, default_background manager has its own transitions
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            PicassoBackgroundManagerTarget that = (PicassoBackgroundManagerTarget) o;

            return mBackgroundManager.equals(that.mBackgroundManager);
        }

        @Override
        public int hashCode()
        {
            return mBackgroundManager.hashCode();
        }
    }

    protected void setDefaultBackground(Drawable background)
    {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground(int resourceId)
    {
        mDefaultBackground = mActivity.getResources().getDrawable(resourceId);
    }

    protected void updateBackground(String url)
    {
        Picasso.with(mActivity)
                .load(url)
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .transform(BlurTransform.getInstance(mActivity))
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    protected void updateBackground(Drawable drawable)
    {
        BackgroundManager.getInstance(mActivity).setDrawable(drawable);
    }

    protected void clearBackground()
    {
        BackgroundManager.getInstance(mActivity).setDrawable(mDefaultBackground);
    }

    private void scheduleUpdate()
    {
        mHandler.removeCallbacks(mUpdateBackgroundAction);
        mHandler.postDelayed(mUpdateBackgroundAction, BACKGROUND_UPDATE_DELAY);
    }

    /**
     * Deprecated, simply use {@link #setBackgroundUrl(String)}
     */
    @Deprecated
    public void startBackgroundTimer()
    {
    }

}
