package com.GamePro;

import java.io.InputStream;
import java.util.Hashtable;



import android.content.Context;
//import android.content.Intent;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
//import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//import android.view.SurfaceHolder;
import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MyShapes extends View implements Animatable{

	public int speedX=5;
	public int speedY=3;
	public int leftY = 0;
	public int leftX= 0;
	public float previousX ;//= 200;
	public float previousY ;//= 200;
	public float presentX ;//= 180;
	public float presentY ;//= 300;
	public float rotation;
	public float contactX ;//= 50;
	public float contactY ;//= 50;
	
	public boolean slope_check;
	public boolean collided;
	
	public float lineLX;//=180;
	public float lineLY;//=300;
	public float lineRX;//=200;
	public float lineRY;//=200;
	
	public Bitmap mBackgroundImage;
	
	public Canvas myCanvas;
	
	public float deg;
	
	public int movie_pos = 0;
	
	public float image_width, image_height;
	
	boolean rotate_L_up, rotate_L_down, rotate_R_up, rotate_R_down;
	
	int screen_level = 0;
	public Bitmap[] Space_Ship = new Bitmap[2];
	public Bitmap[] clouds = new Bitmap[3];
	private Bitmap[] mAsteroids = new Bitmap[12];
	public int space_ship_count = 0;
	
	Drawable bitmap = getResources().getDrawable(R.drawable.trialiensmall);
	
	int pos[] = {0,75,200};
	int rand;
	
	public int cloudX[] = {0,100,200};
	public int cloudY[] = {0,75,200};
	
	public double asteroidX[] = {Math.random()*100, Math.random()*200, Math.random()*400};
	public double asteroidY[] = {Math.random()*230, Math.random()*430, Math.random()*70};
	public int asteroid_count[] = {0,0,0} ;
	
	Paint paint = new Paint();
	
	private Thread myThread;
	
	float origWidth, origHeight;
	public MyShapes(Context context) {
		super(context);
		
		//to enable touchmode
		this.setFocusableInTouchMode(true);
		rotate_L_up = rotate_L_down = rotate_R_up = rotate_R_down = false;
		deg = 0;
		mBackgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		Space_Ship[0] = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship1);
		Space_Ship[1] = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship2);
		
		
		clouds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
		clouds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
		clouds[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
		
		origWidth = image_width = bitmap.getIntrinsicWidth();
		origHeight = image_height = bitmap.getIntrinsicHeight();
		
		mAsteroids[11] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid01);
        mAsteroids[10] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid02);
        mAsteroids[9] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid03);
        mAsteroids[8] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid04);
        mAsteroids[7] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid05);
        mAsteroids[6] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid06);
        mAsteroids[5] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid07);
        mAsteroids[4] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid08);
        mAsteroids[3] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid09);
        mAsteroids[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid10);
        mAsteroids[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid11);
        mAsteroids[0] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid12);
        
       // for(int ast=0 ; ast < 6 ; ast++)
        //{
        	//asteroidX = Math.random()*100;
        	//asteroidY = Math.random()*100;
        	//asteroid_count = 0;
       // }
		
		 
	}
	
	public MyShapes(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public MyShapes(Context context, AttributeSet attrs, int defStyle)
	{
		
		super(context, attrs, defStyle);
	/*	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(inflater!=null)
			inflater.inflate(R.id.myShapes1, null);*/
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		
		myCanvas = new Canvas();
		myCanvas = c;
		ShapeDrawable drawable_shape = new ShapeDrawable(new RectShape());
		
		
		paint.setColor(Color.BLUE);
		drawable_shape.getPaint().set(paint);
		drawable_shape.setBounds(leftX,leftY,leftX+50,leftY+50);
		//drawable_shape.draw(myCanvas);
		
		
				
		
		bitmap.setBounds(leftX, leftY, (int)(leftX+image_width),(int)(leftY+image_height));
		//bitmap.setBounds(leftX, leftY, leftX + image_width , leftY+ image_height);
		if(screen_level == 0)
			{
			c.drawBitmap(mBackgroundImage, 0, -2200, paint);
			
			}
		else if(screen_level == 1)
			c.drawBitmap(mBackgroundImage, 0, -1400, paint);
		else if(screen_level == 2)
			c.drawBitmap(mBackgroundImage, 0, -700, paint);
		else if(screen_level == 3)
			{
			c.drawBitmap(mBackgroundImage, 0, 0, paint);
			c.drawBitmap(Space_Ship[space_ship_count], 100, -50, paint);
			
			if(space_ship_count == 1)
				{
					space_ship_count = 0;
				}
			else
				space_ship_count++;
				Log.i("pic count",String.valueOf(space_ship_count));
			}
		
		/*InputStream is = getResources().openRawResource(R.drawable.bird);
		Movie movie = Movie.decodeStream(is);
		Log.i("movie duration", String.valueOf(android.os.SystemClock.uptimeMillis()));
		
	        movie.setTime(movie_pos);
	        movie.draw(c, 100, 100);
	        if(movie_pos >= movie.duration())
	        	movie_pos = 0;
	        movie_pos += 60; */
	    

		//asteroid drawing
		//if(screen_level > 1)
		for(int ast=0 ; ast<3; ast++)
		{
		c.drawBitmap(mAsteroids[asteroid_count[ast]], (int)asteroidX[ast], (int)asteroidY[ast], paint);
		if(asteroid_count[ast] == 11)
		{
			asteroid_count[ast] = 0;
		}
		else 
			asteroid_count[ast]++;
		double random = Math.random();
		if(asteroidY[ast] < 700)
			asteroidY[ast] += ((int)(random*100))%9 + 1;
		else 
			asteroidY[ast] = Math.random()*100;
		random = Math.random();
		if(asteroidX[ast] < 460)
			asteroidX[ast] += ((int)(random*100))%9 + 1;
		else 
			asteroidX[ast] = Math.random()*100*ast;
		}
		//asteroid_draw(c, 0);
		
		//
		paint.setColor(Color.CYAN);
		paint.setStrokeWidth(4);
		if((screen_level != 3))
		{
		c.drawPoint(leftX, leftY, paint);
		c.drawPoint(leftX, contactY, paint);
		c.drawPoint(contactX, leftY, paint);
		c.drawPoint(contactX, contactY, paint);
		}
		
		c.drawBitmap(clouds[0], cloudX[0], cloudY[0], paint);
		c.drawBitmap(clouds[1], cloudX[1], cloudY[1], paint);
		c.drawBitmap(clouds[2], cloudX[2], cloudY[2], paint);
		cloudX1();
	
			//Camera camera = new Camera();
			//Matrix matrix = c.getMatrix();
			c.save();
		if(rotate_R_down )
		{
			
			c.rotate(deg, leftX+image_width/2, leftY+image_height/2);
			//bitmap.draw(c);
			deg++;
			
		}
		else if(rotate_R_up)
		{
			
			c.rotate(deg, leftX+image_width/2, leftY+image_height/2);
			//bitmap.draw(c);
			deg--;
		}
		else if(rotate_L_up)
		{
			
			c.rotate(deg, leftX+image_width/2, leftY+image_height/2);
			//bitmap.draw(c);
			deg++;
		}
		else if(rotate_L_down)
		{
			
			c.rotate(deg, leftX+image_width/2, leftY+image_height/2);
			//bitmap.draw(c);
			deg--;
		}
		else
			{
			//bitmap.draw(c);
			}
		if(screen_level<3)
		bitmap.draw(c);
		else if(screen_level==3 && leftY >150)
			bitmap.draw(c);
		c.restore();
		update(c);
		paint.setColor(Color.RED);
		line_update(c,paint);
		
		Log.i(" -------- ", String.valueOf(rotate_R_down));
		invalidate();
	
	}
	
public void update(Canvas c)
{
	if(collision_detection(c))
	{
		//speedX = -5;
		//speedY = 1;
	}
	if((screen_level == 3) && leftY < 400 && leftY > 100)
	{
		leftX = 240;
		image_width = (float) (origWidth * ((float)(leftY-100)/300.0));
		image_height = (float) (origHeight * ((float)(leftY-100)/300.0));
	}
	else
		leftX += speedX;
	leftY += speedY;
	contactX = leftX + image_width;//+ 50;
	contactY = leftY + image_height;// 50;
	
	
	
	Log.i("co-ordinates", String.valueOf(contactX) + " " + String.valueOf(contactY) + " " + String.valueOf(collision_detection(c)) + " " + String.valueOf(presentX) + "," + String.valueOf(presentY) + " " + String.valueOf(previousX) + "," + String.valueOf(previousY) );
}
public void line_update(Canvas c, Paint paint)
{
	c.drawLine(lineLX,lineLY,lineRX,lineRY,paint);
	}

public boolean collision_detection(Canvas c){
	//synchronized(mHolder)
{

	float B = previousX - presentX;
	float A = presentY - previousY;
	float C = previousY*(presentX - previousX) + previousX*(previousY - presentY);
	float DisDown;
	float DisUp;
	float Num;
	float Denom;
	
	float slope_check = (previousY-presentY)/(previousX-presentX);
	// edge detection starts
	if(leftX < 0)
	{
		leftX = 440;
		
	}
	else if(leftX > 440)
	{
		leftX = 40;
		
	}
	else if(leftY < 0)
	{
		leftY = 720;
		if(screen_level < 3)
		screen_level ++;
	}
	else if(leftY > 760)
	{
		leftY = 40;
		
	} 
	
	// edge detection ends
	
	// collision detection starts
	
	if(slope_check < 0.0)
	{
		Num = (A*contactX) + (B*contactY) + C;
		Num = (Num < 0) ? (Num*-1) : Num;
		Denom = (float) Math.sqrt((A*A) + (B*B));
		DisDown = Num/Denom;
		
		Num = (A*leftX) + (B*leftY) + C;
		Num = (Num < 0) ? (Num*-1) : Num;
		DisUp = Num/Denom;
		
	float region_L = (contactY-presentY) -((contactX-presentX)*((previousY-presentY)/(previousX-presentX)));
	float region_R = (leftY-presentY) -((leftX-presentX)*((previousY-presentY)/(previousX-presentX)));
	if((region_L > 0.0) && (region_R < 0.0))
		{
		collided = true;
		if(DisDown < DisUp)
		{
		speedX = -5;
		speedY = -3;
		rotate_R_down = true;
		rotate_R_up = rotate_L_up = rotate_L_down = false;
		}
		else if(DisUp < DisDown)
		{
			speedX = 5;
			speedY = 3;
			rotate_R_up = true;
			rotate_R_down = rotate_L_up = rotate_L_down = false;	
		}
		}
	else 
		collided = false;
	}
	
	else if(slope_check > 0.0)
	{
		Num = (A*leftX) + (B*contactY) + C;
		Num = (Num < 0) ? (Num*-1) : Num;
		Denom = (float) Math.sqrt((A*A) + (B*B));
		DisDown = Num/Denom;
		
		Num = (A*contactX) + (B*leftY) + C;
		Num = (Num < 0) ? (Num*-1) : Num;
		DisUp = Num/Denom;
		
		Log.i("Distance check: ", String.valueOf(DisDown) + " " + String.valueOf(DisUp));
		
		float region_L = (contactY-presentY) -((leftX-presentX)*((previousY-presentY)/(previousX-presentX)));
		float region_R = (leftY-presentY) -((contactX-presentX)*((previousY-presentY)/(previousX-presentX)));
		if((region_L > 0.0) && (region_R < 0.0))
			{
			collided = true;
			if(DisDown < DisUp)
			{
			speedX = 5;
			speedY = -3;
			rotate_L_down = true;
			rotate_R_up = rotate_L_up = rotate_R_down = false;
			}
			else if(DisUp < DisDown)
			{
				speedX = -5;
				speedY = 3;
				rotate_L_up = true;
				rotate_R_up = rotate_R_down = rotate_L_down = false;	
			}
			}
		
		else 
			collided = false;
	}
	else
	{
		collided = false;
	}
	
	// collision detection ends
	return collided;
	
}
}
@Override
public boolean onTouchEvent(MotionEvent event)
{
	previousX = presentX;
	previousY = presentY;
	 presentX = event.getX();
	 presentY = event.getY();
	if(event.getAction() == MotionEvent.ACTION_MOVE)
	{
		lineLX = previousX;
		lineLY = previousY;
		lineRX = presentX;
		lineRY = presentY;
	}
	
return true;	
}

@Override
public boolean isRunning() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void start() {
	// TODO Auto-generated method stub
	
}

@Override
public void stop() {
	// TODO Auto-generated method stub
	
}

public void cloudX1()
{
	
	for(int i=0; i<3; i++)
	{
		cloudX[i] = cloudX[i] > 400 ? 0 : cloudX[i];
		if(cloudX[i] == 0)
		{
			rand = cloudY[0];
			cloudY[0] = cloudY[1];
			cloudY[1] = cloudY[2];
			cloudY[2] = rand;
			/*for(int j=0; j<3 ;j++)
			 {
				rand = (Math.round(System.currentTimeMillis())) % 3;
				cloudY[j] = pos[rand];
				Log.i("rand", String.valueOf(rand));
			 }*/
			
		}
	}
	
	
	
cloudX[0] += 2;
cloudX[1] += 4;
cloudX[2] += 3;
}

/*public void asteroid_draw(Canvas c, int ast)
{
		c.drawBitmap(mAsteroids[asteroid_count[ast]], (int)asteroidX[ast], (int)asteroidY[ast], paint);
		if(asteroid_count[ast] == 11)
		{
			asteroid_count[ast] = 0;
		}
		else 
			asteroid_count[ast]++;
		
		asteroidY[ast] += (double)3;
		asteroidX[ast] += (double)4;
	
}*/

}


class ShapesThread extends Thread
{
private SurfaceHolder mHolder;
private MyShapes myShapes;
private boolean _run = false;

public ShapesThread(SurfaceHolder sh, MyShapes ms)
{
	mHolder = sh;
	myShapes = ms;
	}

public void setRunning(boolean run)
{
	_run = run;
}

@Override

public void run()
{
	Canvas c;
	while(_run)
	{
		c = null;
		try{
		c = mHolder.lockCanvas(null);
		synchronized(mHolder)
		{
			myShapes.draw(c);
		}
		}finally{
			if(c!=null)
				mHolder.unlockCanvasAndPost(c);
		}
	}
	}

}