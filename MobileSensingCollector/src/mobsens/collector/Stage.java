package mobsens.collector;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import mobsens.collector.intents.IntentExternalize;
import mobsens.collector.intents.IntentUpload;
import mobsens.collector.util.Dialogs;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Stage extends Activity
{
	private static final String PREFERENCES_NAME = "mscprefs";

	private static final String PREFERENCE_EMAIL = "mscemail";

	private static final String PREFERENCE_PASSWORD = "mscpwd";

	private final static long UPDATE_INTERVAL = 1000L;

	private final Runnable UPDATE_ENDPOINT = new Runnable()
	{
		@Override
		public void run()
		{
			updateFileList();

			updateHandler.postDelayed(this, UPDATE_INTERVAL);
		}
	};

	private Button buttonStageAllToLocal;
	private Button buttonStageAllToWeb;
	private Button buttonStageRemoveAll;

	private ListView listViewStageList;

	private ArrayAdapter<File> fileAdapter;

	private Handler updateHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage);

		// Views finden
		buttonStageAllToLocal = (Button) findViewById(R.id.stage_allToLocal);
		buttonStageAllToWeb = (Button) findViewById(R.id.stage_allToWeb);
		buttonStageRemoveAll = (Button) findViewById(R.id.stage_removeAll);
		listViewStageList = (ListView) findViewById(R.id.stage_list);

		// Adapter erstellen
		fileAdapter = new ArrayAdapter<File>(this, R.layout.layout_stageitem)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// Quellitem
				final File file = getItem(position);

				final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View rowView = inflater.inflate(R.layout.layout_stageitem, parent, false);

				final TextView textViewStageName = (TextView) rowView.findViewById(R.id.stage_name);
				final TextView textViewStageSize = (TextView) rowView.findViewById(R.id.stage_size);
				final Button buttonStageView = (Button) rowView.findViewById(R.id.stage_view);
				final Button buttonStageToLocal = (Button) rowView.findViewById(R.id.stage_toLocal);
				final Button buttonStageToWeb = (Button) rowView.findViewById(R.id.stage_toWeb);
				final TextView textViewStageTime = (TextView) rowView.findViewById(R.id.stage_time);
				final TextView textViewStageDate = (TextView) rowView.findViewById(R.id.stage_date);
				final Button buttonStageRemove = (Button) rowView.findViewById(R.id.stage_remove);

				textViewStageName.setText(file.getName());
				textViewStageSize.setText((Math.round(file.length() * 100.0 / 1024.0) / 100.0) + "kb");
				textViewStageTime.setText(String.format(Locale.ENGLISH, "%tT", file.lastModified()));
				textViewStageDate.setText(String.format(Locale.ENGLISH, "%tF", file.lastModified()));

				buttonStageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						executeView(file);
					}
				});
				buttonStageToLocal.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						executeToLocal(file);
					}
				});

				buttonStageToWeb.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						executeToWeb(file);
					}
				});

				buttonStageRemove.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Dialogs.yesNo(Stage.this, "Confirm delete", "Delete " + file.getName() + "?", "Ok", "Cancel", new Runnable()
						{
							@Override
							public void run()
							{
								executeRemove(file);
							}
						});
					}
				});

				return rowView;
			}
		};

		// Handler mit lokalem Looper erstellen
		updateHandler = new Handler();

		buttonStageAllToLocal.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				for (File file : stageFiles())
				{
					executeToLocal(file);
				}
			}
		});

		buttonStageAllToWeb.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				for (File file : stageFiles())
				{
					executeToWeb(file);
				}
			}
		});

		buttonStageRemoveAll.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Dialogs.yesNo(Stage.this, "Confirm delete", "Delete all staged files?", "Ok", "Cancel", new Runnable()
				{
					@Override
					public void run()
					{
						for (File file : stageFiles())
						{
							executeRemove(file);
						}
					}
				});
			}
		});

		// Adapter einsetzen
		listViewStageList.setAdapter(fileAdapter);

		// Update Beginnen
		updateHandler.postDelayed(UPDATE_ENDPOINT, 0);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		updateHandler.removeCallbacks(UPDATE_ENDPOINT);
	}

	protected void updateFileList()
	{
		Set<File> files = new HashSet<File>(stageFiles());

		// Difference-Sets
		for (int i = 0; i < fileAdapter.getCount(); i++)
		{
			final File candidate = (File) fileAdapter.getItem(i);

			if (files.contains(candidate))
			{
				files.remove(candidate);
			}
			else
			{
				fileAdapter.remove(candidate);
				i--;
			}
		}

		// Adapter befüllen
		for (File file : files)
		{
			fileAdapter.add(file);
		}

		fileAdapter.sort(new Comparator<File>()
		{
			@Override
			public int compare(File lhs, File rhs)
			{
				return Long.signum(rhs.lastModified() - rhs.lastModified());
			}
		});
	}

	private List<File> stageFiles()
	{
		return Arrays.asList(new File(getFilesDir(), "wfj").listFiles());
	}

	private void executeView(File file)
	{
		Viewer.showActivity(this, file);
	}

	private void executeToLocal(final File file)
	{
		if (file.exists())
		{
			final File target = new File(getExternalFilesDir(null), file.getName());

			IntentExternalize.startService(Stage.this, file.getName(), file, target);
		}
	}

	private void executeToWeb(File file)
	{
		if (file.exists())
		{
			final SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

			IntentUpload.startService(Stage.this, file.getName(), "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json", sp.getString(PREFERENCE_EMAIL, ""),
					sp.getString(PREFERENCE_PASSWORD, ""), "http://mobilesensing.west.uni-koblenz.de/recordings/upload", file, "application/text", "*/*");
		}
	}

	private void executeRemove(File file)
	{
		if (file.exists())
		{
			file.delete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stage, menu);
		return true;
	}

}
