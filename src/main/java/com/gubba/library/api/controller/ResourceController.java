package com.gubba.library.api.controller;

import com.gubba.library.api.model.Resource;
import com.gubba.library.api.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;

@RestController
public class ResourceController {

    @Autowired
    private LibraryService libraryService;

    @ApiOperation(value = "Add a resource to library catalogue")
    @PostMapping(value = "/admin/addResource")
    public ResponseEntity addResource(@RequestBody Resource resource) {
        libraryService.addResource(resource);
        return ResponseEntity.ok("Resource added successfully");
    }

    @ApiOperation(value = "Add resources in bulk to library catalogue")
    @PostMapping(value = "/addResources")
    public ResponseEntity addResource(@RequestBody List<Resource> resources) {
        libraryService.addResources(resources);
        return ResponseEntity.ok("Resources added successfully");
    }

    @ApiOperation(value = "Get all resources from library catalogue")
    @GetMapping(value = "/getCatalogue")
    public ResponseEntity<Iterable<Resource>> getCatalogue() {
        return ResponseEntity.ok(libraryService.getCatalogue());
    }
}
