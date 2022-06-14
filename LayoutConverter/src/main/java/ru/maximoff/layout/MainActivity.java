package ru.maximoff.layout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity {
	private EditText ru;
	private EditText en;
	private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		final Utils utils = new Utils();
		ru = findViewById(R.id.mainEditText1);
		en = findViewById(R.id.mainEditText2);
		ru.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
				}

				@Override
				public void afterTextChanged(Editable p1) {
					if (ru.isFocused()) {
						en.setText(utils.convert(p1.toString(), true));
					}
				}
			});
		en.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
				}

				@Override
				public void afterTextChanged(Editable p1) {
					if (en.isFocused()) {
						ru.setText(utils.convert(p1.toString(), false));
					}
				}
			});
		ru.requestFocus();
		onNewIntent(getIntent());
    }

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SEND)) {
			final String text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT).toString();
			new AlertDialog.Builder(this)
				.setItems(getResources().getStringArray(R.array.variants), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface p1, int p2) {
						if (p2 == 0) {
							ru.requestFocus();
							ru.setText(text);
							ru.setSelection(text.length());
						} else {
							en.requestFocus();
							en.setText(text);
							en.setSelection(text.length());
						}
						p1.dismiss();
					}
				})
				.create()
				.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.reset:
				ru.setText("");
				en.setText("");
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (exit) {
			finish();
			return;
		}
		exit = true;
		Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;                       
				}
			}, 2000);
	}
}
