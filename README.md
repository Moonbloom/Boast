# Boast

Library to easily create and modify Toast messages.

## Overview

**Boast** is a class that can be used by Android developers that feel the need for an **alternative to [Toast](http://developer.android.com/reference/android/widget/Toast.html)**.

A Boast will be displayed at the position of standard Toasts.
You can line up multiple Boasts for display, that will be shown one after another, modify colors, text, and even add an image.

You can check some features in the Boast Demo application that is also posted here in this repository.

### Current version: 0.2.5

## Usage

The API is made even more simple than the Toast API (No need to call .show() on Boasts):

Create a Boast for any CharSequence:

    Boast.makeText(Context, CharSequence);
    
Create a Boast for any CharSequence with a specific BStyle:

    Boast.makeText(Context, CharSequence, BStyle);
    
Create a Boast with a String from your application's resources:

	Boast.makeText(Context, int);
	
Create a Boast with a String from your application's resources and a specific BStyle:

	Boast.makeText(Context, int, BStyle);

## Basic Examples
Currently there are 4 different BStyles you can use out of the box (Or you can create your own, be creative!):

- **BStyle.OK**
- **BStyle.MESSAGE**
- **BStyle.CAUTION**
- **BStyle.ALERT**

In general you can modify:

- Display duration
- Padding settings
- Text size
- Custom Views
- Displayed image
- Image position
- Image size

## Developer
- [Kasper Hübner Liholm] (https://www.linkedin.com/profile/view?id=330373856)

## License

- [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)