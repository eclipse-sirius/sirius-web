CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;

CREATE TABLE document (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    project_id uuid NOT NULL,
    name text NOT NULL,
    content text NOT NULL,
    CONSTRAINT document_name_length CHECK (((char_length(name) > 0) AND (char_length(name) <= 1024)))
);

CREATE TABLE idmapping (
    id text NOT NULL,
    externalid text NOT NULL
);

CREATE TABLE project (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name text NOT NULL,
    CONSTRAINT project_name_length CHECK (((char_length(name) > 0) AND (char_length(name) <= 1024)))
);

CREATE TABLE representation (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    project_id uuid NOT NULL,
    targetobjectid text NOT NULL,
    label text NOT NULL,
    kind text NOT NULL,
    descriptionId TEXT NOT NULL,
    content text NOT NULL,
    CONSTRAINT representation_label_length CHECK (((char_length(label) > 0) AND (char_length(label) <= 1024)))
);

ALTER TABLE ONLY idmapping
    ADD CONSTRAINT idmapping_externalid_unique UNIQUE (externalid);

ALTER TABLE ONLY document
    ADD CONSTRAINT pk_document_id PRIMARY KEY (id);

ALTER TABLE ONLY idmapping
    ADD CONSTRAINT pk_idmapping_id PRIMARY KEY (id);

ALTER TABLE ONLY project
    ADD CONSTRAINT pk_project_id PRIMARY KEY (id);

ALTER TABLE ONLY representation
    ADD CONSTRAINT pk_representation_id PRIMARY KEY (id);

ALTER TABLE ONLY document
    ADD CONSTRAINT fk_document_project_id_id2 FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;

ALTER TABLE ONLY representation
    ADD CONSTRAINT fk_representation_project_id_id FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE;

CREATE TABLE CustomImage (
	id UUID NOT NULL,
    project_id uuid,
	label TEXT NOT NULL,
	content_type TEXT NOT NULL,
	content BYTEA NOT NULL,
	CONSTRAINT pk_customimage_id PRIMARY KEY (id),
    CONSTRAINT fk_customimage_project_id_id FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE ProjectNature (
	id UUID NOT NULL,
    project_id uuid NOT NULL,
	name text NOT NULL,   
	CONSTRAINT pk_projectnature_id PRIMARY KEY (id),
    CONSTRAINT fk_projectnature_project_id_id FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT projectnature_name_length CHECK (((char_length(name) > 0) AND (char_length(name) <= 1024)))
);