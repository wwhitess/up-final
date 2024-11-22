CREATE TABLE Roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NULL REFERENCES Roles(id)
);

CREATE TABLE Manufacturers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100)
);

CREATE TABLE Vehicles (
    id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    manufacturer_id INT NOT NULL REFERENCES Manufacturers(id),
    price NUMERIC(10, 2) NOT NULL,
    year INT NOT NULL,
    stock_quantity INT NOT NULL
);

CREATE TABLE Customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    address TEXT
);

CREATE TABLE Orders (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES Customers(id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE OrderDetails (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES Orders(id),
    vehicle_id INT NOT NULL REFERENCES Vehicles(id),
    quantity INT NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE Inventory (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL REFERENCES Vehicles(id),
    location VARCHAR(100),
    quantity INT NOT NULL
);




