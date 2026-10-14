const ingredientInput = document.getElementById("ingredientInput");
const recipeButton = document.getElementById("recipeButton");
const recipeResult = document.getElementById("recipeResult");

recipeButton.addEventListener("click", generateRecipe);

async function generateRecipe() {
    const ingredients = ingredientInput.value;

    const response = await fetch("/ai/recipe", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `ingredients=${encodeURIComponent(ingredients)}`
    });

    const result = await response.text();

    recipeResult.textContent = result;
}