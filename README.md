# 家計簿

[クラス図](https://user-images.githubusercontent.com/105257899/203700201-045c0bb3-6bb5-4ac5-a900-8f729678de39.png)
当ECサイトの [ER図](https://user-images.githubusercontent.com/105257788/176073157-efff1074-3298-4139-be33-e3b6cbd8e00f.png)はこちらです。




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
