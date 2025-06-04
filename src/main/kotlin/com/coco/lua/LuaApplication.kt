package com.coco.lua

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LuaApplication

fun main(args: Array<String>) {
	runApplication<LuaApplication>(*args)
}
