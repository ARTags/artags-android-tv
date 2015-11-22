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

package org.artags.android.app.tv.model;

import java.util.List;

/**
 *
 * @author pierre
 */
public class CategorizedTags
{
    private String mTitle;
    private String mUrl;
    private List<Tag> mTags;

    /**
     * @return the mTitle
     */
    public String getTitle()
    {
        return mTitle;
    }

    /**
     * @param title the mTitle to set
     */
    public void setTitle(String title)
    {
        mTitle = title;
    }

    /**
     * @return the mUrl
     */
    public String getUrl()
    {
        return mUrl;
    }

    /**
     * @param url the mUrl to set
     */
    public void setUrl(String url)
    {
        mUrl = url;
    }

    /**
     * @return the mTags
     */
    public List<Tag> getTags()
    {
        return mTags;
    }

    /**
     * @param tags the mTags to set
     */
    public void setTags(List<Tag> tags)
    {
        mTags = tags;
    }
    
}
