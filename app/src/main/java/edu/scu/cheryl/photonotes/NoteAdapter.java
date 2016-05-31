package edu.scu.cheryl.photonotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XiangM on 16-2-18.
 */
public class NoteAdapter extends ArrayAdapter {




    private final List<Notes> notes;

    public NoteAdapter(Context context, int resource, List<Notes> notes) {
        super(context, resource, notes);
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.my_list, null);

        TextView textView = (TextView) row.findViewById(R.id.label);
        textView.setText(notes.get(position).getCaption());
//        final ToggleButton favorite = (ToggleButton) row.findViewById(R.id.favoriteButton);
//        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getContext(), "set as favorite", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "removed from favorite", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        try {
            ImageView imageView = (ImageView) row.findViewById(R.id.icon);
            String filename = notes.get(position).getFilename();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8; // Experiment with different sizes

            Bitmap bitmap= BitmapFactory.decodeFile(filename, options);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {
            Toast.makeText(getContext(), "image view problem", Toast.LENGTH_SHORT).show();
        }
        return row;
    }
}



