# ProGuard rules
-keep public class * extends android.app.Application
-keep class com.topperg.data.local.entity.** { *; }
-keep class com.topperg.data.remote.model.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
