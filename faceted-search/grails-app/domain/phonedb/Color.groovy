package phonedb

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'color')
class Color {

    static constraints = {
        color(unique: true, blank: false)
    }

    static searchable = {
        root: false
        color index:'not_analyzed'
    }

    void setColor(String color) {
        this.color = color?.trim()?.toLowerCase()
    }

    String color


    @Override
    public String toString() {
        color
    }
}
