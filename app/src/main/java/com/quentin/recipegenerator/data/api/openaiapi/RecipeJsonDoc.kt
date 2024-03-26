package com.quentin.recipegenerator.data.api.openaiapi

val recipeSchema = """
{
    "name": "<The dish name>",
    "ingredients": [
        "<Description of the first ingredient>",
        "<Description of the second ingredient>",
        "<Description of the third ingredient>"
    ],
    "instructions": [
        {
            "title": "<Name of the first instruction>",
            "description": "<Recipe details of this step>"
        },
        {
            "title": "<Name of the second instruction>",
            "description": "<Recipe details of this step>"
        }
    ]
}""".trimIndent()

val userMessageSample = "chicken onion"

val assistantMessageSample = """
{
    "name": "Grilled Onion Chicken",
    "ingredients": [
        "4 boneless, skinless chicken breasts",
        "2 large onions, thinly sliced",
        "3 cloves garlic, minced",
        "2 tablespoons olive oil",
        "2 tablespoons balsamic vinegar",
        "1 teaspoon dried thyme",
        "1 teaspoon dried rosemary",
        "Salt and pepper to taste"
    ], 
    "instructions": [
        {
            "title": "Prepare the Marinade",
            "description": "In a small bowl, whisk together the olive oil, balsamic vinegar, minced garlic, dried thyme, dried rosemary, salt, and pepper."
        },
        {
            "title": "Marinate the Chicken",
            "description": "Place the chicken breasts in a shallow dish or a resealable plastic bag. Pour the marinade over the chicken, making sure it's evenly coated. Cover the dish or seal the bag and refrigerate for at least 30 minutes, or up to 4 hours, to allow the flavors to meld."   
        },
        {
            "title": "Preheat the Grill",
            "description": "Preheat your grill to medium-high heat, around 375-400°F (190-200°C). Make sure the grill grates are clean and lightly oiled to prevent sticking."
        },
        {
            "title": "Prepare the Onions",
            "description": "While the grill is heating, toss the thinly sliced onions with a drizzle of olive oil, salt, and pepper in a bowl."
        },
        {
            "title": "Grill the Chicken and Onions",
            "description": "Place the marinated chicken breasts on the preheated grill. Discard any excess marinade. Grill the chicken for about 6-8 minutes per side, or until it's cooked through and no longer pink in the center. The internal temperature should reach 165°F (74°C). While the chicken is grilling, place the seasoned onions in a grill basket or on a sheet of aluminum foil. Grill the onions alongside the chicken, stirring occasionally, until they are caramelized and tender, about 10-12 minutes."
        },
        {
            "title": "Serve",
            "description": "Once the chicken is fully cooked and the onions are caramelized, remove them from the grill. Let the chicken rest for a few minutes before slicing it thinly. Serve the grilled onion chicken hot, garnished with the caramelized onions on top."
        }
    ]
}""".trimIndent()