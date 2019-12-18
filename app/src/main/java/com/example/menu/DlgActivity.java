package com.example.menu;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class DlgActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlg);

        // 获取所有的按钮对象，设置按钮监听器
        findViewById(R.id.btn_multi_dlg).setOnClickListener(this);
        findViewById(R.id.btn_list_dlg).setOnClickListener(this);
        findViewById(R.id.btn_radio_dlg).setOnClickListener(this);
        findViewById(R.id.btn_checkbox_dlg).setOnClickListener(this);
        findViewById(R.id.btn_progress_dlg).setOnClickListener(this);
        findViewById(R.id.btn_date_dlg).setOnClickListener(this);
        findViewById(R.id.btn_time_dlg).setOnClickListener(this);
        findViewById(R.id.btn_custom_dlg).setOnClickListener(this);

        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_multi_dlg:
                createMultiDlg();
                break;
            case R.id.btn_list_dlg:
                createListDlg();
                break;
            case R.id.btn_radio_dlg:
                createRadioDlg();
                break;
            case R.id.btn_checkbox_dlg:
                createCheckboxDlg();
                break;
            case R.id.btn_progress_dlg:
                createProgressDlg();
                break;
            case R.id.btn_date_dlg:
                createDateDlg();
                break;
            case R.id.btn_time_dlg:
                createTimeDlg();
                break;
            case R.id.btn_custom_dlg:
                createCoustomDlg();
                break;
        }
    }

    // 自定义布局对话框
    private void createCoustomDlg() {
        // 通过Layout的inflater创建布局视图，包括两个参数：layout文件和layout布局文件中的布局id
        View layout = getLayoutInflater().inflate(R.layout.login_dlg, (ViewGroup) findViewById(R.id.login_dlg));
        // 创建对话框
        new AlertDialog.Builder(DlgActivity.this)
                .setTitle("自定义布局")
                .setView(layout)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .show();
    }

    String selectedTime = ""; // 存放选择的时间
    private void createTimeDlg() {
        final int hour, minute;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime today = LocalTime.now();  // LocalTime获取时间
            hour = today.getHour();
            minute = today.getMinute();
        } else {
            Calendar today = Calendar.getInstance();
            hour = today.get(Calendar.HOUR);
            minute = today.get(Calendar.MINUTE);
        }

        new TimePickerDialog(DlgActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = hourOfDay + ":" + minute;
                tvResult.setText("选择的时间是：" + selectedTime);
                Toast.makeText(DlgActivity.this, selectedTime, Toast.LENGTH_LONG).show();
            }
        }, hour, minute, true).show();
    }

    String selectedDate = "";  // 存放选择的日期

    private void createDateDlg() {
        int year, month, day;

        // android 8.0支持jdk 8获取日期的方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();  // LocalDate获取日期
            year = today.getYear();
            month = today.getMonthValue();
            day = today.getDayOfMonth();
        } else { // jdk 8以下版本获取日期的方法
            Calendar today = Calendar.getInstance();  // 获取当前日历
            year = today.get(Calendar.YEAR);
            month = today.get(Calendar.MONTH) + 1 ;  // 默认的月份是0~11
            day = today.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(DlgActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = year + "年" + (month) + "月" + dayOfMonth + "日";
                tvResult.setText("选择的日期是：" + selectedDate);
                Toast.makeText(DlgActivity.this, selectedDate, Toast.LENGTH_LONG).show();
            }
        }, year, month, day).show();

    }

    private void createProgressDlg() {
        ProgressDialog dlg = new ProgressDialog(DlgActivity.this);
//        dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  // 水平进度条
        dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);  // 圆形进度条
        dlg.setTitle("进度提示");
        dlg.setMessage("圆形进度条");
        dlg.setIcon(R.mipmap.ic_launcher_round);
        dlg.setProgress(100);
        dlg.setCancelable(true);
        dlg.show();

    }

    String[] list = {"喜欢", "谈不上", "不喜欢"};  // 用于列表、单选和复选对话框的数据
    String yourChoice;   // 保存单选框选择的值
    int currentChoice;  // 保存单选框选择的下标索引

    // 复选对话框
    boolean[] selected = {false, false, false};  // 保存复选框每一项是否选中的值
    private void createCheckboxDlg() {
        new AlertDialog.Builder(DlgActivity.this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("问卷调查")
                .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selected[which] = isChecked;
                        Toast.makeText(DlgActivity.this, "选中项为：" + list[which], Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedStr = "选中项：";
                        for(int i = 0; i < selected.length; i++) {
                            if(selected[i]) {
                                selectedStr += list[i] + ",";
                            }
                        }
                        tvResult.setText(selectedStr.substring(0, selectedStr.length() - 1));
                    }
                })
                .show();
    }

    // 单选对话框
    private void createRadioDlg() {
        new AlertDialog.Builder(DlgActivity.this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("问卷调查")
                .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = list[which];
                        currentChoice = which;
                        Toast.makeText(DlgActivity.this, list[which], Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvResult.setText("选中项：" + yourChoice);
                    }
                })
                .show();
    }

    // 列表对话框
    private void createListDlg() {
        // final String[] list = {"喜欢", "谈不上", "不喜欢"};
        new AlertDialog.Builder(DlgActivity.this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("问卷调查")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = list[which];
                        currentChoice = which;
                        Toast.makeText(DlgActivity.this, list[which], Toast.LENGTH_LONG).show();
                        tvResult.setText("选中项：" + list[which]);
                    }
                })
                .show();
    }

    // 多按钮对话框
    private void createMultiDlg() {
        new AlertDialog.Builder(DlgActivity.this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("问卷调查")
                .setMessage("是否喜欢Android开发")
                .setPositiveButton("喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DlgActivity.this, "喜欢Android", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("不一定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DlgActivity.this, "谈不上喜欢还是不喜欢", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("不喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DlgActivity.this, "不喜欢Android", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
