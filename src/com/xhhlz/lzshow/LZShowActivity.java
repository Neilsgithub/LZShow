package com.xhhlz.lzshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xhhlz.lzshow.R;
import com.xhhlz.lzshow.provider.RecordUtil;

/**
 * 这个界面是LZShow的主界面
 * 
 * @author grace
 * 
 */
public class LZShowActivity extends Activity {
	private Button train_button;
	private Button practice_button;
	private Button record_button;
	private Button about_button;
	private Button intro_button;
	private TextView days;
	private TextView total;
	private TextView average;

	/**
	 * 初始化各类界面元素
	 */
	private void init() {
		train_button = (Button) this.findViewById(R.id.train);
		practice_button = (Button) this.findViewById(R.id.practice);
		record_button = (Button) this.findViewById(R.id.record);
		about_button = (Button) this.findViewById(R.id.aboutButton);
		intro_button = (Button) this.findViewById(R.id.introButton);

		days = (TextView) this.findViewById(R.id.days);
		total = (TextView) this.findViewById(R.id.total);
		average = (TextView) this.findViewById(R.id.average);

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		init();

		/**
		 * 字帖练习的界面
		 */
		train_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(LZShowActivity.this,
						PractiseActivity.class);
				startActivity(intent);
			}

		});

		/**
		 * 个人秀的界面
		 */
		practice_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LZShowActivity.this, PracticeActivity.class);
				startActivity(intent);

			}

		});

		/**
		 * 启动练字记录的界面
		 */
		record_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(LZShowActivity.this, RecordActivity.class);
				startActivity(intent);
			}

		});

		intro_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(LZShowActivity.this, HelpActivity.class);
				startActivity(intent);
			}

		});

		about_button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LZShowActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int todaySum = RecordUtil.queryTodayFontSum(LZShowActivity.this);
		int totalSum = RecordUtil.queryFontSum(LZShowActivity.this);
		int recordSum = RecordUtil.queryRecordRows(LZShowActivity.this);
		days.setText(todaySum + "");
		total.setText(totalSum + "");
		average.setText((recordSum == 0 ? recordSum : totalSum / recordSum)
				+ "");
	}
}