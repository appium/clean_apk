# Building

After building `Clean.apk` run `java -jar unsign.jar Clean.apk`

[unsign](https://github.com/bootstraponline/unsign)

## From Eclipse

To build, use the export option in Eclipse.

![](img/eclipse_apk_export.png)

## From Command Line

    $ cd into/this/repo
    $ ant debug

You should now have a `clean_apk-debug.apk` under the `bin/` directory.

## Notes

Clean APK uses [Instrumentation](http://developer.android.com/reference/android/app/Instrumentation.html)
which means the app process will be running when the data is cleared. Instrumentation runs from within the app process.

Some apps may have issues with data being removed when the app is running.
