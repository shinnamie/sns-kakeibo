@startuml
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

@enduml
