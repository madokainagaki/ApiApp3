package jp.techacademy.madoka.inagaki.apiapp2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.net.toUri
import com.google.android.material.color.MaterialColors.getColor
import kotlinx.android.synthetic.main.activity_web_view.*


var webViewShop: FavoriteShop? =null

class WebViewActivity: AppCompatActivity() {


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())


        var favorite = webViewShop?.let { FavoriteShop.findBy(it.id) }
        Log.d("test6",favorite.toString())
        if (favorite == null) {
                btnFab.setText("お気に入り登録する")
                }else{
                btnFab.setText("お気に入り登録済み")
            }

        btnFab.setOnClickListener {
            if (favorite == null) {
                //お気に入り登録されていない場合の処理
                webViewShop?.also { FavoriteShop.insert(it) }
                btnFab.setText("お気に入り登録済み")
            } else {
                //お気に入り登録されていた場合の処理
                webViewShop?.also { FavoriteShop.delete(it.id) }
                btnFab.setText("お気に入り登録する")
                favorite = null
            }
            favorite = webViewShop?.let { FavoriteShop.findBy(it.id) }
        }
    }


    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, favorite :FavoriteShop) {
            webViewShop = favorite
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    KEY_URL,
                    favorite.url

                )
            )
        }
    }
}