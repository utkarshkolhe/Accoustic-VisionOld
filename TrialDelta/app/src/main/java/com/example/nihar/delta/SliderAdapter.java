package com.example.nihar.delta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context=context;
    }

    public int[] slide_images={R.drawable.slide2,R.drawable.slide4,R.drawable.slide1,R.drawable.slide3,R.drawable.slide5};
    public String[] slide_headings={"OBJECT DETECTION","TEXT RECOGNITION","GEO LOCATION","SOS","SPEECH COMMANDS"};
    public String[] slide_descriptions={
            "This App will help you by detecting objects around you. It will detect the people and their expressions too.Tap speak and say object to start ",
            "This App will help you by detecting text around you. Tap speak and say text to start ",
            "GeoLocation tracking in case of emergency",
            "Incase of emergency Click or Speak sos to send message and call ur guardian ",
            "You can use speech commands. If YOu Need any help, Tap Speak at the bottom and say Help"};
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(RelativeLayout)o;
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        RelativeLayout layout = view.findViewById(R.id.back);
        TextView slideheading=(TextView)view.findViewById(R.id.slide_heading);
        TextView slidedescription=(TextView)view.findViewById(R.id.slide_desc);

        //slideImageView.setImageResource(slide_images[position]);
        slideheading.setText(slide_headings[position]);
        slidedescription.setText(slide_descriptions[position]);
        layout.setBackgroundResource(slide_images[position]);
        container.addView(view);



    return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
