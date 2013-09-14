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
