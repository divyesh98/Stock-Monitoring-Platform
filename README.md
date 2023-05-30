# Stock Monitoring Platform

The Stock Monitoring Platform is a Java application that allows users to monitor stock data, manage their own stocks, and create watchlists.

## Features

- **Login and Registration:** Users can register with a username and password, and then log in to access the platform.
- **Stock Data Display:** The platform provides real-time stock data retrieved from the Alpha Vantage API.
- **User Stocks:** Users can view and manage their own stocks. They can add stocks by entering the stock symbol.
- **Watchlists:** Users can create watchlists and add stocks to their watchlists.
- **Database Integration:** User data, including registered users, user stocks, and watchlists, is stored in a MySQL database.
- **Concurrency:** The application is suitable to work for multi-user environment. Multiple instances can be created where each user can register and log in without the loss of information. Each user has their own stocks and watchlists.

## Usage

1. **Login/Register:** On the login panel, enter your username and password to log in. If you don't have an account, click the "New User? Register" button to register a new account.
2. **Stock Data:** The stock data panel displays real-time stock data retrieved from the Alpha Vantage API.
3. **User Stocks:** The user stocks panel shows the stocks owned by the logged-in user. Enter a stock symbol in the text field and click "Add Stock" to add a stock to the user's portfolio.
4. **Watchlists:** The user watchlist panel allows users to create watchlists and add stocks to them. Enter a stock symbol and the watchlist name in the respective text fields, then click "Add to Watchlist" to add the stock to the specified watchlist.
5. **Logout:** Click the "Logout" button to log out of the application.

## Requirements

- Java Development Kit (JDK)
- MySQL database
- Alpha Vantage API key (replace `YOUR_API_KEY` with your actual API key in the code)

## Setup

1. Clone the repository or download the source code files.
2. Set up a MySQL database and configure the database connection settings in the code (`connection = DriverManager.getConnection("jdbc:mysql://localhost/stock_monitoring", "username", "password")`).
3. Replace `YOUR_API_KEY` in the code with your actual Alpha Vantage API key.
4. Compile the Java files.
5. Run the application.

## Notes

- This application uses the Alpha Vantage API to retrieve real-time stock data. Make sure you have a valid API key and update the code accordingly.
- The database integration assumes you have a MySQL database set up and configured correctly.
- More applications for stock monitoring can be easily embedded in this code

## Limitations

- The application currently supports retrieving stock data from the Alpha Vantage API. If you want to use a different stock data source, you need to modify the code accordingly.
- The application's functionality is limited to displaying stock data, managing user stocks, and creating watchlists. Additional features like buying and selling stocks are not yet implemented.

## License

This project is licensed under the [MIT License](LICENSE).
