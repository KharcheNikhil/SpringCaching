package com.turvo.cachingtutorial.resource;

import com.turvo.cachingtutorial.helper.CacheHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class CacheController {

  @Autowired
  private CacheHelper cacheHelper;

  @GetMapping
  public ResponseEntity<List<String>> findAll() {
    return ResponseEntity.ok(cacheHelper.getKeys());
  }

  @GetMapping("{cache_key}")
  public ResponseEntity<List<String>> findAll(@PathVariable("cache_key") String key) {
    return ResponseEntity.ok(cacheHelper.getEntries(key));
  }

  @PutMapping
  public ResponseEntity evictAll() {
    cacheHelper.evict();
    return ResponseEntity.accepted().build();
  }
}