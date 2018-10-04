package onefirst.com.ofpickerviewlauncher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import onefirst.com.onefirstcomponentlib.OFDataModel;
import onefirst.com.onefirstcomponentlib.OFPickerView;
import onefirst.com.onefirstcomponentlib.OfPickerViewDelegate;

public class MainActivity extends AppCompatActivity implements OfPickerViewDelegate {

    private OFPickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText url = findViewById(R.id.edittext_url);
        EditText phone = findViewById(R.id.edittext_phone);

//        OFPickerView pickerView = new OFPickerView(this);
        pickerView = findViewById(R.id.pickerview);
        pickerView.dataModel = OFDataModel.create(url.getText().toString(), phone.getText().toString());
        pickerView.delegate = this;

        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.startScanDevice();
            }
        });

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pickerView.openDoor(0);
                pickerView.openSelectedDoor();
            }
        });
    }

    @Override
    public void onScanFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pickerView.stopScanDevice();
    }
}
