package com.moto.tablet.model

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moto.tablet.R

data class PMSPackageContent(
    val id: Long,
    val title: String,
    val description: String,
    var isRemoved: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PMSPackageContent) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        return isRemoved == other.isRemoved
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + isRemoved.hashCode()
        return result
    }
}

data class PMSPackage(
    val id: Long,
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
    val price: Float = 0f,
    var contentList: List<PMSPackageContent>,
    val available: Boolean = true,
) {
    @get:StringRes
    val statusString: Int
        get() = if (available) {
            R.string.available
        } else {
            R.string.unavailable
        }

    val orderId: String
        get() = "pms_package_$id"

    fun availableItemsCountString(resources: Resources): String {
        val totalContentCount = contentList.size
        val availableContentCount = contentList.filter { !it.isRemoved }.size
        return resources.getString(
            R.string.package_available_item_count_string,
            availableContentCount,
            totalContentCount,
            resources.getQuantityString(R.plurals.items, availableContentCount)
        )
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PMSPackage) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (image != other.image) return false
        if (price != other.price) return false
        if (contentList != other.contentList) return false
        return available == other.available
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + image
        result = 31 * result + price.hashCode()
        result = 31 * result + contentList.hashCode()
        result = 31 * result + available.hashCode()
        return result
    }
}

fun fakePackage(): PMSPackage =
    PMSPackage(
        1,
        "Package 1",
        "Package 1 description",
        R.drawable.ic_pms_package,
        1200f,
        fakePackageContentList_1
    )

fun fakePackages(): List<PMSPackage> {
    return listOf(
        PMSPackage(
            1,
            "Package 1",
            "Package 1 description",
            R.drawable.ic_pms_package,
            1200f,
            fakePackageContentList_1 + fakePackageContentList_3 + fakePackageContentList_3
        ),
        PMSPackage(
            2,
            "Package 2",
            "Package 1 description",
            R.drawable.ic_pms_package,
            1000f,
            fakePackageContentList_2
        ),
        PMSPackage(
            3,
            "Package 3",
            "Package 1 description",
            R.drawable.ic_pms_package,
            500f,
            fakePackageContentList_3
        )
    )
}

val fakePackageContentList_1 = listOf(
    PMSPackageContent(1,"Change Oil", "Change Oil description"),
    PMSPackageContent(2,"Check Brakes", "Change Oil description"),
    PMSPackageContent(3,"Check Chain", "Change Oil description"),
    PMSPackageContent(4,"Change Oil Filter", "Change Oil description"),
    PMSPackageContent(5,"Check Tire Pressure", "Change Oil description"),
    PMSPackageContent(6,"Check Coolant", "Change Oil description")
)
val fakePackageContentList_2 = listOf(
    PMSPackageContent(1,"Change Oil", "Change Oil description"),
    PMSPackageContent(2,"Check Brakes", "Change Oil description"),
    PMSPackageContent(3,"Check Chain", "Change Oil description"),
    PMSPackageContent(4,"Change Oil Filter", "Change Oil description"),
    PMSPackageContent(5,"Check Tire Pressure", "Change Oil description"),
    PMSPackageContent(6,"Check Coolant", "Change Oil description"),
    PMSPackageContent(7,"Check Bolt Engine", "Change Oil description"),
    PMSPackageContent(8,"Check Clutch Cable", "Change Oil description")
)
val fakePackageContentList_3 = listOf(
    PMSPackageContent(1,"Change Oil", "Change Oil description"),
    PMSPackageContent(2,"Check Brakes", "Change Oil description"),
    PMSPackageContent(3,"Check Chain", "Change Oil description"),
    PMSPackageContent(4,"Change Oil Filter", "Change Oil description"),
    PMSPackageContent(5,"Check Tire Pressure", "Change Oil description"),
    PMSPackageContent(6,"Check Coolant", "Change Oil description"),
    PMSPackageContent(7,"Check Bolt Engine", "Change Oil description"),
    PMSPackageContent(8,"Check Clutch Cable", "Change Oil description"),
    PMSPackageContent(9,"Check Wirings", "Change Oil description"),
    PMSPackageContent(10,"Check Air Pressure", "Change Oil description"),
    PMSPackageContent(11,"Check Spark Plug", "Change Oil description"),
    PMSPackageContent(12,"Check Throttle Play", "Change Oil description"),
    PMSPackageContent(13,"Clean Clutch Cable",  "Change Oil description"),
)