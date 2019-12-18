package com.example.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

//    创建选项菜单OptionMenu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem item = menu.add(Menu.NONE,1,menu.NONE,"个人信息");
//        SubMenu subMenu = menu.addSubMenu(Menu.NONE,2,1,"设置相关");
//        subMenu.add(Menu.NONE,201,Menu.NONE,"设置");
//        subMenu.add(Menu.NONE,202,Menu.NONE,"帮助");
//        subMenu.add(Menu.NONE,203,Menu.NONE,"关于");
        return true;

    }

    //        处理选项菜单项的点击事件


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_info:
                break;
            case R.id.item_about:
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("关于")
                        .setMessage("版本为1.0")
                        .setPositiveButton("确定",null)
                        .show();
                break;
            case R.id.item_help:
                Intent intent = new Intent(MainActivity.this, DlgActivity.class);
                startActivity(intent);
                break;
            case R.id.item_setting:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;

        }
        return true;

    }

}