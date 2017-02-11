package gonorus.makancepatkurir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import gonorus.makancepatkurir.R;

/**
 * Created by ADMIN on 8/19/2016.
 */

public class ItemDrawerAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> listHeader; //header titles
    private HashMap<String, List<String>> listChild;

    public ItemDrawerAdapter(Context context, List<String> listDataHeader,
                             HashMap<String, List<String>> listChildData) {
        this.mContext = context;
        this.listHeader = listDataHeader;
        this.listChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (groupPosition == 11)
            childCount = this.listChild.get(this.listHeader.get(groupPosition)).size();
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChild.get(this.listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final int posGroup = groupPosition;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_nav_drawer, null);
        }
        final TextView lblListHeader = (TextView) convertView.findViewById(R.id.labelListDrawer);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgExpandList);

        if (groupPosition == 11) {
            img.setVisibility(View.VISIBLE);
            lblListHeader.setTextColor(mContext.getResources().getColor(R.color.list_text_color));
            if (!isExpanded) {
                img.setImageResource(R.drawable.ic_action_expand);
            } else {
                img.setImageResource(R.drawable.ic_action_collapse);
            }
        } else {
            img.setVisibility(View.GONE);
        }

        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_child_nav_drawer, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.labelListChildDrawer);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
