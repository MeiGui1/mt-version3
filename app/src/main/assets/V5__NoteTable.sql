CREATE TABLE Note (
    id serial PRIMARY KEY,
    patient_id int NOT NULL,
    note_bytes BLOB NOT NULL,
    selected boolean,
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE
);


