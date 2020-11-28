# Notes!

This LINE bot extracts handwritten notes in photos into text. Simply send a pic 
of your handwritten note and the bot will reply with a typed version of your notes!

Built with Spring Framework.

<br>
<img src="https://gitlab.com/fwrhine/notes-bot/-/raw/master/images/ss1.png" height="430" />
<img src="https://gitlab.com/fwrhine/notes-bot/-/raw/master/images/ss2.png" height="430" />

## How to use
Add Notes! bot as your friend through QR code or LINE ID:

<img src="https://gitlab.com/fwrhine/notes-bot/-/raw/master/images/qr.png" height="150">

LINE ID: @aal3304s

## API
This bot uses [LINE Messaging API](https://developers.line.biz/en/docs/messaging-api/overview/#what-you-can-do) and [Microsoft Azure Computer Vision API](https://docs.microsoft.com/en-au/azure/cognitive-services/computer-vision/).

To run the code, set your environment variables for LINE API:
```
LINE_BOT_CHANNEL_SECRET
LINE_BOT_CHANNEL_TOKEN
```
And Computer Vision API:
```
subscription_key
endpoint
```

## Note
Repository was imported from Gitlab and designed for Gitlab CI/CD.

Original repo: [https://gitlab.com/fwrhine/notes-bot](https://gitlab.com/fwrhine/notes-bot)
