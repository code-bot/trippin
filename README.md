# trippin

Android app that uses the GM API along with the SoundCloud API to bring you local music as you travel through various cities.

To make this work, you need your own soundcloud credentials. a soundcloud account and then you need to register for an app on their developer side which will get you the client_id and client_secret (see Credentials.java).
For gmasync, this line was modified
String authStringEnc = "bDd4eGIxYWNlM2I0YzQ0NTRmMWE5ZWM3YTMwYjY5Yzg1MGY2OjVkNDEwMzVhNTYxOTQxM2Q4Y2ViZTZlYjFjNGJkOWVi";
The dependency i used in libs was soundcloud-0.2.1-jar-with-dependencies.jar; don't add in all dependencies or else you'll run into issues.
