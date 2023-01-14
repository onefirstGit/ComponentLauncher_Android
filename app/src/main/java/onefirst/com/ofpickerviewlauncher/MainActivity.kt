package onefirst.com.ofpickerviewlauncher

import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import onefirst.com.onefirstcomponentlib.OFPickerView
import onefirst.com.onefirstcomponentlib.OfPickerViewDelegate

class MainActivity: AppCompatActivity(), OfPickerViewDelegate {

    private lateinit var pickerView: OFPickerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(arrayOf(BLUETOOTH_SCAN, BLUETOOTH_CONNECT))
        }else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetooth.launch(enableBtIntent)
        }

        // example1
//        OFPickerView pickerView = new OFPickerView(this);
        // example2
        pickerView = findViewById(R.id.pickerview)

        findViewById<View>(R.id.btn_scan).setOnClickListener {
            val url = findViewById<EditText>(R.id.edittext_url)
            val phone = findViewById<EditText>(R.id.edittext_phone)
            pickerView.loadDoorList(url.text.toString(), phone.text.toString(), this@MainActivity)
        }

        findViewById<View>(R.id.btn_open).setOnClickListener {
            // example1
//            pickerView.openDoor(0);
            // example2
            pickerView.openSelectedDoor()
        }
    }

    private var requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            //granted
        }else{
            //deny
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("Test", "${it.key} = ${it.value}")
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        pickerView.onDestroy()
    }

    override fun failure(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun success(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}