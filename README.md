# 家計簿


[ER図](https://user-images.githubusercontent.com/105257899/204226975-9e11bcc1-e8c4-405e-99ea-8065eb7cc991.png)

[クラス図](https://user-images.githubusercontent.com/105257899/206100682-6d7dab14-cec3-4c93-b6e7-1c6e5cf4fd79.png)


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

# 掲示板投稿機能
 
<details>
    <summary>シーケンス図</summary>
  <br />
     掲示板投稿機能のシーケンス図です。 <br>
 
 ```mermaid
sequenceDiagram
actor ユーザー
  participant ログイン
  participant 掲示板情報
  participant 投稿情報
  participant コメント情報
  participant DB
 
 
  ユーザー ->> ログイン : ログインする
  ログイン ->> 掲示板情報 : 掲示板を表示できるか
  掲示板情報 ->> 投稿情報 : 投稿されている情報を表示できるか
  掲示板情報 ->> DB : 掲示板DBアクセス
  投稿情報 ->> コメント情報 : コメント情報を表示できるか

  投稿情報 ->> DB : 投稿DBアクセス
  コメント情報 ->> DB : コメントDBアクセス
  DB ->> ユーザー : DB情報を返す
```
</details>
