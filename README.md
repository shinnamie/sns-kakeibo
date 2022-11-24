# 家計簿

 
<details>
    <summary>ログイン機能シーケンス図</summary>
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
