package com.example.shaketest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener{

	/**
	 * 传感器管理器
	 */
	private SensorManager  sensorManager;
	/**
	 * 传感器
	 */
	private Sensor sensor;
	/**
	 * 自定义重力感应监听接口
	 */
	private OnShakeListener onShakeListener;
	/**
	 * x,y,z方向的重力加速度
	 */
	private float xValue;
	private float yValue;
	private float zValue;
	/**
	 * 上下文
	 */
	private Context mContext;
	public ShakeListener(Context c){
		mContext=c;//传递监听对象
		start();
	}
	public void start(){
		
		 // 获得传感器管理器		
		sensorManager=(SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
		
		if(sensorManager!=null){
			//如果管理器不为空，获得传感器
			sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if(sensor!=null){
			//以给定的采样频率为传感器注册监听事件
			sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
		}
		
	}
	public void stop(){
		sensorManager.unregisterListener(this);
	}
	/**
	 * 设置setOnShakeListener()方法，供外部调用
	 */
	public void setOnShakeListener(OnShakeListener listener){
		onShakeListener=listener;//传递外部接口
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		//获取加速度，加速度可能是负值，所以取其绝对值
		xValue=Math.abs(event.values[0]);
		yValue=Math.abs(event.values[1]);
		zValue=Math.abs(event.values[2]);
		if(xValue>11||yValue>11||zValue>11){
			//如果某个方向上的加速度大于11m/s2,认为可以触发摇一摇逻辑
			onShakeListener.onShake();
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自动生成的方法存根
		
	}
	/**
	 * 自定义重力感应监听接口
	 */
	public interface OnShakeListener{
		public void onShake();
	}
}
