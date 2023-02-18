package com.example.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sign.constants.Constants
import com.example.sign.databinding.ActivityMainBinding
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null

    private var login:String = "e"
    private var password:String = "e"
    private var name:String = "e"
    private var email:String = "e"
    private var avatar_id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(bind.root)
        supportActionBar?.hide()

//        bind.bSignin.setOnClickListener(this::onClickSignIn)
//        bind.bSignup.setOnClickListener(this::onClickSignUp)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
            var signState = "s"
            if(result.resultCode == RESULT_OK){
                signState = result.data?.getStringExtra(Constants.SIGN_STATE)!!
                if(signState == Constants.SIGN_IN_STATE){
                    val lgn = result.data?.getStringExtra(Constants.LOGIN)!!
                    val psw = result.data?.getStringExtra(Constants.PASSWORD)!!
                    //ищем в бд
                    //login, password, name, email, avatar_id
                    if(login == lgn && password == psw){
                        bind.imAvatar1.visibility = View.VISIBLE
                        bind.imAvatar1.setImageResource(avatar_id)
                        bind.twInfo.text = "$name $email"
                    }
                    else{
                        bind.imAvatar1.visibility = View.VISIBLE
                        bind.imAvatar1.setImageResource(R.drawable.bad)
                        bind.twInfo.text = "Такого аккаунта не существует"
                    }
                }
                else if(signState == Constants.SIGN_UP_STATE){
                    //записываем в бд
                    login = result.data?.getStringExtra(Constants.LOGIN)!!
                    password = result.data?.getStringExtra(Constants.PASSWORD)!!
                    name = result.data?.getStringExtra(Constants.NAME)!!
                    email = result.data?.getStringExtra(Constants.EMAIL)!!
                    avatar_id = result.data?.getIntExtra(Constants.AVATAR_ID, 0)!!
                    bind.imAvatar1.setImageResource(avatar_id)
                    bind.twInfo.text = "$name $email"
                    bind.imAvatar1.visibility = View.VISIBLE
                }
            }
        }
    }

    fun onClickSignIn(view: View) {
        val i = Intent(this, SignInUpActivity::class.java)
        i.putExtra(Constants.SIGN_STATE, Constants.SIGN_IN_STATE)
        //startActivity(i)
        launcher?.launch(i)
    }

    fun onClickSignUp(view: View) {
        val i = Intent(this, SignInUpActivity::class.java)
        i.putExtra(Constants.SIGN_STATE, Constants.SIGN_UP_STATE)
        launcher?.launch(i)
    }
}