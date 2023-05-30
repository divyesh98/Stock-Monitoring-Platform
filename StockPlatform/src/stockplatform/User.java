/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StockPlatform;

/**
 *
 * @author sagla
 */
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class User {
    private String username;
    private String password;
    private int userid;
    private Set<String> stocks;
    private Map<String, Set<String>> watchlists;

    public User(int userid, String username) {
        this.username = username;
        this.password = password;
        this.stocks = new HashSet<>();
        this.watchlists = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return userid;
    }

    public Set<String> getStocks() {
        return stocks;
    }

    public Map<String, Set<String>> getWatchlists() {
        return watchlists;
    }

    public void addStock(String symbol) {
        stocks.add(symbol);
    }

    public void createWatchlist(String watchlistName) {
        watchlists.put(watchlistName, new HashSet<>());
    }

    public void addToWatchlist(String watchlistName, String symbol) {
        if (watchlists.containsKey(watchlistName)) {
            watchlists.get(watchlistName).add(symbol);
        }
    }
}
