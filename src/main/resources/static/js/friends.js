function initializeFriendButtons() {

    document.querySelectorAll(".send-request").forEach(btn => {

        btn.addEventListener("click", function () {

            const receiverId = this.dataset.id;

            const senderId = currentUserId;
            fetch(`/friends/request/${receiverId}?senderId=${senderId}`, {
                method: "POST"
            })
            .then(res => res.text())
            .then(msg => alert(msg));
        });
    });
}

function initializeAcceptRejectButtons() {

    document.querySelectorAll(".accept").forEach(btn => {

        btn.addEventListener("click", function () {

            const id = this.dataset.id;

            fetch(`/friends/accept/${id}`, {
                method: "POST"
            })
            .then(res => res.text())
            .then(msg => {
                alert(msg);
                location.reload();
            });
        });
    });

    document.querySelectorAll(".reject").forEach(btn => {

        btn.addEventListener("click", function () {

            const id = this.dataset.id;

            fetch(`/friends/reject/${id}`, {
                method: "POST"
            })
            .then(res => res.text())
            .then(msg => {
                alert(msg);
                location.reload();
            });
        });
    });
}