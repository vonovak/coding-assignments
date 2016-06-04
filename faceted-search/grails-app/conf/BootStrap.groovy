import phonedb.Color
import phonedb.Phone
import phonedb.Seller
import phonedb.Brand

import javax.servlet.http.HttpServletRequest

class BootStrap {
    def grailsApplication

    def init = { servletContext ->
        def samsung = Brand.findOrSaveWhere(brand: "Samsung")
        def apple = Brand.findOrSaveWhere(brand: "Apple")
        def ms = Brand.findOrSaveWhere(brand: "Microsoft")
        def lg = Brand.findOrSaveWhere(brand: "LG")
        def huawei = Brand.findOrSaveWhere(brand: "Huawei")
        def sony = Brand.findOrSaveWhere(brand: "Sony")

        def amazon = Seller.findOrSaveWhere(name: "Amazon")
        def bestbuy = Seller.findOrSaveWhere(name: "BestBuy")
        def newegg = Seller.findOrSaveWhere(name: "Newegg")
        def craiglist = Seller.findOrSaveWhere(name: "Craiglist")

        def red = Color.findOrSaveWhere(color: "red")
        def green = Color.findOrSaveWhere(color: "green")
        def blue = Color.findOrSaveWhere(color: "blue")

        if (Phone.list().size() == 0) {
            def p = new Phone(name: "iPhone 5C", brand: apple, color: red, screenSize: 4.7D, ram: 2, storage: 16).save()
            amazon.addToPhones(p)

            p = new Phone(name: "iPhone 6", brand: apple, color: red, screenSize: 4.7D, ram: 4, storage: 32).save()
            craiglist.addToPhones(p)
            amazon.addToPhones(p)
            p = new Phone(name: "Galaxy 7", brand: samsung, color: red, screenSize: 5d, ram: 2, storage: 32).save()
            bestbuy.addToPhones(p)
            newegg.addToPhones(p)
            amazon.addToPhones(p)
            craiglist.addToPhones(p)
            p = new Phone(name: "Galaxy 6", brand: samsung, color: blue, screenSize: 5d, ram: 2, storage: 32).save()
            amazon.addToPhones(p)
            craiglist.addToPhones(p)
            p = new Phone(name: "Lumia 900", brand: ms, color: blue, screenSize: 5d, ram: 2, storage: 32).save()
            bestbuy.addToPhones(p)
            newegg.addToPhones(p)
            p = new Phone(name: "Lumia 850", brand: ms, color: blue, screenSize: 5.2d, ram: 4, storage: 64).save()
            newegg.addToPhones(p)

            p = new Phone(name: "G3", brand: huawei, color: blue, screenSize: 5d, ram: 4, storage: 64).save()
            p = new Phone(name: "Nexus 5", brand: lg, color: blue, screenSize: 5d, ram: 4, storage: 64).save()
            amazon.addToPhones(p)

            p = new Phone(name: "Nexus 3", brand: lg, color: blue, screenSize: 3.9d, ram: 2, storage: 8).save()
            p = new Phone(name: "Nexus 6", brand: huawei, color: green, screenSize: 5.1d, ram: 4, storage: 32).save()
            craiglist.addToPhones(p)
            amazon.addToPhones(p)

            p = new Phone(name: "Xperia", brand: sony, color: green, screenSize: 4.7d, ram: 4, storage: 32).save()
            amazon.addToPhones(p)

            p = new Phone(name: "S3 mini", brand: samsung, color: green, screenSize: 3.8d, ram: 4, storage: 8).save()
            bestbuy.addToPhones(p)

            p = new Phone(name: "Honor", brand: huawei, color: green, screenSize: 4.8d, ram: 4, storage: 8).save()
            bestbuy.addToPhones(p)
            newegg.addToPhones(p)
            amazon.addToPhones(p)
            craiglist.addToPhones(p)

            craiglist.save(flush: true)
            bestbuy.save(flush: true)
            newegg.save(flush: true)
            amazon.save(flush: true)
        }
        //for spring service proxy, to use in services
        for (sc in grailsApplication.serviceClasses) {
            sc.clazz.metaClass.getItsProxy = { -> grailsApplication.mainContext.getBean(sc.propertyName) }
        }

        HttpServletRequest.metaClass.getSiteUrl = {
            return (delegate.scheme + "://" + delegate.serverName + delegate.getForwardURI())
        }
        HttpServletRequest.metaClass.getCompleteUrl = {
            return (delegate.scheme + "://" + delegate.serverName + delegate.getForwardURI() + '?' + delegate.queryString)
        }


        //url encode on strings
        String.metaClass.encodeURL = {
            java.net.URLEncoder.encode(delegate, "UTF-8")
        }

    }
    def destroy = {
    }
}
