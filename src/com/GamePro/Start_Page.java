package com.GamePro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Start_Page extends Activity{

	public Button start;
	public Button exit;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.startscreen);
		
		start = (Button) findViewById(R.id.start);
		start.setOnClickListener(l);
		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(0);
			}});
	}
	
	public OnClickListener l = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), GameProActivity.class);
			startActivity(intent);
		}};
	
}
