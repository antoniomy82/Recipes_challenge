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

## Storyboard

**Step 1:** Enter one or more ingredients to search recipes, and press search button

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/01_home.PNG>

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/02_home.PNG>

***
**Step 2 :** When data is received it will show all recipes in a recyclerView. &nbsp;

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/03_home.PNG>

***

**Step 3 :** To save the recipe, click on the "make favorite" button.

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/04_home.PNG>

***
**Step 4:** To see saved recipes, click on the favorites icon. &nbsp;

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/05_saved_recipes.PNG>
***

**Step 5:** To see href link, click on the item (saved or retrieved).

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/06_href_to_web.PNG>

***


##  Comments and reasoning

1. **Design and architectural pattern:**

	- It has been designed in MVVM, since it is the Kotlin reference pattern and is the most recommended by Google.

	- Below is how it has been applied in the project:

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/00_mvvm.png>

2. **Offline mode:**
	- It has been designed and implemented with Jetpack, specifically the DB with SQL Lite and the data mapping with Room, since it simplifies the work and allows the Recipe object to be shared between the database and the rest of the application.

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/07_room.PNG>

3. **Used libraries:**
  	- Volley: For simplicity to construct the recipePuppy API call (For this case it greatly simplified the solution with respect to retrofit)
 	- Coroutines: To insert recipe objects into the background database
 	- Picasso: To load images from urls, It is very simple and for this case it works better than glide.
  	- lifecycle-viewmodel-ktx & lifecycle-extensions:2.2.0: For DataBinding, ViewModel, LiveData ... (100% architecture MVVM)

4. **Design approach:**
  	- For simplicity, a design has been made for functionality (instead of use cases).

##Video
- In this video I explain how it works, as well as its implementation

To download:
https://github.com/antoniomy82/Recipes_challenge/blob/master/screenshots/Recipes%20challenge.mp4

To view with a google account:
https://drive.google.com/file/d/1VjpKoA_MINTxmCmbfpetytMc5ADA9VWw/view?usp=sharing

