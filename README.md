# 家計簿

[ER図](https://user-images.githubusercontent.com/105257899/203700586-a3977956-5702-493c-a911-968f4cfa9ed3.png)

[クラス図](https://user-images.githubusercontent.com/105257899/203700201-045c0bb3-6bb5-4ac5-a900-8f729678de39.png)




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
