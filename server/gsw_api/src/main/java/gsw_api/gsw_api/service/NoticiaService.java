package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Transactional
    public Noticia create(DadosNoticia dadosNoticia) {
        Noticia noticia = new Noticia();
        noticia.setTitulo(dadosNoticia.titulo());
        noticia.setConteudo(dadosNoticia.conteudo());
        noticia.setDataPublicacao(dadosNoticia.dataPublicacao());
        noticia.setAutor(dadosNoticia.autor());
        // Defina tags aqui, se necessário

        return noticiaRepository.save(noticia);
    }

    public Optional<Noticia> findById(Long id) {
        return noticiaRepository.findById(id);
    }

    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    @Transactional
    public Noticia update(Long id, DadosNoticia dadosNoticia) {
        Optional<Noticia> optionalNoticia = noticiaRepository.findById(id);
        if (optionalNoticia.isPresent()) {
            Noticia noticia = optionalNoticia.get();
            noticia.setTitulo(dadosNoticia.titulo());
            noticia.setConteudo(dadosNoticia.conteudo());
            noticia.setDataPublicacao(dadosNoticia.dataPublicacao());
            noticia.setAutor(dadosNoticia.autor());
            // Atualize tags aqui, se necessário
            return noticiaRepository.save(noticia);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        noticiaRepository.deleteById(id);
    }

    public List<Noticia> filtrarNoticias(FiltroNoticia filtro) {
        return noticiaRepository.findAll((root, query, criteriaBuilder) -> {
            Specification<Noticia> spec = Specification.where(null);

            if (filtro.getTitulo() != null && !filtro.getTitulo().isEmpty()) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.like(root1.get("titulo"), "%" + filtro.getTitulo() + "%"));
            }
            if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
                spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.between(root1.get("dataPublicacao"), filtro.getDataInicio(), filtro.getDataFim()));
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        });
    }


}
