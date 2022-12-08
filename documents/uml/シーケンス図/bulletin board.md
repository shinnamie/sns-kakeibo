@startuml
  actor ユーザー
  participant ログイン
  participant 掲示板情報
  participant 掲示板DB
  participant 投稿情報
 
  ユーザー ->> ログイン : ログインする
  ログイン ->> 掲示板情報 : 掲示板を表示できるか
 
  投稿情報 ->> 掲示板DB
 掲示板情報 ->> 投稿情報 : 投稿されている情報を表示できるか
  掲示板情報 ->> 掲示板DB    : 掲示板へ投稿できるか
  掲示板DB ->> ユーザー : 掲示板を投稿し反映
@enduml