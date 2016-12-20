#EasyPermissions

This Android library is very simple to use and very helpful. You can register a number of permissions you want to check in runtime.

##Installation

###Gradle

Add the following lines on your module's build.gradle file
```gradle
compile 'lalosoft.android-utilities:easypermissions:1.0.0'
```


###Maven
```xml
<dependency>
  <groupId>lalosoft.android-utilities</groupId>
  <artifactId>easypermissions</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

##Usage

You can use the annotations to register the permissions in your activity class. Add the following line above your activity class declaration

```java
@RegisterPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
```

The property **permissions** can contain an array of permissions or a simple permission string.

Then, you need to extend of _EasyPermissionActivity_

```java
public class MainActivity extends EasyPermissionActivity {
```

And that's it!! Very very simple to use!

##Optional

You can know when the permissions were granted or denied by overriding the following methods of _EasyPermissionActivity_

```java
    @Override
    public void onRequestPermissionGranted(String[] permission, int[] grantResults) {
        // The permissions were granted
    }

    @Override
    public void onRequestPermissionDenied(String[] permission, int[] grantResults) {
        // The permissions were denied
    }
```

##License
    Copyright 2016 EasyPermissions

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
