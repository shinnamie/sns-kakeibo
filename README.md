# 家計簿


[ER図](https://user-images.githubusercontent.com/105257899/204226975-9e11bcc1-e8c4-405e-99ea-8065eb7cc991.png)

[クラス図](https://user-images.githubusercontent.com/105257899/206100415-cc104b2a-c1dc-41a7-a125-30fd388a5274.png)




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
