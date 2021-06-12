# Todo Server
Java server capable of asynchronously serving multiple clients. Provides API functionality (Account management, ToDos).

## Team Members  
* Tibor Haller (@972C8)
* Marco Kaufmann (@mahgoh)
  
## Implemented Features

Note: No custom MVC client was created. The Client.java included is taken from the template and only serves for testing purposes.

In addition to the minimum requirements (4p), multiple optional features were implemented to enhance the server:

| Type | Description | Status |
|------|-------------|:------:|
| Additional | Validate data on the server (0.5p) | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) |
| Additional | Hash the passwords (0.5p) | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) |
| Additional | Use real tokens for user logins (0.5p) | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) |
| Additional | Save and restore data (1p) | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) |

![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) Not Implemented ![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) In Progress ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) Done

## Validate data on the server
* TODO

## Message Types
* TODO

## Account Management
* TODO
    
## Save and restore data
* Based on [GsonUtility](https://github.com/972C8/GsonUtility) written by us
* Main code found in ReadWriteData.java
* Data (Accounts + ToDos) is automatically read from disk on server start
* Data is automatically exported on given time interval (every 5 minutes)
* Data is also exported on user logout
* Server logs information on action (import/export).