# AshBot
An IRC bot framework in Java

## Features
The bot comes ready made with some basic commands it can respond to while in a channel.
The bot recognizes commands that are addressed to it using the irc convention.  Assuming the bot nick is bishop you would prefix any commands with ``bishop:``.  Example commands follow.

Roll random dice, generate any combination of random numbers in the form of <number of dice>d<number of sides>
```
dice 1d6
```

Set a reminder for yourself, the bot will deliver the message to you after the specified time in minutes expires
```
remind 5 This is your 5 minute reminder
```

Leave a message for user who is not in the channel, when they join the channel the bot will give them the message
```
message <nick> This is the message
```
