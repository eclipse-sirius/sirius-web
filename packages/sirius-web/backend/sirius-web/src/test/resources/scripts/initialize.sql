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
  '{"id":"e81eec5c-42d6-491c-8bcc-9beb951356f8","kind":"siriusComponents://representation?type=Portal","descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672","label":"Portal","targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8","views":[{"id":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df","representationId":"05e44ccc-9363-443f-a816-25fc73e3e7f7"}],"layoutData":[{"portalViewId":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df","x":0,"y":0,"width":500,"height":200}]}',
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
  '05e44ccc-9363-443f-a816-25fc73e3e7f7',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  'siriusComponents://representation?type=Portal',
  '{"id":"05e44ccc-9363-443f-a816-25fc73e3e7f7","kind":"siriusComponents://representation?type=Portal","descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672","label":"Child Portal","targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8","views":[],"layoutData":[]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
