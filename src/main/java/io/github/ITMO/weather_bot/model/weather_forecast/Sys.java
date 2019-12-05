package io.github.ITMO.weather_bot.model.weather_forecast;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.asif.gsonpojogenerator")
public class Sys implements Serializable {

	@SerializedName("pod")
	private String pod;

	public void setPod(String pod){
		this.pod = pod;
	}

	public String getPod(){
		return pod;
	}
}