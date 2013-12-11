package mobsens.collector;

import java.util.Date;

import mobsens.collector.intents.IntentAnnotation;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Fasttag extends Activity
{
	private final OnClickListener tagFromButtonText = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			IntentAnnotation.sendBroadcast(Fasttag.this, new Date(), ((Button) v).getText().toString());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fasttag);

		((Button) findViewById(R.id.fasttag_topLeft)).setOnClickListener(tagFromButtonText);
		((Button) findViewById(R.id.fasttag_topRight)).setOnClickListener(tagFromButtonText);
		((Button) findViewById(R.id.fasttag_bottomLeft)).setOnClickListener(tagFromButtonText);
		((Button) findViewById(R.id.fasttag_bottomRight)).setOnClickListener(tagFromButtonText);

		((Button) findViewById(R.id.fasttag_tag)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IntentAnnotation.sendBroadcast(Fasttag.this, new Date(), ((EditText) findViewById(R.id.fasttag_value)).getEditableText().toString());
			}
		});
	}
}
