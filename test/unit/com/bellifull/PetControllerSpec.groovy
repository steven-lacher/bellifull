package com.bellifull



import grails.test.mixin.*
import spock.lang.*

@TestFor(PetController)
@Mock(Pet)
class PetControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.petInstanceList
            model.petInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.petInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def pet = new Pet()
            pet.validate()
            controller.save(pet)

        then:"The create view is rendered again with the correct model"
            model.petInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            pet = new Pet(params)

            controller.save(pet)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/pet/show/1'
            controller.flash.message != null
            Pet.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def pet = new Pet(params)
            controller.show(pet)

        then:"A model is populated containing the domain instance"
            model.petInstance == pet
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def pet = new Pet(params)
            controller.edit(pet)

        then:"A model is populated containing the domain instance"
            model.petInstance == pet
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/pet/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def pet = new Pet()
            pet.validate()
            controller.update(pet)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.petInstance == pet

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            pet = new Pet(params).save(flush: true)
            controller.update(pet)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/pet/show/$pet.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/pet/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def pet = new Pet(params).save(flush: true)

        then:"It exists"
            Pet.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(pet)

        then:"The instance is deleted"
            Pet.count() == 0
            response.redirectedUrl == '/pet/index'
            flash.message != null
    }
}
