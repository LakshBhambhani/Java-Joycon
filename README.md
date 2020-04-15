# Java-Joycon

Find javadocs here: https://lakshbhambhani.github.io/Java-Joycon/

Gradle instructions: https://plugins.gradle.org/plugin/com.laksh.JoyconLib

## Gradle - Use with Groovy
### Using Plugins DSL

```
plugins {
  id "com.laksh.JoyconLib" version "1.0"
}
```

### Using Legacy Plugin Application

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.laksh.JoyconLib:JoyconLib:1.0"
  }
}

apply plugin: "com.laksh.JoyconLib"
```

## Gradle - Use with Kotlin
### Using Plugins DSL

```
plugins {
  id("com.laksh.JoyconLib") version "1.0"
}
```

### Using Legacy Plugin Application

```
buildscript {
  repositories {
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
  }
  dependencies {
    classpath("gradle.plugin.com.laksh.JoyconLib:JoyconLib:1.0")
  }
}

apply(plugin = "com.laksh.JoyconLib")
```

## Other Approaches
https://github.com/AlexCouch/JoyCouch<br>
https://github.com/elgoupil/joyconLib
