/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
function comment2target(targetId,type,content) {
    if(!content){
        alert("回复内容不能为空");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if(response.code == 200){
                window.location.reload();
            }else {
                if(response.code == 2004){
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=ec488d44344ef745bdce&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    })
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/*
*  展开二级评论
* */
function responseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    //获取展开二级评论状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var secCommentContainer = $("#comment-"+id);
        if(secCommentContainer.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记展开二级评论状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else{
            $.getJSON( "/comment/" + id, function(data) {
                $.each( data.data.reverse(), function(index,comment) {
                    var timeElement = $("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD hh:mm')
                    });
                    var menuElement = $("<div/>",{
                        "class":"menu"
                    });
                    menuElement.append(timeElement);
                    var commentContentElement = $("<div/>",{
                        "html":comment.content
                    });
                    var userNameElement = $("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    });
                    var mediaBodyElement = $("<div/>",{
                        "class": "media-body"
                    });
                    mediaBodyElement.append(userNameElement);
                    mediaBodyElement.append(commentContentElement);
                    mediaBodyElement.append(menuElement);
                    var avatarElement = $("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    });
                    var mediaLeftElement = $("<div/>",{
                       "class":"media-left"
                    });
                    mediaLeftElement.append(avatarElement);
                    var mediaElement = $("<div/>",{
                        "class":"media"
                    });
                    mediaElement.append(mediaLeftElement);
                    mediaElement.append(mediaBodyElement);
                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    });
                    commentElement.append(mediaElement);
                    secCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                //标记展开二级评论状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }

    }
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    //如果标签框里面没有其中的一个标签，那么再点击该标签就需要添加进去
    if(previous.split(",").indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+","+value);
        }else{
            var previous = $("#tag").val(value);
        }
    }

}
function showTag() {
    $("#select-tag").show();
}