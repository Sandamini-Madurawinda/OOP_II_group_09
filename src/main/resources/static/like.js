document.querySelectorAll(".like-btn").forEach(button => {

    button.addEventListener("click", async function () {

        const postCard = this.closest(".post-card");

        const postId = postCard.dataset.postId;

        try {

            this.disabled = true;

            const response = await fetch(`/posts/${postId}/like`, {
                method: "POST"
            });

            if (!response.ok) {
                throw new Error("Request failed");
            }

            const data = await response.json();

            // Update like count
            postCard.querySelector(".like-count").innerText =
                data.likeCount;

            // Update button UI
            if (data.liked) {

                this.innerHTML = "❤️ Liked";

                this.classList.remove("btn-outline-primary");

                this.classList.add("btn-primary");

            } else {

                this.innerHTML = "🤍 Like";

                this.classList.remove("btn-primary");

                this.classList.add("btn-outline-primary");
            }

        } catch (error) {

            console.error("Like failed", error);

        } finally {

            this.disabled = false;
        }

    });

});
