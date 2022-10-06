package gog.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gog.model.Customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class CustomerResourceTest {

    @Test
    public void testListCustomers() {
        given()
          .when().get("/api/v1/customers")
          .then()
             .statusCode(200)
             .body(containsString("Adam"));
    }

    @Test
    public void testGetCustomer() {
        Customer customer = given().when().get("/api/v1/customers/1")
        .then()
        .statusCode(200)
        .extract().as(Customer.class);

        assertEquals("Adam", customer.getFirstName());
        assertEquals("Pióro", customer.getLastName());
    }

    @Test public void testCreateCustomer() {
        Customer customer = new Customer()
        .setFirstName("test")
        .setLastName("test")
        .setEmail("test@test.com")
        .setTelephone("012345678");

        given().when()
            .body(customer)
            .contentType(ContentType.JSON)
            .post("/api/v1/customers")
            .then()
            .statusCode(200)
            .body(
                containsString("test"));
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = given().when().get("/api/v1/customers/1")
        .then().statusCode(200)
        .extract().as(Customer.class);

        customer.setFirstName("Updated");

        Response response = given().when().body(customer)
            .contentType(ContentType.JSON)
            .put("/api/v1/customers/1")
            .then()
            .statusCode(200)
            .extract()
            .response();

        assertTrue(response.jsonPath().get("firstName").equals("Updated"));
    }
}