package phonedb

class PhoneTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]


    def renderFilter = { attrs ->
        out << attrs.filterHtml
    }
}
