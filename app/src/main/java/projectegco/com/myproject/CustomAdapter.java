package projectegco.com.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 20/12/2559.
 */
public class CustomAdapter extends ArrayAdapter<Photo>{
    Context context;
    List<Photo> objects;
    Subject subject;
    protected static final String imgPath = "path";
    ImageView photoView;
    protected static final String idselectedSubject = "subjectid";
    String imgname;

    //Checkbox
    ArrayList<Integer> msgMultiSelected;

    public CustomAdapter(Context context, int resource, List<Photo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        Intent intent = ((Activity) context).getIntent();
        final String getSubjectID = intent.getStringExtra(idselectedSubject);

        ViewHolder viewHolder = null;
//        if (view == null){
            final Photo photo = objects.get(position);

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE); //link with interface
            view = inflater.inflate(R.layout.listview_row,null);

            viewHolder = new ViewHolder();
//            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    objects.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    System.out.println("1234 check change "+getPosition);

                    msgMultiSelected = new ArrayList<Integer>();
                    msgMultiSelected.add(getPosition);

                }
            });
            view.setTag(viewHolder);
//            view.setTag(R.id.label, viewHolder.text);
            view.setTag(R.id.checkBox, viewHolder.checkbox);

            TextView txtDT = (TextView)view.findViewById(R.id.timestampTxt);
            txtDT.setText(photo.getTimestamp());

            TextView txtimgname = (TextView)view.findViewById(R.id.imgTextView);
//            imgname = "EGCO"+getSubjectID+" no."+photo.getId();
            imgname = "EGCO"+getSubjectID+" pic "+photo.getId();
            txtimgname.setText(imgname);
            intent.putExtra(TakePhotoActivity.imgName,imgname);

            //Set photo
//            imgPath = photo.getImgpath();

////            Bitmap myBitmap = BitmapFactory.decodeFile(photo.getThumbnailpath());
        Bitmap myBitmap = null;
            ImageView myImage = (ImageView)view.findViewById(R.id.photoView);

        myImage.setImageResource(R.drawable.defaultphoto);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8;
//        myBitmap = BitmapFactory.decodeFile(imgPath, options);

////        myImage.setImageBitmap(myBitmap);

            //Checkbox
            CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            checkBox.isChecked();


//        }else {
//            viewHolder = (ViewHolder) view.getTag();
//        }

        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.checkbox.setChecked(objects.get(position).isSelected());


        //Click to zoom photo
        photoView = (ImageView)view.findViewById(R.id.photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hey click photo");
                Intent intent = new Intent(context,ZoomPhotoActivity.class);
                intent.putExtra(ZoomPhotoActivity.ImagePath,photo.getImgpath());
                context.startActivity(intent);
                System.out.println("imgpath:"+photo.getImgpath()+" uri:"+imgPath);
            }
        });
        return view;
    }

}
