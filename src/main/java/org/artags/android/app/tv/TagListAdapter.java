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

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.artags.android.app.tv.model.Tag;

/**
 *
 * @author pierre
 */
public class TagListAdapter implements ListAdapter
{

    private List<Tag> mList;
    private Context mContext;
    private final Picasso mPicasso;

        private final static View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(
                        new Intent(v.getContext(), TagDetailsActivity.class)
                                .putExtra( Constants.EXTRA_TAG, (Tag) v.getTag())
                );
            }
        };

    public static class ViewHolder
    {

        public final TextView info;
        public final ImageView image;
        public final Button play;

        public ViewHolder(final View view )
        {
            this.info = (TextView) view.findViewById(R.id.info_text);
            this.image = (ImageView) view.findViewById(R.id.info_image);
            this.play = (Button) view.findViewById(R.id.play_button);
        }
    }

    public TagListAdapter(Context context, List<Tag> list)
    {
        mContext = context;
        mList = list;
        mPicasso = Picasso.with(context);
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Tag getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return (long) position;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.tag_card, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tag tag = getItem(position);
        if (tag != null)
        {
            viewHolder.info.setText(tag.getTitle());
            mPicasso.load(tag.getContentUrl()).into(viewHolder.image);
            
        }
        viewHolder.play.setOnClickListener(onClickListener);
        viewHolder.play.setTag( tag );
        convertView.setFocusable(true);
        convertView.setFocusableInTouchMode(true);

        return convertView;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return mList.isEmpty();
    }

}
