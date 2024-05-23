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
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
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
                 "name":"sirius-web-domain"
               }
             },
             {
               "id":"13e0b82e-3d24-403a-bfc1-4bda81846e55",
               "eClass":"papaya:Component",
               "data":{
                 "name":"sirius-web-application"
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