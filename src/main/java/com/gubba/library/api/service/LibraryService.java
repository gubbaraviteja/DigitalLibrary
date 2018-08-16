package com.gubba.library.api.service;

import com.gubba.library.api.model.Resource;
import com.gubba.library.api.model.User;
import com.gubba.library.api.repository.ResourceRepository;
import com.gubba.library.api.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This service takes care of business logic and acts as bridge between rest API to DAO
 */
@Service
public class LibraryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * searches catalogue for the title and return list of resources on successful match else return empty list     *
     * @return List of Resources
     */
    public List<Resource> queryCatalogue(String title) {
        return resourceRepository.findByTitleContains(title);
    }

    /**
     * retrieves all the resources in the catalogue
     * @return List of Resources
     */
    public Iterable<Resource> getCatalogue() {
        return resourceRepository.findAll();
    }

    public Resource addResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Iterable<Resource> addResources(List<Resource> resources) {
        return resourceRepository.saveAll(resources);
    }

    public Resource getResourceByTitle(String title) {
        return resourceRepository.findByTitle(title);
    }

    /**
     * When the user searches for some resource and if he see that the resource is not available for borrowing then he can add to his wishlist
     * so that he gets notified when it becomes available
     * @param userId user's uniqueId who wanted to add resource to his wishlist
     * @param wishedResource resource wished by user
     */
    public void addToWishList(Long userId, Resource wishedResource) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            byte[] wishListByteArray = user.get().getWishlist();
            if (wishListByteArray != null) {
                user.get().setWishlist((new String(wishListByteArray) + ", " + wishedResource.getTitle()).getBytes());
            } else {
                user.get().setWishlist(wishedResource.getTitle().getBytes());
            }
            userRepository.save(user.get());
        } else {
            logger.error("Unknown user");
        }
    }

    /**
     *  The thought process for this method is in the same lines of {@link #addToWishList(Long, Resource)}
     *  When the user searches for some resource and if he see that the resource is not present in the library he can suggest this to library
     *  and adds to his wishlist so that he can be notified in future. It can be used by library administration as well, when many people are
     *  requesting for a specific resource, they can prioritize the items to procure and can also utilize the budget effectively
     * @param userId user's uniqueId who wanted to suggest resource to library
     * @param wishedResource resource wished by user
     */
    public void suggestToLibrary(Long userId, Resource wishedResource) {
        wishedResource.setWaitingList(wishedResource.getWaitingList()+1);   //incrementing the wish list of resource
        resourceRepository.save(wishedResource);
        addToWishList(userId, wishedResource);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void addUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public Iterable<User> getAllRegisteredUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name){
        return userRepository.findByUsername(name);
    }

    public Iterable<User> getUserByMailId(String mailId){
        return userRepository.findByEmailId(mailId);
    }

}
