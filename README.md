<h2>Project Overview</h2>
<p>This backend project is built using Java, Spring Boot, Spring Data JPA, and MySQL. It serves as a RESTful API for fetching and managing cricket match data, including live match scores, points table data, and all match listings. The application scrapes data from Cricbuzz using the Jsoup library and stores it in a MySQL database.</p>

<h2>Key Components</h2>

<h3>1. Match Service Implementation (<code>MatchServiceImpl</code>)</h3>
<ul>
  <li>This service class implements the <code>MatchService</code> interface and is responsible for fetching live cricket match data and the points table.</li>
  <li>Data is scraped from Cricbuzz using Jsoup and then processed into <code>Match</code> entities.</li>
</ul>

<h3>2. Functionalities</h3>
<ul>
  <li><strong>Fetching Live Matches:</strong> The <code>getLiveMatches()</code> method scrapes live match data from Cricbuzz, including team information, scores, and match status. It also checks if the match already exists in the database and updates it accordingly.</li>
  <li><strong>Fetching Points Table:</strong> The <code>getPointsTable()</code> method scrapes the current cricket points table from Cricbuzz, extracting team standings and other relevant data.</li>
  <li><strong>Fetching All Matches:</strong> The <code>getAllMatches()</code> method retrieves all matches stored in the database.</li>
</ul>

<h3>3. Data Storage</h3>
<ul>
  <li>The application uses MySQL as the database, and Spring Data JPA handles database interactions. Matches are saved or updated in the database to keep the data synchronized with the latest information from Cricbuzz.</li>
</ul>

<h3>4. Match Controller (<code>MatchController</code>)</h3>
<ul>
  <li>Exposes RESTful endpoints for accessing the service methods.</li>
  <li><strong>Endpoints:</strong>
    <ul>
      <li><code>/match/live</code>: Fetches and returns a list of current live matches.</li>
      <li><code>/match</code>: Returns all stored matches in the database.</li>
      <li><code>/match/point-table</code>: Returns the current points table.</li>
    </ul>
  </li>
</ul>

<h2>How It Works</h2>
<ul>
  <li><strong>Scraping:</strong> The application connects to Cricbuzz URLs and extracts relevant HTML elements to fetch match data.</li>
  <li><strong>Data Processing:</strong> Extracted data is converted into Java objects (<code>Match</code> entities) with fields like team names, scores, and match status.</li>
  <li><strong>Database Update:</strong> Matches are checked against existing records in the database; new matches are added, and existing matches are updated as needed.</li>
  <li><strong>REST API:</strong> The data is exposed through RESTful endpoints, allowing easy access to live scores, match history, and points tables.</li>
</ul>

<h2>Technologies Used</h2>
<ul>
  <li><strong>Java:</strong> Core language used for building the backend logic.</li>
  <li><strong>Spring Boot:</strong> Provides the framework for building the application, handling dependencies, and creating RESTful endpoints.</li>
  <li><strong>Spring Data JPA:</strong> Simplifies database operations by handling CRUD operations automatically.</li>
  <li><strong>MySQL:</strong> Database used to store match information.</li>
  <li><strong>Jsoup:</strong> A Java library for parsing and scraping HTML from Cricbuzz.</li>
</ul>

<p>This project offers a robust backend solution for managing cricket data and can be extended further by adding more features or enhancing the data processing capabilities.</p>
