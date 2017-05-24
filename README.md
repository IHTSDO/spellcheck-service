# Spell Check Service

## Introduction
A generic spell checking REST API service using Lucene. Plain text dictionaries are loaded at startup. Can also be used as a library.

## Setup
There is a tiny sample dictionary file in the dictionaries directory. You can replace this with your when you are ready. Dictionaries are read from here on startup of the application. 
```
mvn clean package
java -jar target/spellcheck-service*.jar --dictionariesDirectory=dictionaries
```

## REST API
Use the /check endpoint to check words against your dictionary and get suggestions for misspelt words.
The 'words' parameter must be a comma separated list of words to be checked.
The response will contain a map of misspelt words and available suggestions.

### Example:
GET http://localhost:8080/check?words=carot,thing,apple,banan
Response:
```json
{
	carot: [
		"carrot"
	],
	thing: [ ],
	banan: [
		"banana"
	]
}
```
## Limitation
The service is not able to process words shorter than three characters in length. Any words shorter than three characters will simply be ignored.
