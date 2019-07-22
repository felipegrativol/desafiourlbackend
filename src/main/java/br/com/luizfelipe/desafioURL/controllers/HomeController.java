package br.com.luizfelipe.desafioURL.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import br.com.luizfelipe.desafioURL.models.IDConverter;
import br.com.luizfelipe.desafioURL.models.URL;
import br.com.luizfelipe.desafioURL.repository.URLrepository;



@RestController
public class HomeController{
	
	public static final String HTTP_PREFIX = "http://";
	public static final String HTTPS_PREFIX = "https://";
	
	 private final String baseServerUrl = "http://localhost:8080/";

	 @Autowired
	    private URLrepository URLrepository;
	
	 @CrossOrigin
	 @RequestMapping(value = "/urls", method = RequestMethod.GET)
	    public List<URL> Get() {
	        return URLrepository.findAll();
	    }
	
	
	 @CrossOrigin
	 @RequestMapping(path="/{shorturl}", method = RequestMethod.GET)
	    public void stack(HttpServletRequest request, HttpServletResponse response,@PathVariable (value = "shorturl")String shortURL) throws IOException {
		 URL url = URLrepository.findByShortURL(shortURL);   
		 response.sendRedirect("http://"+url.getNome());
	    }
	 
	  @CrossOrigin
	  @RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<URL> insert(@Valid @RequestBody URL url) {
			
			String goingshort = url.getNome();
		    IDConverter idconv = new IDConverter();
			url.setShorturl(idconv.encode(goingshort));
		    URLrepository.save(url);
		    return new ResponseEntity<URL>(url, HttpStatus.OK);
		   
		}
	
	  @CrossOrigin
	  @RequestMapping(path="/edit/{nome}/{editnome}",method=RequestMethod.POST )
	  public ResponseEntity update(
			  @PathVariable("nome")String nome,
			  @PathVariable("editnome") String editnome, 
			  URL url) {
	     
		   url = URLrepository.findByNome(nome);
		 
		  if (url.getNome().length()!=0) { 
			 URL find = URLrepository.getOne(url.getId());
			 find.setNome(editnome);
			// return URLrepository.save(find);
			 return new ResponseEntity<URL>(URLrepository.save(find), HttpStatus.OK); 
		  }else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	  }
	 
	  
	 
	
	  @CrossOrigin
	  @RequestMapping(path="/delete/{nome}",method=RequestMethod.DELETE ) 
	  public ResponseEntity delete
	  (@RequestParam("nome") String nome) {
	  
	  URL url = URLrepository.findByNome(nome);
	  
	  if (url.getNome().length()!=0) {
		  URLrepository.delete(url);
		  return new ResponseEntity<String>( url.getNome() , HttpStatus.OK); }else { return new
	  ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND); } }
	
	  
	  
	  
	  
	
	 
	
	
}
