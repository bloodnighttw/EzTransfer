package io.github.bloodnighttw.ezTransfer

import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

//57 04
val TestList = LinkedList<Beatmap>()
val executor: ThreadPoolExecutor = Executors.newFixedThreadPool(50) as ThreadPoolExecutor

var count = 0

fun runTasks() {

	for (i in TestList) {
		executor.submit(Thread4Pool(i))
	}

}

class Thread4Pool(private val a: Beatmap) : Runnable {
	override fun run() {

		val link = "https://beatconnect.io/b/${a.id}"
		val path = "${System.getProperty("user.dir")}/maps/${a.name}.osz"
		println("Start to download file from $link with file name ${a.name}")
		URL(link).openStream().use { input ->

			Channels.newChannel(input).use {
				FileOutputStream(path).use { output ->
					output.channel.transferFrom(it, 0, Long.MAX_VALUE)
				}
			}

		}

		println("file $path download done")
		count++

		if (count == TestList.size)
			executor.shutdown()

	}
}