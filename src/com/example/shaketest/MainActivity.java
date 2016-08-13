package com.example.shaketest;

import com.example.shaketest.ShakeListener.OnShakeListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RelativeLayout shakeDown;
	private RelativeLayout shakeUp;
	private ShakeListener mShakeListener;
	/**
	 * ����
	 */
	private Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		shakeDown=(RelativeLayout)findViewById(R.id.shake_down);
		shakeUp=(RelativeLayout)findViewById(R.id.shake_up);
		vibrator=(Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
		mShakeListener=new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener(){

			@Override
			public void onShake() {
				//����ҡһҡ����
				startAnimation();
				mShakeListener.stop();
				//��ʼ��
				startVibrate();
				/**
				 * ������ʱ�̣߳�2s����ʾtoast
				 */
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "�ܱ�Ǹ����ʱû���ҵ�\n��ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", Toast.LENGTH_SHORT).show();
						//ȡ����
						vibrator.cancel();
						//��������ҡһҡ
						mShakeListener.start();
					}
					
				}, 2000);
			}
			
		});
	}

	private void startAnimation(){
		AnimationSet animUp=new AnimationSet(true);
		TranslateAnimation shakeUpAnim0=new TranslateAnimation
				(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF, 0f,Animation.RELATIVE_TO_SELF, 0f,Animation.RELATIVE_TO_SELF, -0.5f);
		shakeUpAnim0.setDuration(1000);
		TranslateAnimation shakeUpAnim1=new TranslateAnimation
				(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0.5f);
		shakeUpAnim1.setDuration(1000);
		shakeUpAnim1.setStartOffset(1000);
		animUp.addAnimation(shakeUpAnim0);
		animUp.addAnimation(shakeUpAnim1);
		shakeUp.startAnimation(animUp);
		
		AnimationSet animDown=new AnimationSet(true);
		TranslateAnimation shakeDownAnim0=new TranslateAnimation
				(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		shakeDownAnim0.setDuration(1000);
		TranslateAnimation shakeDownAnim1=new TranslateAnimation
				(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		shakeDownAnim1.setDuration(1000);
		shakeDownAnim1.setStartOffset(1000);
		animDown.addAnimation(shakeDownAnim0);
		animDown.addAnimation(shakeDownAnim1);
		shakeDown.startAnimation(animDown);
	}
	/**
	 * ������
	 */
	private void startVibrate(){
		//�������ڶ����𶯣�500�����𶯵�ʱ�䣬200�����𶯳���ʱ�䣬������msΪ��λ
		//�����500��200�Ǻ����ݣ���һ��Ͷ�һ���𶯣�-1�����ظ�
		vibrator.vibrate(new long[]{500,200,500,200,500,200,500,200}, -1);
	}
	/**
	 * �����ٵ�ʱ��Ҫ���ע�ᣬ�����𶯼�����һֱ����
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(mShakeListener!=null){
			mShakeListener.stop();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
