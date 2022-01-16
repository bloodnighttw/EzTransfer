package io.github.bloodnighttw.ezTransfer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader

val gson: Gson = GsonBuilder().create()

fun String.getFirst(): String? {
	val array = this.toCharArray()
	for( i in array.indices){
		if( array[i] == ' ' && i != 0){
			return this.substring(0,i)
		}
	}
	return null
}

fun String.getSecond(): String?{
	val array = this.toCharArray()
	for( i in array.indices){
		if( array[i] == ' ' && i+1 < this.length){
			return this.substring(i+1)
		}
	}
	return null
}

fun main(args:Array<String>){

	for( i in args.indices){
		when(args[i]){
			"backup" -> run {
				parseToFile(args[i+1])
			}

			"back" -> run{

				back("backup.ezt")
			}
		}
	}
}

data class Beatmap(val id:String, val name:String)

fun parseToFile(path:String){

	val folder = File(path)
	val arraylist = ArrayList<Beatmap>()

	folder.listFiles()?.let { subFolder ->
		for( i in subFolder.filter { it.isDirectory }){
			val first = i.name.getFirst()
			val second = i.name.getSecond()
			if(first != null && second !=null)
				arraylist.add(Beatmap(first, second))
		}
	}

	File("backup.ezt").printWriter().use { out ->
		out.println(gson.toJson(arraylist))
	}

}

fun back(ezt:String){

	val a = FileReader(ezt).readText()
	val myType = object : TypeToken<ArrayList<Beatmap>>() {}.type
	val arrList = gson.fromJson<ArrayList<Beatmap>>(a, myType)

	val dir = File("${System.getProperty("user.dir")}/maps/")
	println(dir.path)
	if (dir.exists() || !dir.isDirectory) {
		dir.mkdir()
	}

	arrList.forEach {
		TestList.add(it)
	}

	runTasks()
}

