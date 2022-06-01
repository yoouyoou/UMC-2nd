package com.umc.project.flo.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umc.project.flo.data.remote.AuthService
import com.umc.project.flo.data.entities.User
import com.umc.project.flo.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity(), SignUpView {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener{ signUp() }
    }

    //사용자가 입력한 값을 가져오는 함수
    private fun getUser(): User {
        val email:String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd:String = binding.signUpPasswordEt.text.toString()
        val name:String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name);
    }

    //기존 roomDB가 아닌 API를 통해 회원가입 진행
    private fun signUp(){
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //코드 가독성 불편 -> 이부분은 하나의 응답값을 처리해주는 서비스클래스를 만들어서 관리
        /*
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(getUser()).enqueue(object: Callback<AuthResponse>{
            //응답왔을때 처리하는 부분
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                //서버개발자가 작성한 값이 아니라 네트워크에서 온 응답값
                Log.d("SignUpActivity", response.toString())

                //실제값은 response안 body값에 들어있음
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000 -> finish()
                    2016, 2018 -> {
                        binding.signUpEmailErrorTv.visibility = View.VISIBLE
                        binding.signUpEmailErrorTv.text = resp.message
                    }
                }
            }
            //네트워크 연결자체가 실패일때
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SignUpActivity", t.message.toString())
            }

        })

        //위 코드를 기다리지 않고 아래로 넘어가고 응답이 올 시 위 콜백함수로 들어감
        Log.d("SignUpActivity", "비동기 처리가 잘되었는지 확인")
        */

        val authService = AuthService()
        authService.setSignUpView(this)
        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure(message: String) {
        binding.signUpEmailErrorTv.visibility = View.VISIBLE
        binding.signUpEmailErrorTv.text = message
    }

    /*
    //회원가입 진행해주는 함수
    private fun signUp(){
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //validation후 db저장
        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user = userDB.userDao().getUsers()
        Log.d("SIGNUP", user.toString())
    }
    */

}