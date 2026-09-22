package it.its.catalogoservice.web;

import it.its.catalogoservice.service.EsitoImport;
import it.its.catalogoservice.service.ImportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping
    public EsitoImport importa() {
        return importService.importa();
    }
}
