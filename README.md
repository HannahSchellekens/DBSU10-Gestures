# DBSU10 Gestures Module #

## Functionality

### Installation
To use the gesture module, the one who should perform gestures must have an Android app installed with the gestures module code.
 This can be our own simple app that solely broadcasts events (see [OOCSI Messages](https://github.com/RubenSchellekens/DBSU10-Gestures#oocsi-messages)) or an integrated version into one of your own
 apps (see [Android Library](https://github.com/RubenSchellekens/DBSU10-Gestures#android-library)).

 To install the standalone app (the one that only broadcasts), you can download the latest `.apk` file from the releases-page.

### OOCSI Messages
Whenever the gestures module detects a gesture, a message is sent over OOCSI. The following data is contained in the message that is sent over the **`dbsu10-gestures`** channel:

```
{
    "gesture":"<gesture_id>",
    "mac":"<mac_address>"
}
```

Here is `<gesture_id>` the id of the gesture that has been performed and `<mac_address>` the physical address of the
device that performed the gesture.

### Android Library
It can be annoying for the customer to install multiple apps for one service. If you make an android app yourself you can
make use of our Android library to embed the functionality into your own app.

#### Project setup
To embed gestures you first need to download the android archive (`.aar`) from the releases-page.
After that you need to setup your project to include the archive.
You can do this by accessing your build file (`build.gradle`) and adding the following lines:

```
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'libs'
        }
    }
}
```

Then add the following line to the end of `dependencies`:

```
    compile(name: 'Gestures', ext: 'aar')
```

Now resync your project and you can start doing stuff.

#### Enabling gestures
The first thing you have to to is to enable Gestures in your `onCreate` method of the main activity.
The lines below will connect to OOCSI and will setup everything that Gestures need in order to detect and handle gestures.

```
OOCSIClient client = Gestures.newClient();
Gestures.enable(this, client);
```

#### Listening to gestures
Whenever a gesture is recognised, certain events will be triggered. These events can be added by yourself.
An example:

```
Gesture.ROLL_LEFT.addListener(new GestureHandler() {
    @Override
    public void gesturePerformed(GestureEvent gestureEvent) {
        Gesture gesture = gestureEvent.getGesture();
        String macAddress = gestureEvent.getMAC();
    }
});
```

This will add a `GestureHandler` to the `ROLL_LEFT` gesture. Every time the device detects a roll to the left, the given
 gesture handler will be executed. After the `GestureHandler` is done handling everything, the gesture message will be broadcasted
 over OOCSI. If you don't want to broadcast, you can use `gestureEvent.setCancelled(true)` to cancel OOCSI broadcasts.
 Besides that, the module will also give vibration feedback to the user. To disable that, you need to call `gestureEvent.setFeedback(false)`.

## Supported gestures

The following gestures are supported by default:
- `roll_left` - A small but firm roll to the left.
- `roll_right` - A small but firm roll to the right.
- `flip_up` - A small, but form rotation towards you.
- `flip_down` - A small, but form rotation away from you.

If there are gestures that you require and that are not in this list, please leave an [Issue](https://github.com/RubenSchellekens/DBSU10-Gestures/issues)
so we can add it.

### Custom gestures
If you want to implement your own gestures, you can. However, you have to write your own `GestureTracker`. After you have created your gesture
you need to enable it with `Gesture#enableGesture(Gesture)`.

An example:
```
Gesture myGesture = new Gesture("my_gesture", new MyGestureTracker());
Gesture.enableGesture(myGesture);
```
