package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<DadosTag> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosTag> getTagById(@PathVariable Long id) {
        DadosTag dadosTag = tagService.findById(id);
        return ResponseEntity.ok(dadosTag);
    }

    @PostMapping
public ResponseEntity<DadosTag> createTag(@RequestBody DadosTag dadosTag) {
    try {
        if (dadosTag.nome() == null || dadosTag.nome().trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }

        DadosTag createdTag = tagService.save(dadosTag);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
