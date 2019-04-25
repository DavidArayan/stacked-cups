<h3 align="center">
  <img src="graphics/icon.png?raw=true" alt="Stacked-Cups Logo" width="250">
</h3>

[![Twitter: @DavidArayan](https://img.shields.io/badge/contact-DavidArayan-blue.svg?style=flat)](https://twitter.com/DavidArayan)
[![License](https://img.shields.io/badge/license-MIT-orange.svg?style=flat)](LICENSE)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/24d33661cc5f40748769524946b8e701)](https://app.codacy.com/app/DavidArayan/stacked-cups?utm_source=github.com&utm_medium=referral&utm_content=DavidArayan/stacked-cups&utm_campaign=Badge_Grade_Dashboard)

Android/Java application solving the [Stacked Cups](https://math.stackexchange.com/questions/2164392/display-of-cups-in-triangle-pyramid) problem using OO design patterns. This repository was created as a simple code exercise.

***

#### Required Tools

The following tools are required to compile and run the code.

-   [Android Studio](https://developer.android.com/studio)

#### Guide

Refer to the following guides to run the code using Android Studio.

-   [Running Android Application](https://developer.android.com/studio/run)
-   [Running Unit Tests](https://developer.android.com/studio/test)

#### Launching the app

Compile and run either in an Android Device simulator or on an Android device via Android Studio.

#### Assumptions

the following assumptions about the problem have been made during the coding exercise.

-   The smallest amount of liquid that can be added into the cups is 1ml
-   There is no inherent limit to how large the stacked cups can grow, however this application has a depth limit of 65536 including the root cup (topmost cup)
-   The UI/UX has been designed to allow adding liquid from the top of the stacked cups, however the structure allows adding liquid to any of the cups at any time
-   liquid can be removed from any of the cups at any time without breaking the structure
-   The application is stateful, as in liquid can be added and removed at any time and state will be preserved
-   The Stacked Cups structure grows automatically depending on the amount of liquid being added, cups which have not been created cannot be queried. Previously empty cups can still be queried.
-   The Data Structure used for the Stacked Cups assumes that the stack of the cups will always be triangular/pyramid in shape
-   Individual cups in the Stacked Cups Data Structure cannot have variable volumes, however the volume can be customized when the first cup is created (the root cup)

#### Known bugs and issues

There are at least 2 critical logic bugs in the application, but I'm not going to say what they are. If they can be found, I'm happy to discuss them.
