package com.xhhlz.lzshow;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xhhlz.lzshow.provider.RecordUtil;
import com.xhhlz.lzshow.ui.ChartView;

/**
 * 这个是练字记录的界面
 * 
 * @author grace
 * 
 */
public class RecordActivity extends Activity {
	private ChartView mychartView;
	private Button preMonthButton;
	private Button nextMonthButton;
	private TextView recorddateTextView;

	private int curYear;// 当前年份
	private int curMonth;// 当前月份
	SimpleDateFormat dateFormat;// 格式化日期信息，将其格式化为yyyy-dd的形式

	private void init() {
		mychartView = (ChartView) this.findViewById(R.id.recordChart);
		preMonthButton = (Button) this.findViewById(R.id.monthPre);
		nextMonthButton = (Button) this.findViewById(R.id.monthNext);
		recorddateTextView = (TextView) this.findViewById(R.id.recorddate);

		dateFormat = new SimpleDateFormat("yyyy-MM");

		/* 初始化recorddateTextView上的信息 */
		Date date = new Date();
		String temp = dateFormat.format(date);
		recorddateTextView.setText(temp);

		String[] data = temp.split("-");
		/* 初始化当前年份和月份 */
		curYear = Integer.parseInt(data[0]);
		curMonth = Integer.parseInt(data[1]);

		int tempdata[] = RecordUtil.queryWriteSumByDate(curYear, curMonth,
				RecordActivity.this);

		mychartView.setData(tempdata);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.record);

		init();
		addAllListener();

	}

	private void addAllListener() {
		preMonthButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (curMonth == 1) {
					curYear = curYear - 1;
					curMonth = 12;
				} else {
					curMonth = curMonth - 1;
				}

				String temp = curYear + "-"
						+ ((curMonth < 10) ? "0" + curMonth : curMonth);
				recorddateTextView.setText(temp);

				int tempdata[] = RecordUtil.queryWriteSumByDate(curYear,
						curMonth, RecordActivity.this);
				mychartView.setData(tempdata);
			}
		});

		nextMonthButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (curMonth == 12) {
					curYear = curYear + 1;
					curMonth = 1;
				} else {
					curMonth = curMonth + 1;
				}

				String temp = curYear + "-"
						+ ((curMonth < 10) ? "0" + curMonth : curMonth);
				recorddateTextView.setText(temp);

				int tempdata[] = RecordUtil.queryWriteSumByDate(curYear,
						curMonth, RecordActivity.this);
				mychartView.setData(tempdata);
			}

		});
	}
}
