package com.letsstart.testapplication.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.letsstart.testapplication.model.Test;



@RestController
@RequestMapping("/test")
public class TestController {
	
	
	String server_url = "http://localhost:8080/SpringHibernate/";
	
	/**
    *
    * @param check boolean, to test the api working or not
    * @return if check = true, reutrn 200, false return 500
    */
	@GetMapping("/endpoint/{check}")
	public ResponseEntity testing(@PathVariable("check") boolean check) {
        if (check == false) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
	
//	get all list contact
	@GetMapping("/endpoint/listContact")
    public ResponseEntity getListContact() {
	    String url = server_url; 
        final String uri = url + "contact/list/";
        RestTemplate restTemplate = new RestTemplate();

        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
        httpMessageConverter.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(httpMessageConverter);
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("text", "html", utf8);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        requestHeaders.set("User-Agent", "mozilla");
        requestHeaders.set("Accept-Language", "cn");

        HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        String object = response.getBody();
        System.out.println(object);
        return new ResponseEntity<String>(object, HttpStatus.OK);
    }
	
	@GetMapping("/endpoint/contact/{cid}")
    public ResponseEntity getListContactById(@PathVariable("cid") int cid) {
	    String url = server_url; 
        final String uri = url + "contact/" + cid + "/";
        RestTemplate restTemplate = new RestTemplate();

        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
        httpMessageConverter.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(httpMessageConverter);
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("text", "html", utf8);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        requestHeaders.set("User-Agent", "mozilla");
        requestHeaders.set("Accept-Language", "cn");

        HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        String object = response.getBody();
        return new ResponseEntity<String>(object, HttpStatus.OK);
	}
	
	@PostMapping("/endpoint/contact")
    public ResponseEntity addContact(@RequestBody Test t) throws JSONException, Exception {
	    String url = server_url; 
        final String uri = url + "contact/";
        System.out.println(uri);
//        SSLUtil.disableCertificateVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        requestHeaders.set("Accept-Charset", "UTF-8");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Content-type", "application/json;charset=UTF-8");
        
        JSONObject request = new JSONObject();
        request.put("name", t.getName());
        request.put("phone", t.getPhone());
        request.put("gender", t.isGender());
        System.out.println(request.toString());
        
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), requestHeaders);
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(uri, HttpMethod.POST, entity, String.class);
        String object = loginResponse.getBody();

        System.out.println(object);

        return new ResponseEntity<String>(object, HttpStatus.OK);   
    }

}
