USE TaskDB;
GO

CREATE TABLE Tasks (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX),
    Status NVARCHAR(50),
    DueDate DATETIME
);
GO
