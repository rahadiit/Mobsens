package mobsens.collector.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContextWrapper;
import android.content.DialogInterface;

public class Dialogs
{
	public static void yesNo(ContextWrapper context, String title, String message, String yes, String no, final Runnable onYes, final Runnable onNo)
	{
		final Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setPositiveButton(yes, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (onYes != null) onYes.run();

				dialog.dismiss();
			}
		});
		alert.setNegativeButton(no, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (onNo != null) onNo.run();

				dialog.dismiss();
			}
		});
		alert.create().show();
	}

	public static void yesNo(ContextWrapper context, String title, String message, String yes, String no, Runnable onYes)
	{
		yesNo(context, title, message, yes, no, onYes, null);
	}
}
