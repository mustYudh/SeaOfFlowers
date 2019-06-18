<#import "../../common/shared_manifest_macros.ftl" as manifestMacros>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:tools="http://schemas.android.com/tools" package="com.hzrcht.seaofflowers">

    <application>

        <activity 
            android:name="${packageName}.${activityClass}Activity"
            android:screenOrientation="portrait"/>
            
    </application>
</manifest>
