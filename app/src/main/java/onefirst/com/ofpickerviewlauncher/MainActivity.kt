package onefirst.com.ofpickerviewlauncher

import android.Manifest.permission.*
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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

        // *************** Permission Check Start ****************
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(arrayOf(BLUETOOTH_SCAN, BLUETOOTH_CONNECT))
        }else {
            requestMultiplePermissions.launch(arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ))

            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetooth.launch(enableBtIntent)
        }
        // *************** Permission Check End ****************

        // GPS Check
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            Toast.makeText(this, "위치정보가 꺼져있어 설정으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        // *************** Setup ****************

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