INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '5b7cb887-b38a-4424-9508-ea7aa869ba6f',
  '2025-02-12 10:25:11.267',
  '2025-02-12 10:25:11.267'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '5b7cb887-b38a-4424-9508-ea7aa869ba6f',
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
  'ad5f432f-e16f-338a-8755-91861b827953',
  '5b7cb887-b38a-4424-9508-ea7aa869ba6f',
  'Java Standard Library',
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "papaya":"https://www.eclipse.org/sirius-web/papaya"
    },"content":[
      {
        "id":"a463e723-5748-4817-92d8-12d7e6ef967d",
        "eClass":"papaya:Package",
        "data":{
          "name":"java.lang",
          "types":[
            {
              "id":"f15f11fc-fb6e-47a1-bb48-c23d1088a70e",
              "eClass":"papaya:DataType",
              "data":{
                "name":"void"
              }
            },
            {
              "id":"af5bb2a3-9eda-431b-91ae-c74006d2a9b6",
              "eClass":"papaya:DataType",
              "data":{
                "name":"byte"
              }
            },{
              "id":"d4b9859d-9f71-4361-9774-a70ff6d58a50",
              "eClass":"papaya:DataType",
              "data":{
                "name":"short"
              }
            },{
              "id":"2ab0ebfa-9fcf-47ff-b990-dc7fc9f404fa",
              "eClass":"papaya:DataType",
              "data":{
                "name":"int"
              }
            },{
              "id":"f1d6f8d0-ec0f-40d4-836e-3db06e1969be",
              "eClass":"papaya:DataType",
              "data":{
                "name":"long"
              }
            },{
              "id":"0f16dbfa-b002-40fe-a2ee-7a498ed3628b",
              "eClass":"papaya:DataType",
              "data":{
                "name":"float"
              }
            },{
              "id":"77dd974a-ff68-4590-a8ad-7b70c9b6b4d6",
              "eClass":"papaya:DataType",
              "data":{
                "name":"double"
              }
            },{
              "id":"9b0b46aa-1016-45fa-b812-88e1b69c0c5b",
              "eClass":"papaya:DataType",
              "data":{
                "name":"boolean"
              }
            },{
              "id":"7d969450-78a0-4420-b547-6ed9e20e387e",
              "eClass":"papaya:DataType",
              "data":{
                "name":"char"
              }
            },{
              "id":"b89d5bf9-59cf-4b89-9723-6790bda4a245",
              "eClass":"papaya:Class",
              "data":{
                "name":"Object"
              }
            },{
              "id":"61f711a1-6e3c-4917-8608-ab037e842598",
              "eClass":"papaya:Class",
              "data":{
                "name":"String"
              }
            },{
              "id":"80d84958-ffcf-40cc-9435-699b203b4d69",
              "eClass":"papaya:Class",
              "data":{
                "name":"Void"
              }
            },{
              "id":"2e8684a9-542e-4a7a-879c-b3e719950af2",
              "eClass":"papaya:Class",
              "data":{
                "name":"Byte"
              }
            },{
              "id":"37072fae-d9fe-44ab-b3bf-6b6d3a956644",
              "eClass":"papaya:Class",
              "data":{
                "name":"Short"
              }
            },{
              "id":"be70391b-b726-406a-8b57-cfb69ca16628",
              "eClass":"papaya:Class",
              "data":{
                "name":"Integer"
              }
            },{
              "id":"f52a9f0a-7679-415a-bb0c-57ceb95fb7aa",
              "eClass":"papaya:Class",
              "data":{
                "name":"Long"
              }
            },{
              "id":"ac704a7c-8d24-40a6-9003-eeb369203ed7",
              "eClass":"papaya:Class",
              "data":{
                "name":"Float"
              }
            },{
              "id":"accbfdce-3f87-4b15-8b57-54b237a83989",
              "eClass":"papaya:Class",
              "data":{
                "name":"Double"
              }
            },{
              "id":"83dbbd9d-96d8-447e-8d0c-0fb0f21c0542",
              "eClass":"papaya:Class",
              "data":{
                "name":"Boolean"
              }
            },{
              "id":"03948292-f6ee-42dc-a5e9-62786607de08",
              "eClass":"papaya:Class",
              "data":{
                "name":"Character"
              }
            },
            {
              "id":"755101b7-ad25-41cd-b89b-7a3da865c7e4",
              "eClass":"papaya:Interface",
              "data":{
                "name":"AutoCloseable"
              }
            },
            {
              "id":"c94e7a4d-5600-4641-bb7f-eca0c5e3e50a",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Cloneable"
              }
            },
            {
              "id":"44e9bf70-c1f9-4f4e-ac70-84106288d8e9",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Comparable",
                "typeParameters":[
                  {
                    "id":"fdbf036f-a0f4-4625-bc4b-fc5542fff8ab",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
                    }
                  }
                ]
              }
            },
            {
              "id":"b3d47762-6a34-4e3c-875a-cb134634a66e",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Iterable",
                "typeParameters":[
                  {
                    "id":"7aa6db21-df77-426c-914f-32a3619626f5",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
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
  '2025-02-12 10:25:11.265977+01',
  '2025-02-12 10:25:11.265977+01'
);

INSERT INTO library (
  id,
  namespace,
  name,
  version,
  semantic_data_id,
  description,
  created_on,
  last_modified_on
) VALUES (
  '221c7352-c371-4c88-9542-8ea015c859e6',
  'papaya',
  'java',
  '1.0.0',
  '5b7cb887-b38a-4424-9508-ea7aa869ba6f',
  'The standard library of the Java programming language',
  '2025-02-12 10:25:11.524',
  '2025-02-12 10:25:11.524'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '7a273947-7b34-48dc-982d-4fac14a259d5',
  '2025-03-12 10:25:11.267',
  '2025-03-12 10:25:11.267'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '7a273947-7b34-48dc-982d-4fac14a259d5',
  'https://www.eclipse.org/sirius-web/papaya'
);

INSERT INTO semantic_data_dependency (
  semantic_data_id,
  dependency_semantic_data_id,
  index
) VALUES (
  '7a273947-7b34-48dc-982d-4fac14a259d5',
  '5b7cb887-b38a-4424-9508-ea7aa869ba6f',
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
  '1700060e-4b20-481b-8301-217c40aaacbd',
  '7a273947-7b34-48dc-982d-4fac14a259d5',
  'Reactive Streams Library',
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "papaya":"https://www.eclipse.org/sirius-web/papaya"
    },"content":[
      {
        "id":"20c243a1-6475-4c29-80cc-5b790ad209d8",
        "eClass":"papaya:Package",
        "data":{
          "name":"org.reactivestreams",
          "types":[
            {
              "id":"084acf02-fbc7-4da4-ae4f-9b2f2a37eab0",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Processor",
                "typeParameters":[
                  {
                    "id":"41dde7be-a49a-4bd2-a622-812be097580b",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
                    }
                  }
                ]
              }
            },
            {
              "id":"79612d73-9057-4dd7-a63a-aff5e570fa54",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Publisher",
                "typeParameters":[
                  {
                    "id":"a554e132-a8dc-4864-b668-91514dcd203c",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
                    }
                  }
                ]
              }
            },
            {
              "id":"aa496ed9-42a1-44e9-9b3d-e6ce76a31203",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Subscriber",
                "typeParameters":[
                  {
                    "id":"bda5b8e6-98cd-4556-93f8-10e525648109",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
                    }
                  }
                ]
              }
            },
            {
              "id":"60936785-ae13-45a7-858c-27ab3fbb56e7",
              "eClass":"papaya:Interface",
              "data":{
                "name":"Subscription",
                "typeParameters":[
                  {
                    "id":"dd16679b-9282-414a-8e79-7099667f7a74",
                    "eClass":"papaya:TypeParameter",
                    "data":{
                      "name":"T"
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
  '2025-02-12 10:25:11.265977+01',
  '2025-02-12 10:25:11.265977+01'
);

INSERT INTO library (
  id,
  namespace,
  name,
  version,
  semantic_data_id,
  description,
  created_on,
  last_modified_on
) VALUES (
  '0ee984b0-7c40-4b63-bcd9-16dc5e97e455',
  'papaya',
  'reactivestreams',
  '1.0.0',
  '7a273947-7b34-48dc-982d-4fac14a259d5',
  'The Reactive Stream library',
  '2025-03-12 10:25:11.524',
  '2025-03-12 10:25:11.524'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '6f24a044-1605-484d-96c3-553ff6bc184d', 
  '2025-03-14 10:58:52.703625+00', 
  '2025-03-14 10:59:22.361539+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '6f24a044-1605-484d-96c3-553ff6bc184d',
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
  '27d8bea1-c595-4616-9208-a97218ad2316', 
  '6f24a044-1605-484d-96c3-553ff6bc184d', 
  'Sirius Web Tests Data', 
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "papaya":"https://www.eclipse.org/sirius-web/papaya"
    },"content":[
      {
        "id": "fd766e2d-dfdf-41b4-b3df-89066ecd975d",
        "eClass": "papaya:Project",
        "data": {
          "name": "backend",
          "elements": [
            {
              "id": "429fb025-f429-4f78-a314-a8502024997a",
              "eClass": "papaya:Component",
              "data": {
                "name": "sirius-web-tests-data",
                "packages": [
                  {
                    "id": "f7804002-16b6-4bac-935a-e2eda2e0a753",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "org.eclipse.sirius.web.tests.data",
                      "types": [
                        {
                          "id": "c5c48959-793b-4264-97d2-890d21dcb940",
                          "eClass": "papaya:Annotation",
                          "data": { "name": "GivenSiriusWebServer" }
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
  '2025-03-14 10:58:52.703625+00', 
  '2025-03-14 10:59:22.361539+00'
);

INSERT INTO library (
  id,
  namespace,
  name,
  version,
  semantic_data_id,
  description,
  created_on,
  last_modified_on
) VALUES (
  '040bcff4-fe92-40d3-8180-41403ffc08bf',
  'papaya',
  'sirius-web-tests-data',
  '1.0.0',
  '6f24a044-1605-484d-96c3-553ff6bc184d',
  'The Sirius Web Tests Data library',
  '2025-03-14 11:25:11.524',
  '2025-03-14 11:25:11.524'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '1c981f51-cb6b-4470-b2fb-c3d474aff651', 
  '2025-03-14 12:58:52.703625+00', 
  '2025-03-14 12:59:22.361539+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '1c981f51-cb6b-4470-b2fb-c3d474aff651',
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
  '27d8bea1-c595-4616-9208-a97218ad2316', 
  '1c981f51-cb6b-4470-b2fb-c3d474aff651', 
  'Sirius Web Tests Data', 
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "papaya":"https://www.eclipse.org/sirius-web/papaya"
    },"content":[
      {
        "id": "fd766e2d-dfdf-41b4-b3df-89066ecd975d",
        "eClass": "papaya:Project",
        "data": {
          "name": "backend",
          "elements": [
            {
              "id": "429fb025-f429-4f78-a314-a8502024997a",
              "eClass": "papaya:Component",
              "data": {
                "name": "sirius-web-tests-data",
                "packages": [
                  {
                    "id": "f7804002-16b6-4bac-935a-e2eda2e0a753",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "org.eclipse.sirius.web.tests.data",
                      "types": [
                        {
                          "id": "c5c48959-793b-4264-97d2-890d21dcb940",
                          "eClass": "papaya:Annotation",
                          "data": { "name": "GivenSiriusWebServer" }
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
  '2025-03-14 12:58:52.703625+00', 
  '2025-03-14 12:59:22.361539+00'
);

INSERT INTO library (
  id,
  namespace,
  name,
  version,
  semantic_data_id,
  description,
  created_on,
  last_modified_on
) VALUES (
  '96721905-d5e1-40fe-ae71-8c44123661f8',
  'papaya',
  'sirius-web-tests-data',
  '2.0.0',
  '1c981f51-cb6b-4470-b2fb-c3d474aff651',
  'The Sirius Web Tests Data library',
  '2025-03-14 12:25:11.524',
  '2025-03-14 12:25:11.524'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '194ba253-70ee-4c09-928f-3541e8a0e906', 
  '2025-07-09 12:00:00.703625+00', 
  '2025-07-09 12:00:00.361539+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '194ba253-70ee-4c09-928f-3541e8a0e906',
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
  '27d8bea1-c595-4616-9208-a97218ad2316', 
  '194ba253-70ee-4c09-928f-3541e8a0e906', 
  'Sirius Web Tests Data', 
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "papaya":"https://www.eclipse.org/sirius-web/papaya"
    },"content":[
      {
        "id": "fd766e2d-dfdf-41b4-b3df-89066ecd975d",
        "eClass": "papaya:Project",
        "data": {
          "name": "backend",
          "elements": [
            {
              "id": "429fb025-f429-4f78-a314-a8502024997a",
              "eClass": "papaya:Component",
              "data": {
                "name": "sirius-web-tests-data",
                "packages": [
                  {
                    "id": "f7804002-16b6-4bac-935a-e2eda2e0a753",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "org.eclipse.sirius.web.tests.data"
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
  '2025-03-14 12:58:52.703625+00', 
  '2025-03-14 12:59:22.361539+00'
);

INSERT INTO library (
  id,
  namespace,
  name,
  version,
  semantic_data_id,
  description,
  created_on,
  last_modified_on
) VALUES (
  'af292062-6a2a-4484-a671-edae354a8a13',
  'papaya',
  'sirius-web-tests-data',
  '3.0.0',
  '194ba253-70ee-4c09-928f-3541e8a0e906',
  'The Sirius Web Tests Data library',
  '2025-07-09 12:00:00.524',
  '2025-07-09 12:00:00.524'
);