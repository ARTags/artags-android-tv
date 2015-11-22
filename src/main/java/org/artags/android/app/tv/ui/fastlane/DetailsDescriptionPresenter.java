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
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.util.Log;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * DetailsDescriptionPresenter
 */

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    private final Context mContext;

    private DetailsDescriptionPresenter() {
        super();
        this.mContext = null;
    }

    public DetailsDescriptionPresenter(Context ctx) {
        super();
        this.mContext = ctx;
    }

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Tag tag = (Tag) item;
        

        if (tag != null) {
            Log.d("DetailsDescriptionPresenter", String.format("%s, %s, %s", tag.getTitle(), tag.getThumbUrl(), tag.getDescription()));
            viewHolder.getTitle().setText(tag.getTitle());
            viewHolder.getSubtitle().setText( mContext.getString(R.string.rating) + ": "
                    + tag.getRating() + " - " + mContext.getString(R.string.date) +": " + tag.getDate() );
            viewHolder.getBody().setText(tag.getDescription());
            
        }
    }
}
