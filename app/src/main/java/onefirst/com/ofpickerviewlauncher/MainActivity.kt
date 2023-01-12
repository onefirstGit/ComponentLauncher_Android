package onefirst.com.ofpickerviewlauncher

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import onefirst.com.onefirstcomponentlib.OFPickerView
import onefirst.com.onefirstcomponentlib.OfPickerViewDelegate

class MainActivity: AppCompatActivity(), OfPickerViewDelegate {

    private lateinit var pickerView: OFPickerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        OFPickerView pickerView = new OFPickerView(this);
        pickerView = findViewById(R.id.pickerview)

        findViewById<View>(R.id.btn_scan).setOnClickListener {
            val url = findViewById<EditText>(R.id.edittext_url)
            val phone = findViewById<EditText>(R.id.edittext_phone)
            pickerView.loadDoorList(url.text.toString(), phone.text.toString(), this@MainActivity)
        }

        findViewById<View>(R.id.btn_open).setOnClickListener {
//            pickerView.openDoor(0);
            pickerView.openSelectedDoor()
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