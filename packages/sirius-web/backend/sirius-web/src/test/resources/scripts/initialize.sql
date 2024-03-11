-- Sample empty studio project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'Empty Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'bd3017e3-d95f-4535-8701-af6ba982619f',
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample studio project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '01234836-0902-418a-900a-4c0afd20323e',
  'Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '01234836-0902-418a-900a-4c0afd20323e',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  '01234836-0902-418a-900a-4c0afd20323e',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/domain'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/form'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'f0e490c1-79f1-49a0-b1f2-3637f2958148',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Domain',
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns":{"domain":"http://www.eclipse.org/sirius-web/domain"},"content":[{"id":"f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf","eClass":"domain:Domain","data":{"name":"buck","types":[{"id":"c341bf91-d315-4264-9787-c51b121a6375","eClass":"domain:Entity","data":{"name":"Root","attributes":[{"id":"7ac92c9d-3cb6-4374-9774-11bb62962fe2","eClass":"domain:Attribute","data":{"name":"label"}}],"relations":[{"id":"f8fefc5d-4fee-4666-815e-94b24a95183f","eClass":"domain:Relation","data":{"name":"humans","many":true,"containment":true,"targetType":"//@types.1"}}]}},{"id":"1731ffb5-bfb0-46f3-a23d-0c0650300005","eClass":"domain:Entity","data":{"name":"Human","attributes":[{"id":"e34109d2-f51b-4070-af0e-da8b31c1f830","eClass":"domain:Attribute","data":{"name":"name"}},{"id":"e86d3f45-d043-441e-b8ab-12e4b3f8915a","eClass":"domain:Attribute","data":{"name":"description"}},{"id":"703e6db4-a193-4da7-a872-6efba2b3a2c5","eClass":"domain:Attribute","data":{"name":"promoted","type":"BOOLEAN"}}]}}]}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'ed2a5355-991d-458f-87f1-ea3a18b1f104',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Form View',
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns":{"form":"http://www.eclipse.org/sirius-web/form","view":"http://www.eclipse.org/sirius-web/view"},"content":[{"id":"c4591605-8ea8-4e92-bb17-05c4538248f8","eClass":"view:View","data":{"descriptions":[{"id":"ed20cb85-a58a-47ad-bc0d-749ec8b2ea03","eClass":"form:FormDescription","data":{"name":"Human Form","domainType":"buck::Human","pages":[{"id":"b0c73654-6f1b-4be5-832d-b97f053b5196","eClass":"form:PageDescription","data":{"name":"Human","labelExpression":"aql:self.name","domainType":"buck::Human","groups":[{"id":"28d8d6de-7d6f-4434-9293-0ac4ef2461ac","eClass":"form:GroupDescription","data":{"name":"Properties","labelExpression":"Properties","children":[{"id":"b02b89b7-6c06-40f8-9366-83d5f885ada1","eClass":"form:TextfieldDescription","data":{"name":"Name","labelExpression":"Name","helpExpression":"The name of the human","valueExpression":"aql:self.name","body":[{"id":"ecdc23ff-fd4b-47a4-939d-1bc03e656d3d","eClass":"view:ChangeContext","data":{"children":[{"id":"a8b95d5b-833a-4b19-b783-3025225613de","eClass":"view:SetValue","data":{"featureName":"name","valueExpression":"aql:newValue"}}]}}]}},{"id":"98e756a2-305f-4767-b75c-4130996ae6da","eClass":"form:TextAreaDescription","data":{"name":"Description","labelExpression":"Description","helpExpression":"The description of the human","valueExpression":"aql:self.description","body":[{"id":"59ea57d5-c365-4421-9648-f38a74644768","eClass":"view:ChangeContext","data":{"children":[{"id":"811bb719-ab53-49ea-9281-6558f7022ecc","eClass":"view:SetValue","data":{"featureName":"description","valueExpression":"aql:newValue"}}]}}]}},{"id":"ba20babb-0e75-4f66-a382-a2f02bce904a","eClass":"form:CheckboxDescription","data":{"name":"Promoted","labelExpression":"Promoted","helpExpression":"Has this human been promoted?","valueExpression":"aql:self.promoted","body":[{"id":"afac13bd-71ac-4287-baf6-3669f23ac806","eClass":"view:ChangeContext","data":{"children":[{"id":"0eaeca64-ee2b-4f2c-9454-c528181d0d64","eClass":"view:SetValue","data":{"featureName":"promoted","valueExpression":"aql:newValue"}}]}}]}}]}}]}}]}}]}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample empty UML project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  'UML Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  'uml'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '503a1f9b-13f7-4394-94df-ddbf32840a31',
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample empty SysML project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'SysML Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'sysml'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '86fa5d90-a602-4083-b3c1-65912b93b673',
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample Ecore project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '99d336a2-3049-439a-8853-b104ffb22653',
  'Ecore Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '99d336a2-3049-439a-8853-b104ffb22653',
  'ecore'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  'http://www.eclipse.org/emf/2002/Ecore'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '48dc942a-6b76-4133-bca5-5b29ebee133d',
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  'Ecore',
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns": {"ecore":"http://www.eclipse.org/emf/2002/Ecore"},"content":[{"id":"3237b215-ae23-48d7-861e-f542a4b9a4b8","eClass":"ecore:EPackage","data":{"name":"Sample"}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_data (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  kind,
  content,
  created_on,
  last_modified_on
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  'siriusComponents://representation?type=Portal',
  '{"id":"e81eec5c-42d6-491c-8bcc-9beb951356f8","kind":"siriusComponents://representation?type=Portal","descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672","label":"Portal","targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8","views":[],"layoutData":[]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
