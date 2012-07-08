package com.GamePro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.view.Menu;
import android.view.animation.*;

public class GameProActivity extends Activity {
    /** Called when the activity is first created. */
	
	public LinearLayout linear_layout;
	public ImageView logo;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		
		menu.add(0,1,0,R.string.menu_start);
		menu.add(0,2,0,R.string.menu_pause);
		menu.add(0,3,0,R.string.menu_resume);
		menu.add(0,4,0,R.string.menu_options);
		menu.add(0,5,0,R.string.menu_scores);
		menu.add(0,6,0,R.string.menu_exit);
		
		return true;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
        MyShapes myshapes = new MyShapes(getApplicationContext()); 
        setContentView(myshapes);
        //setContentView(R.layout.main);
       // Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fall);
       // logo = (ImageView) findViewById(R.id.imageView1);
       // logo.startAnimation(an);        
    }
}