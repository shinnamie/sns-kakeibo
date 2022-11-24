# 家計簿

<details>
 
  <summary>クラス図</summary>
 
 ```mermaid

top to bottom direction

class LoginController {

    login(LoginForm loginForm)
    logout(???)

}

class LoginService{
    
    login(String mailAdderess , String password)
    
}

class LoginMapper{
    
    login(String mailAdderess , String password)
    
}

class SignUpController{

    signUp(SignUpForm signUpForm)
    
}

class SignUpService{

    signUp(User user)
}

class SignUpMapper{

    signUp(User user)
}

class SignUpForm{

    String mailAdderess
    String password
    String name
    Integer age
    Integer gender
    String remarks
}

class User{

    Long id
    String mailAdderess
    String password
    String name
    Integer age
    Integer gender
    String remarks
    List<kakeibo> kakeiboList
}

class LoginForm{

    String mailAdderess
    String password

}



LoginController  ..>  LoginService 
LoginService  ..>  LoginMapper 
LoginController -- LoginForm
LoginController -- User

SignUpController  ..>  SignUpService 
SignUpService  ..>  SignUpMapper 
SignUpController -- SignUpForm
SignUpController -- User

 ```
</details>



# ログイン機能
 
<details>
    <summary>シーケンス図</summary>
  <br />
     ログイン機能のシーケンス図です。 <br>
 
 ```mermaid
sequenceDiagram
  actor ユーザー
  participant ログイン
  participant ユーザー情報
  participant 権限情報
 
  ユーザー ->> ログイン : ログインする
  ログイン ->> ユーザー情報 : ユーザーが存在するか(メール)
  ユーザー情報 ->> 権限情報    : 権限があるか
  権限情報 ->> ログイン : 管理者としてログイン
  権限情報 ->> ログイン : 一般ユーザーとしてログイン
  ログイン ->> ユーザー : リダイレクト
```
</details>
