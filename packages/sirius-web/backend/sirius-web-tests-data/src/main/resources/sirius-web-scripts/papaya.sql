-- Sample Papaya project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  'Papaya Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  'siriusComponents://nature?kind=papaya'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '315c90b5-51a8-4f88-a8eb-ffa08c9b8045',
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'https://www.eclipse.org/sirius-web/papaya'
);
INSERT INTO semantic_data_dependency (
  semantic_data_id,
  dependency_semantic_data_id,
  index
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  '6f24a044-1605-484d-96c3-553ff6bc184d',
  0
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'a4495c9c-d00c-4f0e-a591-1176d102a4a1',
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'Sirius Web Architecture',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "papaya":"https://www.eclipse.org/sirius-web/papaya"
     },
     "content":[
       {
         "id":"aa0b7b22-ade2-4148-9ee2-c5972bd72ab7",
         "eClass":"papaya:Project",
         "data":{
           "components":[
             {
               "id":"fad0f4c9-e668-44f3-8deb-aef0edb6ddff",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web-domain",
                 "packages": [
                   {
                     "id": "569d3f9b-2a43-4254-b609-511258251d96",
                     "eClass": "papaya:Package",
                     "data": {
                       "name": "services",
                       "types": [
                         {
                           "id": "c715807f-73f6-44fb-b17b-df6d12558458",
                           "eClass": "papaya:Class",
                           "data": {
                             "name": "Success"
                           }
                         },
                         {
                           "id": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                           "eClass": "papaya:Class",
                           "data": {
                             "name": "Failure",
                             "operations": [
                               {
                                 "eClass": "papaya:Operation",
                                 "id": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                                 "data": {
                                   "name": "fooOperation",
                                   "parameters": [
                                     {
                                       "eClass": "papaya:Parameter",
                                       "id": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                                       "data": {
                                           "name": "fooParameter"
                                       }
                                     }
                                   ]
                                 }
                               }
                             ]
                           }
                         },
                         {
                          "id": "0e18f8e9-c5e3-4ccc-afe6-c937478f78ad",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "AbstractTest",
                            "annotations":[
                              "papaya:Annotation 27d8bea1-c595-4616-9208-a97218ad2316#//@components.0/@packages.0/@types.0"
                            ]
                          }
                        }
                       ]
                     }
                   }
                 ]
               }
             },
             {
               "id":"13e0b82e-3d24-403a-bfc1-4bda81846e55",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web-application",
                 "dependencies":[
                   "//@components.0"
                 ]
               }
             },
             {
               "id":"5c313fbf-d254-4a37-962b-7817cfa18526",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web-infrastructure"
               }
             },
             {
               "id":"e462e8ac-39d3-4ab2-b20f-ea7f0a0283d6",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web-starter"
               }
             },
             {
               "id":"92221ad3-a0b5-4774-b941-87cda3edb772",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web"
               }
             }
           ]
         }
       }
     ]
   }',
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
  '8139fdb7-bb71-4bca-b50b-9170870bbc0d',
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'Sirius Web Architecture',
  '{
  "json": { "version": "1.0", "encoding": "utf-8" },
  "ns": { "papaya": "https://www.eclipse.org/sirius-web/papaya" },
  "content": [
   {
    "id": "41dcf367-1e71-4548-a309-bc5277fea177",
    "eClass": "papaya:Project",
    "data": {
      "name": "Sirius Web",
      "applicationConcerns": [
        {
          "id": "df75f516-eb20-4844-b94d-cdff0ad0b2ac",
          "eClass": "papaya:ApplicationConcern",
          "data": {
            "name": "Project Creation",
            "controllers": [
              {
                "id": "9966a703-72c9-4029-80c2-31ad73e719b9",
                "eClass": "papaya:Controller",
                "data": {
                  "name": "Controller1",
                  "publications": [
                    {
                      "id": "2e91f8ff-1d09-4ce9-b221-9a52b8237b4e",
                      "eClass": "papaya:Publication",
                      "data": {
                        "channel": "//@channels.0",
                        "message": "//@applicationConcerns.0/@commands.0"
                      }
                    }
                  ]
                }
              },
              {
                "id": "66b09642-f962-4174-8f61-3a43dafead51",
                "eClass": "papaya:Controller",
                "data": {
                  "name": "Controller2",
                  "subscriptions": [
                    {
                      "id": "94612afc-33c9-41c8-aa9e-f439aea6cfe7",
                      "eClass": "papaya:Subscription",
                      "data": {
                        "message": "//@applicationConcerns.0/@commands.1"
                      }
                    }
                  ]
                }
              }
            ],
            "commands": [
              {
                "id": "1fa2194c-0fb2-4e34-9dd6-66b2f42d4e56",
                "eClass": "papaya:Command",
                "data": { "name": "Command1" }
              },
              {
                "id": "9a8c3df5-de15-4d3c-b369-d7c87c8be032",
                "eClass": "papaya:Command",
                "data": { "name": "Command2" }
              }
            ]
          }
         }
        ],
        "channels": [
          {
            "id": "e7fc138e-1b76-414d-86ca-a7980af6b88a",
            "eClass": "papaya:Channel",
            "data": { "name": "HTTP" }
          },
          {
            "id": "988e3b7e-8361-43d6-bf31-2564e4b2351f",
            "eClass": "papaya:Channel",
            "data": { "name": "HTTPS" }
          }
        ]
      }
     }
    ]
   }',
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
  'a0473b31-912f-4d99-8b41-3d44a8a1b238',
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'Sirius Web Project',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "papaya":"https://www.eclipse.org/sirius-web/papaya"
     },
     "content":[
       {
         "id":"1135f77e-0d16-4b10-8a4b-c492ac80221f",
         "eClass":"papaya:Project",
         "data":{
           "name":"Sirius Web",
           "iterations":[
             {
               "id":"8a8e1113-b7ce-4549-a80d-91349879e3d8",
               "eClass":"papaya:Iteration",
               "data":{
                 "name":"2024.3.0",
                 "startDate":"2023-12-11T09:00:00z",
                 "endDate":"2024-02-02T18:00:00z",
                 "tasks":[
                   "//@tasks.4",
                   "//@tasks.5",
                   "//@tasks.6"
                 ]
               }
             },
             {
               "id":"87a8edac-9b4e-40a4-9a5c-672602bf6792",
               "eClass":"papaya:Iteration",
               "data":{
                 "name":"2024.5.0",
                 "startDate":"2024-02-05T09:00:00z",
                 "endDate":"2024-03-29T18:00:00z"
               }
             }
           ],
           "tasks":[
             {
               "id":"31395bb3-1e09-42a5-b450-034955c45ac2",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Improve some features of the diagram",
                 "priority":"P1",
                 "cost":1,
                 "done":true
               }
             },
             {
               "id":"e6e8f081-27f5-40e3-a8ab-1e6f0f13df12",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Improve the code of the new architecture",
                 "priority":"P1",
                 "cost":5,
                 "done":true
               }
             },
             {
               "id":"087a700e-c509-458d-9634-5b4132ce10e3",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Add additional tests",
                 "priority":"P2"
               }
             },
             {
               "id":"e1c5bd66-54c2-45f1-ae3a-99d3f039affd",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Update the documentation",
                 "priority":"P3"
               }
             },
             {
               "id":"63bdd245-a042-4266-8229-99718e3b4d09",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Improve some features of the deck",
                 "startDate":"2023-12-11T09:00:00z",
                 "endDate":"2023-12-15T09:00:00z",
                 "tasks":[
                   {
                     "id":"82473bec-3402-4b8c-9030-6551496fa521",
                     "eClass":"papaya:Task",
                     "data":{
                       "name":"Support card drag and drop",
                       "startDate":"2023-12-11T09:00:00z",
                       "endDate":"2023-12-13T09:00:00z"
                     }
                   },
                   {
                     "id":"9832677f-1bb4-4d77-a663-27dfb0554fe9",
                     "eClass":"papaya:Task",
                     "data":{
                       "name":"Support hide/show card",
                       "startDate":"2023-12-13T09:00:00z",
                       "endDate":"2023-12-15T09:00:00z"
                     }
                   }
                 ]
               }
             },
             {
               "id":"49ab243f-c4c8-4234-8873-524568364ed2",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Improve some features of the gantt",
                 "startDate":"2023-12-15T09:00:00z",
                 "endDate":"2023-12-20T09:00:00z"
               }
             },
             {
               "id":"57bd9e84-d1c5-4b24-9b71-8ff6ed87fcb0",
               "eClass":"papaya:Task",
               "data":{
                 "name":"Improve some features of the portal",
                 "startDate":"2023-12-20T09:00:00z",
                 "endDate":"2023-12-25T09:00:00z"
               }
             }
           ],
           "contributions":[
             {
               "id":"25ba0aea-74c0-4c2c-8b2a-beb3d53a5abc",
               "eClass":"papaya:Contribution",
               "data":{
                 "name":"Contribute improvements to the diagrams",
                 "done":true
               }
             },
             {
               "id":"2dd4563a-e1e2-4c23-9e7d-b327c565624c",
               "eClass":"papaya:Contribution",
               "data":{
                 "name":"Contribute improvements to the new architecture",
                 "done":true
               }
             },
             {
               "id":"fbf744b6-6c9b-4c8a-a959-ed90e793c9b7",
               "eClass":"papaya:Contribution",
               "data":{
                 "name":"Contribute additional tests"
               }
             }
           ]
         }
       }
     ]
   }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_metadata (
    id,
    semantic_data_id,
    target_object_id,
    description_id,
    label,
    documentation,
    kind,
    created_on,
    last_modified_on
) VALUES (
    'dd0080f8-430d-441f-99a4-f46c7d9b28ef',
    'cc89c500-c27e-4968-9c67-15cf767c6ef0',
    '569d3f9b-2a43-4254-b609-511258251d96',
    'papaya_package_table_description',
    '',
    'test', 'siriusComponents://representation?type=Table',
    '2024-11-29 14:36:25.346642 +00:00',
    '2024-11-29 14:36:35.460101 +00:00'
);

INSERT INTO representation_content (
    id,
    content,
    last_migration_performed,
    migration_version,
    created_on,
    last_modified_on
) VALUES (
    'dd0080f8-430d-441f-99a4-f46c7d9b28ef',
    '{
         "id": "dd0080f8-430d-441f-99a4-f46c7d9b28ef",
         "kind": "siriusComponents://representation?type=Table",
         "targetObjectId": "569d3f9b-2a43-4254-b609-511258251d96",
         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Package",
         "descriptionId": "papaya_package_table_description",
         "lines": [
             {
                 "id": "0c5b553f-2a78-3071-9c06-f176b134f764",
                 "targetObjectId": "c715807f-73f6-44fb-b17b-df6d12558458",
                 "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                 "descriptionId": "6c9154c7-a924-3bd1-b5e4-28aff1d4e5c8",
                 "cells": [
                     {
                         "id": "b455e4d3-1622-384c-9e56-4b0bf4fc2b7e",
                         "targetObjectId": "c715807f-73f6-44fb-b17b-df6d12558458",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "d0fd98f3-dfae-3a2d-9cbe-f9a1d6ebee56",
                         "value": "Success",
                         "type": "TEXTFIELD"
                     },
                     {
                         "id": "870da07e-b989-3dea-8ba0-762ad2a3a857",
                         "targetObjectId": "c715807f-73f6-44fb-b17b-df6d12558458",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "b6e82202-fe33-3b19-bf2c-5c9648cb96ce",
                         "value": "",
                         "type": "TEXTFIELD"
                     },
                     {
                         "id": "56d61ed4-9fd9-3155-83d8-e9ba2a7a6d6d",
                         "targetObjectId": "c715807f-73f6-44fb-b17b-df6d12558458",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                         "options": [
                             {
                                 "id": "PUBLIC",
                                 "label": "PUBLIC"
                             },
                             {
                                 "id": "PROTECTED",
                                 "label": "PROTECTED"
                             },
                             {
                                 "id": "PACKAGE",
                                 "label": "PACKAGE"
                             },
                             {
                                 "id": "PRIVATE",
                                 "label": "PRIVATE"
                             }
                         ],
                         "value": "PUBLIC",
                         "type": "SELECT"
                     },
                     {
                         "id": "b2804d04-40c9-3920-8e0b-49ff5e29d84a",
                         "targetObjectId": "c715807f-73f6-44fb-b17b-df6d12558458",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "94277463-12c8-34bc-b69b-99bb5bfb0fc4",
                         "options": [],
                         "values": [],
                         "type": "MULTI_SELECT"
                     }
                 ],
                 "initialHeight": 53,
                 "resizable": true
             },
             {
                 "id": "e7c3d768-c7fe-32b9-afae-02df39fe7e02",
                 "targetObjectId": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                 "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                 "descriptionId": "6c9154c7-a924-3bd1-b5e4-28aff1d4e5c8",
                 "cells": [
                     {
                         "id": "50d21e4d-b3d1-3c9b-b0b4-a6a152773b49",
                         "targetObjectId": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "d0fd98f3-dfae-3a2d-9cbe-f9a1d6ebee56",
                         "value": "Failure",
                         "type": "TEXTFIELD"
                     },
                     {
                         "id": "a5682a49-f9ac-379c-b395-930db0003fd7",
                         "targetObjectId": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "b6e82202-fe33-3b19-bf2c-5c9648cb96ce",
                         "value": "",
                         "type": "TEXTFIELD"
                     },
                     {
                         "id": "dbcdb2ee-0e58-37f2-81dc-ba9d864438a4",
                         "targetObjectId": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                         "options": [
                             {
                                 "id": "PUBLIC",
                                 "label": "PUBLIC"
                             },
                             {
                                 "id": "PROTECTED",
                                 "label": "PROTECTED"
                             },
                             {
                                 "id": "PACKAGE",
                                 "label": "PACKAGE"
                             },
                             {
                                 "id": "PRIVATE",
                                 "label": "PRIVATE"
                             }
                         ],
                         "value": "PUBLIC",
                         "type": "SELECT"
                     },
                     {
                         "id": "604237ba-1776-3065-8ea2-57ff06279629",
                         "targetObjectId": "b0f27d20-4705-40a7-9d28-67d605b5e9d1",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Class",
                         "columnId": "94277463-12c8-34bc-b69b-99bb5bfb0fc4",
                         "options": [],
                         "values": [],
                         "type": "MULTI_SELECT"
                     }
                 ],
                 "initialHeight": 53,
                 "height":100,
                 "resizable": true
             },
             {
                 "cells": [
                    {
                        "columnId": "d0fd98f3-dfae-3a2d-9cbe-f9a1d6ebee56",
                        "id": "4bf6dc01-4da7-330e-a7f6-18c9789408d7",
                        "targetObjectId": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                        "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Operation",
                        "type": "TEXTFIELD",
                        "value": "barOperation"
                    },
                    {
                        "columnId": "b6e82202-fe33-3b19-bf2c-5c9648cb96ce",
                        "id": "8b566c5e-6877-3f35-91dc-dd27f3ead414",
                        "targetObjectId": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                        "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Operation",
                        "type": "TEXTAREA",
                        "value": ""
                    },
                    {
                        "columnId": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                        "id": "c4dafa63-2bf8-3e8c-bdc0-fe8f93f70ca2",
                        "options": [
                            {
                              "id": "PUBLIC",
                              "label": "PUBLIC"
                            },
                            {
                              "id": "PROTECTED",
                              "label": "PROTECTED"
                            },
                            {
                              "id": "PACKAGE",
                              "label": "PACKAGE"
                            },
                            {
                              "id": "PRIVATE",
                              "label": "PRIVATE"
                            }
                        ],
                        "targetObjectId": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                        "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Operation",
                        "type": "SELECT",
                        "value": ""
                    },
                    {
                      "columnId": "94277463-12c8-34bc-b69b-99bb5bfb0fc4",
                      "id": "580e07af-51cd-39f3-bffd-272ad274dcbd",
                      "options": [
                      ],
                      "targetObjectId": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                      "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Operation",
                      "type": "MULTI_SELECT",
                      "values": [
                      ]
                    }
                ],
                 "id": "40ffe0b3-3fb4-35f1-b2bd-3634679398ad",
                 "targetObjectId": "6f531172-8314-4145-8b36-d8fa45bf3b20",
                 "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Operation",
                 "descriptionId": "6c9154c7-a924-3bd1-b5e4-28aff1d4e5c8",
                 "depthLevel": 1,
                 "initialHeight": 53,
                 "height": 53,
                 "resizable": true
             },
             {
                 "id": "7127b7a2-50b0-3b39-8fcd-ad2910856e0a",
                 "targetObjectId": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                 "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Parameter",
                 "cells": [
                     {
                         "columnId": "d0fd98f3-dfae-3a2d-9cbe-f9a1d6ebee56",
                         "id": "40c134b9-3969-398e-85fc-573ae8b18c05",
                         "targetObjectId": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Parameter",
                         "type": "TEXTFIELD",
                         "value": "fooParameter"
                     },
                     {
                         "columnId": "b6e82202-fe33-3b19-bf2c-5c9648cb96ce",
                         "id": "f672ab49-a2d8-38d8-9600-60f736642cff",
                         "targetObjectId": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Parameter",
                         "type": "TEXTAREA",
                         "value": ""
                     },
                     {
                         "columnId": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                         "id": "5404877a-fe46-3e00-ad3d-1dfe476a714f",
                         "options": [
                             {
                                 "id": "PUBLIC",
                                 "label": "PUBLIC"
                             },
                             {
                                 "id": "PROTECTED",
                                 "label": "PROTECTED"
                             },
                             {
                                 "id": "PACKAGE",
                                 "label": "PACKAGE"
                             },
                             {
                                 "id": "PRIVATE",
                                 "label": "PRIVATE"
                             }
                         ],
                         "targetObjectId": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Parameter",
                         "type": "SELECT",
                         "value": ""
                     },
                     {
                         "columnId": "94277463-12c8-34bc-b69b-99bb5bfb0fc4",
                         "id": "7b212e76-3220-3235-9f25-df8724633bfc",
                         "options": [],
                         "targetObjectId": "69ead9da-9302-45a7-86d8-c4ad54056e39",
                         "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Parameter",
                         "type": "MULTI_SELECT",
                         "values": []
                     }
                 ],
                 "depthLevel": 2,
                 "descriptionId": "6c9154c7-a924-3bd1-b5e4-28aff1d4e5c8",
                 "headerLabel": "fooParameter",
                 "height": 53,
                 "resizable": true
             }
         ],
         "columns": [
             {
                 "id": "d0fd98f3-dfae-3a2d-9cbe-f9a1d6ebee56",
                 "targetObjectId": "papaya.NamedElement#name",
                 "targetObjectKind": "",
                 "descriptionId": "9f034b63-9487-33d4-89f9-44319c2eb3de",
                 "label": "Name"
             },
             {
                 "id": "b6e82202-fe33-3b19-bf2c-5c9648cb96ce",
                 "targetObjectId": "papaya.NamedElement#description",
                 "targetObjectKind": "",
                 "descriptionId": "9f034b63-9487-33d4-89f9-44319c2eb3de",
                 "label": "Description"
             },
             {
                 "id": "94277463-12c8-34bc-b69b-99bb5bfb0fc4",
                 "targetObjectId": "papaya.AnnotableElement#annotations",
                 "targetObjectKind": "",
                 "descriptionId": "9f034b63-9487-33d4-89f9-44319c2eb3de",
                 "label": "Annotations"
             },
             {
                 "id": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                 "targetObjectId": "papaya.Type#visibility",
                 "targetObjectKind": "",
                 "descriptionId": "9f034b63-9487-33d4-89f9-44319c2eb3de",
                 "label": "Visibility"
             }
         ],
         "globalFilter": "PUB",
         "columnFilters": [
            {
                "id": "eabb569d-f99e-3e6e-86a6-db600b1fa526",
                "value": "LIC"
            }
        ],
        "columnSort": []
     }',
     'none',
     '0',
     '2024-11-29 14:36:25.905630 +00:00',
     '2024-11-29 14:36:25.905630 +00:00'
);
