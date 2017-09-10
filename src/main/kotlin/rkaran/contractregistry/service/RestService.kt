package rkaran.contractregistry.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rkaran.contractregistry.repositories.ContractRepository
import javax.servlet.http.HttpServletRequest

/**
 * Created by rkaran on 08/09/17.
 */
@RestController
class RestService {

    private val contractRegistry = ContractRepository.create()

    @RequestMapping(path = arrayOf("/ping"), method = arrayOf(RequestMethod.GET))
    fun ping():ResponseEntity<String> {
        return ResponseEntity.ok("It Works!")
    }

    @RequestMapping(path = arrayOf("/codes/{id}"), method = arrayOf(RequestMethod.PUT))
    fun codes(@PathVariable("id") id:String, req:HttpServletRequest):ResponseEntity<String> {
        if(this.contractRegistry.register(id, req.inputStream.buffered().use { it.readBytes() })) {
            return ResponseEntity.accepted().build()
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @RequestMapping(path = arrayOf("/codes/{id}"), method = arrayOf(RequestMethod.GET))
    fun codes(@PathVariable("id") id:String, @RequestParam("version") version:Int):ResponseEntity<ByteArray> {
        val code = this.contractRegistry.fetch(id, version)
        if(null != code) {
            return ResponseEntity.ok(code)
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}