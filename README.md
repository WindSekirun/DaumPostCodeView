## DaumPostCodeView [![](https://jitpack.io/v/WindSekirun/DaumPostCodeView.svg)](https://jitpack.io/#WindSekirun/DaumPostCodeView)

이 라이브러리는 [코틀린](http://kotlinlang.org)으로 작성된 [다음 우편번호 서비스](http://postcode.map.daum.net/guide#usage) 의 Wrapping View 라이브러리입니다. 

### Usages

*rootProject/build.gradle*
```	
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.github.WindSekirun:DaumPostCodeView:1.0.0'
}
```

#### 주의점

* 다음 우편번호 서비스의 특성상 '웹서버'에 접속할 url가 있어야 합니다. 앱 내에서 ```setPostUrl``` 메서드로 설정하며, 전체 HTML 코드는 [여기](https://github.com/WindSekirun/DaumPostCodeView/blob/gh-pages/index.html)에 공개되어 있습니다.
* postUrl를 설정하지 않은 채로 사용할 경우, 예외를 발생시킵니다.
* 현재 예제 주소인 https://windsekirun.github.io/DaumPostCodeView/ 는 Github Pages를 통해 제공되고 있으며, 언제 해당 방법이 사라질지 모릅니다.
* 따라서 PRODUCTION 사용에 있어서는 [RawGit](https://rawgit.com) 나 호스팅 서비스를 이용해 사용해주세요.
* 현재 1.0.0에서는 사용자가 지번 주소를 클릭했는지, 도로명을 클릭했는지에 대한 값을 제공하지 않습니다. (각 도로명 / 지번 주소는 address 필드로 내려옵니다.)

#### XML

```XML
 <pyxis.uzuki.live.daumpostcodeview.DaumPostCodeView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/postView"/>
```

#### Kotlin

```Kotlin
postView.setCallback { zoneCode, address, buildingName ->
            this@MainActivity.alert("zoneCode: %s, address: %s, buildingName: %s".format(zoneCode, address, buildingName))
}
postView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/")
postView.startLoad()
```

#### Java

```Java
DaumPostCodeView postCodeView = findViewById(R.id.postView);
postCodeView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/");
postCodeView.setCallback((zoneCode, address, buildingName) -> {
        RichUtils.alert(this, String.format("zoneCode: %s, address: %s, buildingName: %s", zoneCode, address, buildingName));
});
postCodeView.startLoad();
```
