package projectegco.com.myproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 20/12/2559.
 */
public class CustomAdapter extends ArrayAdapter<Photo>{
    Context context;
    List<Photo> objects;
    String imgPath;
    boolean checkAll_flag = false;
    public static boolean checkItem_flag = false;

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
    public View getView(int position, View view, ViewGroup parent){

        ViewHolder viewHolder = null;
//        if (view == null){
            Photo photo = objects.get(position);
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
            txtimgname.setText("img_"+photo.getId());

            //Set photo
            imgPath = photo.getImgpath();
            Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
            ImageView myImage = (ImageView)view.findViewById(R.id.photoView);
            myImage.setImageBitmap(myBitmap);

            //Checkbox
            CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            checkBox.isChecked();

        //Select All checkbox
//        final CheckBox checkboxAll = (CheckBox)view.findViewById(R.id.checkBox2);
//        checkboxAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checkboxAll.isChecked())
//                {
////                    checkBox.setChecked(true);
//                    // check all list items
//                    System.out.println("xxx1");
//
//
//                }
//                else if(!checkboxAll.isChecked())
//                {
//                    //  unselect all list items
////                   checkBox.setChecked(false);
//                    System.out.println("xxx2");
//
//                }
//            }
//        });



//        }else {
//            viewHolder = (ViewHolder) view.getTag();
//        }

        viewHolder.checkbox.setTag(position); // This line is important.

//        viewHolder.text.setText(objects.get(position).getName());
        viewHolder.checkbox.setChecked(objects.get(position).isSelected());

        return view;
    }
}
