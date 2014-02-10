package com.example.mockups;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RatingActivity extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
	}
	
	public void radioButton(View v){
		
		RadioButton b = (RadioButton) findViewById(R.id.radio1);
		RadioButton c = (RadioButton) findViewById(R.id.radio0);
		
		if (v.getId() == R.id.radio0){
			if(c.isChecked() == true){
				c.setChecked(false);
				b.setChecked(true);
			}else {
				c.setChecked(true);
				b.setChecked(false);
			}
//			if((c.isChecked() != true)){
//				c.setChecked(true);
//				b.setChecked(false);
//			}
		}
		
		if(v.getId() == R.id.radio1){
			if(b.isChecked() == true){
				b.setChecked(false);
				c.setChecked(true);
			}else {
//			if((b.isChecked() != true)){
				b.setChecked(true);
				c.setChecked(false);
			}
		}
		
	}
}
