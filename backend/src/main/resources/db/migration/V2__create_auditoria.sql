CREATE TABLE auditoria (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    metodo VARCHAR(10),
    endpoint TEXT,
    status INT
);
