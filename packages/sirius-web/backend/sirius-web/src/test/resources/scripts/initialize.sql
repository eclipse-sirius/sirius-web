-- Sample empty studio project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'Studio',
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