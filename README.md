# Recipes challenge
A simple app to see cooking recipes

##  Introduction
This challenge should be done by using the free to use RecipePuppy API. We would like you to retrieve some recipes from there, display the recipes and perform certain operations on those recipes. Hereby the details:

1. API connection, you should use their search endpoint and perform recipe searches with one or multiple ingredients (ie: http://www.recipepuppy.com/api/?i=onions,garlic&p=1) and parse the results. We would like you to use the networking tools iOS provides and not an external library.

2. Use a searchbar as user input for the first point and show the results in a collection view with a layout like the following. Each recipe should show the image on top, the recipe name, ingredients (this one could have multiple lines so the layouts should support dynamic heights) and a label in a 45% angle that would show only if it contains lactose (to simplify consider that only milk and cheese contain lactose).

3. Add pagination to the list whenever the user scrolls,this should be as seamless as possible.

4. Each recipe has an href parameter that is an URL pointing to a website with the recipe details. Whenever the user clicks on a recipe use this parameter to open the website in a new view without leaving the app.

5. Offline functionality, each recipe should have a favorite button and clicking it should save the full recipe offline. Create a separate screen and a way to access it to show the favorite recipes.

## Technical specifications


	- Kotlin 1.3 programming language.
	
	- Development enviroment:
	    - Android Studio 4.2
	    - Build #AI-202.7660.26.42.7322048, built on April 29, 2021
        - Runtime version: 11.0.8+10-b944.6842174 amd64
        - VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
        - OS: Windows 10 10.0
        
		
	- SDK: minSdkVersion 23 , targetSdkVersion 30
	
	- Libraries used:
       - lifecycle-viewmodel-ktx:2.3.1 : Live Data - Databinding - Jetpack 
       - Volley:library:2.0.1' : Http services library
       - picasso:2.71828 : To get images from URL.
       - lifecycle-extensions:2.2.0 : Lifecycle
       - kotlinx-coroutines-core:1.3.9 : Kotlin coroutines - Jetpack 
       - androidx.room:room-ktx:2.3.0 : Room Ktx - Jetpack
