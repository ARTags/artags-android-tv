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

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.artags.android.app.tv.model.Tag;

/**
 * TagDetails Fragment
 */
public class TagDetailsFragment extends Fragment
{

    private View mView;
    private Tag mTag;

    /**
     * Create a new instance of MyDialogFragment, providing "num" as an
     * argument.
     */
    static TagDetailsFragment newInstance(String tag)
    {
        TagDetailsFragment f = new TagDetailsFragment();
        Bundle args = new Bundle();
        args.putString("TAG", tag);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_details, container, false);
        return mView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (mTag != null)
        {
            DisplayMetrics mMetrics;
            mMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
            ImageView vImage = (ImageView) mView.findViewById(R.id.image_view);
            Picasso.with(getActivity())
                    .load(mTag.getContentUrl())
                    .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                    .centerInside()
                    //                .error(mDefaultBackground)
                    .into(vImage);
        }

    }

    public Tag getmTag()
    {
        return mTag;
    }

    public void setmTag(Tag mTag)
    {
        this.mTag = mTag;
    }

}
