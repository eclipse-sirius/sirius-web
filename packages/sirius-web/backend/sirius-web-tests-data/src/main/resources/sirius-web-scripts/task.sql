INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '8ac3a00a-9db0-4fc6-9bfd-ceb6ea3064fb',
  'Task',
  '2026-10-30 09:00:00+00',
  '2026-10-30 09:00:00+00'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  '8ac3a00a-9db0-4fc6-9bfd-ceb6ea3064fb',
  'siriusWeb://nature?kind=task'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'f3f4685e-45b8-40f7-959f-238c62ed4ee0',
  '2026-10-30 09:00:00+00',
  '2026-10-30 09:00:00+00'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'd79f8a7c-ac5f-4522-bcc2-57b5ef02cead',
  '8ac3a00a-9db0-4fc6-9bfd-ceb6ea3064fb',
  'f3f4685e-45b8-40f7-959f-238c62ed4ee0',
  'main',
  '2026-10-30 09:00:00+00',
  '2026-10-30 09:00:00+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'f3f4685e-45b8-40f7-959f-238c62ed4ee0',
  'http://www.eclipse.org/sirius-web/task'
);

INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on,
  is_read_only
) VALUES (
  'ec632218-196b-4595-b996-b186cc89f472',
  'f3f4685e-45b8-40f7-959f-238c62ed4ee0',
  'Task', '{
    "json": { "version": "1.0", "encoding": "utf-8" },
    "ns": { "task": "http://www.eclipse.org/sirius-web/task" },
    "content": [
      {
        "id": "a91883a0-61a9-4470-a77f-7a862c0fe66e",
        "eClass": "task:Company",
        "data": {
          "ownedProjects": [
            {
              "id": "2e23115a-2795-4d04-bd5e-aa9f27ac6658",
              "eClass": "task:Project",
              "data": {
                "name": "Project",
                "ownedTasks": [
                  {
                    "id": "d0d93a2a-1f7b-4b90-b6aa-8b7748ba6fc3",
                    "eClass": "task:Task",
                    "data": {
                      "name": "Task",
                      "tags": ["5bc9222a-20c7-45c5-88b7-ce1451047d4e"]
                    }
                  }
                ],
                "ownedTags": [
                  {
                    "id": "5bc9222a-20c7-45c5-88b7-ce1451047d4e",
                    "eClass": "task:TaskTag",
                    "data": { "prefix": "Tag" }
                  }
                ]
              }
            }
          ]
        }
      }
    ]
  }',
  '2026-10-30 09:00:00+00',
  '2026-10-30 09:00:00+00',
  false
);
