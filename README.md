[![Build Status](https://travis-ci.org/tslamic/luas-api.svg?branch=master)](https://travis-ci.org/tslamic/luas-api)
[![codecov](https://codecov.io/gh/tslamic/luas-api/branch/master/graph/badge.svg)](https://codecov.io/gh/tslamic/luas-api)

# LUAS API

A simple Kotlin/Java library for [LUAS](https://www.luas.ie/) timetables.

## How to use it

Add the JitPack repository to your root `build.gradle` file:

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Then add the following to your `dependencies` block:

```groovy
dependencies {
  implementation 'com.github.tslamic:luas-api:0.1.0'
}
```
