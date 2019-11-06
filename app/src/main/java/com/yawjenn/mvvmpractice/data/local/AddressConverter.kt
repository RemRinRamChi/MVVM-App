package com.yawjenn.mvvmpractice.data.local

import androidx.room.TypeConverter
import com.yawjenn.mvvmpractice.data.Address

const val ADDRESS_SEPARATOR = ";"

class AddressConverter {
    @TypeConverter
    fun fromAddressString(value: String?): Address? {
        value?.run {
            val splitAddress = split(ADDRESS_SEPARATOR)
            if(splitAddress.size == 4){
                return Address(
                    street = splitAddress[0],
                    suite = splitAddress[1],
                    city = splitAddress[2],
                    zipcode = splitAddress[3])
            }
        }

        return Address()
    }

    @TypeConverter
    fun addressToString(address: Address?): String? {
        if(address != null){
            return arrayOf(address.street, address.suite, address.city, address.zipcode).joinToString(separator = ADDRESS_SEPARATOR)
        } else {
            return ""
        }
    }
}