package rkaran.contractregistry

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import rkaran.contractregistry.util.ConfigReader

@SpringBootApplication
open class App  {

    companion object {
        @JvmStatic
        fun main(args:Array<String>) {
            System.getProperties().putAll(ConfigReader.get("app"))
            SpringApplication.run(App::class.java, *args)
        }
    }
}

