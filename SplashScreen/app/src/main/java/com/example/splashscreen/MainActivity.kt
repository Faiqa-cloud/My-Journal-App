package com.example.splashscreen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn = findViewById <Button>(R.id.fancybtn)
        val btn2 = findViewById <Button>(R.id.defbutton)


        // fancy toast
q      btn.setOnClickListener {
          FancyToast.makeText(this,"Faiqa ka app", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,R.drawable.bg,false).show()
      }

//        btn2.setOnClickListener {
//            FancyToast.makeText(this,"Hello ji", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
//        }


        ///custom toast
        btn2.setOnClickListener {
            SnToast.Builder()
                .context(this@MainActivity)
                .type(Type.SUCCESS)
                .message("Success !") //.cancelable(false or true) Optional Default: False
                 .iconSize(40) //Optional Default: 34dp
                 .textSize(20) //Optional Default 18sp
                 .animation( true)// Optional Default: True
                 .duration(3000) //Optional Default: 3000ms
                 .backgroundColor(R.color.pink) //Default: It is filled according to the toast type. If an assignment is made, the assigned value is used
                 .icon(R.drawable.bg) //Default: It is filled according to the toast type. If an assignment is made, the assigned value is used
                .build()
        }
//        val url = "https://api.github.com/users"
//        val userInfoList = userInfo() // This is your custom ArrayList class
//
//        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
//            try {
//                val gson = GsonBuilder().create()
//                // Use Kotlin's Array, not java.sql.Array
//                val items = gson.fromJson(response, Array<userInfoItem>::class.java)
//
//                userInfoList.clear()
//                userInfoList.addAll(items)
//
//                Toast.makeText(this, "Data Loaded: ${userInfoList.size} users", Toast.LENGTH_LONG).show()
//            } catch (e: Exception) {
//                Toast.makeText(this, "Parsing error: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }, { error ->
//            Toast.makeText(this, "Network error: ${error.message}", Toast.LENGTH_LONG).show()
//        })
//
//        val volleyQueue = Volley.newRequestQueue(this)
//        // Pass the object 'stringRequest', not the class name 'StringRequest'
//        volleyQueue.add(stringRequest)



    }
}
