const form = document.querySelector(".search-form");
const input = document.querySelector(".search-input");
const answer = document.querySelector(".answer p");

form.addEventListener("submit", async function(event) {

    event.preventDefault(); // 기본 동작 막음

    const question = input.value;

    answer.textContent = "AI가 생각 중...";

    const response = await fetch("/ai/chat", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `question=${encodeURIComponent(question)}`
    });

    const result = await response.text();

    answer.textContent = result;
});