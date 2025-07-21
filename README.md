# <img src="uniconv-logo.svg" width="30" height="30"> Uniconv #

![Kotlin](https://img.shields.io/badge/-Kotlin-7f52ff?style=flat-square&logo=kotlin&logoColor=7f52ff&labelColor=white)
![GitHub release (with filter)](https://img.shields.io/github/v/release/magicstar7213/uniconv?logo=github&style=flat-square)
![GitHub](https://img.shields.io/github/license/magicstar7213/uniconv?logo=github&style=flat-square)

Uniconv is a unit converter using Compose Multiplatform.

It is very simple and intuitive to use.

## Development ##
This is a Kotlin Multiplatform Project, with support to JVM 21+ and from Android 8 Oreo to Android 14.

In order to run the current desktop app, run `./gradlew run` 

Conversion logic is inside [commonMain](/src/commonMain) module

### Testing ###

Currently test are being developed to test the accuracy if conversion logic. To run all tests execute `./gradlew check`
