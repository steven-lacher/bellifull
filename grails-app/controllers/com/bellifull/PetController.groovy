package com.bellifull



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PetController {
	static scaffold = true
	
    
}
