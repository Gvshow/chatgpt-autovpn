package com.example.v2bridgelauncher

object LauncherConfig {
    /**
     * 改成你要最终打开的应用包名。
     * 这里先给一个常见示例：微信。
     */
    const val TARGET_APP_PACKAGE = "com.openai.chatgpt"

    /**
     * v2rayNG 当前包名。
     */
    const val V2RAYNG_PACKAGE = "com.v2ray.ang"

    /**
     * v2rayNG 小组件点击广播动作。
     * 来自 v2rayNG AppConfig.BROADCAST_ACTION_WIDGET_CLICK。
     */
    const val V2RAYNG_WIDGET_ACTION = "com.v2ray.ang.action.widget.click"

    /**
     * 显式广播到的小组件接收器。
     */
    const val V2RAYNG_WIDGET_RECEIVER = "com.v2ray.ang/com.v2ray.ang.receiver.WidgetProvider"

    /**
     * 最长等待 VPN 建立的时间。
     */
    const val VPN_WAIT_TIMEOUT_MS = 12_000L

    /**
     * 轮询间隔。
     */
    const val VPN_POLL_INTERVAL_MS = 400L

    /**
     * 当 VPN 超时未建立时，是否仍然强行打开目标应用。
     * 为了避免目标应用在裸连状态下联网，默认 false。
     */
    const val LAUNCH_TARGET_IF_VPN_TIMEOUT = false

    /**
     * 如果当前已经有 VPN 连接，则不再触发 v2rayNG 开关，只直接打开目标应用。
     */
    const val SKIP_TOGGLE_WHEN_VPN_ALREADY_UP = true
}
