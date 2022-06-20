package com.soyu.mycall.season3

import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.soyu.mycall.season3.UTIL.UIUtil

class MainActivity : AppCompatActivity() {

    private val TAG: String = com.soyu.mycall.season3.MainActivity::class.java.getSimpleName()

    private var activity: MainActivity? = null
    internal var numbers: ArrayList<String>? = null

    private var lv_number_list: ListView? = null
    private var ll_number_list_empty: TextView? = null
    private var tv_add:TextView? = null
    private var layout: SlidingUpPanelLayout? = null
    private var et_number: EditText? = null
    private var bt_regist: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_main)

        activity = this;

        layout = findViewById<SlidingUpPanelLayout>(R.id.main_frame) as SlidingUpPanelLayout
        tv_add = findViewById<TextView>(R.id.tv_add) as TextView
        tv_add!!.setOnClickListener(View.OnClickListener {
            val state: PanelState = layout!!.getPanelState()
            if (state == PanelState.COLLAPSED) {
                layout!!.setPanelState(PanelState.ANCHORED)
            }
            if (state == PanelState.EXPANDED) {
                layout!!.setPanelState(PanelState.COLLAPSED)
            }
        })

        getNumber()

        lv_number_list = findViewById(R.id.lv_number_list) as ListView
        ll_number_list_empty = findViewById(R.id.ll_number_list_empty) as TextView
        listViewCheck()

        lv_number_list!!.adapter = ListExampleAdapter(this)

        lv_number_list!!.onItemLongClickListener =
            OnItemLongClickListener { adapterView, view, i, l ->
                val tv_list_item = view.findViewById<TextView>(R.id.tv_list_item)
                val click_number = tv_list_item.text.toString()
                UIUtil.viewDialog(
                    this,
                    "전화번호 삭제",
                    "$click_number 번호를 삭제 하시겠습니까?",
                    resources.getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, which ->
                        Log.e( TAG, "click_number : $click_number")
                        numberRemove(click_number)
                    },
                    resources.getString(R.string.cancel),
                    DialogInterface.OnClickListener { dialogInterface, i -> },
                    null,
                    null
                )
                false
            }

        et_number = findViewById(R.id.et_number) as EditText
        bt_regist = findViewById(R.id.bt_regist) as Button
        bt_regist!!.setOnClickListener(View.OnClickListener {
            if (et_number!!.text.toString() == "") {
                Toast.makeText(this@MainActivity, "번호가 없어서 등록이 안됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                numberRegist(et_number!!.text.toString())
                UIUtil.hideKeyboard(activity!!)
                runOnUiThread {
                    Handler().postDelayed({
                        if (layout!!.panelState == PanelState.EXPANDED) {
                            layout!!.panelState = PanelState.COLLAPSED
                        }
                        et_number!!.setText("")
                    }, 100)
                }
            }
        })


    }
    inner class ListExampleAdapter(context: Context) : BaseAdapter() {
        private val mInflator: LayoutInflater

        init {
            this.mInflator = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            Log.e(TAG, "numbers size : "+numbers!!.size);
            if (numbers == null) {
                return 0
            } else {
                return numbers!!.size
            }
        }

        override fun getItem(position: Int): Any {
            return numbers!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var myConvertView: View? = convertView

            myConvertView = mInflator.inflate(R.layout.layout_list_item, parent, false)
            var tv_list_item: TextView? = myConvertView?.findViewById<TextView>(R.id.tv_list_item)
            Log.e(TAG, "tv_list_item : "+tv_list_item);
            Log.e(TAG, "numbers : "+numbers);
            tv_list_item!!.text = numbers!![position]

            return myConvertView
        }
    }


    private fun getNumber() {
        numbers = ArrayList<String>()
        numbers!!.clear()
        val inputNumber: String = MyApplication.prefs.getValue("PHONE_NUMBER", "")
        if (inputNumber != "") {
            Log.e(TAG, "numbeinputNumber : "+inputNumber);
            val numberlist = inputNumber.split(",".toRegex()).toTypedArray()
            for (i in numberlist.indices) {
                if (numberlist[i] != "") {
                    numbers!!.add(numberlist[i])
                }
            }
        }

        Log.e(TAG, "numbers : "+numbers!!.size);
    }


    private fun listViewCheck() {
        if (numbers!!.size > 0) {
            lv_number_list!!.visibility = View.VISIBLE
            ll_number_list_empty!!.visibility = View.GONE
        } else {
            ll_number_list_empty!!.visibility = View.VISIBLE
            lv_number_list!!.visibility = View.GONE
        }
    }

    private fun numberRegist(number: String) {
        for (num in numbers!!) {
            if (num == number) {
                Toast.makeText(this@MainActivity, "동일한 번호가 등록 되어있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
        numbers!!.add(number)
        runOnUiThread {
            (lv_number_list!!.adapter as BaseAdapter).notifyDataSetChanged()
            listViewCheck()
        }
        MyApplication.prefs.put("PHONE_NUMBER", convertArrayListToStringAppend()!!)
    }
    private fun convertArrayListToStringAppend(): String? {
        val stringBuilder = StringBuilder()
        for (item in numbers!!) {
            stringBuilder.append("$item,")
        }
        val strAppend = stringBuilder.toString()
        println("convertArrayListToString: strAppend = $strAppend")
        return strAppend
    }

    private fun numberRemove(number: String) {
        numbers!!.remove(number)
        runOnUiThread {
            (lv_number_list!!.adapter as BaseAdapter).notifyDataSetChanged()
            listViewCheck()
        }
        MyApplication.prefs.put("PHONE_NUMBER", convertArrayListToStringAppend()!!)
    }

}