package br.com.luizfelipe.desafioURL.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.luizfelipe.desafioURL.models.URL;

@Repository
public interface URLrepository extends JpaRepository<URL, Long> {

	URL findByShortURL(String shortURL);

	URL findByNome(String nome);

	
}
