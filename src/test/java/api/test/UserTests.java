package api.test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
    Faker faker;
    User userPayload;
	
	@BeforeClass
	public void setUpData() {
		faker=new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
				
	}
	
	
	@Test(priority=1)
	public void testPostUser() {
		
		Response res= userEndPoints.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	    System.out.println("\nposted user sucessful\n");
	}
	
	@Test(priority=2,dependsOnMethods= {"testPostUser"})
	public void testGetUser() {
		
		Response res=userEndPoints.getUser(userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		System.out.println("\nRetrieved user details successfully\n");


	}
	
	@Test(priority=3,dependsOnMethods= {"testPostUser"})
	public void updateUser() {
		Response res=userEndPoints.updateUser(userPayload.getUsername(), userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		System.out.println("\nUpdated user details successfully\n");
	}
	
	@Test(priority=4, dependsOnMethods= {"testPostUser"})
	public void deleteUser() {
		
		Response res=userEndPoints.deletUser(userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		System.out.println("\ndelete user details successfully\n");
		
		
	}
}
