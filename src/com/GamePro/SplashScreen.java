package com.GamePro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends Activity{
	
	private Thread mSplashThread;
	public TextView game_name;
	
	@Override
	
	public void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
		 
		 setContentView(R.layout.splash);
		 
		 final SplashScreen splashScreen = this;
		 game_name = (TextView) findViewById(R.id.textView1);
		 final Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
		 game_name.startAnimation(an);
		// game_name.setVisibility(View.VISIBLE);
		 mSplashThread = new Thread(){
			 @Override 
			 public void run()
			 {
				 synchronized(this){try {
					 
					wait(2000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				 finish();
				 Intent intent = new Intent();
				 intent.setClass(splashScreen, Start_Page.class);
				 startActivity(intent);
				 stop();
			 }
		 };
		 mSplashThread.start();
		 
	}

}
