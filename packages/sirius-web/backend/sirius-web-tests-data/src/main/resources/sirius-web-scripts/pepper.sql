-- Sample Pepper Project

INSERT INTO project (id,name,created_on,last_modified_on) VALUES
    ('a6cf3fee-18e1-4f56-9c96-4511ea3d95db',
     'Task Sample',
     '2026-03-19 10:59:20.810292+01',
     '2026-03-19 10:59:20.810292+01');

INSERT INTO nature (project_id,name) VALUES
    ('a6cf3fee-18e1-4f56-9c96-4511ea3d95db',
     'siriusWeb://nature?kind=task');

INSERT INTO semantic_data (id,created_on,last_modified_on) VALUES
    ('d98557ca-e6fa-4943-ac47-99135429ec34',
     '2026-03-19 10:59:20.886809+01',
     '2026-03-19 11:16:32.341391+01');

INSERT INTO project_semantic_data (id,project_id,semantic_data_id,name,created_on,last_modified_on) VALUES
    ('43f397b3-2deb-4de9-9de5-90436afcf0e8',
     'a6cf3fee-18e1-4f56-9c96-4511ea3d95db',
     'd98557ca-e6fa-4943-ac47-99135429ec34',
     'main',
     '2026-03-19 10:59:21.07628+01',
     '2026-03-19 10:59:21.07628+01');

INSERT INTO semantic_data_domain (semantic_data_id,uri) VALUES
    ('d98557ca-e6fa-4943-ac47-99135429ec34',
     'http://peppermm');

INSERT INTO semantic_data_dependency (semantic_data_id,dependency_semantic_data_id,index) VALUES
    ('d98557ca-e6fa-4943-ac47-99135429ec34',
     '6f24a044-1605-484d-96c3-553ff6bc184d',
     0);

INSERT INTO document (id,semantic_data_id,name,content,created_on,last_modified_on,is_read_only) VALUES
    ('d11614ec-04f7-40bf-9d4f-16162c7b55d7',
     'd98557ca-e6fa-4943-ac47-99135429ec34',
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
        "id": "3eb378ca-6303-4db4-b758-55bddb328438",
        "eClass": "peppermm:Organization",
        "data": {
            "ownedProjects": [
            {
                "id": "9bd5554c-b46b-48c5-a16b-9badbedfd088",
                "eClass": "peppermm:Project",
                "data": {
                "name": "Project Dev",
                "ownedWorkpackages": [
                    {
                    "id": "ad4fed5b-f8ec-4a69-9ada-a369f17c557b",
                    "eClass": "peppermm:Workpackage",
                    "data": {
                        "name": "Main workpackage",
                        "ownedTasks": [
                        {
                            "id": "5e1b80ee-5dde-43c0-9093-4be2d2937de4",
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
                            "id": "f91beca3-4204-4a6e-943f-ba0545491eb5",
                            "eClass": "peppermm:Task",
                            "data": {
                            "name": "Development",
                            "description": "",
                            "startTime": "2023-12-11T23:00:00Z",
                            "endTime": "2023-12-14T11:00:00Z",
                            "subTasks": [
                                {
                                "id": "33c7ce2b-e496-47c1-a03b-283264db112d",
                                "eClass": "peppermm:Task",
                                "data": {
                                    "name": "Code Development",
                                    "startTime": "2023-12-13T08:30:00Z",
                                    "endTime": "2023-12-15T17:30:00Z",
                                    "progress": 40,
                                    "assignedPersons": [
                                    "8d9ee3a9-4575-416b-b251-f29dcb75e080",
                                    "4feea17c-297b-40e8-9e08-873c03912417"
                                    ],
                                    "subTasks": [
                                    {
                                        "id": "0b3f1b2f-4e93-4f9b-9cb3-0e293d1cad11",
                                        "eClass": "peppermm:Task",
                                        "data": {
                                        "name": "Front",
                                        "description": "",
                                        "startTime": "2023-12-14T11:00:00Z",
                                        "endTime": "2023-12-15T20:00:00Z",
                                        "progress": 30,
                                        "assignedPersons": [
                                            "8d9ee3a9-4575-416b-b251-f29dcb75e080"
                                        ],
                                        "dependencies": [
                                            {
                                            "id": "e40e6de9-923c-42d3-9109-d59b958f01b2",
                                            "eClass": "peppermm:DependencyLink",
                                            "data": {
                                                "dependency": "33c7ce2b-e496-47c1-a03b-283264db112d"
                                            }
                                            }
                                        ]
                                        }
                                    }
                                    ],
                                    "dependencies": [
                                    {
                                        "id": "fcb8e8b9-3379-41ed-8ff2-b71bbdde389b",
                                        "eClass": "peppermm:DependencyLink",
                                        "data": {
                                        "dependency": "f91beca3-4204-4a6e-943f-ba0545491eb5"
                                        }
                                    }
                                    ]
                                }
                                }
                            ],
                            "dependencies": []
                            }
                        }
                        ]
                    }
                    }
                ]
                }
            }
            ],
            "ownedResourceFolders": [
            {
                "id": "a7d17e77-813c-4c37-8dfb-e0a31e19c049",
                "eClass": "peppermm:ResourceFolder",
                "data": {
                "name": "Resources",
                "ownedResources": [
                    {
                    "id": "4feea17c-297b-40e8-9e08-873c03912417",
                    "eClass": "peppermm:Person",
                    "data": {
                        "name": "Paul"
                    }
                    },
                    {
                    "id": "8d9ee3a9-4575-416b-b251-f29dcb75e080",
                    "eClass": "peppermm:Person",
                    "data": {
                        "name": "Peter"
                    }
                    },
                    {
                    "id": "dc0c0e71-f88b-4d94-b14c-e9d6e2969f22",
                    "eClass": "peppermm:Team",
                    "data": {
                        "name": "Development Team",
                        "members": [
                        "4feea17c-297b-40e8-9e08-873c03912417",
                        "8d9ee3a9-4575-416b-b251-f29dcb75e080"
                        ]
                    }
                    },
                    {
                    "id": "9cd72180-98db-4d47-9c5e-133c01a87b0c",
                    "eClass": "peppermm:InternalStakeholder",
                    "data": {
                        "name": "Indus Department"
                    }
                    },
                    {
                    "id": "8e7d2b2f-8be3-4eae-a65b-7fc8433cc561",
                    "eClass": "peppermm:InternalStakeholder",
                    "data": {
                        "name": "Dev Department"
                    }
                    },
                    {
                    "id": "643a6035-ee64-4203-b0e7-49b8cb359004",
                    "eClass": "peppermm:ExternalStakeholder",
                    "data": {
                        "name": "Customer 1"
                    }
                    },
                    {
                    "id": "74cd313d-f046-4f0d-9b95-0470f41b5d34",
                    "eClass": "peppermm:ExternalStakeholder",
                    "data": {
                        "name": "Customer 2"
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
     '2026-03-19 11:16:32.341391+01',
     '2026-03-19 11:16:32.341391+01',
     false);


INSERT INTO representation_metadata (id,target_object_id,description_id,label,kind,created_on,last_modified_on,documentation,semantic_data_id,representation_metadata_id) VALUES
    ('d98557ca-e6fa-4943-ac47-99135429ec34#e16db0ec-22f6-4866-8f36-f82be75cca16',
     'ad4fed5b-f8ec-4a69-9ada-a369f17c557b',
     'siriusComponents://representationDescription?kind=ganttDescription&sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=1035a9dc-fd08-39e1-b02c-99740626f9e6',
     'New Gantt',
     'siriusComponents://representation?type=Gantt',
     '2026-03-19 10:59:31.198872+01',
     '2026-03-19 10:59:31.198872+01',
     '',
     'd98557ca-e6fa-4943-ac47-99135429ec34',
     'e16db0ec-22f6-4866-8f36-f82be75cca16');

INSERT INTO representation_content (id,content,last_migration_performed,migration_version,created_on,last_modified_on,semantic_data_id,representation_metadata_id) VALUES
    ('d98557ca-e6fa-4943-ac47-99135429ec34#e16db0ec-22f6-4866-8f36-f82be75cca16',
     '{
    "id": "e16db0ec-22f6-4866-8f36-f82be75cca16",
    "descriptionId": "siriusComponents://representationDescription?kind=ganttDescription&sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=1035a9dc-fd08-39e1-b02c-99740626f9e6",
    "targetObjectId": "ad4fed5b-f8ec-4a69-9ada-a369f17c557b",
    "tasks": [
        {
        "id": "ee6bd4d4-6f8a-31b0-a4f3-c9de7dffc023",
        "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9bd2e8ba-7d40-3430-b5a6-866dd2040494",
        "targetObjectId": "5e1b80ee-5dde-43c0-9093-4be2d2937de4",
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
        "dependencyLinks": [],
        "subTasks": []
        },
        {
        "id": "e62ed459-8263-35eb-a04d-d99e23346387",
        "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9bd2e8ba-7d40-3430-b5a6-866dd2040494",
        "targetObjectId": "f91beca3-4204-4a6e-943f-ba0545491eb5",
        "targetObjectKind": "siriusComponents://semantic?domain=peppermm&entity=Task",
        "targetObjectLabel": "Development",
        "detail": {
            "name": "Development",
            "description": "",
            "startTime": "2023-12-11T23:00:00Z",
            "endTime": "2023-12-14T11:00:00Z",
            "temporalType": "DATE_TIME",
            "progress": 0,
            "computeStartEndDynamically": false,
            "collapsed": false
        },
        "dependencyLinks": [],
        "subTasks": [
            {
            "id": "67e0ef97-9fc5-351e-8445-182cdab5bfd3",
            "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9f7d9111-74ed-3e66-9323-84442384a92f",
            "targetObjectId": "33c7ce2b-e496-47c1-a03b-283264db112d",
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
            "dependencyLinks": [
                {
                "source": "END",
                "target": "START",
                "sourceDependencyId": "e62ed459-8263-35eb-a04d-d99e23346387",
                "delay": 0
                }
            ],
            "subTasks": [
                {
                "id": "a1510ce5-9b33-3b3b-8013-175add0eee0f",
                "descriptionId": "siriusComponents://representationDescription?kind=taskDescription?sourceKind=view&sourceId=ae84ea26-3206-30ff-a644-3cec19715105&sourceElementId=9f7d9111-74ed-3e66-9323-84442384a92f",
                "targetObjectId": "0b3f1b2f-4e93-4f9b-9cb3-0e293d1cad11",
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
                "dependencyLinks": [
                    {
                    "source": "END",
                    "target": "START",
                    "sourceDependencyId": "67e0ef97-9fc5-351e-8445-182cdab5bfd3",
                    "delay": 0
                    }
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
     '2026-03-19 10:59:31.296073+01',
     '2026-03-19 11:16:32.216518+01',
     'd98557ca-e6fa-4943-ac47-99135429ec34',
     'e16db0ec-22f6-4866-8f36-f82be75cca16');


