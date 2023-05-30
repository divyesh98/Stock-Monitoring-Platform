package StockPlatform;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class StockPlatform extends JFrame {

    private Map<String, Double> stockData;
    private Map<String, User> users;
    private User loggedInUser;

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnLogout;
    private JButton btnRegister;
    private JTextArea txtAreaStockData;
    private JTextArea txtAreaUserStocks;
    private JTextField txtStockSymbol;
    private JButton btnAddStock;
    private JButton btnCreateWatchlist;
    private JTextField txtWatchlistSymbol;
    private JTextField txtStockWatchlist;
    private JButton btnAddToWatchlist;
    private JTextArea txtAreaUserWatchlist;

    private Connection connection;

    public StockPlatform() {
        stockData = new HashMap<>();
        users = new HashMap<>();

        setTitle("Stock Monitoring Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setResizable(false);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(15);
        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Login");
        btnLogout = new JButton("Logout");
        btnRegister = new JButton("New User? Register");
        btnLogout.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnLogin, gbc);
        gbc.gridy = 3;
        loginPanel.add(btnLogout, gbc);
        gbc.gridy = 6;
        loginPanel.add(btnRegister, gbc);

        add(loginPanel, BorderLayout.NORTH);

        // Stock Data Panel
        JPanel stockDataPanel = new JPanel();
        stockDataPanel.setLayout(new GridBagLayout());

        txtAreaStockData = new JTextArea();
        txtAreaStockData.setEditable(false);
        JScrollPane scrollPaneStockData = new JScrollPane(txtAreaStockData);

        // Set GridBagConstraints for the components
        GridBagConstraints gbcStockDataLabel = new GridBagConstraints();
        gbcStockDataLabel.gridx = 0;
        gbcStockDataLabel.gridy = 0;
        gbcStockDataLabel.anchor = GridBagConstraints.WEST;
        gbcStockDataLabel.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcScrollPaneStockData = new GridBagConstraints();
        gbcScrollPaneStockData.gridx = 0;
        gbcScrollPaneStockData.gridy = 1;
        gbcScrollPaneStockData.fill = GridBagConstraints.BOTH;
        gbcScrollPaneStockData.weightx = 1.0;
        gbcScrollPaneStockData.weighty = 1.0;
        gbcScrollPaneStockData.insets = new Insets(0, 5, 5, 5);

        //add components to stock data panel
        stockDataPanel.add(new JLabel("Stock Data"), gbcStockDataLabel);
        stockDataPanel.add(scrollPaneStockData, gbcScrollPaneStockData);

        add(stockDataPanel, BorderLayout.CENTER);

        // User Stocks and Watchlist Panel
        JPanel userStocksWatchlistPanel = new JPanel(new GridLayout(1, 2));

        JPanel userStocksPanel = new JPanel(new BorderLayout());
        txtAreaUserStocks = new JTextArea();
        txtAreaUserStocks.setEditable(false);
        JScrollPane scrollPaneUserStocks = new JScrollPane(txtAreaUserStocks);
        userStocksPanel.add(new JLabel("User Stocks"), BorderLayout.NORTH);
        userStocksPanel.add(scrollPaneUserStocks, BorderLayout.CENTER);

        JPanel userWatchlistPanel = new JPanel(new BorderLayout());
        txtAreaUserWatchlist = new JTextArea();
        txtAreaUserWatchlist.setEditable(false);
        JScrollPane scrollPaneUserWatchlist = new JScrollPane(txtAreaUserWatchlist);
        userWatchlistPanel.add(new JLabel("User Watchlist"), BorderLayout.NORTH);
        userWatchlistPanel.add(scrollPaneUserWatchlist, BorderLayout.CENTER);

        userStocksWatchlistPanel.add(userStocksPanel);
        userStocksWatchlistPanel.add(userWatchlistPanel);

        add(userStocksWatchlistPanel, BorderLayout.SOUTH);

        // Add Stock Panel
        JPanel addStockPanel = new JPanel();
        addStockPanel.setLayout(new GridBagLayout());

        JLabel lblStockSymbol = new JLabel("Stock Symbol:");
        txtStockSymbol = new JTextField(10);
        btnAddStock = new JButton("Add Stock");

        GridBagConstraints gbcStockSymbolLabel = new GridBagConstraints();
        gbcStockSymbolLabel.gridx = 0;
        gbcStockSymbolLabel.gridy = 0;
        gbcStockSymbolLabel.anchor = GridBagConstraints.WEST;
        gbcStockSymbolLabel.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcStockSymbolTextField = new GridBagConstraints();
        gbcStockSymbolTextField.gridx = 1;
        gbcStockSymbolTextField.gridy = 0;
        gbcStockSymbolTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcStockSymbolTextField.weightx = 1.0;
        gbcStockSymbolTextField.insets = new Insets(5, 0, 5, 5);

        GridBagConstraints gbcAddStockButton = new GridBagConstraints();
        gbcAddStockButton.gridx = 0;
        gbcAddStockButton.gridy = 1;
        gbcAddStockButton.gridwidth = 2;
        gbcAddStockButton.anchor = GridBagConstraints.CENTER;
        gbcAddStockButton.insets = new Insets(5, 5, 5, 5);

        addStockPanel.add(lblStockSymbol, gbcStockSymbolLabel);
        addStockPanel.add(txtStockSymbol, gbcStockSymbolTextField);
        addStockPanel.add(btnAddStock, gbcAddStockButton);

        add(addStockPanel, BorderLayout.WEST);

        // Add Watchlist Panel
        JPanel addWatchlistPanel = new JPanel();
        addWatchlistPanel.setLayout(new GridBagLayout());

        JLabel lblWatchlistSymbol = new JLabel("Watchlist Symbol:");
        txtWatchlistSymbol = new JTextField(10);
        JLabel lblStockWatchList = new JLabel("Stock for watchlist: ");
        txtStockWatchlist = new JTextField(10);
        btnCreateWatchlist = new JButton("Create Watchlist");
        btnAddToWatchlist = new JButton("Add to Watchlist");

        GridBagConstraints gbcWatchlistSymbolLabel = new GridBagConstraints();
        gbcWatchlistSymbolLabel.gridx = 0;
        gbcWatchlistSymbolLabel.gridy = 0;
        gbcWatchlistSymbolLabel.anchor = GridBagConstraints.WEST;
        gbcWatchlistSymbolLabel.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcWatchlistSymbolTextField = new GridBagConstraints();
        gbcWatchlistSymbolTextField.gridx = 1;
        gbcWatchlistSymbolTextField.gridy = 0;
        gbcWatchlistSymbolTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcWatchlistSymbolTextField.weightx = 1.0;
        gbcWatchlistSymbolTextField.insets = new Insets(5, 0, 5, 5);

        GridBagConstraints gbcStockWatchlistLabel = new GridBagConstraints();
        gbcWatchlistSymbolLabel.gridx = 0;
        gbcWatchlistSymbolLabel.gridy = 1;
        gbcWatchlistSymbolLabel.anchor = GridBagConstraints.WEST;
        gbcWatchlistSymbolLabel.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcStockWatchlistTextField = new GridBagConstraints();
        gbcWatchlistSymbolTextField.gridx = 1;
        gbcWatchlistSymbolTextField.gridy = 1;
        gbcWatchlistSymbolTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcWatchlistSymbolTextField.weightx = 1.0;
        gbcWatchlistSymbolTextField.insets = new Insets(5, 0, 5, 5);

        GridBagConstraints gbcCreateWatchlistButton = new GridBagConstraints();
        gbcCreateWatchlistButton.gridx = 0;
        gbcCreateWatchlistButton.gridy = 2;
        gbcCreateWatchlistButton.gridwidth = 2;
        gbcCreateWatchlistButton.anchor = GridBagConstraints.CENTER;
        gbcCreateWatchlistButton.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcAddToWatchlistButton = new GridBagConstraints();
        gbcAddToWatchlistButton.gridx = 0;
        gbcAddToWatchlistButton.gridy = 3;
        gbcAddToWatchlistButton.gridwidth = 2;
        gbcAddToWatchlistButton.anchor = GridBagConstraints.CENTER;
        gbcAddToWatchlistButton.insets = new Insets(5, 5, 5, 5);

        addWatchlistPanel.add(lblWatchlistSymbol, gbcWatchlistSymbolLabel);
        addWatchlistPanel.add(txtWatchlistSymbol, gbcWatchlistSymbolTextField);
        addWatchlistPanel.add(lblStockWatchList, gbcStockWatchlistLabel);
        addWatchlistPanel.add(txtStockWatchlist, gbcStockWatchlistTextField);
        addWatchlistPanel.add(btnCreateWatchlist, gbcCreateWatchlistButton);
        addWatchlistPanel.add(btnAddToWatchlist, gbcAddToWatchlistButton);

        add(addWatchlistPanel, BorderLayout.EAST);

        // Event Listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (login(username, password)) {
                    JOptionPane.showMessageDialog(StockPlatform.this, "Login Successful!");
                    updateUI();
                } else {
                    JOptionPane.showMessageDialog(StockPlatform.this, "Invalid username or password.");
                }
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
                updateUI();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(StockPlatform.this, "Registration Successful!");
                    updateUI();
                } else {
                    JOptionPane.showMessageDialog(StockPlatform.this, "Username already exists. Please choose a different username.");
                }
            }
        });

        btnAddStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stockSymbol = txtStockSymbol.getText().toUpperCase();
                if (!stockSymbol.isEmpty()) {
                    addStock(stockSymbol);
                    System.out.println("Stock " + stockSymbol + " added");
                    txtStockSymbol.setText("");
                    updateUI();
                }
            }
        });

        btnCreateWatchlist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String watchlistSymbol = txtWatchlistSymbol.getText();
                if (!watchlistSymbol.isEmpty()) {
                    createWatchlist(watchlistSymbol);
                    txtWatchlistSymbol.setText("");
                    updateUI();
                }
            }
        });

        btnAddToWatchlist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String watchlistSymbol = txtWatchlistSymbol.getText();
                String stockWatchlist = txtStockWatchlist.getText().toUpperCase();
                if (!watchlistSymbol.isEmpty()) {
                    addToWatchlist(watchlistSymbol, stockWatchlist);
                    txtWatchlistSymbol.setText("");
                    updateUI();
                }
            }
        });

        // Connect to the MySQL database
        try {
            String url = "jdbc:derby://localhost:1527/database";
            String user = "divyesh";
            String password = "divyesh";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the MySQL database.");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to the MySQL database: " + ex.getMessage());
        }
    }
    
    private void updateUI() {
        if (loggedInUser != null) {
            lblUsername.setText("Welcome, " + loggedInUser.getUsername() + "!");
            txtUsername.setEnabled(false);
            txtPassword.setEnabled(false);
            btnLogin.setEnabled(false);
            btnLogout.setEnabled(true);
            btnRegister.setEnabled(false);
            btnAddStock.setEnabled(true);
            btnCreateWatchlist.setEnabled(true);
            btnAddToWatchlist.setEnabled(true);
            
            showUserStocks();
            showUserWatchlist();
        } else {
            lblUsername.setText("Username:");
            txtUsername.setEnabled(true);
            txtPassword.setEnabled(true);
            btnLogin.setEnabled(true);
            btnLogout.setEnabled(false);
            btnRegister.setEnabled(true);
            btnAddStock.setEnabled(false);
            btnCreateWatchlist.setEnabled(false);
            btnAddToWatchlist.setEnabled(false);
            txtAreaUserStocks.setText("");
            txtAreaUserWatchlist.setText("");
        }
//        showStockData();
    }

    private boolean login(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                loggedInUser = new User(userId, username);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Failed to login: " + ex.getMessage());
        }
        return false;
    }

    private void logout() {
        loggedInUser = null;
    }

    private boolean registerUser(String username, String password) {
        try {
            String query = "INSERT INTO USERS (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Failed to register user: " + ex.getMessage());
        }
        return false;
    }

    private void addStock(String stockSymbol) {
        try {
            if(retrieveStockPrice(stockSymbol) != 0.){
                String query = "INSERT INTO user_stocks (username, symbol) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, loggedInUser.getUsername());
                statement.setString(2, stockSymbol);
                statement.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(StockPlatform.this, "Stock Symbol not found, try again!");
            }
        } catch (SQLException ex) {
            System.out.println("Failed to add stock: " + ex.getMessage());
        }
    }

    private void createWatchlist(String watchlistSymbol) {
        try {
            String query = "INSERT INTO user_watchlists (username, watchlist_name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loggedInUser.getUsername());
            statement.setString(2, watchlistSymbol);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to create watchlist: " + ex.getMessage());
        }
    }

    private void addToWatchlist(String watchlistSymbol, String stockWatchlist) {
        try {
            if(retrieveStockPrice(stockWatchlist) != 0.){
                String query = "INSERT INTO watchlist_symbols (username, watchlist_name, symbol) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, loggedInUser.getUsername());
                statement.setString(2, watchlistSymbol);
                statement.setString(3, stockWatchlist);
                statement.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(StockPlatform.this, "Stock Symbol not found, try again!");
            }
        } catch (SQLException ex) {
            System.out.println("Failed to add to watchlist: " + ex.getMessage());
        }
    }
    private double retrieveStockPrice(String symbol){
        String apiKey = "YOUR_API_KEY";
        String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=ABCI3SO8E0G1C5LS";

        try {
            // Make the HTTP request to the Alpha Vantage API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Check if the API response contains an error message
            if (jsonResponse.has("Error Message")) {
//                JOptionPane.showMessageDialog(StockPlatform.this, "Error finding stock symbol");
                System.out.println("Error finding stock symbol");
                return 0.;
            }

            JSONObject globalQuote = jsonResponse.getJSONObject("Global Quote");
            System.out.println(globalQuote);
            String symbol1 = globalQuote.getString("01. symbol");
            double price1 = globalQuote.getDouble("05. price");

            // Update the stock price in stockData
            stockData.put(symbol1, price1);
            System.out.println("Stock data retrieved successfully!");
            return price1;
        } catch (IOException e) {
            System.out.println("Error finding stock symbol");
            e.printStackTrace();
        }catch (Exception e){
//            JOptionPane.showMessageDialog(StockPlatform.this, "Error finding stock symbol");            
        }
        return stockData.get(symbol);
    }
    
    private void showStockData() {
        try {
            String query = "SELECT * FROM stock_data";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                String stockSymbol = resultSet.getString("stock_symbol");
                double stockPrice = resultSet.getDouble("stock_price");
                sb.append(stockSymbol).append(": ").append(stockPrice).append("\n");
                stockData.put(stockSymbol, stockPrice);
            }
            txtAreaStockData.setText(sb.toString());
        } catch (SQLException ex) {
            System.out.println("Failed to fetch stock data: " + ex.getMessage());
        }
    }

    private void showUserStocks() {
        try {
            String query = "SELECT * FROM user_stocks WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loggedInUser.getUsername());
            ResultSet resultSet = statement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                String stockSymbol = resultSet.getString("symbol");
                double price = retrieveStockPrice(stockSymbol);
                sb.append(stockSymbol).append(" : $").append(price).append("\n");
            }
            txtAreaUserStocks.setText(sb.toString());
        } catch (SQLException ex) {
            System.out.println("Failed to fetch user stocks: " + ex.getMessage());
        }
    }

    private void showUserWatchlist() {
        Set<String> watchlists = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        try {
            String query = "SELECT * FROM user_watchlists WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loggedInUser.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String watchlistSymbol = resultSet.getString("watchlist_name");
//                sb.append(watchlistSymbol).append("\n");
                watchlists.add(watchlistSymbol);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to fetch user watchlist: " + ex.getMessage());
        }
        ArrayList<String> arr = new ArrayList<>(watchlists);
        for(String s: arr){
            sb.append("\n").append(s).append(" Watchlist :").append("\n");
            try {
                String query = "SELECT * FROM watchlist_symbols WHERE username = ? AND watchlist_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, loggedInUser.getUsername());
                statement.setString(2, s);
                ResultSet resultSet = statement.executeQuery();
                int itr = 1;
                while (resultSet.next()) {
                    String watchlistSymbol = resultSet.getString("symbol");
                    double price = retrieveStockPrice(watchlistSymbol);
                    sb.append(itr).append(")").append(watchlistSymbol).append(" : $").append(price).append("\n");
                    itr++;
                }
            } catch (SQLException ex) {
                System.out.println("Failed to fetch user watchlist: " + ex.getMessage());
            }
        }
        txtAreaUserWatchlist.setText(sb.toString());

    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StockPlatform().setVisible(true);
            }
        });
    }

}