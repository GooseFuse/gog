package gog.resource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import gog.model.Address;
import gog.model.Order;
import gog.model.OrderItem;
import gog.model.Payment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashSet;
import java.util.Set;

@QuarkusTest
public class OrderResourceTest {

  @Test
  public void testGetAddres() {
    given()
        .when().get("/api/v1/addresses")
        .then()
        .statusCode(200)
        .body(containsString("test city1"));
  }

  @Test
  public void testGetPayment() {
    given().when()
        .get("/api/v1/payments")
        .then()
        .statusCode(200)
        .body(containsString("wire1"));
  }

  @Test
  public void testGetOrderItem() {
    given().when()
        .get("/api/v1/order_items")
        .then()
        .statusCode(200)
        .body("$.size()", is(3));
  }

  @Test
  public void testCreateOrder() {
    Set<OrderItem> orderItems = new HashSet<>();
    orderItems.add(new OrderItem().setId(1L));
    orderItems.add(new OrderItem().setId(2L));
    orderItems.add(new OrderItem().setId(3L));
    Order order = new Order()
        .setAddress(new Address().setId(1L))
        .setPayment(new Payment().setId(1L))
        .setOrderItems(orderItems);

    given().when()
        .contentType(ContentType.JSON)
        .body(order)
        .then()
        .statusCode(201);

  }

}