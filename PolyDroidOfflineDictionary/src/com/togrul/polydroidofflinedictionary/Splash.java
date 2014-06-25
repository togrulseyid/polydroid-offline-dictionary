package com.togrul.polydroidofflinedictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author Togrul Seyidov
 * @version 1.0.0
 * @see <a href="http://www.polydroid.info">PolyDroid.info</a>
 * This is Splash screen  
 * */

public class Splash extends Activity{
	
	@Override
	protected void onCreate(Bundle TravisLoveBacon) {
		super.onCreate(TravisLoveBacon);
		setContentView(R.layout.splash);
		
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		ImageView sponsor1=(ImageView)findViewById(R.id.splash);
		
		Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
		a.reset();
		sponsor1.clearAnimation();
		sponsor1.startAnimation(a);

		
		a.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}
			
			public void onAnimationRepeat(Animation animation) {
			}
			
			public void onAnimationEnd(Animation animation) {
				Intent PolyDroidActivity = new Intent("com.togrul.polydroidofflinedictionary.POLYDROIDACTIVITY");
				startActivity(PolyDroidActivity);
				finish();
			}
		});
		
	}
}