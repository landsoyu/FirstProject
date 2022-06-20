package com.soyu.mycall.season2.UTIL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.soyu.mycall.season2.R;

import androidx.annotation.RequiresApi;

/**
 * @author Jang-Ho Hwang, <rath@xrath.com>
 * @version 1.0,  05/01/2012, 04:51
 */
public class UIUtil {
	
    public static void setLayout(View view, boolean fillWidth, boolean fillHeight) {
        setLayout(view, fillWidth, fillHeight, 0.0f);
    }

	public static void setLayout(View view, boolean fillWidth, boolean fillHeight, float weight) {
		view.setLayoutParams(new LinearLayout.LayoutParams(
			fillWidth ? LinearLayout.LayoutParams.MATCH_PARENT: LinearLayout.LayoutParams.WRAP_CONTENT,
			fillHeight ? LinearLayout.LayoutParams.MATCH_PARENT: LinearLayout.LayoutParams.WRAP_CONTENT,
            weight
		));
	}

    public static void frame(View view, float x, float y, float width, float height) {
        frame(view, (int)x, (int)y, (int)width, (int)height);
    }
    public static void frame(View view, double x, double y, double width, double height) {
    	frame(view, (int)x, (int)y, (int)width, (int)height);
    }

    public static void frame(View view, int x, int y, int width, int height) {
        FrameLayout.LayoutParams params;
        if( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.width = width;
            params.height = height;
        } else {
            params = new FrameLayout.LayoutParams(width, height);
        }
        params.leftMargin = x;
        params.topMargin = y;
        view.setLayoutParams(params);
        view.layout(x, y, x+width, y+height);
    }
    public static void frame(View view, int width, int height) {
        LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(width, height);
        view.setLayoutParams(params);
    }
    public static void frame(View view, int width, int height, float weight) {
        LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(width, height, weight);
        view.setLayoutParams(params);
    }
    public static void frame(View view, double width, double height) {
    	frame(view, (int)width, (int)height);
    }

    public static void frameFill(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
    }
    public static void frameFillMatch(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
    }
    public static void frameMatch(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
    }

    public static void setBounds(View view, int x, int y, int w, int h) {
		if( view instanceof TextView ) {
			TextView tv = (TextView) view;
			tv.setWidth(w);
			tv.setHeight(h);
		}
		view.setLayoutParams(new AbsoluteLayout.LayoutParams(w, h, x, y));
	}

	public static String toString(View view) {
		StringBuilder sb = new StringBuilder();
		sb.append(view.getClass().getName()).append(':');
		sb.append(view.getLeft()).append(',');
		sb.append(view.getTop()).append(',');
		sb.append(view.getRight()).append(',');
		sb.append(view.getBottom()).append(" ");
		sb.append(view.getVisibility());
		return sb.toString();
	}

	public static Animation createFadeOut() {
		AlphaAnimation ani = new AlphaAnimation(0.7f, 0.0f);
		ani.setDuration(300);
//		ani.setInterpolator(new AccelerateDecelerateInterpolator());
		return ani;
	}

	public static Animation createFadeIn() {
		AlphaAnimation ani = new AlphaAnimation(0.3f, 1.0f);
		ani.setDuration(300);
//		ani.setInterpolator(new AccelerateDecelerateInterpolator());
		return ani;
	}

	public static int getBrighterColor(int value, int delta) {
		int a = (value >> 24) & 0xff;
		int r = (value >> 16) & 0xff;
		int g = (value >>  8) & 0xff;
		int b = (value >>  0) & 0xff;

		r += delta;
		g += delta;
		b += delta;

		r = safeByte(r);
		g = safeByte(g);
		b = safeByte(b);

		int newValue = (a<<24) | (r<<16) | (g<<8) | (b<<0);
		return newValue;
	}

	private static int safeByte(int value) {
		if( value < 0 ) value = 0x00;
		if( value > 0xff ) value = 0xff;
		return value;
	}

    public static void updateLinearMargin(View view, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) view.getLayoutParams();
        param.setMargins(left, top, right, bottom);
    }
    public static void updateLinearMargin(View view, double left, double top, double right, double bottom) {
    	updateLinearMargin(view, (int) left, (int) top, (int) right, (int) bottom);
    }
    public static void updateLinearMarginCenter(View view, double left, double top, double right, double bottom, boolean flag) {
    	updateLinearMarginCenter(view, (int) left, (int) top, (int) right, (int) bottom, flag);
    }
    public static void updateLinearMarginCenter(View view, int d, int e, int f, int g, boolean flag) {
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) view.getLayoutParams();
        param.setMargins(d, e, f, g);
        if (flag) {
            param.gravity = Gravity.CENTER_HORIZONTAL;
        } else {
            param.gravity = Gravity.CENTER_VERTICAL;
        }
    }
    public static void updateFrameMargin(View view, int left, int top, int right, int bottom) {
    	FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) view.getLayoutParams();
        param.setMargins(left, top, right, bottom);
    }
    public static void updateFrameMargin(View view, int left, int top, int right, int bottom, boolean flag) {
    	FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) view.getLayoutParams();
        param.setMargins(left, top, right, bottom);
        if (flag) {
            param.gravity = Gravity.TOP;
        } else {
            param.gravity = Gravity.BOTTOM;
        }
    }

    public static SpannableString makeBold(String text) {
        SpannableString str = new SpannableString(text);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, str.length(), 0);
        return str;
    }
    public static SpannableString makeBold(Context context, int textId) {
    	String text = context.getResources().getString(textId);
        SpannableString str = new SpannableString(text);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, str.length(), 0);
        return str;
    }

    public static Bitmap filterGrayscale(Bitmap b) {
        int w = b.getWidth();
        int h = b.getHeight();
        Bitmap ret = Bitmap.createBitmap(w ,h, Bitmap.Config.RGB_565);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
        p.setColorFilter(filter);

        Canvas c = new Canvas(ret);
        c.drawBitmap(b, 0, 0, p);
        b.recycle();
        return ret;
    }
    
    public static int charSize(Activity a) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int deviceWidth = displayMetrics.widthPixels;

    	return (int) (deviceWidth * 0.04 / (int)displayMetrics.density);
    }
    public static int charSize(Activity a, double value) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int deviceWidth = displayMetrics.widthPixels;

    	return (int) (deviceWidth * value / displayMetrics.density);
    }
    public static int charSize(Context context, double value) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int deviceWidth = displayMetrics.widthPixels;

    	return (int) (deviceWidth * value / displayMetrics.density);
    }
    public static int textfielHeight(Activity a) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int deviceHeight = displayMetrics.heightPixels;
    	
    	return (int) (deviceHeight * 0.07);
    }
    public static int deviceWidth(Activity a) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int width = displayMetrics.widthPixels;
    	
    	return width;
    }
    public static int deviceWidth(Context context) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int width = displayMetrics.widthPixels;
    	
    	return width;
    }
    public static int deviceWidth(WindowManager windowManager) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    	int width = displayMetrics.widthPixels;
    	
    	return width;
    }

    public static int deviceHeight(Activity a) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int height = displayMetrics.heightPixels;
    	
    	return height;
    }
    public static int deviceHeight(Context context) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int height = displayMetrics.heightPixels;
    	
    	return height;
    }
    public static int deviceHeight(WindowManager windowManager) {
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    	int height = displayMetrics.heightPixels;
    	
    	return height;
    }
    
    
    
    
    
    public static Spannable stripUnderlines(TextView textView) {
    	CharSequence str = textView.getText();
    	try {
        	Spannable s = (Spannable)str;
            URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
            for (URLSpan span: spans) {
                int start = s.getSpanStart(span);
                int end = s.getSpanEnd(span);
                s.removeSpan(span);
                span = new URLSpanNoUnderline(span.getURL());
                s.setSpan(span, start, end, 0);
            }
            return s;
    	} catch (ClassCastException e) {
//    		Log.e("Flitto", e.getMessage());
    	}
    	return new SpannableString(textView.getText().toString());
    }
    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    //	soyu add 07/18/2013
    public static void frameLeftButton(View view, int x, int y, int width, int height, boolean flag) {
        FrameLayout.LayoutParams params;
        if( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.width = width;
            params.height = height;
        } else {
            params = new FrameLayout.LayoutParams(width, height);
        }
        params.bottomMargin = y;
        if ( flag ) {
            params.leftMargin = x;
            params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        } else {
            params.rightMargin = x;
            params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        }
        view.setLayoutParams(params);
        view.layout(x, y, x+width, y+height);
    }
    //	soyu add 07/18/2013
    public static TranslateAnimation getButtonAnimation(int point1, int point2, int duration) {
        TranslateAnimation move = new TranslateAnimation(0, 0, point1, point2);
        move.setDuration(duration);
    	return move;
    }
    
    //	soyu add 08/06/2013
    public static RotateAnimation getStartButtonAnination(int point1, int point2, int duration) {
    	RotateAnimation rotate = new RotateAnimation(point1, point2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	rotate.setDuration(duration);
    	return rotate;
    }

    //	soyu add 08/12/2013
    public static String localizedFormatTime(int langId, Date item) {
        String langCode = "en";	// default

        String time;
        long l = System.currentTimeMillis() - item.getTime();
        long min = l / 1000L / 60L;
        if (l < 60000L) {
            time = "now";
        } else if (min < 60) {
        	String localizedMin = null;
        	if (langCode.equals("ja") || langCode.equals("zh-CN")) {
        		localizedMin = "???";
        	} else if (langCode.equals("ko")) {
        		localizedMin = "�?";
        	} else if (langCode.equals("ru")) {
        		localizedMin = "мин";
        	} else if (langCode.equals("th")) {
        		localizedMin = "�?า�??�?";
        	} else {
        		localizedMin = "m";
        	}
            time = String.format("%d%s", min, localizedMin);
        } else if (min < 60 * 24) {
        	String localizedHour = null;
        	if (langCode.equals("id")) {
        		localizedHour = "jam";
        	} else if (langCode.equals("ja")) {
        		localizedHour = "??????";
        	} else if (langCode.equals("ko")) {
        		localizedHour = "???�?";
        	} else if (langCode.equals("ru")) {
        		localizedHour = "??";
        	} else if (langCode.equals("th")) {
        		localizedHour = "�?�?.";
        	} else if (langCode.equals("zh-CN")) {
        		localizedHour = "???";
        	} else {
        		localizedHour = "h";
        	}
            time = String.format("%d%s", min / 60L, localizedHour);
        } else {
        	SimpleDateFormat localizedFormat = null;
        	if (langCode.equals("ja") || langCode.equals("zh-CN")) {
        		time = (item.getMonth() + 1) + "??? " + item.getDate() + "???";
        	} else if (langCode.equals("ko")) {
        		time = (item.getMonth() + 1) + "??? " + item.getDate() + "???";
        	} else if (langCode.equals("de")) {
        		localizedFormat = new SimpleDateFormat("dd MMM", Locale.GERMAN);
        		time = localizedFormat.format(item);
        	} else if (langCode.equals("es")) {
        		String localizedMonth[] = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
        		time = item.getDate() + " " + localizedMonth[item.getMonth()];
        	} else if (langCode.equals("fr")) {
        		localizedFormat = new SimpleDateFormat("dd MMM", Locale.FRENCH);
        		time = localizedFormat.format(item);
        	} else if (langCode.equals("id")) {
        		String localizedMonth[] = {"jan", "feb", "mar", "apr", "mei", "jun", "jul", "agu", "sep", "okt", "nov", "des"};
        		time = item.getDate() + " " + localizedMonth[item.getMonth()];
        	} else if (langCode.equals("it")) {
        		localizedFormat = new SimpleDateFormat("dd MMM", Locale.ITALIAN);
        		time = localizedFormat.format(item);
        	} else if (langCode.equals("pt")) {
        		String localizedMonth[] = {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};
        		time = item.getDate() + " " + localizedMonth[item.getMonth()];
        	} else if (langCode.equals("ru")) {
        		String localizedMonth[] = {"Янв", "Фев", "??а??", "??п??", "??ай", "????н", "????л", "??вг", "Сен", "??к??", "??о??", "??ек"};
        		time = item.getDate() + " " + localizedMonth[item.getMonth()];
        	} else if (langCode.equals("th")) {
        		String localizedMonth[] = {"�?.�?.", "�?.�?.", "มี.�?.", "�?�?.�?.", "�?.�?.", "มิ.�?.", "�?.�?.", "�?.�?.", "�?.�?.", "�?.�?.", "�?.�?.", "�?.�?."};
        		time = item.getDate() + " " + localizedMonth[item.getMonth()];
        	} else {
        		localizedFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        		time = localizedFormat.format(item);
        	}
        }
        return time;
    }

	public static void IntentDataView(Intent data) {
        for ( String key : data.getExtras().keySet() ) {
        	System.out.println("key = "+key);
        	System.out.println("value = "+data.getStringExtra(key));
        }
	}

    //	soyu add 07/16/2014
    public static void frameCenterButton(View view, int width, int height, boolean flag) {
        FrameLayout.LayoutParams params;
        if( view.getLayoutParams() instanceof FrameLayout.LayoutParams ) {
            params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.width = width;
            params.height = height;
        } else {
            params = new FrameLayout.LayoutParams(width, height);
        }
        if ( flag ) {
            params.gravity = Gravity.TOP | Gravity.CENTER;
        } else {
            params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        }
        params.bottomMargin = height;
        view.setLayoutParams(params);
    }


    //  20220615
    //  soyu Dialog view
    private static boolean dialogView = false;

    public static void viewDialog(Context context, String title, String message,
                                  String positiveStr, DialogInterface.OnClickListener positiveListener,
                                  String negativeStr, DialogInterface.OnClickListener negativeListener,
                                  String neutralStr, DialogInterface.OnClickListener neutralListener) {
        Log.e("!!!", "viewDialog : " + dialogView);
        if (!dialogView) {
            dialogView = true;
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(context, R.style.MyDialogTheme);
            if (title != null) {
                alt_bld.setTitle(title);
            }
            if (message != null) {
                alt_bld.setMessage(message);
            }
            if (positiveStr != null) {
                alt_bld.setPositiveButton(positiveStr, positiveListener);
            }
            if (negativeStr != null) {
                alt_bld.setNegativeButton(negativeStr, negativeListener);
            }
            if (neutralStr != null) {
                alt_bld.setNeutralButton(neutralStr, neutralListener);
            }
            AlertDialog alert = alt_bld.create();
            alert.setCancelable(false);
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Log.e("!!!", "viewDialog : " + dialogView);
                    dialogView = false;
                }
            });
            alert.show();

            TextView tv_dialog_message = (TextView) alert.findViewById(android.R.id.message);
            tv_dialog_message.setTypeface(context.getResources().getFont(R.font.applesdgothicneor));
            tv_dialog_message.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        }
    }

    /**
     * 키보드 숨기기
     *
     * @param activity 액티비티
     */
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager mgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
        }
    }

}
