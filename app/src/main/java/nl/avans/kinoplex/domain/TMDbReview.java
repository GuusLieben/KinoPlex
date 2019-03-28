package nl.avans.kinoplex.domain;

import java.util.HashMap;
import java.util.Map;

public class TMDbReview extends DomainObject implements Review {
  String id;
  String author;
  String content;

  public TMDbReview(String id, String author, String content) {
    this.id = id;
    this.author = author;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public Map<String, Object> storeToMap() {
    return new HashMap<String, Object>() {
      {
        put("id", id);
        put("author", author);
        put("content", content);
      }
    };
  }
}
