CREATE TABLE PatientImage
(
    patient_id int  NOT NULL,
    image_path text NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE,
    UNIQUE (patient_id, image_path)
);

CREATE TABLE PatientVideo
(
    patient_id int  NOT NULL,
    video_path text NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE,
    UNIQUE (patient_id, video_path)
);

CREATE TABLE PatientDocument
(
    patient_id    int  NOT NULL,
    document_path text NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE,
    UNIQUE (patient_id, document_path)
);


CREATE TABLE WebsiteType
(
    id          serial PRIMARY KEY,
    name        text NOT NULL,
    url         text NOT NULL,
    description text
);

CREATE TABLE PatientWebsite
(
    patient_id int  NOT NULL,
    website_id int NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE,
    FOREIGN KEY (website_id) REFERENCES WebsiteType (id) ON DELETE CASCADE,
    UNIQUE (patient_id, website_id)
);
