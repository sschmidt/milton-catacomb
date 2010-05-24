CREATE TABLE dasl_resource (
  serialno int NOT NULL,
  user_id bigint NOT NULL,
  uri varchar NOT NULL,
  creationdate bigint default NULL,
  getcontentlength int NOT NULL,
  document_id varchar default NULL,
  getlastmodified bigint default NULL,
  resourcetype tinyint default NULL,
  depth tinyint NOT NULL,
  win32fileattributes int NOT NULL,
  PRIMARY KEY  (serialno)
);