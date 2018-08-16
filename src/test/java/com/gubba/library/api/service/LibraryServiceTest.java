package com.gubba.library.api.service;

import com.gubba.library.api.AbstractIntegrationTest;
import com.gubba.library.api.model.Resource;
import com.gubba.library.api.model.ResourceType;
import com.gubba.library.api.model.User;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This is a test-suite where the execution order of test cases is important as the earlier tests are used to build data in the database
 * Execution of individual test cases might fail
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LibraryServiceTest extends AbstractIntegrationTest {
    @Autowired
    private LibraryService libraryService;

    @Test
    public void addResource() throws Exception {
        Date publishedDate = new Date(System.currentTimeMillis());
        libraryService.addResource(new Resource(null, "The tusk that did the damage", "Tania james", publishedDate, ResourceType.BOOK, 0));

        //asserting resource attributes
        Resource retrievedResource = libraryService.getResourceByTitle("The tusk that did the damage");
        Assert.assertNotNull(retrievedResource);
        Assert.assertEquals("Tania james", retrievedResource.getAuthor());
        Assert.assertEquals(publishedDate, retrievedResource.getPublishedDate());
        Assert.assertEquals(ResourceType.BOOK, retrievedResource.getResourceType());
        Assert.assertEquals(0, retrievedResource.getWaitingList().intValue());
    }

    @Test
    public void addUser() throws Exception {
        Date joinedDate = new Date(System.currentTimeMillis());
        libraryService.addUser(new User(null, "ravi", "gubba@gmail.com", joinedDate, null));

        //asserting user attributes
        User retrievedUser = libraryService.getUserByName("ravi");
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals("gubba@gmail.com", retrievedUser.getEmailId());
        Assert.assertEquals(joinedDate, retrievedUser.getJoinedDate());
        Assert.assertNull(retrievedUser.getWishlist());
    }

    @Test
    public void addUsers() throws Exception {
        Date joinedDate = new Date(System.currentTimeMillis());
        libraryService.addUser(new User(null, "gubbaravi", "gubbaravi@gmail.com", joinedDate, null));
        libraryService.addUser(new User(null, "teja", "gubbaravi@gmail.com", joinedDate, null));

        //asserting total no of users persisted
        Iterable<User> allUsers = libraryService.getUserByMailId("gubbaravi@gmail.com");
        Assert.assertEquals(2, StreamSupport.stream(allUsers.spliterator(), false).count());

        //asserting user attributes
        User retrievedUser = libraryService.getUserByName("gubbaravi");
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals("gubbaravi@gmail.com", retrievedUser.getEmailId());
        Assert.assertEquals(joinedDate, retrievedUser.getJoinedDate());
        Assert.assertNull(retrievedUser.getWishlist());
    }

    @Test
    public void getAllRegisteredUsers() throws Exception {
        //asserting total no of users
        Iterable<User> allUsers = libraryService.getAllRegisteredUsers();
        Assert.assertEquals(3, StreamSupport.stream(allUsers.spliterator(), false).count());

        //asserting user attributes
        User retrievedUser = libraryService.getUserByName("teja");
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals("gubbaravi@gmail.com", retrievedUser.getEmailId());
        Assert.assertNull(retrievedUser.getWishlist());
    }

    @Test
    public void getCatalogue() throws Exception {
        //asserting total no of resources
        Iterable<Resource> allResources = libraryService.getCatalogue();
        Assert.assertNotNull(allResources);
        Assert.assertEquals(1, StreamSupport.stream(allResources.spliterator(), false).count());

        //asserting resource attributes
        List<String> allResourceTitles = StreamSupport.stream(allResources.spliterator(), false).map(Resource::getTitle).collect(Collectors.toList());
        Assert.assertTrue(allResourceTitles.contains("The tusk that did the damage"));
    }

    @Test
    public void queryCatalogue() throws Exception {
        //Negative scenario: looking for resource which didn't exist
        List<Resource> matchedResources = libraryService.queryCatalogue("Norway");
        Assert.assertNotNull(matchedResources);
        Assert.assertTrue(matchedResources.isEmpty());

        //PositiveScenario: looking for resource which exists in library
        libraryService.addResource(new Resource(null, "History of Norway", "John A. Yilek",
                                                new Date(System.currentTimeMillis()), ResourceType.BOOK, 0));
        matchedResources = libraryService.queryCatalogue("Norway");
        Assert.assertNotNull(matchedResources);
        Assert.assertEquals(1, matchedResources.size());
        Assert.assertEquals("John A. Yilek", matchedResources.get(0).getAuthor());
    }

    @Test
    public void suggestToLibrary() throws Exception {
        Resource retrievedResource = libraryService.getResourceByTitle("History of Norway");
        User retrievedUser = libraryService.getUserByName("ravi");
        libraryService.suggestToLibrary(retrievedUser.getId(), retrievedResource);

        //Asserting whether the user's wishlist is updated or not
        retrievedUser = libraryService.getUserByName("ravi");
        Assert.assertEquals("History of Norway", new String(retrievedUser.getWishlist()));

        //Asserting whether the waitingList of resource is updated or not
        retrievedResource = libraryService.getResourceByTitle("History of Norway");
        Assert.assertEquals(1, retrievedResource.getWaitingList().intValue());
    }

    @Test
    public void suggestToLibraryByDifferentUser() throws Exception {
        User user = libraryService.addUser(new User(null, "Navneet", "navneet@gmail.com", new Date(System.currentTimeMillis()), null));
        Resource retrievedResource = libraryService.getResourceByTitle("History of Norway");
        libraryService.suggestToLibrary(user.getId(), retrievedResource);

        //Asserting whether the user's wishlist is updated or not
        user = libraryService.getUserByName("Navneet");
        Assert.assertEquals("History of Norway", new String(user.getWishlist()));

        //Asserting whether the waitingList of resource is updated or not
        retrievedResource = libraryService.getResourceByTitle("History of Norway");
        Assert.assertEquals(2, retrievedResource.getWaitingList().intValue());
    }

    @Test
    public void testAddToWishList() throws Exception {
        Resource retrievedResource = libraryService.getResourceByTitle("The tusk that did the damage");
        User retrievedUser = libraryService.getUserByName("ravi");
        libraryService.addToWishList(retrievedUser.getId(), retrievedResource);

        //Asserting whether the user's wishlist is updated or not
        retrievedUser = libraryService.getUserByName("ravi");
        Assert.assertTrue(new String(retrievedUser.getWishlist()).contains("The tusk that did the damage"));
        String[] wishListArray = new String(retrievedUser.getWishlist()).split(",");
        Assert.assertEquals(2, wishListArray.length);
    }

}