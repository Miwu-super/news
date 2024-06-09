document.addEventListener('DOMContentLoaded', function() {
    // 评论表单提交
    const commentForm = document.querySelector('.comment-form');
    if (commentForm) {
        commentForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const contentTextarea = this.querySelector('textarea[name="content"]');
            const authorInput = this.querySelector('input[name="author"]');

            if (contentTextarea.value.trim() === '' || authorInput.value.trim() === '') {
                alert('请输入评论内容和昵称！');
                return;
            }

            // 使用 AJAX 提交评论 (示例)
            fetch(this.action, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams(new FormData(this)).toString()
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text(); // 或 response.json()
                })
                .then(data => {
                    // 处理成功响应，例如刷新评论列表
                    console.log('评论成功！', data);
                    location.reload(); // 刷新页面
                })
                .catch(error => {
                    console.error('评论提交出错：', error);
                    alert('评论提交失败，请稍后再试！');
                });
        });
    }
});