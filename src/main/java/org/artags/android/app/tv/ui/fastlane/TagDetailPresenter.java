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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * TagDetailPresenter
 */
public class TagDetailPresenter extends Presenter
{

    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 200;

    private static Context mContext;

    public static class ViewHolder extends Presenter.ViewHolder
    {
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public ViewHolder(View view)
        {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.artags_badge);
        }

        public ImageCardView getCardView()
        {
            return mCardView;
        }

        protected void updateCardViewImage(String url)
        {

            Picasso.with(mContext)
                    .load(url)
                    .resize(CARD_WIDTH, CARD_HEIGHT)
                    .centerInside()
                    .error(mDefaultCardImage)
                    .into(mImageCardViewTarget);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup)
    {
        Log.d("onCreateViewHolder", "creating viewholder");
        mContext = viewGroup.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        ((TextView) cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o)
    {
        Tag tag = (Tag) o;
        ((ViewHolder) viewHolder).mCardView.setTitleText(tag.getTitle());
        ((ViewHolder) viewHolder).mCardView.setContentText(tag.getDescription());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH * 2, CARD_HEIGHT * 2);
        ((ViewHolder) viewHolder).updateCardViewImage(tag.getContentUrl());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder)
    {

    }

    public static class PicassoImageCardViewTarget implements Target
    {

        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView)
        {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom)
        {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable drawable)
        {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable)
        {
            // Do nothing, default_background manager has its own transitions
        }

    }

}
