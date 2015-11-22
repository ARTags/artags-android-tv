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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;
import org.artags.android.app.tv.model.Tag;

public class MainActivity extends Activity
        implements TagItemFragment.OnFragmentInteractionListener
{

    private ViewPager mPager;
    private TagCategoriesPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup ViewPager
        mAdapter = new TagCategoriesPagerAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Sliding tabs for viewpager
        SlidingTabLayout slidingTab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTab.setViewPager(mPager);
        // slidingTab.setSelectedIndicatorColors(new int[]{getResources().getColor(android.R.color.white)});
        slidingTab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)
            {
                return getResources().getColor(R.color.accent);
            }

            @Override
            public int getDividerColor(int position)
            {
                return Color.argb(0, 0, 0, 0);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction( Tag tag )
    {
        Toast.makeText(this, tag.getTitle() , Toast.LENGTH_LONG ).show();

    }

    /**
     * Simple implementation for {@link #FragmentPagerAdapter}
     */
    public static class TagCategoriesPagerAdapter extends FragmentPagerAdapter
    {

        public TagCategoriesPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return Constants.CATEGORIES.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            return TagItemFragment.newInstance( position );
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return Constants.CATEGORIES[position];
        }

    }

}
