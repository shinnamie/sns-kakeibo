'use strict';

$(function () {
  window.addEventListener('load', function () {
    $.ajax({
      url: 'http://localhost:8080/board/' + $('#board_id'),
      dataType: 'json',
      data: {
        postId: $('#post_id').val(),
        boardId: $('#board_id').val(),
        userId: $('#user_id').val(),
      },
      async: true
    }).done(function (data) {
      console.log(JSON.stringify(data));
      $('#count').text(data.count);
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
      alert('正しい結果を得られませんでした')
      console.log('XMLHttpRequest:' + XMLHttpRequest.status);
      console.log('textStatus:' + textStatus);
      console.log('errorThrown:' + errorThrown.message);
    });
  });

  $(document).on('click', '#iine', function () {
    $.ajax({
      url: 'http://localhost:8080/api/iine',
      dataType: 'json',
      data: {
        articleId: $('#article_id').val(),
      },
      async: true
    }).done(function (data) {
      console.log(JSON.stringify(data));
      $('#count').text(data.count);
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
      alert('正しい結果を得られませんでした')
      console.log('XMLHttpRequest:' + XMLHttpRequest.status);
      console.log('textStatus:' + textStatus);
      console.log('errorThrown:' + errorThrown.message);
    });
  });
});
