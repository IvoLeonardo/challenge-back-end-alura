package br.com.ilsc.aluraflix.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ilsc.aluraflix.Repositoy.VideoRepository;
import br.com.ilsc.aluraflix.model.Video;

public class VideoForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String titulo;

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String descricao;

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String url;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Video converter() {

		return new Video(titulo, descricao, url);
	}

	public Video atualizar(Long id, VideoRepository videoRepository) {

		Video video = videoRepository.getOne(id);
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setUrl(url);
		return video;
	}

}
