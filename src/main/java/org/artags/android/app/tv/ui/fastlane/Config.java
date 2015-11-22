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

import org.artags.android.app.tv.model.CategorizedTags;
import java.util.ArrayList;
import java.util.List;
import org.artags.android.app.tv.Constants;

/**
 * Config
 */
public class Config
{
    public static List<CategorizedTags> getCategories()
    {
        List<CategorizedTags> list = new ArrayList<CategorizedTags>();
        CategorizedTags bestTags = new CategorizedTags();
        bestTags.setTitle( "Best tags");
        bestTags.setUrl( Constants.URL_BEST_TAGS );
        list.add(bestTags);
        CategorizedTags latestTags = new CategorizedTags();
        latestTags.setTitle( "Latest tags");
        latestTags.setUrl( Constants.URL_LATEST_TAGS );
        list.add(latestTags);
        return list;
        
    }
    
}
