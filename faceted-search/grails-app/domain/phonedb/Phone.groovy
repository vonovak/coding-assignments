package phonedb

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'name,brand,color')
class Phone {

    static searchable = {
        root: true

        sellers component: true
        brand component: 'inner'
        color component: 'inner'
    }

    static constraints = {
        name unique: true, blank: false
    }

    static hasMany = [sellers: Seller]
    Set<Seller> sellers;
    static belongsTo = Seller

    Double screenSize
    Integer ram
    Integer storage
    Brand brand
    Color color
    String name


    @Override
    public String toString() {
        brand.toString() + " " + name
    }
}
