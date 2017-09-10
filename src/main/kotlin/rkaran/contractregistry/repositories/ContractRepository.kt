package rkaran.contractregistry.repositories

import rkaran.contractregistry.util.ConfigReader

/**
 * Created by rkaran on 08/09/17.
 */
interface ContractRepository {

    companion object {
        fun create():ContractRepository {
            return with(ConfigReader.getString("contract.repository")) {
                ClassLoader.getSystemClassLoader().loadClass(this)
            }.getConstructor().newInstance() as ContractRepository
        }
    }

    fun register(fqn:String, code:ByteArray):Boolean
    fun fetch(fqn:String, version:Int):ByteArray
}