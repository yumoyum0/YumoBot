package top.yumoyumo.yumobot.mirai.tool

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2023/4/25 12:41
 **/
import net.mamoe.mirai.internal.utils.*
import net.mamoe.mirai.utils.*
import java.time.*

@Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
public object FixProtocolVersion {

    @JvmStatic
    internal fun last(current: MiraiProtocolInternal, target: MiraiProtocolInternal): MiraiProtocolInternal {
        return if (current.id < target.id) {
            target
        } else {
            current
        }
    }

    @JvmStatic
    public fun update() {
        MiraiProtocolInternal.protocols.merge(
                BotConfiguration.MiraiProtocol.ANDROID_PHONE,
                MiraiProtocolInternal(
                        apkId = "com.tencent.mobileqq",
                        id = 537153294,
                        ver = "8.9.35.10440",
                        sdkVer = "6.0.0.2535",
                        miscBitMap = 0x08f7_ff7c,
                        subSigMap = 0x0001_0400,
                        mainSigMap = 0x00ff_32f2,
                        sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D",
                        buildTime = 1676531414L,
                        ssoVersion = 19
                ),
                ::last
        )
        MiraiProtocolInternal.protocols.merge(
                BotConfiguration.MiraiProtocol.ANDROID_PAD,
                MiraiProtocolInternal(
                        apkId = "com.tencent.mobileqq",
                        id = 537152242,
                        ver = "8.9.35.10440",
                        sdkVer = "6.0.0.2535",
                        miscBitMap = 0x08f7_ff7c,
                        subSigMap = 0x0001_0400,
                        mainSigMap = 0x00ff_32f2,
                        sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D",
                        buildTime = 1676531414L,
                        ssoVersion = 19
                ),
                ::last
        )
        MiraiProtocolInternal.protocols.merge(
                BotConfiguration.MiraiProtocol.ANDROID_WATCH,
                MiraiProtocolInternal(
                        apkId = "com.tencent.qqlite",
                        id = 537065138,
                        ver = "2.0.8",
                        sdkVer = "6.0.0.2365",
                        miscBitMap = 0x00f7_ff7c,
                        subSigMap = 0x0001_0400,
                        mainSigMap = 0x00ff_32f2,
                        sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D",
                        buildTime = 1559564731L,
                        ssoVersion = 5
                ),
                ::last
        )
        MiraiProtocolInternal.protocols.merge(
                BotConfiguration.MiraiProtocol.IPAD,
                MiraiProtocolInternal(
                        apkId = "com.tencent.minihd.qq",
                        id = 537151363,
                        ver = "8.9.33.614",
                        sdkVer = "6.0.0.2433",
                        miscBitMap = 0x08f7_ff7c,
                        subSigMap = 0x0001_0400,
                        mainSigMap = 0x001e_10e0,
                        sign = "AA 39 78 F4 1F D9 6F F9 91 4A 66 9E 18 64 74 C7",
                        buildTime = 1640921786L,
                        ssoVersion = 19
                ),
                ::last
        )
        MiraiProtocolInternal.protocols.merge(
                BotConfiguration.MiraiProtocol.MACOS,
                MiraiProtocolInternal(
                        apkId = "com.tencent.minihd.qq",
                        id = 537128930,
                        ver = "5.8.9",
                        sdkVer = "6.0.0.2433",
                        miscBitMap = 0x08f7_ff7c,
                        subSigMap = 0x0001_0400,
                        mainSigMap = 0x001e_10e0,
                        sign = "AA 39 78 F4 1F D9 6F F9 91 4A 66 9E 18 64 74 C7",
                        buildTime = 1595836208L,
                        ssoVersion = 12
                ),
                ::last
        )
    }

    @JvmStatic
    public fun info(): Map<BotConfiguration.MiraiProtocol, String> {
        return MiraiProtocolInternal.protocols.mapValues { (protocol, info) ->
            val version = info.ver
            val datetime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(info.buildTime), ZoneId.systemDefault())

            "%-13s  %-12s  %s".format(protocol, version, datetime)
        }
    }
}