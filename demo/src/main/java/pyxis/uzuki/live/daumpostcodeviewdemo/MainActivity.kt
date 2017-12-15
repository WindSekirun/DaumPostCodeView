package pyxis.uzuki.live.daumpostcodeviewdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pyxis.uzuki.live.richutilskt.utils.alert

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postView.setCallback { zoneCode, address, buildingName ->
            this@MainActivity.alert("zoneCode: %s, address: %s, buildingName: %s".format(zoneCode, address, buildingName))
        }
        postView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/")
        postView.startLoad()
    }
}
