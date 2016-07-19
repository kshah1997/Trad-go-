package teamcool.tradego.Clients;

//Created by selinabing on 7/11/16.


import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import teamcool.tradego.Models.Acquaintance;
import teamcool.tradego.Models.Item;

public class ParseClient {

    public ParseClient () {

    }
    public List<ParseUser> queryFriendsOnName(String name) {
        List<ParseUser> friends = new ArrayList<>();
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereContains("username",name);
        try {
            friends = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public List<Item> queryItemsOnName(String name,boolean self) {
        List<Item> items = new ArrayList<>();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereContains("item_name",name);
        query.whereEqualTo("status","Available");
        if(!self) {
            query.whereNotEqualTo("owner", ParseUser.getCurrentUser());
        } else {
            query.whereEqualTo("owner", ParseUser.getCurrentUser());
        }
        try {
            items = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> queryItemsOnCategory(String category) {
        List<Item> items = new ArrayList<>();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereNotEqualTo("owner",ParseUser.getCurrentUser());
        query.whereEqualTo("category",category);
        query.whereEqualTo("status","Available");
        try {
            items = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> queryItemsOnUserAndStatus(ParseUser user, String status) {
        List<Item> items = new ArrayList<>();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        if (user != null) {
            query.whereEqualTo("owner", user);
        }
        if (status != null) {
            query.whereEqualTo("status", status);
        }
        try {
            items = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> queryBoughtItemsOnUser(ParseUser user) {
        List<Item> items = new ArrayList<>();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereEqualTo("buyer",user);
        query.whereEqualTo("status","Sold");
        try {
            items = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> queryItemsOnOtherUserAndStatus(ParseUser user, String status) {
        List<Item> items = new ArrayList<>();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereNotEqualTo("owner",user);
        query.whereEqualTo("status",status);
        try {
            items = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    public int countNumFriendsOfUser(ParseUser user) {
        int count = -15251;
        /*
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        try {
            count = query.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        return count;
    }

    public int countNumItemsOnStatus(ParseUser user, String status) {
        int count = -15251;
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereEqualTo("owner",user);
        query.whereEqualTo("status",status);
        try {
            count = query.count();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countNumItemsBought(ParseUser user) {
        int count = -15210;
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.whereEqualTo("buyer", user);
        query.whereEqualTo("status","Sold");
        try {
            count = query.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Item queryItemBasedonObjectID(String itemId) {

        Item item = new Item();
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY

        query.whereEqualTo("objectId", itemId);
        try {
            item = query.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return item;
    }

    public ParseUser queryUserBasedonObjectID(String userId) {

        ParseUser user = new ParseUser();
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY

        query.whereEqualTo("objectId", userId);
        try {
            user = query.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<String> queryAcquaintanceNamesofUser(ParseUser user){
        List<Acquaintance> acquaintances;
        List<String> acqauintancesName = new ArrayList<>();
        ParseQuery<Acquaintance> query = ParseQuery.getQuery(Acquaintance.class);
        query.whereEqualTo("owner", user.getCurrentUser());
        try {
            acquaintances = query.find();
            for (int i = 0; i < acquaintances.size(); i++){
                acqauintancesName.add(acquaintances.get(i).getName());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return acqauintancesName;
    }

    public List<Acquaintance> queryAcquaintancesofUser(ParseUser user){
        List<Acquaintance> acquaintances = new ArrayList<>();
        ParseQuery<Acquaintance> query = ParseQuery.getQuery(Acquaintance.class);
        query.whereEqualTo("owner", user.getCurrentUser());
        try {
            acquaintances = query.find();
            for (int i = 0; i < acquaintances.size(); i++){
                acquaintances.add(acquaintances.get(i));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return acquaintances;
    }

    public void updateItem(String objectId, Item newItem) {
        ParseObject point = ParseObject.createWithoutData("Point",objectId);
        point.put("item_name",newItem.getItem_name());
        point.put("category",newItem.getCategory());
        point.put("price",newItem.getPrice());
        point.put("status",newItem.getStatus());
        point.put("negotiable",newItem.getNegotiable());
        point.put("description",newItem.getDescription());
        //point.put("transaction_time",newItem.getTransactionTime());
        point.put("image_1",newItem.getImage1());
        point.put("image_2",newItem.getImage2());
        point.put("image_3",newItem.getImage3());
        point.saveInBackground();
    }
}