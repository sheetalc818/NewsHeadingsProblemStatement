package com.bridgelabz.spotify.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;


public class SpotifyRestAssured {
    String userId = "";
    String playListId = "";
    public static String tokenValue = "Bearer BQDi9omfpezZZd4DgQoISfZJoY_jiLHBZp_hcFP9_T0_t8DV2KgcGmeshJyyMU7SW3w9AQUlITOz0ZiyBGUhW9FooaBeKHdgvG-hqgQ011v2kUbiZ9NicqHcD2AceynSA3mmoB_ldqG_hJCTkvGq2AXM1lNxxEY9UjWQNefCOeIkVyN8FPWUhCzmSl0slJW0otxu9H0ULMy_gaW6KJFU1gRB4CcSP1tFgV5IudpdnAdAWC3OTu-umBFk0xURKmnyMksJwwgI699LsuBqdlvZxNFPa4Gk2WRYhQ";

    @Test
    public void getUserIdFromSpotifyApp() throws ParseException {
        Response response1 = RestAssured.given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", tokenValue)
                .when()
                .get("https://api.spotify.com/v1/me");
        String userId = response1.path("id");
        System.out.println("userId------------>" + userId);
//--------------------------------------------------------------------------
        Response response2 = RestAssured.given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", tokenValue)
                .pathParam("user_id", userId)
                .when()
                .get("https://api.spotify.com/v1/users/{user_id}/playlists");

        int total = response2.path("total");
        String pId1 = response2.path("items[0].id");

        String pId2 = response2.path("items[1].id");
        System.out.println("Total:>" + total);
        System.out.println("PLID1--------------->" + pId1);

        System.out.println("PLID2--------------->" + pId2);

//------------------------------------------------------------
        JSONObject playListData = new JSONObject();
        playListData.put("name", "Testing Playlist");
        playListData.put("description", "Sheetal's Favorite songs");
        playListData.put("public", false);
        RestAssured.given()
                .accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", tokenValue)
                .pathParam("playlist_id", pId1)
                .body(playListData.toJSONString())
                .when()
                .put("http://api.spotify.com/v1/playlists/{playlist_id}");

//----------------------------------------------------------------------------------------------------

        //  https://api.spotify.com/v1/users/{user_id}/playlists

        JSONObject newPlayListData = new JSONObject();
        newPlayListData.put("name", "Testing Playlist");
        newPlayListData.put("description", "Sheetal's Favorite songs");
        newPlayListData.put("public", false);

        Response newResponse=RestAssured.given().
                accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", tokenValue)
                .pathParam("user_id", userId)
                .body(newPlayListData.toJSONString())
                .when()
                .post("https://api.spotify.com/v1/users/{user_id}/playlists");
        newResponse.prettyPrint();

    }


}
