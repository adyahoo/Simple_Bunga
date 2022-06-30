package id.ac.uasbunga.helper

import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.data.model.User
import java.lang.Exception

object LocalDataHelper {
    fun getAllUser(userHelper: UserHelper): ArrayList<User> {
        userHelper.open()
        val users = userHelper.getAllUser()
        return MappingHelper.mapCursorAllUser(users)
    }

    fun getAllBarang(barangHelper: BarangHelper): ArrayList<Barang> {
        barangHelper.open()
        val barangs = barangHelper.getAllBarang()
        return MappingHelper.mapCursorAllBarang(barangs)
    }

    fun getAllBarangKode(barangHelper: BarangHelper): ArrayList<String> {
        barangHelper.open()
        val kodes = barangHelper.getAllBarangKode()
        return MappingHelper.mapCursorAllBarangKode(kodes)
    }

    fun getBarang(barangHelper: BarangHelper, kodeBarang: String): Barang {
        barangHelper.open()
        val barang = barangHelper.getBarangByKode(kodeBarang)
        return MappingHelper.mapCursorBarang(barang)
    }
}