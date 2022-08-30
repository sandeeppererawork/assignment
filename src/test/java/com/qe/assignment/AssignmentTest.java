package com.qe.assignment;

import com.qe.assignment.function.Category;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssignmentTest {

    String categoryName = "Carbon credits";
    String description = "Good position in category";

    @Test
    public void testCategory(){
        SoftAssert softAssert = new SoftAssert();
        Response response = Category.getCategoryResponse();
        softAssert.assertEquals(Category.getCategoryName(response), categoryName, "Category Name does not match");
        softAssert.assertEquals(Category.getCanRelistValue(response), "true", "CanRelist does not match");
        softAssert.assertEquals(Category.getDescriptionInPromotionArray(response,"Promotions","Name","Gallery","Description"),description, "Description does not match");
        softAssert.assertAll();
    }
}
