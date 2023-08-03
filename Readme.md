
Start MongoDB Server:
    mongod --dbpath "E:\mongodb_data\db"
    mongod --dbpath "/Users/kkumar/Documents/mongodb_data/db"

Replace "C:\data\db" with the appropriate path to the MongoDB data directory, which you have previously created.

Check MongoDB Port: By default, MongoDB runs on port 27017.
    mongod --dbpath "E:\mongodb_data\db" --port 27018


Verify MongoDB Server Status: After starting MongoDB, open a new terminal or command prompt and run the mongo command to connect to the local MongoDB server:
    mongo

To shutdown MondoDB, use the db.shutdownServer() command inside the MongoDB shell.

Endpoints:

GET http://localhost:8080/products: Retrieve all products.
GET http://localhost:8080/products/{id}: Retrieve a specific product by ID.
POST http://localhost:8080/products: Create a new product (provide the product details in the request body).
PUT http://localhost:8080/products/{id}: Update an existing product (provide the updated product details in the request body).
DELETE http://localhost:8080/products/{id}: Delete a product by ID.
Make sure you have MongoDB running and properly conhfigured in your Spring Boot application (e.g., connection settings in application.properties).


Docker commands:
    docker build -t product-app .

    docker run -p 8080:8080 product-app
	
	docker tag product-app kishore979/product-app:v3.0
	
	docker push kishore979/product-app:v3.0
	
	docker pull kishore979/product-app:v3.0
	
	docker run kishore979/product-app:v3.0



In POM.xml:
    For Window, update property ${os.detected.classifier} to ""
    For MacOS M1, update property ${os.detected.classifier} to "osx-aarch_64" 