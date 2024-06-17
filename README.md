## Steps to run the application in local 
1. Setup redis.
2. Create a set named "LAYOUT" (do not change the set name) and add all the URLS you want to distribute
    - eg. SADD LAYOUT URL1
    - Check if the value is added using SMEMBERS "LAYOUT"
3. Update host and post to the application.properties file according to your redis configuration.
4. Start the server.
5. Hit the get endpoint - /user/getLayout.
