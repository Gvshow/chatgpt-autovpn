apply plugin: 'com.android.application'

android {
    compileSdkVersion 32

    defaultConfig {
        applicationId "com.example.v2bridgelauncher"
        minSdkVersion 21
        targetSdkVersion 32
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.4.0'
    implementation 'androidx.core:core-ktx:1.7.0'
}