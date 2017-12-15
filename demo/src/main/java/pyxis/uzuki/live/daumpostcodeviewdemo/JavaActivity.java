package pyxis.uzuki.live.daumpostcodeviewdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pyxis.uzuki.live.daumpostcodeview.DaumPostCodeView;
import pyxis.uzuki.live.richutilskt.utils.RichUtils;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaumPostCodeView postCodeView = findViewById(R.id.postView);
        postCodeView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/");
        postCodeView.setCallback((zoneCode, address, buildingName) -> {
            RichUtils.alert(this, String.format("zoneCode: %s, address: %s, buildingName: %s", zoneCode, address, buildingName));
        });
        postCodeView.startLoad();
    }
}
