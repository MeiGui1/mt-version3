CREATE TABLE PsychoSocialBefore
(
    patient_id   int PRIMARY KEY,
    pain_xpos    int NOT NULL,
    pain_ypos    int NOT NULL,
    familiy_xpos int NOT NULL,
    familiy_ypos int NOT NULL,
    work_xpos    int NOT NULL,
    work_ypos    int NOT NULL,
    finance_xpos int NOT NULL,
    finance_ypos int NOT NULL,
    event_xpos   int NOT NULL,
    event_ypos   int NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE
);

CREATE TABLE PsychoSocialAfter
(
    patient_id   int PRIMARY KEY,
    pain_xpos    int NOT NULL,
    pain_ypos    int NOT NULL,
    familiy_xpos int NOT NULL,
    familiy_ypos int NOT NULL,
    work_xpos    int NOT NULL,
    work_ypos    int NOT NULL,
    finance_xpos int NOT NULL,
    finance_ypos int NOT NULL,
    event_xpos   int NOT NULL,
    event_ypos   int NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE
);

CREATE TABLE ImprovementReason
(
    patient_id   int PRIMARY KEY,
    drugs boolean,
    exercises boolean,
    awareness boolean,
    other_reason boolean,
    other_reason_text text,
    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE
);

