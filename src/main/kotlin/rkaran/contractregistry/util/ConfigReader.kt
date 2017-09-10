package rkaran.contractregistry.util

import java.io.FileInputStream
import java.util.*

/**
 * Created by rkaran on 08/09/17.
 */
object ConfigReader {

    private val props = hashMapOf<String, String>().apply {
        val config = System.getProperty("config")
        if(null != config) {
            FileInputStream(config)
        } else {
            javaClass.getResourceAsStream("/config.properties")
        }.bufferedReader().use {
            it.lines()
                    .map { x -> x.split("=") }
                    .filter { x -> 2 == x.size }
                    .forEach { x -> this[x[0]] = x[1] }
        }
    }

    fun getInt(key:String):Int {
        return props[key]!!.toInt()
    }

    fun getDouble(key:String):Double {
        return props[key]!!.toDouble()
    }

    fun getString(key:String):String {
        return props[key]!!
    }

    fun getStrings(keys:Iterable<String>):Iterable<String> {
        return keys.map { key -> props[key]!! }
    }

    fun get(prefix:String):Properties {
        val newProps = Properties()
        for ((key, value) in props) {
            if (key.startsWith(prefix)) {
                newProps.put(key.substring(prefix.length + 1), value)
            }
        }
        return newProps
    }
}