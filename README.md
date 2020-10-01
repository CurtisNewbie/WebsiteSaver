# WebsiteSaver

Server that saves given URL (website) as a PDF file.

This program now supports two ways of grabing a webpage to PDF
1. Programmatically - Using Jsoup and Itext 
2. External Tool - Having Google Chrome installed and use the Chrome in headless mode

Google Chrome is always preferred as it renders the webpage way better than the programmatic way. But it requires you to install Google Chrome, and having it named as "google-chrome", you can create **alias** for it, so that this app can successfully invoke chrome in CLI. 

## Demo

An ugly webpage is created for using websiteSaver, access it through:
    
    http://localhost:8080

### API For Chrome

POST /download/with/chrome

1. url - the URL for the webpage
2. path - absolute or relative path to where the PDF file should be generated

### API For Itext

POST /download/with/itext 

1. url - the URL for the webpage
2. path- absolute or relative path to where the PDF file should be generated

