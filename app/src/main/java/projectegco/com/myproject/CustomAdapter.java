package projectegco.com.myproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
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
    public static final String imgPath = "imgpath";

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

//        TextView txt = (TextView)view.findViewById(R.id.imageTxt);
//        txt.setText(photo.getSubject());

        TextView txtDT = (TextView)view.findViewById(R.id.timestampTxt); 
        txtDT.setText(photo.getTimestamp());

       // String getImgpath = getIntent().getStringExtra(imgPath);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
        ImageView myImage = (ImageView)view.findViewById(R.id.photoView);
        myImage.setImageBitmap(myBitmap);


        System.out.println("xximgpath"+imgPath);
//        ImageView image = (ImageView)view.findViewById(R.id.photoView);
//        String res = photo.getImgpath();
//        image.setImageResource(res);
        return view;
    }
}
