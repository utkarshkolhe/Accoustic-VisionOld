package com.example.nihar.delta;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Walkthrough extends AppCompatActivity {
    private ViewPager nSlideViewPager;
    private LinearLayout nDotlayout;
    private SliderAdapter sliderAdapter;
    private TextView[] nDots;
    private Button nNextBtn;
    private Button nPrevBtn;
    private int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        nSlideViewPager=(ViewPager)findViewById(R.id.view_page_main);
        nDotlayout=(LinearLayout)findViewById(R.id.dots);
        sliderAdapter=new SliderAdapter(this);
        nSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        nSlideViewPager.addOnPageChangeListener(viewListener);
        //  nNextBtn=(Button)findViewById(R.id.nextBtn);
        //nPrevBtn=(Button)findViewById(R.id.prevBtn);

       /* nNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nSlideViewPager.setCurrentItem(currentPage+1);
            }
        });
        nPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nSlideViewPager.setCurrentItem(currentPage-1);
            }
        });
*/

    }

    public void addDotsIndicator(int position){
        nDots=new TextView[5];
        nDotlayout.removeAllViews();
        int i;
        for(i=0;i<nDots.length;i++){

            nDots[i]=new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226"));
            nDots[i].setTextSize(35);
            nDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            nDotlayout.addView(nDots[i]);

        }
        if(nDots.length>0)
        {
            nDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
        if(nDots.length>0)
        {
            nDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }



        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    public void onSkip(View view){
        Intent inte = new Intent(Walkthrough.this,Login.class);
        startActivity(inte);
        finish();
    }
}
