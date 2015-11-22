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

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.artags.android.app.tv.Constants;
import org.artags.android.app.tv.R;
import org.artags.android.app.tv.model.Tag;
import org.artags.android.app.tv.util.BlurTransform;

/**
 * ViewTagFragment
 */
public class ViewTagFragment extends Fragment
{
    private Tag mTag;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mTag = (Tag) getActivity().getIntent().getSerializableExtra(Constants.EXTRA_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        DisplayMetrics mMetrics;
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        View view = inflater.inflate(R.layout.fragment_view_tag, container, false);
        ImageView vImage = (ImageView) view.findViewById(R.id.image_view );
        Picasso.with( getActivity() )
                .load( mTag.getContentUrl() )
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerInside()
//                .error(mDefaultBackground)
                .into(vImage);
        return view;
    }
    
    
}
