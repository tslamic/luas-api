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
implementation 'com.github.tslamic:luas-api:0.1.1'
```

## Examples

```kotlin
val api = LuasApi()

// Get LUAS lines and its corresponding stops
api.lines()
  .subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(
    { showLines(it) },
    { showError(it) }
  )
        
// Show a forecast for the e.g. Sandyford stop.
api.forecast("SAN")
  .subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(
    { showForecast(it) },
    { showError(it) }
  )
```

## License

    Copyright 2018 Tadej Slamic
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
