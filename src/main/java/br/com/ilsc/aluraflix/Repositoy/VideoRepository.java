package br.com.ilsc.aluraflix.Repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ilsc.aluraflix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findByTitulo(String titulo);

}
