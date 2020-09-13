# WebsiteSaver

Server that saves given url (website) as a pdf file

## Demo

GET /download with two http headers:

1. url - the url for the webpage
2. target - absolute or relative path to where the pdf file should be generated

   curl localhost:8080/download -H "url:https://jsoup.org/" -H "target:pdf/jsoup.pdf"
