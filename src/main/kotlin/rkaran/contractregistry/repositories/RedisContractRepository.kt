package rkaran.contractregistry.repositories

import redis.clients.jedis.Jedis
import rkaran.contractregistry.util.ConfigReader
import kotlin.concurrent.getOrSet

/**
 * Created by rkaran on 08/09/17.
 */
class RedisContractRepository:ContractRepository {

    private val clients = ThreadLocal<Jedis>()

    override fun fetch(fqn:String, version:Int) = getClient().hget(fqn.toByteArray(), version.toString().toByteArray())

    override fun register(fqn:String, code:ByteArray):Boolean {
        try {
            val jedis = getClient()
            jedis.watch(fqn)
            val len = jedis.hlen(fqn)
            jedis.hset(fqn.toByteArray(), (len + 1).toString().toByteArray(), code)
            jedis.unwatch()
            return true
        } catch(e:Exception) {
            return false
        }
    }

    private fun getClient() = clients.getOrSet {
        with(ConfigReader.getString("redis.host")) {
            val fields = this.split(":")
            Jedis(fields[0], Integer.parseInt(fields[1]))
        }
    }
}