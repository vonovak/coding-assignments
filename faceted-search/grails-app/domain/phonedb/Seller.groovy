package phonedb

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'name')
class Seller {

    static searchable = {
        root: false
        except = ['phones']
    }

    static constraints = {
        name unique: true, blank: false
    }

    static hasMany = [phones: Phone]

    String name

    @Override
    public String toString() {
        name
    }
}
