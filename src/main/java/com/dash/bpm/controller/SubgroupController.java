package com.dash.bpm.controller;

import java.text.Normalizer;

import com.dash.bpm.entities.Subgroup;
import com.dash.bpm.service.SubgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://127.0.0.1:5500/")

@RestController
@RequestMapping("/api/subgroups")
public class SubgroupController {

    private final SubgroupService subgroupService;

    @Autowired
    public SubgroupController(SubgroupService subgroupService) {
        this.subgroupService = subgroupService;
    }

    @GetMapping("/by-cod-cidade/{codCidade}")
    public ResponseEntity<List<Subgroup>> getSubgroupsByCodCidade(@PathVariable Integer codCidade) {
        List<Subgroup> subgroups = subgroupService.getSubgroupsByCodCidade(codCidade);
        if (subgroups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subgroups);
    }


    @GetMapping("/by-idbase/{idbase}")
    public ResponseEntity<Subgroup> getSubgroupByidbase(@PathVariable Integer idbase) {
        Subgroup subgroup = subgroupService.getSubgroupByidbase(idbase);
        if (subgroup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subgroup);
    }


    @GetMapping("/by-base/{base}")
    public ResponseEntity<List<Subgroup>> getSubgroupsByBase(@PathVariable String base) {
        List<Subgroup> subgroups = subgroupService.getSubgroupsByBase(base);
        if (subgroups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subgroups);
    }


    @GetMapping("/hello")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("App is running");
    }

    @GetMapping("/by-nome-cidade/{nomeCidade}")
    public ResponseEntity<List<Subgroup>> getSubgroupsByNomeCidade(@PathVariable String nomeCidade) {
        String nomeCidadeSemAcentos = Normalizer.normalize(nomeCidade, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        nomeCidadeSemAcentos = nomeCidadeSemAcentos.toUpperCase();
        System.out.println(nomeCidadeSemAcentos);
        List<Subgroup> subgroups = subgroupService.getSubgroupsBynomeCidade(nomeCidadeSemAcentos);
        if (subgroups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subgroups);
    }


    @GetMapping("/test-db")
    public ResponseEntity<String> testDatabase() {
        try {
            List<Subgroup> subgroups = subgroupService.findAll();
            if (subgroups.isEmpty()) {
                return ResponseEntity.ok("O banco de dados está funcionando, mas não há registros na tabela Subgroup.");
            } else {
                return ResponseEntity.ok("O banco de dados está funcionando e há registros na tabela Subgroup.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar o banco de dados: " + e.getMessage());
        }

    }


    @PostMapping("/add")
    public ResponseEntity<Subgroup> addSubgroup(@RequestBody Subgroup subgroup) {
        Subgroup savedSubgroup = subgroupService.save(subgroup);
        return ResponseEntity.ok(savedSubgroup);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Subgroup>> getAllSubgroups() {
        List<Subgroup> subgroups = subgroupService.findAll();
        if (subgroups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subgroups);
    }

}
