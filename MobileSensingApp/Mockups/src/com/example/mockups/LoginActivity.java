package com.example.mockups;

import android.app.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
private static final String SPORT = "Sport";
int state = 0;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	
	public void intention(View v) {
		
	
		
		
		Button b = (Button) findViewById(R.id.intention);
		
		b.setText(R.string.app_name);
		
		if(b.getText().equals(SPORT))
		{
			
		}
		
		if (state == 0) {
			b.setText(SPORT);
			state++;
		} else {
			if (state == 1) {
				b.setText("Alltag");
				state++;
			} else {
				if (state == 2) {
					b.setText("Freizeit");
					state = 3;

				} else {
					if (state == 3) {
						b.setText("None");
						state = 0;
					}
				}
			}
		}

	}
}
