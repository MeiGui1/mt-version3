CREATE TABLE PainBeginning
(
    patient_id int  PRIMARY KEY,
    intensity int,
    location_teeth BLOB,
    location_face_left BLOB,
    location_face_right BLOB,
    pain_quality text,
    pain_pattern text,
    dull boolean,
    pulling boolean,
    stinging boolean,
    pulsating boolean,
    burning boolean,
    pinsneedles boolean,
    tingling boolean,
    numb boolean,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE
);

CREATE TABLE PainCurrent
(
    patient_id int  PRIMARY KEY,
    intensity int,
    location_teeth BLOB,
    location_face_left BLOB,
    location_face_right BLOB,
    pain_quality text,
    pain_pattern text,
    dull boolean,
    pulling boolean,
    stinging boolean,
    pulsating boolean,
    burning boolean,
    pinsneedles boolean,
    tingling boolean,
    numb boolean,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE
);
