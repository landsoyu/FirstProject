package com.soyu.mycall.season2;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.soyu.mycall.season2.UTIL.UIUtil;
import com.soyu.mycall.season2.UTIL.soyuPreference;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mycallActivity extends Activity{
	//  Static
	private static final String TAG = mycallActivity.class.getSimpleName();

	//  UI
	private mycallActivity activity;
	private Context context;
	private soyuPreference soyudata;
	private ListView lv_number_list;
	private TextView ll_number_list_empty, tv_add;
	private SlidingUpPanelLayout layout;
	private EditText et_number;
	private Button bt_regist;

	//	Data
	private List<String> numbers;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);

		context = activity = this;
		getNumber();

		lv_number_list = findViewById(R.id.lv_number_list);
		ll_number_list_empty = findViewById(R.id.ll_number_list_empty);
		listViewCheck();

		lv_number_list.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return numbers.size();
			}
			@Override
			public Object getItem(int i) {
				return null;
			}
			@Override
			public long getItemId(int i) {
				return 0;
			}
			@Override
			public View getView(int i, View view, ViewGroup viewGroup) {
				view = getLayoutInflater().inflate(R.layout.layout_list_item, viewGroup, false);
				TextView tv_list_item = view.findViewById(R.id.tv_list_item);
				tv_list_item.setText(numbers.get(i));
				return view;
			}
		});
		lv_number_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				TextView tv_list_item = view.findViewById(R.id.tv_list_item);
				final String click_number = tv_list_item.getText().toString();

				UIUtil.viewDialog(context,
						"전화번호 삭제",
						click_number+" 번호를 삭제 하시겠습니까?",
						getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Log.e(TAG, "click_number : "+click_number);
								numberRemove(click_number);
							}
						},
						getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
							}
						},
						null,
						null
				);
				return false;
			}
		});

		layout = findViewById(R.id.main_frame);
		tv_add = findViewById(R.id.tv_add);
		tv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				SlidingUpPanelLayout.PanelState state = layout.getPanelState();
				if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
					layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
				}

				if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
					layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
				}
			}
		});

		et_number = findViewById(R.id.et_number);
		bt_regist = findViewById(R.id.bt_regist);
		bt_regist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (et_number.getText().toString().equals("")) {
					Toast.makeText(context, "번호가 없어서 등록이 안됩니다.", Toast.LENGTH_SHORT).show();
				} else {
					numberRegist(et_number.getText().toString());
					UIUtil.hideKeyboard(activity);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									if (layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
										layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
									}
									et_number.setText("");
								}
							}, 100);
						}
					});


				}
			}
		});

	}

	private void listViewCheck() {
		if ( numbers.size() > 0 ) {
			lv_number_list.setVisibility(View.VISIBLE);
			ll_number_list_empty.setVisibility(View.GONE);
		} else {
			ll_number_list_empty.setVisibility(View.VISIBLE);
			lv_number_list.setVisibility(View.GONE);
		}
	}

	private void numberRegist(String number) {
		for (String num: numbers) {
			if (num.equals(number)) {
				Toast.makeText(context, "동일한 번호가 등록 되어있습니다.", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		numbers.add(number);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((BaseAdapter)lv_number_list.getAdapter()).notifyDataSetChanged();
				listViewCheck();
			}
		});

		soyudata.put(soyuPreference.PHONE_NUMBER, convertArrayListToStringAppend(numbers));
	}
	private void numberRemove(String number) {
		numbers.remove(number);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((BaseAdapter)lv_number_list.getAdapter()).notifyDataSetChanged();
				listViewCheck();
			}
		});

		soyudata.put(soyuPreference.PHONE_NUMBER, convertArrayListToStringAppend(numbers));
	}


	private String convertArrayListToStringAppend(List<String> strings) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String item : strings) {
			stringBuilder.append(item+",");
		}
		String strAppend = stringBuilder.toString();
		System.out.println("convertArrayListToString: strAppend = " + strAppend);
		return strAppend;
	}

	private void getNumber() {
		soyudata = new soyuPreference(context);
		String inputNumber = soyudata.getValue(soyuPreference.PHONE_NUMBER, null);

		numbers = new ArrayList<>();
		if (inputNumber != null) {
			String[] numberlist = inputNumber.split(",");
			for (int i=0;i<numberlist.length;i++) {
				numbers.add(numberlist[i]);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
