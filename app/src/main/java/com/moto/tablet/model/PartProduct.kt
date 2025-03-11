package com.moto.tablet.model

data class Stock(
    val id: Long,
    val location: String,
    val quantity: Int
)

data class PartProduct(
    val id: Long,
    val sku: String,
    val productName: String,
    val quantity: Int,
    val price: Float,
    val images: List<String>,
    val stocks: List<Stock>,
) {

    val orderId: String
        get() = "part_product_$id"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PartProduct) return false

        if (id != other.id) return false
        if (sku != other.sku) return false
        if (productName != other.productName) return false
        if (quantity != other.quantity) return false
        if (price != other.price) return false
        if (images != other.images) return false
        return stocks == other.stocks
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + sku.hashCode()
        result = 31 * result + productName.hashCode()
        result = 31 * result + quantity
        result = 31 * result + price.hashCode()
        result = 31 * result + images.hashCode()
        result = 31 * result + stocks.hashCode()
        return result
    }
}

fun fakePartProduct(): PartProduct = PartProduct(
    id = 1,
    sku = "10000033424",
    productName = "Villain Carbon 870",
    quantity = 14,
    price = 1300f,
    images = listOf(
        "https://s3-alpha-sig.figma.com/img/82e7/6590/3e5d036d4d79cad100b4d3142b009537?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=O0C48l9V9iJdt~LqlulIxIGa2Y8j10y9AxlOcnD4yobW77FIhJ-YjDE2A0keV4FeNwURt5B~V0Sp5imfiXq0jZvlmkTlPVJMk17lGY~PQGZI1XSHZFb1r1hRHQWs2V52mwVR565q9zJ5uNKU-~2aENPAP8IgUnn1WM9-fGyj~JQXIWWCkPj6GWBV4vgDw7ZqWGmtG1YHp50Ea8e1aLR1JiAKUDAvxgaeFdItHCjQUXLGkrr4mddIMgALzNB18hItB4wFHqp3ysBoWw3nFJ8sxU07PFzDqCWCX49PIw9Rf0veuqQYnVCh3iEO-POYGGT0hGTRa7vp9YwOkCq8hcAE4g__",
        "https://s3-alpha-sig.figma.com/img/6420/7b49/a3b36670bd43978edfc7d4db21439f6b?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=pamS7dTPAgSDzp1hNmc6tPuKvD~EnlYCNL4wBa15is-wZAO9x91b~O3JVclfwfEL10ixMZQLSj3fVLVIHwVXMvOaPexiGoiZWr-mVjAcLvBykKLgs1S1FKlB-M20qRagemLRasWEGz2kjD1boYytJrQ1jpv67W2nMTJdsZAaiVwN5mWGompdc5EkFLiqTsYZnjXc0~f6ioVh5mF~b4nb6zHbY2LdiJIMc4zGOk5fPGvUtcQkkOU8RfyMo-BQwiAA-i0mdGFYWiUsZEVDQblfHOESzUfrppTV3dsdxgCfeBZUA6nfDwbUuaALaT7szu0Qn~YGyMkwVKquOAGn5VIbew__",
        "https://s3-alpha-sig.figma.com/img/82e7/6590/3e5d036d4d79cad100b4d3142b009537?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=O0C48l9V9iJdt~LqlulIxIGa2Y8j10y9AxlOcnD4yobW77FIhJ-YjDE2A0keV4FeNwURt5B~V0Sp5imfiXq0jZvlmkTlPVJMk17lGY~PQGZI1XSHZFb1r1hRHQWs2V52mwVR565q9zJ5uNKU-~2aENPAP8IgUnn1WM9-fGyj~JQXIWWCkPj6GWBV4vgDw7ZqWGmtG1YHp50Ea8e1aLR1JiAKUDAvxgaeFdItHCjQUXLGkrr4mddIMgALzNB18hItB4wFHqp3ysBoWw3nFJ8sxU07PFzDqCWCX49PIw9Rf0veuqQYnVCh3iEO-POYGGT0hGTRa7vp9YwOkCq8hcAE4g__",
        "https://s3-alpha-sig.figma.com/img/6420/7b49/a3b36670bd43978edfc7d4db21439f6b?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=pamS7dTPAgSDzp1hNmc6tPuKvD~EnlYCNL4wBa15is-wZAO9x91b~O3JVclfwfEL10ixMZQLSj3fVLVIHwVXMvOaPexiGoiZWr-mVjAcLvBykKLgs1S1FKlB-M20qRagemLRasWEGz2kjD1boYytJrQ1jpv67W2nMTJdsZAaiVwN5mWGompdc5EkFLiqTsYZnjXc0~f6ioVh5mF~b4nb6zHbY2LdiJIMc4zGOk5fPGvUtcQkkOU8RfyMo-BQwiAA-i0mdGFYWiUsZEVDQblfHOESzUfrppTV3dsdxgCfeBZUA6nfDwbUuaALaT7szu0Qn~YGyMkwVKquOAGn5VIbew__"
    ),
    stocks = fakeStocks()
)

fun fakePartProducts(): List<PartProduct> = listOf(
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
    fakePartProduct(),
)

fun fakeStocks(): List<Stock> = listOf(
    Stock(1, "Main Hub", 15),
    Stock(2, "Z1 Caloocan", 15),
    Stock(3, "Z1 Cubao", 15),
    Stock(4, "Z1 Fairview", 15),
    Stock(5, "Z1 Paranaque", 0),

)