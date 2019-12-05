package io.github.ITMO.weather_bot.model.weather_forecast;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.asif.gsonpojogenerator")
public class Wind implements Serializable {

	@SerializedName("deg")
	private double deg;

	@SerializedName("speed")
	private double speed;

	public void setDeg(double deg){
		this.deg = deg;
	}

	public double getDeg(){
		return deg;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public double getSpeed(){
		return speed;
	}
}