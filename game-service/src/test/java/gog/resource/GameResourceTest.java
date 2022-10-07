package gog.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import gog.model.Category;
import gog.model.Game;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class GameResourceTest {

  @Test
  public void testGetAllGames() {
    given()
        .when().get("/api/v1/games")
        .then()
        .statusCode(200)
        .body(
            containsString("game description"));
  }

  @Test
  public void testGetGameByid() {
    Game game = given().when()
        .get("/api/v1/games/2")
        .then()
        .statusCode(200)
        .extract()
        .as(Game.class);

    assertEquals("game title2", game.getTitle());
    assertEquals(BigDecimal.valueOf(2.22), game.getPrice());
  }

  @Test
  public void testCreateGame() {
    Category category = given().when()
        .get("/api/v1/categories/1")
        .then()
        .statusCode(200)
        .extract()
        .as(Category.class);
    Game game = new Game()
        .setCategory(category)
        .setTitle("title")
        .setStatus("AVAILABLE")
        .setPrice(BigDecimal.valueOf(22.99))
        .setImageUrl("url")
        .setDescription("description");

    given().when()
        .body(game)
        .contentType(ContentType.JSON)
        .post("/api/v1/games")
        .then()
        .statusCode(201);

  }

  @Test
  public void testDeleteGame() {
    given().when()
        .delete("/api/v1/games/2")
        .then()
        .statusCode(204);
  }

  @Test
  public void testGetAllCategories() {
    given().when()
        .get("/api/v1/categories")
        .then()
        .statusCode(200)
        .body(containsString("category name"));
  }

  @Test
  public void testGetCategoryById() {
    given().when()
        .get("/api/v1/categories/1")
        .then()
        .statusCode(200)
        .body(containsString("category name"));
  }

  @Test
  public void testCreateAndDeleteCategory() {
    Category category = new Category()
        .setName("name")
        .setDescription("description");

    Category cate = given().when()
        .body(category)
        .contentType(ContentType.JSON)
        .post("/api/v1/categories")
        .then()
        .statusCode(200)
        .body(containsString("name"))
        .extract()
        .as(Category.class);

    given().when()
        .delete("/api/v1/categories/" + cate.getId())
        .then()
        .statusCode(204)
        .body(emptyString());
  }

}
