package com.cucumber.definitions;

import com.common.api_engine.EndPoints;
import com.common.api_engine.model.responses.Book;
import com.common.api_engine.model.responses.BooksResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import com.common.ui_engine.page_objects.pages.BooksPage;

import java.util.List;
import java.util.stream.Collectors;

import static com.common.BaseTest.webDriver;

@Epic("Regression Tests")
@Feature("Books list")
public class BookPageSteps {

    List<String> booksTitlesUI;
    List<Book> booksTitlesApi;

    @And("Get books list from UI")
    public void get_books_list_from_UI() {

        BooksPage booksPage = new BooksPage(webDriver);
        booksTitlesUI = booksPage.getUIbooksTitles();
    }

    @And("Get books list from Api")
    public void get_books_list_from_API() {

        Response books = EndPoints.getBooks();
        Assert.assertEquals(books.statusCode(), 200);
        BooksResponse booksResponse = books.getBody().as(BooksResponse.class);
        booksTitlesApi = booksResponse.books.stream().filter(jsonValue -> !jsonValue.title.isEmpty())
                .collect(Collectors.toList());
    }

    @Then("Check books list in UI equals to Books list from API")
    public void check_UI_API_lists() {

        for (int i = 0; i < booksTitlesUI.size(); i++) {
            System.out.println("UI book title: " + (i + 1) + " " + booksTitlesUI.get(i) + " - " + "API book title: " + (i + 1) + " " + booksTitlesApi.get(i).title);
            Assert.assertEquals(booksTitlesUI.get(i), booksTitlesApi.get(i).title, "Verify Book Title " + (i + 1) + "in UI equals to Book Title in API");
        }
    }
}

