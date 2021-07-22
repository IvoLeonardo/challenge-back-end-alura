package br.com.ilsc.aluraflix.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ilsc.aluraflix.Repositoy.VideoRepository;
import br.com.ilsc.aluraflix.controller.dto.VideoDto;
import br.com.ilsc.aluraflix.controller.form.VideoForm;
import br.com.ilsc.aluraflix.model.Video;

@RestController
@RequestMapping("/videos")
public class VideosController {

	@Autowired
	private VideoRepository videoRepository;

	@GetMapping
	public List<VideoDto> lista(String titulo) {

		if (titulo == null) {
			List<Video> videos = videoRepository.findAll();
			return VideoDto.converter(videos);
		} else {
			List<Video> videos = videoRepository.findByTitulo(titulo);
			return VideoDto.converter(videos);
		}

	}

	@PostMapping
	@Transactional
	public ResponseEntity<VideoDto> cadastrar(@RequestBody @Valid VideoForm form, UriComponentsBuilder uriBuilder) {

		Video video = form.converter();

		videoRepository.save(video);

		URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new VideoDto(video));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VideoDto> detalhar(@PathVariable Long id) {

		Optional<Video> video = videoRepository.findById(id);

		if (video.isPresent()) {
			return ResponseEntity.ok(new VideoDto(video.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<VideoDto> atualizar(@PathVariable Long id, @RequestBody @Valid VideoForm form) {

		Video video = form.atualizar(id, videoRepository);

		return ResponseEntity.ok(new VideoDto(video));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {

		Optional<Video> optional = videoRepository.findById(id);

		if (optional.isPresent()) {
			videoRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
