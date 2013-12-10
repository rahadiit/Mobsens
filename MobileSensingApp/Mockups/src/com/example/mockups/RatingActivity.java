package com.example.mockups;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class RatingActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
	}
//
//	public void radioButton(View v)
//	{
//
//		RadioButton radio0 = (RadioButton) findViewById(R.id.radio0);
//		RadioButton radio1 = (RadioButton) findViewById(R.id.radio1);
//
//		if (v.getId() == R.id.radio0)
//		{
//			if (radio0.isChecked() == true)
//			{
//				radio0.setChecked(false);
//				radio1.setChecked(true);
//			}
//			else
//			{
//				radio0.setChecked(true);
//				radio1.setChecked(false);
//			}
//			// if((c.isChecked() != true)){
//			// c.setChecked(true);
//			// b.setChecked(false);
//			// }
//		}
//
//		if (v.getId() == R.id.radio1)
//		{
//			if (radio1.isChecked() == true)
//			{
//				radio1.setChecked(false);
//				radio0.setChecked(true);
//			}
//			else
//			{
//				// if((b.isChecked() != true)){
//				radio1.setChecked(true);
//				radio0.setChecked(false);
//			}
//		}
//
//	}
}
