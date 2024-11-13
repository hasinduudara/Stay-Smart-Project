-- Create the database
CREATE DATABASE StaySmart;
USE StaySmart;

-- User Table
CREATE TABLE User (
                      User_ID VARCHAR(5) PRIMARY KEY,
                      Name VARCHAR(255) NOT NULL,
                      Email VARCHAR(255) NOT NULL UNIQUE,
                      User_Name VARCHAR(255) NOT NULL UNIQUE,
                      Password VARCHAR(255) NOT NULL
);

-- House Table
CREATE TABLE House (
                       House_ID VARCHAR(5) PRIMARY KEY,
                       Status VARCHAR(50) NOT NULL,
                       End_Of_Date DATE NOT NULL,
                       Rent_Price DECIMAL(10, 2) NOT NULL
);

-- Add House Table Values
INSERT INTO House (House_ID, Status, End_Of_Date, Rent_Price) VALUES
                                                                  ('H001', 'Available', '2024-12-31', 12000.00),
                                                                  ('H002', 'Available', '2024-12-31', 15000.00),
                                                                  ('H003', 'Available', '2024-12-31', 13500.00),
                                                                  ('H004', 'Available', '2024-12-31', 18000.00),
                                                                  ('H005', 'Available', '2024-12-31', 11000.00),
                                                                  ('H006', 'Available', '2024-12-31', 12500.00),
                                                                  ('H007', 'Available', '2024-12-31', 14500.00),
                                                                  ('H008', 'Available', '2024-12-31', 16000.00),
                                                                  ('H009', 'Available', '2024-12-31', 11500.00),
                                                                  ('H010', 'Available', '2024-12-31', 15500.00),
                                                                  ('H011', 'Available', '2024-12-31', 13000.00),
                                                                  ('H012', 'Available', '2024-12-31', 17000.00),
                                                                  ('H013', 'Available', '2024-12-31', 14000.00),
                                                                  ('H014', 'Available', '2024-12-31', 10500.00),
                                                                  ('H015', 'Available', '2024-12-31', 17500.00),
                                                                  ('H016', 'Available', '2024-12-31', 16500.00),
                                                                  ('H017', 'Available', '2024-12-31', 12500.00),
                                                                  ('H018', 'Available', '2024-12-31', 15000.00),
                                                                  ('H019', 'Available', '2024-12-31', 10000.00),
                                                                  ('H020', 'Available', '2024-12-31', 15500.00);

-- Tenant Table
CREATE TABLE Tenant (
                        Tenant_ID VARCHAR(5) PRIMARY KEY,
                        House_ID VARCHAR(5),
                        Name VARCHAR(255) NOT NULL,
                        Email VARCHAR(255) NOT NULL UNIQUE,
                        End_Of_Date DATE NOT NULL,
                        FOREIGN KEY (House_ID) REFERENCES House(House_ID)
                            ON UPDATE CASCADE
                            ON DELETE SET NULL
);

-- Rent Payment Table
CREATE TABLE Rent_Payment (
                              Rent_Payment_ID VARCHAR(5) PRIMARY KEY,
                              Rent_Amount DECIMAL(10, 2) NOT NULL,
                              Payment_Date DATE NOT NULL,
                              Tenant_ID VARCHAR(5),
                              House_ID VARCHAR(5),
                              FOREIGN KEY (Tenant_ID) REFERENCES Tenant(Tenant_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (House_ID) REFERENCES House(House_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Maintains Table
CREATE TABLE Maintains (
                           MT_ID VARCHAR(5) PRIMARY KEY,
                           House_ID VARCHAR(5),
                           Amount DECIMAL(10, 2) NOT NULL,
                           Description VARCHAR(255),
                           Date DATE NOT NULL,
                           FOREIGN KEY (House_ID) REFERENCES House(House_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Finances Table
CREATE TABLE Finances (
                          Finance_ID INT PRIMARY KEY,
                          Income DECIMAL(15, 2) DEFAULT 0.00,
                          Expenses DECIMAL(15, 2) DEFAULT 0.00
);

-- Total Income
UPDATE Finances
SET Income = (
    SELECT SUM(Rent_Amount)
    FROM Rent_Payment
);

-- Total Expenses
UPDATE Finances
SET Expenses = (
    SELECT SUM(Amount)
    FROM Maintains
);
