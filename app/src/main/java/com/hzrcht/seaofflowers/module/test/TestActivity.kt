package com.hzrcht.seaofflowers.module.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hzrcht.seaofflowers.R
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber
import com.xuexiang.xhttp2.XHttp
import kotlinx.android.synthetic.main.activity_test.test

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_test)
    test.setOnClickListener {
      XHttp.post("/api/code/index")
          .params("type","Login")
          .params("phone","15968170723")
          .accessToken(false)
          .execute(Any::class.java)
          .subscribeWith(object : NoTipRequestSubscriber<Any>() {
            override fun onSuccess(t: Any?) {

            }
          })
    }
  }
}
