package com.example.v2bridgelauncher

import java.util.concurrent.TimeUnit

object RootShell {
    data class Result(
        val code: Int,
        val stdout: String,
        val stderr: String,
        val timedOut: Boolean,
    ) {
        val ok: Boolean get() = !timedOut && code == 0
    }

    fun run(command: String, timeoutMs: Long = 8000L): Result {
        val process = ProcessBuilder("su", "-c", command)
            .redirectErrorStream(false)
            .start()

        val stdoutReader = process.inputStream.bufferedReader()
        val stderrReader = process.errorStream.bufferedReader()

        val finished = process.waitFor(timeoutMs, TimeUnit.MILLISECONDS)
        if (!finished) {
            process.destroy()
            return Result(
                code = -1,
                stdout = safeRead(stdoutReader),
                stderr = safeRead(stderrReader),
                timedOut = true,
            )
        }

        return Result(
            code = process.exitValue(),
            stdout = safeRead(stdoutReader),
            stderr = safeRead(stderrReader),
            timedOut = false,
        )
    }

    private fun safeRead(reader: java.io.BufferedReader): String {
        return runCatching { reader.readText() }.getOrDefault("")
    }
}
