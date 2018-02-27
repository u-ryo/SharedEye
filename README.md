# SharedEye
## What's this?
One way WebRTC Android App including a signaling server.
This app is especially targetting to <span style="color:red">Smart Glasses</span> (arm/x86)(like [Vuzix M100](www.vuzix.com/Products/m100-smart-glasses), [M300](https://www.vuzix.com/Products/m300-smart-glasses), [Epson Moverio BT-300, BT-350](https://epson.com/moverio-augmented-reality), etc.).
So there's no need to operate on Android App.
Run the App on Android, access to the URL from a remote WWW browser,
and share the vision of the glasses on the remote browser.

This app is spcialized in <span style="color:red">WiFi</span> environment.
If you want to use the app through 3G/4G, you can use [Appear.in](https://appear.in/).

## Caution
The device can be too hot because the camera is always on.

## Download(Version 0.1) APK
* [armv7](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/apk/SharedEye-armv7-release.apk)
* [armv7-64](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/apk/SharedEye-armv7_64-release.apk)
* [x86](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/apk/SharedEye-x86-release.apk)
* [x86-64](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/apk/SharedEye-x86_64-release.apk)

## Basic Use Case
<!-- ![usecase1](https://user-images.githubusercontent.com/12380403/30556048-7a063a62-9ce4-11e7-8766-53d80b4816e2.png "You can see the view through the Smart Glasses on the remote PC WWW browser.") -->
![Use Case1](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/usecase1.png  "You can see the view through the Smart Glasses on the remote PC WWW browser.")  
You can see the view through the Smart Glasses on the remote PC WWW browser.

## Can draw and send directions (red lines)
<!-- ![usecase2](https://user-images.githubusercontent.com/12380403/30556598-57312c16-9ce6-11e7-85bb-6424d6cfdbc9.png "You can chat and draw red lines on the screen capture and send it to the remote Smart Glasses.") -->
![Use Case2](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/usecase2.png "You can chat and draw red lines on the screen capture and send it to the remote Smart Glasses.")  
You can chat and draw red lines on the screen capture and send it to the remote Smart Glasses.

## How to use?
To try this app, you need Smart Glasses(Android) and a PC with a WWW browser.

1. Connect the Android to WiFi
1. Run the App on Android
1. You'll see the message `"Please access to https://XXX.XXX.XXX.XXX:YYYY/"` on the Android screen.  
<!-- ![initial_screen](https://user-images.githubusercontent.com/12380403/30556666-9352cd9e-9ce6-11e7-959f-aa08505d9025.png "initial screen") -->
![initial screen](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/initial_screen.png "initial screen")
1. On the PC, run the WWW browser and go to the URL above. Please be careful it begins <span style="color:red">`https`</span>, not `http`.
1. Accept the invalid cert and access to the site. Go on! Don't worry!  
<!-- ![procedure_01](https://user-images.githubusercontent.com/12380403/30556747-e294d79e-9ce6-11e7-9495-ffdba6a004e3.png "Accept the invalid cert") -->
![Accept the invalid cert](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_01.png "Accept the invalid cert")
1. When the page is loaded on the PC WWW browser, the browser will ask you to permit to use a camera. Please permit it. In fact, no camera will be used. Only a microphone and a speaker will be used for voice chat.  
<!-- ![procedure_02](https://user-images.githubusercontent.com/12380403/30556789-04b5b2b2-9ce7-11e7-9cbb-ba94b1d9035b.png "Permit your camera") -->
![Permit your camera](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_02.png "Permit your camera")
1. You will see and hear the remote Smart Glass on the left side. On the Smart Glass side, the local video stream will be started. You can chat each other.  
<!-- ![procedure_03](https://user-images.githubusercontent.com/12380403/30556822-1ffb7ef8-9ce7-11e7-8e69-2c0c397dbbb2.png "You'll see the the remote view on the left") -->
![You'll see the the remote view on the left](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_03.png "You'll see the the remote view on the left")
1. If you push `Capture` button on the PC browser, you'll see the capture on the right side.  
<!-- ![procedure_04](https://user-images.githubusercontent.com/12380403/30556876-5422784e-9ce7-11e7-8fca-bd81a3a2c2b3.png "Capture!") -->
![Capture!](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_04.png "Capture!")
1. You can draw red lines on the captured picture.  
<!-- ![procedure_05](https://user-images.githubusercontent.com/12380403/30556903-6c4538a8-9ce7-11e7-890a-6783448e4277.png "Draw red lines") -->
![Draw red lines](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_05.png "Draw red lines")
1. You can `Send` it to the remote Smart Glass.  
<!-- ![procedure_06](https://user-images.githubusercontent.com/12380403/30556925-8cff99e4-9ce7-11e7-9c8a-a6208bd220dc.png "Send!") -->
![Send!](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_06.png "Send!")
On the Smart Glass side, the sent image will be shown.  
<!-- ![sent_image](https://user-images.githubusercontent.com/12380403/30556947-a531c05a-9ce7-11e7-8015-977f43052df6.png) -->
![Sent image](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/sent_image.png)
1. On the PC, if you want the Smart Glass to show the local stream, not the sent image, please push `Remote Video Start` button.
1. On the PC, you can `Capture` anytime and draw red lines as many as you want, and `Send` it to the remote Smart Glass.  
<!-- ![procedure_07](https://user-images.githubusercontent.com/12380403/30556992-cad9cb4a-9ce7-11e7-9f81-705ed58e576b.png) -->
![](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/procedure_07.png)

## When the Android is not on WiFi...
If you are off from WiFi, you'll see such a message "After connecting to WIFI, push "RESTART" button below."
As the message says, please connect to WiFi and after that push "RESTART" button, then you'll see the URL to be connected.  
<!-- ![no_wifi](https://user-images.githubusercontent.com/12380403/30557007-db171530-9ce7-11e7-8c96-2362ad135042.png) -->
![no wifi](https://raw.githubusercontent.com/wiki/u-ryo/SharedEye/images/no_wifi.png "No Wifi")


## Why do you use the old version of xwalk?
Because I had to use it on Vuzix M100(Android 4.0.4) first.
