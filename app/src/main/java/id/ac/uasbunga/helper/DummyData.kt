package id.ac.uasbunga.helper

import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.data.model.User

object DummyData {
    val admin = User(username = "admin", password = "admin")
    val flowers = arrayOf(
        Barang(
            nama = "Blue Roses Silver Leaves",
            kode = "BRG_01",
            gambar = "flower1",
            stok = 10,
            harga = 650000
        ),
        Barang(
            nama = "10 Tulip With Baby Breath",
            kode = "BRG_02",
            gambar = "flower2",
            stok = 20,
            harga = 600000
        ),
        Barang(
            nama = "50 Roses With 5 Lily",
            kode = "BRG_03",
            gambar = "flower3",
            stok = 15,
            harga = 1600000
        ),
        Barang(
            nama = "99 Roses Bouquet",
            kode = "BRG_04",
            gambar = "flower4",
            stok = 18,
            harga = 2200000
        ),
    )
}