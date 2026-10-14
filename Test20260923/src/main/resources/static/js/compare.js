const question = document.getElementById("question");
const model1 = document.getElementById("model1");
const model2 = document.getElementById("model2");
const compareBtn = document.getElementById("compareBtn");

const model1Name = document.getElementById("model1Name");
const model2Name = document.getElementById("model2Name");

const time1 = document.getElementById("time1");
const time2 = document.getElementById("time2");

const result1 = document.getElementById("result1");
const result2 = document.getElementById("result2");

compareBtn.addEventListener("click", async () => {

    const questionText = question.value;
    const selectedModel1 = model1.value;
    const selectedModel2 = model2.value;

    // 질문이 비어있는지 확인
    if (!questionText.trim()) {
        alert("질문을 입력해주세요.");
        return;
    }

    // 선택한 모델 이름 표시
    model1Name.textContent = selectedModel1;
    model2Name.textContent = selectedModel2;

    // 기존 결과 초기화
    result1.textContent = "";
    result2.textContent = "";

    time1.textContent = "응답 중...";
    time2.textContent = "응답 중...";

    // 두 모델에게 동시에 요청
    await Promise.all([
        getAIResponse(questionText, selectedModel1, result1, time1),
        getAIResponse(questionText, selectedModel2, result2, time2)
    ]);
});

async function getAIResponse(question, model, resultElement, timeElement) {

    const startTime = performance.now(); // 현재 시각 측정

    const response = await fetch("/ai/ollamachat", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            question: question,
            model: model
        })
    });

    if (!response.ok) {
        resultElement.textContent = "AI 응답을 가져오지 못했습니다.";
        timeElement.textContent = "응답 실패";
        return;
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    while (true) {

        const { value, done } = await reader.read();

        if (done) {
            break;
        }

        const text = decoder.decode(value, { stream: true });

        resultElement.textContent += text;
    }

    const endTime = performance.now();
    const responseTime = ((endTime - startTime) / 1000).toFixed(2);

    timeElement.textContent = `응답 시간: ${responseTime}초`;
}