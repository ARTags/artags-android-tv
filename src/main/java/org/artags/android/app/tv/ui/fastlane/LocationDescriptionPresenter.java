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
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;

/**
 * LocationDescriptionPresenter
 */
public class LocationDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    private final Context mContext;

    private LocationDescriptionPresenter() {
        super();
        this.mContext = null;
    }

    public LocationDescriptionPresenter(Context ctx) {
        super();
        this.mContext = ctx;
    }

    @Override
    protected void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object item) {
        Tag tag = (Tag) item;

        if (tag != null) {
            viewHolder.getTitle().setText( mContext.getText( R.string.title_location ));
            viewHolder.getSubtitle().setText( String.format("%s , %s", tag.getLatitude(), tag.getLongitude()));
            viewHolder.getBody().setText(tag.getDescription());
            
        }
    }
}
