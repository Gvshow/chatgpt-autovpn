# V2BridgeLauncher

一个极简 Android 启动器 App：

1. 点开图标
2. 通过 root 执行 `am broadcast`，触发 v2rayNG 的小组件开关逻辑
3. 轮询检测系统 `TRANSPORT_VPN`
4. VPN 建立后自动跳转到指定应用

## 你需要改的唯一关键配置

文件：

`app/src/main/java/com/example/v2bridgelauncher/LauncherConfig.kt`

把：

```kotlin
const val TARGET_APP_PACKAGE = "com.tencent.mm"
```

改成你要启动的目标 App 包名。

## 前提

- 手机已 root
- 已安装 v2rayNG
- v2rayNG 里已经手动选好要连接的节点
- 你至少手动成功连接过一次，确保 v2rayNG 的 VPN 授权已经给过

## 为什么用广播而不是直接拉起 ScSwitchActivity

因为 v2rayNG 内部确实有一个 `ScSwitchActivity` 会在运行中执行“开/关”切换，
但实际外部自动化里有人直接调用它时会遇到 permission error；
所以这个项目改为从 root shell 发送显式广播给它自己的 `WidgetProvider`，
走的是和桌面小组件点击一致的路径。

## 构建

用 Android Studio 直接打开工程目录即可。

如果你的环境里 AGP / Kotlin 版本不同，按 IDE 提示升级即可。
