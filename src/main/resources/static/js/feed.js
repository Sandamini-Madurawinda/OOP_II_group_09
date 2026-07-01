function sendReaction(postId, isDislike) {
    fetch(`/posts/${postId}/like?isDislike=${isDislike}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('Network failure');
            return response.json();
        })
        .then(data => {
            const likeBtn      = document.getElementById(`like-btn-${postId}`);
            const dislikeBtn   = document.getElementById(`dislike-btn-${postId}`);
            const likeCount    = document.getElementById(`like-count-${postId}`);
            const dislikeCount = document.getElementById(`dislike-count-${postId}`);

            likeCount.textContent    = data.likeCount;
            dislikeCount.textContent = data.dislikeCount;

            if (data.liked) {
                likeBtn.classList.add('active-like');
            } else {
                likeBtn.classList.remove('active-like');
            }

            if (data.disliked) {
                dislikeBtn.classList.add('active-dislike');
            } else {
                dislikeBtn.classList.remove('active-dislike');
            }
        })
        .catch(error => {
            console.error('Reaction error:', error);
            alert('Could not update. Please try again.');
        });
}

function toggleLike(postId)    { sendReaction(postId, false); }
function toggleDislike(postId) { sendReaction(postId, true);  }

function toggleComments(postId) {
    const section = document.getElementById(`comment-section-${postId}`);
    section.classList.toggle('hidden');
}

function startEditComment(commentId) {
    const contentEl = document.getElementById(`comment-content-${commentId}`);
    const currentText = contentEl.textContent;

    contentEl.innerHTML = `
        <input type="text" id="edit-input-${commentId}" class="comment-edit-input" value="${currentText.replace(/"/g, '&quot;')}" maxlength="500" />
        <div class="comment-edit-actions">
            <button type="button" onclick="saveEditComment(${commentId})">Save</button>
            <button type="button" onclick="cancelEditComment(${commentId}, '${currentText.replace(/'/g, "\\'")}')">Cancel</button>
        </div>
    `;
}

function cancelEditComment(commentId, originalText) {
    document.getElementById(`comment-content-${commentId}`).textContent = originalText;
}

function saveEditComment(commentId) {
    const input = document.getElementById(`edit-input-${commentId}`);
    const newContent = input.value.trim();
    if (!newContent) return;

    fetch(`/comments/edit/${commentId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content: newContent })
    })
        .then(response => {
            if (!response.ok) throw new Error('Edit failed');
            return response.json();
        })
        .then(data => {
            document.getElementById(`comment-content-${commentId}`).textContent = data.content;
        })
        .catch(error => {
            console.error('Edit error:', error);
            alert('Could not update comment.');
        });
}

function deleteComment(commentId) {
    fetch(`/comments/delete/${commentId}`, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) throw new Error('Delete failed');

            const commentEl = document.getElementById(`comment-${commentId}`);
            const section = commentEl.closest('[id^="comment-section-"]');
            const postId = section.id.replace('comment-section-', '');

            commentEl.remove();

            const countEl = document.getElementById(`comment-count-${postId}`);
            countEl.textContent = parseInt(countEl.textContent, 10) - 1;
        })
        .catch(error => {
            console.error('Delete error:', error);
            alert('Could not delete comment.');
        });
}