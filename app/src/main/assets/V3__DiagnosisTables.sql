INSERT INTO DiagnosisType (name, type, description) VALUES ('Anteriore Diskusverlagerung','Kiefergelenk','Verlagerungen der Knorpelscheibe im Kiefergelenk');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Degenerative Kiefergelenk-Anpassungsarthrose','Kiefergelenk','Übermässig starke Abnutzung der Kiefergelenke');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Arthralgie','Kiefergelenk','Kiefergelenkschmerzen');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Arthritis','Kiefergelenk','Entzündung des Kiefergelenks');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Kiefergelenk-Ankylose','Kiefergelenk','Versteifung des Kiefergelenks');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Myalgie der Kaumuskulatur', 'Muskulatur','Muskelschmerz in der Kaumuskulatur');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Parafunktion Zähnepressen','Muskulatur','Zähnepressen');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Nächtlicher Bruxismus','Muskulatur','Nächtliches Zähneknirschen');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Trigeminusneuralgie','Neurologisch','Starker Gesichtsschmerz - Schmerzattacken');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Periphere traumatische Trigeminusneuropathie','Neurologisch','Starker Gesichtsschmerz - Dauerschmerz');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Dentale Überempfindlichkeit','Neurologisch','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Idiopathische dentoalveoläre Schmerzen','Neurologisch','Schmerzen im Mundbereich, unbekannte Ursache');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Zungenbrennen','Neurologisch','Schmerzen und Brennen an den Zungen- oder Mundschleimhäuten');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Glossopharyngeus-Neuralgie','Neurologisch','Attackenförmige Schmerzen im Rachen-, Hals-, Zungenbereich');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Chronische Schmerzstörung','Psychologisch','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Ein-/Durchschalfstörung','Psychologisch','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Obstruktives Schlafapnoe-Syndrom','Psychologisch','Periodische Atemstörungen während des Schlafs');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Zahnbehandlungsphobie','Psychologisch','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Orale Lichenoide Reaktion','Psychologisch','Entzündung der äußeren Haut und der Schleimhäute');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Migräne mit Aura','Kopfschmerz','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Migräne ohne Aura','Kopfschmerz','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Chronische Spannungstypkopfschmerzen','Kopfschmerz','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Episodischer Spannungstypkopfschmerzen','Kopfschmerz','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Cluster-Kopfschmerz','Kopfschmerz','Attackenförmige Schmerzen im Bereich von Auge und Schläfe');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Medikamentenübergebrauchs-Kopfschmerz','Kopfschmerz','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Zahnbehandlungsphobie','Psychologisch','');
INSERT INTO DiagnosisType (name, type, description) VALUES ('Prominente Interkalarlinie','Schleimhaut',null);
INSERT INTO DiagnosisType (name, type, description) VALUES ('Landkartenzunge','Schleimhaut','Entzündliche Veränderung der Zungenoberfläche');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('1', '2', 'beidseits', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('1', '4', 'rechts', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('1', '13', 'mit Dysgeusie', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('2', '1', 'links mit Reposition', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('2', '9', 'rechts mit Anästhesie', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('3', '11', 'mit erosivem Zahnhartsubstanzverlust', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('4', '2', 'links', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('5', '2', 'rechts', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id, comment, priority) VALUES ('6', '5', 'beidseits', -1);
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('1', '7');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('3', '2');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('4', '16');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('6', '16');
INSERT INTO PatientDiagnosis (patient_id, diagnosistype_id) VALUES ('6', '21');