const role = document.getElementById("role");
const question = document.getElementById("question");
const consultBtn = document.getElementById("consultBtn");

const roleName = document.getElementById("roleName");
const time = document.getElementById("time");
const result = document.getElementById("result");

consultBtn.addEventListener("click", async () => {

    const selectedRole = role.value;
    const questionText = question.value;

    // 입력 확인
    if (!selectedRole.trim()) {
        alert("상담 역할을 입력해주세요.");
        return;
    }

    if (!questionText.trim()) {
        alert("고민을 입력해주세요.");
        return;
    }

    // 화면 초기화
    roleName.textContent = selectedRole;
    result.textContent = "";
    time.textContent = "응답 중...";

    await getAIResponse(
        selectedRole,
        questionText
    );
});

async function getAIResponse(role, question) {

    const startTime = performance.now(); // 현재 시각

    const response = await fetch("/ai/persona", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            role: role,
            question: question
        })
    });

    if (!response.ok) {
        result.textContent = "AI 상담 결과를 가져오지 못했습니다.";
        time.textContent = "응답 실패";
        return;
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    while (true) {

        const { value, done } = await reader.read();

        if (done) {
            break;
        }

        const text = decoder.decode(value, {
            stream: true
        });

        result.textContent += text;
    }

    const endTime = performance.now();

    const responseTime =
        ((endTime - startTime) / 1000).toFixed(2);

    time.textContent = `응답 시간: ${responseTime}초`;
}