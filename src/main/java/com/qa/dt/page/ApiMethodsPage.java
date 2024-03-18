package com.qa.dt.page;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qa.dt.base.BaseClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiMethodsPage extends BaseClass {
	public ApiMethodsPage() {
		PageFactory.initElements(driver, this);		
		baseURI = "https://qa1-api.in.invicara.com";
//		baseURI = "https://staging-api.invicara.com";
//		baseURI = "https://staging.sa.invicara.com/console/";
	}
	
	public String getAuthToken(String uName, String password) {
		Response response = given().
				header("Authorization","Basic cGFzc3dvcmQtY2xpZW50OmNla25ZTEhzdmZtMnpLWkE=").
		contentType("multipart/form-data").
		multiPart("username", uName, "multipart/form-data").
		multiPart("password", password, "multipart/form-data").
		multiPart("grant_type", "password", "multipart/form-data").
		when()
        .post("/passportsvc/api/v1/oauth/token");
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		JsonPath res = response.jsonPath(); 
		String token = res.get("access_token");
		System.out.println("tok : "+token);
		return token;
	}
	public String getBearerToken(String uName, String password, String appId ) {
		String basicToken = getAuthToken(uName,password);
		Response response = given().queryParams("appId", appId).
				header("Authorization","Bearer "+basicToken)
		.when()
        .post("/passportsvc/api/v1/auth/token");
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println(response.getStatusCode());
		System.out.println(response);
		JsonPath res = response.jsonPath(); 
		String token = res.get("access_token");
		System.out.println("tok : "+token);
		return token;		
	}
	public String createFolder(String bearerToken, String folderName,String nsfilter) {
		Response response = given()
				.queryParams("_name", folderName)
				.queryParams("_namespaces", nsfilter)
				.queryParams("nsfilter", nsfilter)
				.header("Authorization","Bearer "+bearerToken).when()
        .post("/filesvc/api/v1/files");
		System.out.println(response.getStatusCode());
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println(response);
		JsonPath res = response.jsonPath(); 
		String folderId = res.get("_id");
		System.out.println("folderId : "+folderId);
		return folderId;		
	}
	public String createChildFolder(String bearerToken, String folderName,String nsfilter,String parentFolderId) {
		Response response = given()
				.queryParams("_name", folderName)
				.queryParams("_namespaces", nsfilter)
				.queryParams("nsfilter", nsfilter)
				.queryParams("_parents", parentFolderId)
				.header("Authorization","Bearer "+bearerToken).when()
        .post("/filesvc/api/v1/files");
		System.out.println(response.getStatusCode());
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println(response);
		JsonPath res = response.jsonPath(); 
		String folderId = res.get("_id");
		System.out.println("folderId : "+folderId);
		return folderId;		
	}
	public void uploadFile(String bearerToken, String parentFolderId, String nsfilter) {
		Response response = given().
				 queryParams("_parents", parentFolderId)
				.queryParams("_namespaces", nsfilter)
				.queryParams("nsfilter", nsfilter)
				.header("Authorization","Bearer "+bearerToken).
				contentType("multipart/form-data").
				multiPart("_parents", parentFolderId, "multipart/form-data").
				multiPart("file", new File(("files/sample.pdf")), "multipart/form-data").
				when()
        .post("/filesvc/api/v1/files");
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		System.out.println(response.getStatusCode());
		System.out.println(response);	
	}
	public void deleteFolder(String bearerToken, String parentFolderId, String nsfilter) {
		Response response = given()
				.queryParams("nsfilter", nsfilter)
				.header("Authorization","Bearer "+bearerToken).
				when()
        .delete("/filesvc/api/v1/files/"+parentFolderId);
		System.out.println(response.getStatusCode());
		int statusCode= response.getStatusCode();
		Assert.assertEquals(statusCode, 204);
		System.out.println(response);	
	}
	
	
	

}
