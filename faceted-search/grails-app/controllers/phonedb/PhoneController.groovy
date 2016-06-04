package phonedb

import phonedb.common.FilterCommand

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhoneController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def aggregationService

//    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        respond Phone.list(params), model:[phoneInstanceCount: Phone.count()]
//    }

    def show(Phone phoneInstance) {
        respond phoneInstance
    }

    def create() {
        respond new Phone(params)
    }

    def index(FilterCommand command) {
        def results = aggregationService.getFiltersAndPhones(command)
        Collection<Phone> phones = results.phones

        def filterHtml = results.filter

        render view: "index", model: [phoneInstanceList: phones, filterHtml: filterHtml, phoneInstanceCount: results.total,
                                       minmaxBlue        : results.minmaxBlue]
    }

    @Transactional
    def save(Phone phoneInstance) {
        if (phoneInstance == null) {
            notFound()
            return
        }

        if (phoneInstance.hasErrors()) {
            respond phoneInstance.errors, view:'create'
            return
        }

        phoneInstance.sellers.each {
            it.addToPhones(phoneInstance)
            it.save(flush:true)
        }

        phoneInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'phone.label', default: 'Phone'), phoneInstance.id])
                redirect phoneInstance
            }
            '*' { respond phoneInstance, [status: CREATED] }
        }
    }

    def edit(Phone phoneInstance) {
        respond phoneInstance
    }

    @Transactional
    def update(Phone phoneInstance) {
        if (phoneInstance == null) {
            notFound()
            return
        }

        if (phoneInstance.hasErrors()) {
            respond phoneInstance.errors, view:'edit'
            return
        }

        //todo remove old
        phoneInstance.sellers.each {
            it.addToPhones(phoneInstance)
            it.save(flush:true)
        }

        phoneInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Phone.label', default: 'Phone'), phoneInstance.id])
                redirect phoneInstance
            }
            '*'{ respond phoneInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Phone phoneInstance) {

        if (phoneInstance == null) {
            notFound()
            return
        }

        phoneInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Phone.label', default: 'Phone'), phoneInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'phone.label', default: 'Phone'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
