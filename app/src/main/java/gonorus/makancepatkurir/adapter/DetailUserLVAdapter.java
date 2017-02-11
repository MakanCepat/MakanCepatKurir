package gonorus.makancepatkurir.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gonorus.makancepatkurir.R;

/**
 * Created by USER on 2/9/2017.
 */

public class DetailUserLVAdapter extends ArrayAdapter<String> {
    public DetailUserLVAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.detail_user_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        Log.d("size", data.size() + "");
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // reuse views
        if (convertView == null) {
            LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.detail_user_item, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.txtDetailUser);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iconDetailUser);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String s = data.get(position);
        if (!s.isEmpty())
            viewHolder.text.setText(s);
        else viewHolder.text.setText("-");
        return convertView;
    }

    private final Context context;
    private final ArrayList<String> data;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}
