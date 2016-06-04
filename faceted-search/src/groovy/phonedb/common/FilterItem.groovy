package phonedb.common

@groovy.transform.InheritConstructors
class FilterItem {

//    static constraints = {
//        filterHeading(unique: true, blank: false)
//        filterLabels(validator: {(it.size() == dataNames.size() && dataNames.size() == dataValues.size() && dataValues.size() == matchingCounts.size())})
//        filterLabelAppend nullable: true
//    }
//
//    static hasMany = [filterLabels: String, dataValues: String, dataNames: String, matchingCounts: Integer]

    //FIXME this class sucks, change it to a class that represent a filter AND a class that represents individual filter rows

    String filterHeading
    List<String> filterLabels
    List<String> filterLabelAppend //used for units, ie inches, GHz...
    List<Object> dataValues
    List<String> dataNames
    List<Integer> matchingCounts
    List<Boolean> selected

    FilterItem(String filterHeading) {
        this.filterHeading = filterHeading
        filterLabels = new ArrayList<>();
        filterLabelAppend = new ArrayList<>();
        dataValues = new ArrayList<>();
        dataNames = new ArrayList<>();
        matchingCounts = new ArrayList<>();
        selected = new ArrayList<>();
    }
}
