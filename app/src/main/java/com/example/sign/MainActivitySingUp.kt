package com.example.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.sign.constants.Constants
import com.example.sign.databinding.ActivityMainSingUpBinding

class SignInUpActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainSingUpBinding
    public var signState = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainSingUpBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.hide()

        signState = intent.getStringExtra(Constants.SIGN_STATE)!!
        Log.d("AppLog", "$signState")
        if (signState == Constants.SIGN_UP_STATE) {
            bind.bAvatar.visibility = View.VISIBLE
            bind.edName.visibility = View.VISIBLE
            bind.edEmail.visibility = View.VISIBLE
        }

        bind.bDone.setOnClickListener(this::onClickDone)
        bind.bAvatar.setOnClickListener(this::onClickAvatar)

    }

    fun onClickDone(view: View) {
        if (signState == Constants.SIGN_UP_STATE) {
            val i = Intent()
            i.putExtra(Constants.LOGIN, bind.edLogin.text.toString())
            i.putExtra(Constants.PASSWORD, bind.edPassword.text.toString())
            i.putExtra(Constants.NAME, bind.edName.text.toString())
            i.putExtra(Constants.EMAIL, bind.edEmail.text.toString())
            i.putExtra(Constants.SIGN_STATE, Constants.SIGN_UP_STATE)
            if (bind.idAvatar2.isVisible) i.putExtra(Constants.AVATAR_ID, R.drawable.bazavatar)
            setResult(RESULT_OK, i)
            finish()
        } else if (signState == Constants.SIGN_IN_STATE) {
            intent.putExtra(Constants.LOGIN, bind.edLogin.text.toString())
            intent.putExtra(Constants.PASSWORD, bind.edPassword.text.toString())
            intent.putExtra(Constants.SIGN_STATE, Constants.SIGN_IN_STATE)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun onClickAvatar(view: View) {
        bind.idAvatar2.visibility = View.VISIBLE
    }
}