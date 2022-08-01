import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class PetClinicTest {

    @Test
    public void addPet() {
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .port(80)
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"birthDate\": \"2022/08/01\",\n" +
                        "  \"id\": null,\n" +
                        "  \"name\": \"Brad\",\n" +
                        "  \"owner\": {\n" +
                        "    \"address\": \"Brad ST\",\n" +
                        "    \"city\": \"Brad City\",\n" +
                        "    \"firstName\": \"Brad Owner\",\n" +
                        "    \"id\": 45,\n" +
                        "    \"lastName\": \"Patrick\",\n" +
                        "    \"pets\": [\n" +
                        "      null\n" +
                        "    ],\n" +
                        "    \"telephone\": \"0666516510\"\n" +
                        "  },\n" +
                        "  \"type\": {\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"Snake\"\n" +
                        "  },\n" +
                        "  \"visits\": [\n" +
                        "    {\n" +
                        "      \"date\": \"yyyy/MM/dd\",\n" +
                        "      \"description\": \"string\",\n" +
                        "      \"id\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer petId = response.extract().jsonPath().getInt("id");

        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("petId", petId)
                .when()
                .get("/api/pets/{petId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(petId));
    }

    @Test
    public void getPetList() {
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
              //  .pathParam("petId", 0)
                .when().log().all()
                .get("/api/pets")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void addVisit() {
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .port(80)
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"date\": \"2022/08/01\",\n" +
                        "  \"description\": \"Annual check\",\n" +
                        "  \"id\": null,\n" +
                        "  \"pet\": {\n" +
                        "    \"birthDate\": \"2022/08/01\",\n" +
                        "    \"id\": 45,\n" +
                        "    \"name\": \"Brad\",\n" +
                        "    \"owner\": {\n" +
                        "      \"address\": \"Brad ST\",\n" +
                        "    \t\"city\": \"Brad City\",\n" +
                        "    \"firstName\": \"Brad Owner\",\n" +
                        "    \"id\": 45,\n" +
                        "    \"lastName\": \"Patrick\",\n" +
                        "      \"pets\": [\n" +
                        "        null\n" +
                        "      ],\n" +
                        "      \"telephone\": \"0666516510\"\n" +
                        "    },\n" +
                        "    \"type\": {\n" +
                        "      \"id\": 6,\n" +
                        "      \"name\": \"snake\"\n" +
                        "    },\n" +
                        "    \"visits\": [\n" +
                        "      null\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}")
                .when().log().all()
                .post("/api/visits").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer visitId = response.extract().jsonPath().getInt("id");

        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("visitId", visitId)
                .when()
                .get("/api/visits/{visitId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(visitId));
    }
    }

