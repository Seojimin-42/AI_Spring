const topicInput = document.getElementById("topic");
const storyButton = document.getElementById("storyButton");
const storyResult = document.getElementById("storyResult");

storyButton.addEventListener("click", generateStory);

async function generateStory() {
    const topic = topicInput.value;

    const response = await fetch("/ai/story-stream", {
        method: "POST",
        headers: {
            "Content-Type" : "application/x-www-form-urlencoded"
        },
        body: `topic=${encodeURIComponent(topic)}`
    });

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    storyResult.textContent = "";

    while(true) {
        const { done, value } = await reader.read();

        if (done) {
            break;
        }

        const chunk = decoder.decode(value, { stream: true });

        storyResult.textContent += chunk;
    }
}