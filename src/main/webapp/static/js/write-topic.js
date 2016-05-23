'use strict';

function writeTopic_setup() {
  $('#topic .btn-submit').tooltip({
    placement: 'top',
    trigger: 'manual'
  }).click(function(event){
    event.preventDefault()
    var $submit = $(this)

    var selections = $('.tag-input').data('selections')

    var topicId = window.frontMap.id;
    var submitUrl = topicId ? '/topics/'+topicId+'/edit' : '/topics/new'
    var reqAttrs = {
      title: $('#title').val(),
      content: $('#content').val(),
      tagIds: selections
    }
    if (selections[0]) {
      reqAttrs.belongTagId = selections[0]
    }
    $submit.prop('disabled', true)
    $.post(submitUrl, reqAttrs)
      .done(function(url){
        window.location = url
      })
      .fail(function(err){
        var $submit = $('#topic .btn-submit')
        tipover($submit, '发表失败: '+err, 1000)
        $submit.prop('disabled', false)
      })
  })

  var $content = $('#content')
  $content.keydown(function(event){
      if (event.keyCode == 9) {
        event.preventDefault();
        var content = $(this).val();
        var pos = $(this).getCursorPosition();
        content = content.slice(0, pos) + '\t' + content.slice(pos, content.length);
        $(this).val(content);
        $(this).setCursorPosition(pos+1);
      }
    }).keyup(function() {
      textareaAutoResize.apply(this);
      refresh();
    });
  $('#tabs a[href="#content"]').warnEmpty().tab('show');

  //TODO is this OK?
  $content.val(unescapeHtml($content.val()));

  refresh();
}

function refresh() {
  var input = $('#content').val()
  $('#preview').html(marked(input))
}