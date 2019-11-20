CREATE TABLE DiagnosisType (
    id serial PRIMARY KEY,
    name text NOT NULL,
    type text NOT NULL,
    description text
);

CREATE TABLE PatientDiagnosis (
    patient_id int NOT NULL,
    diagnosistype_id int NOT NULL,
    comment text,
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE,
    FOREIGN KEY (diagnosistype_id) REFERENCES DiagnosisType(id) ON DELETE CASCADE,
    UNIQUE (patient_id, diagnosistype_id)
);



INSERT INTO DiagnosisType (name, type, description) VALUES ('Anteriore Diskusverlagerung','Kiefergelenk',
                                                            'Verlagerungen der Knorpelscheibe im Kiefergelenk');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Degenerative Kiefergelenk-Anpassungsarthrose','Kiefergelenk',
                                                            'Übermässig starke Abnutzung der Kiefergelenke');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Arthralgie','Kiefergelenk',
                                                            'Kiefergelenkschmerzen');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Arthritis','Kiefergelenk',
                                                            'Entzündung des Kiefergelenks');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Ankylose','Kiefergelenk',
                                                            'Versteifung des Kiefergelenks');


INSERT INTO DiagnosisType (name, type, description) VALUES ('Myalgie der Kaumuskulatur', 'Muskulatur',
                                                            'Muskelschmerz in der Kaumuskulatur');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Parafunktion Zähnepressen','Muskulatur',
                                                            'Zähnepressen');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Nächtlicher Bruxismus','Muskulatur',
                                                            'Nächtliches Zähneknirschen');


INSERT INTO DiagnosisType (name, type, description) VALUES ('Trigeminusneuralgie','Neurologisch',
                                                            'Starker Gesichtsschmerz - Schmerzattacken');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Periphere traumatische Trigeminusneuropathie','Neurologisch',
                                                            'Starker Gesichtsschmerz - Dauerschmerz');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Dentale Überempfindlichkeit','Neurologisch',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Idiopathische dentoalveoläre Schmerzen','Neurologisch',
                                                            'Schmerzen im Mundbereich, unbekannte Ursache');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Zungenbrennen','Neurologisch',
                                                            'Schmerzen und Brennen an den Zungen- oder Mundschleimhäuten');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Glossopharyngeus-Neuralgie','Neurologisch',
                                                            'Attackenförmige Schmerzen im Rachen-, Hals-, Zungenbereich');


INSERT INTO DiagnosisType (name, type, description) VALUES ('Chronische Schmerzstörung','Psychologisch',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Ein-/Durchschalfstörung','Psychologisch',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Obstruktives Schlafapnoe-Syndrom','Psychologisch',
                                                            'Periodische Atemstörungen während des Schlafs');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Zahnbehandlungsphobie','Psychologisch',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Landkartenzunge','Psychologisch',
                                                            'Entzündliche Veränderung der Zungenoberfläche');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Orale Lichenoide Reaktion','Psychologisch',
                                                            'Entzündung der äußeren Haut und der Schleimhäute');

INSERT INTO DiagnosisType (name, type, description) VALUES ('Migräne mit Aura','Kopfschmerz',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Migräne ohne Aura','Kopfschmerz',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Chronische Spannungstypkopfschmerzen','Kopfschmerz',
                                                            '');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Cluster-Kopfschmerz','Kopfschmerz',
                                                            'Attackenförmige Schmerzen im Bereich von Auge und Schläfe');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Medikamentenübergebrauchs-Kopfschmerz','Kopfschmerz',
                                                            '');

INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('1', '2', 'beidseits');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('1', '4', 'rechts');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('1', '13', 'mit Dysgeusie');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('2', '1', 'links mit Reposition');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('2', '9', 'rechts mit Anästhesie');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('3', '11', 'mit erosivem Zahnhartsubstanzverlust');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('4', '2', 'links');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('5', '2', 'rechts');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment) VALUES ('6', '5', 'beidseits');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('1', '7');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('3', '2');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('4', '16');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('6', '16');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('6', '21');
