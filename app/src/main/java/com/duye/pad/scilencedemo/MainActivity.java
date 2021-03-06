package com.duye.pad.scilencedemo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        private static final String path = "storage/emulated/0/tencent/QQfile_recv/app-debug.apk";
//    private static final String path = "storage/emulated/0/DCIM/app-debug.apk";
    private Button mButton;
    private TextView mTextView;
    private ReplaceBroadcastReceiver mReplaceBroadcastReceiver = new ReplaceBroadcastReceiver();
    int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.version);
        try {
            version = getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTextView.setText(version + "");
        ShellUtils.checkRootPermission();
        registerReceiver(mReplaceBroadcastReceiver, new IntentFilter(ReplaceBroadcastReceiver.UPDATE_ACTION));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultCode = PackageUtils.installSilent(MainActivity.this,path);
                if (resultCode != PackageUtils.INSTALL_SUCCEEDED) {
                    Toast.makeText(MainActivity.this, "升级失败",Toast.LENGTH_SHORT).show();
                }
                //定制系统
//                File file= new File(path);
//                Log.d("onclickddd", "onClick: "+file.getAbsolutePath());
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra("SILENT_INSTALL", 2);
//                i.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
//                MainActivity.this.startActivity(i);
//                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReplaceBroadcastReceiver);
    }
}
