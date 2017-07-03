# BRGO
Designed and Created by Praveen Sakthivel (BRHS 2017). Feel free to contact me with any questions at prsakthivel0@gmail.com

Libraries are all included in gradle dependencies. Download and run.

Features are as follows:

News: This section displays all news events for selected school. It makes use of an RSS Feed found on the schools website. If your school doesn't have an RSS Feed, you may want to look into web scraping.

Calendar: Very similar to the news section except it draws its information from the portion of a schools website. If your school includes the date of the event in a standard way, you may want to look at it parsing that date and organizing events by their respective dates.

Teacher Websites: This section is specific to service used by some schools called OnCourse Teacher Websites. OnCourse teacher websites are a service that allows teachers to create their own website for their classroom needs. Its a great resource but does not carry well to mobile at all. This section fixes that issue and even allows you to save teacher you frequent. To modify this section you'll need to find out the codes OnCourse uses for your school district. The code is the 5 digit number found at the end of https://app.oncoursesystems.com/school/menu/ link for your school.

School Handbook Handbooks can useful at times but not to the extent you'll always have them on you. This displays a pdf so you will always have it on you. Link to the PDF online or save it within app when customizing this section.

Student ID: Self Explanatory. School Id's are rarely used for anything other than identification. So instead of carrying it around, this section takes a picture and saves it to your phone within the app. You can have it ready with a few clicks instead of searching around your photo gallery. If your school uses the barcode on your ID you may want to inquire about using NFC to transmit that information.

Homework Planner: A digital version of the agenda most schools pass out. Write whatever you want on a selected date and It'll be saved right to your phone.

Schools: I wouldn't design this just for one school, but for an entire district to save time. Most of the time district will use a number code for each school when differentiating the data sources for the webpage. You can concatenate this code into the rest of the url for easy abstraction for multiple schools. In my case I used the codes used by OnCourse Systems.

That marks the end of the current features. But add what you think your schools need most. This is a template for you to start from. If you go through and publish the app, I ask that you do two things. 

Credit me in some way. Nothing big, just a little disclaimer at the end of your app description perhaps. 

No ads or price. These apps are supposed to be easily accessible to the community. Monetization just gets in the way.
