# JavaProject

Small Java Backend project. Users can visit routes to create and delete portfolios. Then they can add and delete positions from those created portfolios as well. To utilize the app, please visit the routes below. The app will return the data in JSON format for the users viewing. 

## Portfolio(s)
* /GET /api/v1/portfolios lists all portfolios on the system by name
* /PUT /api/v1/portfolios/{portfolioName} creates a new portfolio
* /GET /api/v1/portfolios/{portfolioName} list the stocks owned in a single portfolio
* /DELETE /api/v1/portfolios/{portfolioName} delete a portfolio

## Positions(s)
* /PUT /api/v1/portfolios/{portfolioName}/{ticker}?marketValue={marketValue} creates a new position
* /POST /api/v1/portfolios/{portfolioName}/{ticker}?marketValue={marketValue} updates an existing
* /DELETE /api/v1/portfolios/{portfolioName}/{ticker} removes a position


