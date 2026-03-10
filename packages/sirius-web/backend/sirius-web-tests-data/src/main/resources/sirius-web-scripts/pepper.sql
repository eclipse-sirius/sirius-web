-- Sample Pepper Project

INSERT INTO project (id,name,created_on,last_modified_on) VALUES
    ('d866feab-2173-4b37-9972-6f0ba78327a9',
     'Task Sample',
     '2026-03-09 10:38:25.817022+01',
     '2026-03-09 10:38:25.817022+01');

INSERT INTO nature (project_id,name) VALUES
    ('d866feab-2173-4b37-9972-6f0ba78327a9',
     'siriusWeb://nature?kind=task');

INSERT INTO semantic_data (id,created_on,last_modified_on) VALUES
    ('c67f0c59-6e51-4aac-b771-e267c74a98ea',
     '2026-03-09 10:38:25.898655+01',
     '2026-03-09 11:33:39.717303+01');

INSERT INTO project_semantic_data (id,project_id,semantic_data_id,name,created_on,last_modified_on) VALUES
    ('7096fde3-ce37-49e1-a48b-1aca7fec419a',
     'd866feab-2173-4b37-9972-6f0ba78327a9',
     'c67f0c59-6e51-4aac-b771-e267c74a98ea',
     'main','2026-03-09 10:38:26.103233+01',
     '2026-03-09 10:38:26.103233+01');


INSERT INTO semantic_data_domain (semantic_data_id,uri) VALUES
    ('c67f0c59-6e51-4aac-b771-e267c74a98ea','http://peppermm');

INSERT INTO semantic_data_dependency (semantic_data_id,dependency_semantic_data_id,index) VALUES
    (
    'c67f0c59-6e51-4aac-b771-e267c74a98ea',
    '6f24a044-1605-484d-96c3-553ff6bc184d',
    0);


INSERT INTO document (id,semantic_data_id,name,content,created_on,last_modified_on,is_read_only) VALUES
    ('53857397-b950-4a4a-8830-cabb7e97d7c2',
     'c67f0c59-6e51-4aac-b771-e267c74a98ea',
     'Task',
     '{
      "json": {
        "version": "1.0",
        "encoding": "utf-8"
      },
      "ns": {
        "peppermm": "http://peppermm"
      },
      "content": [
        {
          "id": "c9632f2b-06f7-4f93-8a7a-184cff05029d",
          "eClass": "peppermm:Organization",
          "data": {
            "ownedProjects": [
              {
                "id": "20fcd054-8c2c-45dd-95fe-ee66e20d1d1e",
                "eClass": "peppermm:Project",
                "data": {
                  "name": "Project Dev",
                  "ownedWorkpackages": [
                    {
                      "id": "b0a16b2a-e08e-4d1a-b839-654820d47cc3",
                      "eClass": "peppermm:Workpackage",
                      "data": {
                        "name": "Main workpackage",
                        "ownedTasks": [
                          {
                            "id": "935cd266-5813-416c-8461-c39da8cd8471",
                            "eClass": "peppermm:Task",
                            "data": {
                              "name": "Idea",
                              "description": "Description of the Idea",
                              "startTime": "2023-12-10T08:30:00Z",
                              "endTime": "2023-12-11T17:30:00Z",
                              "progress": 50
                            }
                          },
                          {
                            "id": "57107c5c-3927-4d3a-af4e-7017ce336491",
                            "eClass": "peppermm:Task",
                            "data": {
                              "name": "Development",
                              "startTime": "2023-12-13T08:30:00Z",
                              "endTime": "2023-12-16T17:30:00Z",
                              "computeStartEndDynamically": true,
                              "subTasks": [
                                {
                                  "id": "260ce536-e42a-4af1-b966-af1b387098ca",
                                  "eClass": "peppermm:Task",
                                  "data": {
                                    "name": "Code Development",
                                    "startTime": "2023-12-13T08:30:00Z",
                                    "endTime": "2023-12-15T17:30:00Z",
                                    "progress": 40,
                                    "subTasks": [
                                      {
                                        "id": "c8444965-b891-4cbb-bc3c-814daee5c67d",
                                        "eClass": "peppermm:Task",
                                        "data": {
                                          "name": "Front",
                                          "description": "",
                                          "startTime": "2023-12-14T11:00:00Z",
                                          "endTime": "2023-12-15T20:00:00Z",
                                          "progress": 30,
                                          "dependencies": [
                                            "260ce536-e42a-4af1-b966-af1b387098ca"
                                          ]
                                        }
                                      }
                                    ],
                                    "dependencies": [
                                      "57107c5c-3927-4d3a-af4e-7017ce336491"
                                    ]
                                  }
                                }
                              ],
                              "dependencies": [
                                "935cd266-5813-416c-8461-c39da8cd8471"
                              ]
                            }
                          }
                        ]
                      }
                    }
                  ]
                }
              }
            ]
          }
        }
      ]
    }',
     '2026-03-09 11:33:39.717303+01',
     '2026-03-09 11:33:39.717303+01',
     false);


INSERT INTO representation_metadata (id,target_object_id,description_id,label,kind,created_on,last_modified_on,documentation,semantic_data_id,representation_metadata_id) VALUES
    ('c67f0c59-6e51-4aac-b771-e267c74a98ea#9563600d-eeea-4040-85a1-5c02f959b430',
     'b0a16b2a-e08e-4d1a-b839-654820d47cc3',
     'siriusComponents://representationDescription?kind=ganttDescription&sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=1035a9dc-fd08-39e1-b02c-99740626f9e6',
     'New Gantt',
     'siriusComponents://representation?type=Gantt',
     '2026-03-09 11:33:28.215378+01',
     '2026-03-09 11:33:28.215378+01',
     '',
     'c67f0c59-6e51-4aac-b771-e267c74a98ea',
     '9563600d-eeea-4040-85a1-5c02f959b430');

INSERT INTO representation_content (id,content,last_migration_performed,migration_version,created_on,last_modified_on,semantic_data_id,representation_metadata_id) VALUES
    ('c67f0c59-6e51-4aac-b771-e267c74a98ea#9563600d-eeea-4040-85a1-5c02f959b430',
     '{
          "id": "9563600d-eeea-4040-85a1-5c02f959b430",
          "descriptionId": "siriusComponents://representationDescription?kind=ganttDescription&sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=1035a9dc-fd08-39e1-b02c-99740626f9e6",
          "targetObjectId": "b0a16b2a-e08e-4d1a-b839-654820d47cc3",
          "tasks": [
            {
              "id": "ebeca356-0ae1-3618-adf2-afeeccce4b6e",
              "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9bd2e8ba-7d40-3430-b5a6-866dd2040494",
              "targetObjectId": "935cd266-5813-416c-8461-c39da8cd8471",
              "targetObjectKind": "siriusComponents://semantic?domain=peppermm&entity=Task",
              "targetObjectLabel": "Idea",
              "detail": {
                "name": "Idea",
                "description": "Description of the Idea",
                "startTime": "2023-12-10T08:30:00Z",
                "endTime": "2023-12-11T17:30:00Z",
                "temporalType": "DATE_TIME",
                "progress": 50,
                "computeStartEndDynamically": false,
                "collapsed": false
              },
              "taskDependencyIds": [],
              "subTasks": []
            },
            {
              "id": "97c8a735-89c2-3e8c-bb04-851957283dc8",
              "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9bd2e8ba-7d40-3430-b5a6-866dd2040494",
              "targetObjectId": "57107c5c-3927-4d3a-af4e-7017ce336491",
              "targetObjectKind": "siriusComponents://semantic?domain=peppermm&entity=Task",
              "targetObjectLabel": "Development",
              "detail": {
                "name": "Development",
                "description": "",
                "startTime": "2023-12-13T08:30:00Z",
                "endTime": "2023-12-15T17:30:00Z",
                "temporalType": "DATE_TIME",
                "progress": 40,
                "computeStartEndDynamically": true,
                "collapsed": false
              },
              "taskDependencyIds": [
                "ebeca356-0ae1-3618-adf2-afeeccce4b6e"
              ],
              "subTasks": [
                {
                  "id": "85624e0e-20ac-31ac-9c62-ef7f4618adca",
                  "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9f7d9111-74ed-3e66-9323-84442384a92f",
                  "targetObjectId": "260ce536-e42a-4af1-b966-af1b387098ca",
                  "targetObjectKind": "siriusComponents://semantic?domain=peppermm&entity=Task",
                  "targetObjectLabel": "Code Development",
                  "detail": {
                    "name": "Code Development",
                    "description": "",
                    "startTime": "2023-12-13T08:30:00Z",
                    "endTime": "2023-12-15T17:30:00Z",
                    "temporalType": "DATE_TIME",
                    "progress": 40,
                    "computeStartEndDynamically": false,
                    "collapsed": false
                  },
                  "taskDependencyIds": [
                    "97c8a735-89c2-3e8c-bb04-851957283dc8"
                  ],
                  "subTasks": [
                    {
                      "id": "70f529c2-857f-31fe-9aec-7c010a376d3e",
                      "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9f7d9111-74ed-3e66-9323-84442384a92f",
                      "targetObjectId": "c8444965-b891-4cbb-bc3c-814daee5c67d",
                      "targetObjectKind": "siriusComponents://semantic?domain=peppermm&entity=Task",
                      "targetObjectLabel": "Front",
                      "detail": {
                        "name": "Front",
                        "description": "",
                        "startTime": "2023-12-14T11:00:00Z",
                        "endTime": "2023-12-15T20:00:00Z",
                        "temporalType": "DATE_TIME",
                        "progress": 30,
                        "computeStartEndDynamically": false,
                        "collapsed": false
                      },
                      "taskDependencyIds": [
                        "85624e0e-20ac-31ac-9c62-ef7f4618adca"
                      ],
                      "subTasks": []
                    }
                  ]
                }
              ]
            }
          ],
          "columns": [
            {
              "id": "NAME",
              "isDisplayed": true,
              "width": 210
            },
            {
              "id": "START_DATE",
              "isDisplayed": true,
              "width": 130
            },
            {
              "id": "END_DATE",
              "isDisplayed": true,
              "width": 130
            },
            {
              "id": "PROGRESS",
              "isDisplayed": true,
              "width": 40
            }
          ],
          "dateRounding": {
            "value": 12,
            "timeUnit": "HOUR"
          },
          "kind": "siriusComponents://representation?type=Gantt"
        }',
     'none',
     '0',
     '2026-03-09 11:33:28.326843+01',
     '2026-03-09 11:33:39.603878+01',
     'c67f0c59-6e51-4aac-b771-e267c74a98ea',
     '9563600d-eeea-4040-85a1-5c02f959b430');
