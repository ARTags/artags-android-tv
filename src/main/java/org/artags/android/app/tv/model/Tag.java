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

import java.io.Serializable;
import java.text.MessageFormat;

public class Tag implements Serializable
{
    private static final String LOCATION_URL = "http://maps.google.com/maps/api/staticmap?zoom=6&size=500x500&markers={0},{1}&sensor=false";
    private static final String CONTENT_URL =  "http://art-server-1068.appspot.com/display?id=";
    private static final long serialVersionUID = 1L;
    private String mId;
    private String mRating;
    private String mDescription;
    private String mTitle;
    private String mThumbUrl;
    private String mContentUrl;
    private String mLongitude;
    private String mLatitude;
    private String mDate;

    public String getRating()
    {
        return mRating;
    }

    public void setRating(String rating)
    {
        mRating = rating;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public String getThumbUrl()
    {
        return mThumbUrl;
    }

    public void setThumbUrl(String thumbUrl)
    {
        mThumbUrl = thumbUrl;
    }

    public String getContentUrl()
    {
        return CONTENT_URL + mId;
    }


    public void setId(String id)
    {
        mId = id;
    }

    public String getId()
    {
        return mId;
    }

    public String getLocationUrl()
    {
        return MessageFormat.format( LOCATION_URL, mLatitude, mLongitude );
    }


    /**
     * @return the mDate
     */
    public String getDate()
    {
        return mDate;
    }

    /**
     * @param date the mDate to set
     */
    public void setDate(String date)
    {
        mDate = date;
    }

    /**
     * @return the mLongitude
     */
    public String getLongitude()
    {
        return mLongitude;
    }

    /**
     * @param longitude the mLongitude to set
     */
    public void setLongitude(String longitude)
    {
        mLongitude = longitude;
    }

    /**
     * @return the mLatitude
     */
    public String getLatitude()
    {
        return mLatitude;
    }

    /**
     * @param latitude the mLatitude to set
     */
    public void setLatitude(String latitude)
    {
        mLatitude = latitude;
    }
}
