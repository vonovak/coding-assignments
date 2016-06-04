package phonedb

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'brand')
class Brand {

    static searchable = {
        root: false
        brand multi_field: true
    }

    static constraints = {
        brand(unique: true, blank: false)
    }

    String brand

    @Override
    public String toString() {
        brand
    }
}
