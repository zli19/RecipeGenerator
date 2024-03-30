package com.quentin.recipegenerator.data.api.openaiapi

val recipeSchema = """
{
    "name": "<The dish name>",
    "ingredients": "<List all the ingredients for this recipe and start a new line for each ingredient.>",
    "info": "<List information about time, makes or other helpful information, and start a new line for each info.>"
    "directions": "<Write comprehensive directions for the recipe.>"
    "nutrition": "<calculate nutrition facts per make, may include calories, fat (or saturated fat), cholesterol, sodium, carbohydrate (could include sugars or fiber), protein.>",
    "prompt": "Generate one sentence of prompt for text to image generation."
}""".trimIndent()

val mealTypes = listOf("Appetizers", "Breakfast", "Brunch", "Deserts", "Dinner", "Lunch", "Side", "Snacks")
val specialDiet = listOf("Dairy-Free", "Gluten-Free", "High-Protein", "Low-Fat", "Low-Carb", "Nut-Free", "Paleo", "Pescatarian", "Vegan", "Vegetarian")
