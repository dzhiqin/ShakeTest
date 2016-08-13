package com.example.shaketest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener{

	/**
	 * ������������
	 */
	private SensorManager  sensorManager;
	/**
	 * ������
	 */
	private Sensor sensor;
	/**
	 * �Զ���������Ӧ�����ӿ�
	 */
	private OnShakeListener onShakeListener;
	/**
	 * x,y,z������������ٶ�
	 */
	private float xValue;
	private float yValue;
	private float zValue;
	/**
	 * ������
	 */
	private Context mContext;
	public ShakeListener(Context c){
		mContext=c;//���ݼ�������
		start();
	}
	public void start(){
		
		 // ��ô�����������		
		sensorManager=(SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
		
		if(sensorManager!=null){
			//�����������Ϊ�գ���ô�����
			sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if(sensor!=null){
			//�Ը����Ĳ���Ƶ��Ϊ������ע������¼�
			sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
		}
		
	}
	public void stop(){
		sensorManager.unregisterListener(this);
	}
	/**
	 * ����setOnShakeListener()���������ⲿ����
	 */
	public void setOnShakeListener(OnShakeListener listener){
		onShakeListener=listener;//�����ⲿ�ӿ�
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		//��ȡ���ٶȣ����ٶȿ����Ǹ�ֵ������ȡ�����ֵ
		xValue=Math.abs(event.values[0]);
		yValue=Math.abs(event.values[1]);
		zValue=Math.abs(event.values[2]);
		if(xValue>11||yValue>11||zValue>11){
			//���ĳ�������ϵļ��ٶȴ���11m/s2,��Ϊ���Դ���ҡһҡ�߼�
			onShakeListener.onShake();
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO �Զ����ɵķ������
		
	}
	/**
	 * �Զ���������Ӧ�����ӿ�
	 */
	public interface OnShakeListener{
		public void onShake();
	}
}
