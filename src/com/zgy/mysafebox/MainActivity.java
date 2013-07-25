package com.zgy.mysafebox;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MainUtil.checkService(MainActivity.this);
		
		
		
    }
    
}
