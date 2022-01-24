# NewsBreeze
An application that uses newsapi.org/ API to fetch and display news items

The application uses Model View Viewmodel architecture that helps in separation of concerns and also lets us make full use of LiveData objects. 
The user can click on Save button, which adds that news item to the list of saved items in memory. The Bookmark icon at the top right corner in the home screen helps to view all the saved items.
In the screen that shows list of saved items, the user can even use the search view to filter the items based on the title of the saved cards.

The application uses Glide, Retrofit and GSON libraries. Constraint layout is made use of whereever possible as the root layout to get finer control of how the views are arranged in the layout.
There is only a single activity used for the entire application and the remaining screens are designed as fragments as it helps with loading time. Since there is only a single activity, whenever communication was required from the fragment to activity, the getActivity() method is used with a simple cast to MainActivity to communicate easily.

Due to time constraint, the application is developed with the assumption that the device is internet enabled while the app is run.
