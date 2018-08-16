package com.gubba.library.api.controller;

import com.gubba.library.api.model.Resource;
import com.gubba.library.api.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @ApiOperation(value = "Find resource from library catalogue")
    @GetMapping(value = "/{userId}/query/{title}")
    public ResponseEntity<Iterable<Resource>> queryCatalogue(@PathVariable(value = "userId") Long userId,
                                                             @PathVariable(value = "title") String title) {
        return ResponseEntity.ok(libraryService.queryCatalogue(title));
    }

    @ApiOperation(value = "Add a resource to wishlist")
    @PostMapping(value = "/{userId}/addToWishList")
    public ResponseEntity addToWishList(@PathVariable(value = "userId") Long userId,
                                        @RequestBody Resource resource) {
        libraryService.addToWishList(userId, resource);
        return ResponseEntity.ok(resource.getTitle() + " added to your wishlist");
    }

    @ApiOperation(value = "Suggest a resource to library")
    @PostMapping(value = "/{userId}/suggestResource")
    public ResponseEntity suggestToLibrary(@PathVariable(value = "userId") Long userId,
                                           @RequestBody Resource resource) {
        libraryService.suggestToLibrary(userId, resource);
        return ResponseEntity.ok(resource.getTitle() + " suggested to library");
    }
}
