package com.example.mockups;




import android.app.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
int state = 0;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	
	public void intention(View v) {
		
		
		Button b = (Button) findViewById(R.id.intention);
		if (state == 0) {
			b.setText("Sport");
			b.setBackgroundResource(R.drawable.red_enabled);
			state++;
		} else {
			if (state == 1) {
				b.setText("Alltag");
				b.setBackgroundResource(R.drawable.blue_enabled);
				state++;
			} else {
				if (state == 2) {
					b.setText("Freizeit");
					b.setBackgroundResource(R.drawable.green_enabled);
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
