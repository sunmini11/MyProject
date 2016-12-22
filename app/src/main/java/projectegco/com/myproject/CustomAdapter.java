package projectegco.com.myproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell pc on 20/12/2559.
 */
public class CustomAdapter extends ArrayAdapter<Photo>{
    Context context;
    List<Photo> objects;

    public CustomAdapter(Context context, int resource, List<Photo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Photo photo = objects.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE); //link with interface
        View view = inflater.inflate(R.layout.listview_row,null);

        TextView txt = (TextView)view.findViewById(R.id.imageTxt);
        txt.setText(photo.getMessage());

        TextView txtDT = (TextView)view.findViewById(R.id.timestampTxt);
        txtDT.setText(photo.getTimestamp());

        ImageView image = (ImageView)view.findViewById(R.id.photoView);
        int res = context.getResources().getIdentifier(photo.getImgname(),"drawable",context.getPackageName());
        image.setImageResource(res);
        return view;
    }
}
