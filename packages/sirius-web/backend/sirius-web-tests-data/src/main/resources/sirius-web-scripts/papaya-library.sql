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
  is_read_only,
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
  true,
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
  is_read_only,
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
  true,
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
  is_read_only,
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
  true,
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
  is_read_only,
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
  true,
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
  is_read_only,
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
  true,
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

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '3777ff6f-9206-48c1-a6ed-f3ae04ca03eb',
  '2026-07-09 12:00:00.703625+00',
  '2026-07-09 12:00:00.361539+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '3777ff6f-9206-48c1-a6ed-f3ae04ca03eb',
  'https://www.eclipse.org/sirius-web/papaya'
);

INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  is_read_only,
  created_on,
  last_modified_on
) VALUES (
  'b1f064d3-b928-319d-8853-022b0c5dd63a',
  '3777ff6f-9206-48c1-a6ed-f3ae04ca03eb',
  'Java',
  '{
    "json": { "version": "1.0", "encoding": "utf-8" },
    "ns": { "papaya": "https://www.eclipse.org/sirius-web/papaya" },
    "content": [
      {
        "id": "ee0e4a71-593a-3c25-a5e5-82ce716dd289",
        "eClass": "papaya:Project",
        "data": {
          "name": "Java Standard Library",
          "elements": [
            {
              "id": "fbcd3b85-08ca-30ef-a315-2662b36685f5",
              "eClass": "papaya:Component",
              "data": {
                "name": "java.base",
                "packages": [
                  {
                    "id": "40506bb8-a34c-3067-bce3-0b4af54c4a53",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "java.lang",
                      "types": [
                        {
                          "id": "8f7fb50a-ec3f-3023-81a8-750fd0ad34a2",
                          "eClass": "papaya:DataType",
                          "data": { "name": "void" }
                        },
                        {
                          "id": "f70e512a-e96f-3e4c-89f7-de39c59f6e82",
                          "eClass": "papaya:DataType",
                          "data": { "name": "byte" }
                        },
                        {
                          "id": "1f530a35-0e83-303f-8b49-7c1c75d0f535",
                          "eClass": "papaya:DataType",
                          "data": { "name": "short" }
                        },
                        {
                          "id": "32cd29d5-de07-35bd-9072-aba2dd3500fc",
                          "eClass": "papaya:DataType",
                          "data": { "name": "int" }
                        },
                        {
                          "id": "01a20dda-2603-326f-8127-30a01fe6a58b",
                          "eClass": "papaya:DataType",
                          "data": { "name": "long" }
                        },
                        {
                          "id": "a604374c-6ce4-3d96-85f7-6938d87ea637",
                          "eClass": "papaya:DataType",
                          "data": { "name": "float" }
                        },
                        {
                          "id": "c10b2136-2023-39b7-97c9-cc0fa7c3e404",
                          "eClass": "papaya:DataType",
                          "data": { "name": "double" }
                        },
                        {
                          "id": "a16dbd80-c633-3409-9139-31f566e8fbb2",
                          "eClass": "papaya:DataType",
                          "data": { "name": "boolean" }
                        },
                        {
                          "id": "7f276f5d-517f-39b8-b447-f129c12e1138",
                          "eClass": "papaya:DataType",
                          "data": { "name": "char" }
                        },
                        {
                          "id": "22c40ae6-0c41-3165-a1f6-a5c6fa67911f",
                          "eClass": "papaya:Class",
                          "data": { "name": "Object" }
                        },
                        {
                          "id": "1ae8be53-dd50-398b-be50-67b397f174e4",
                          "eClass": "papaya:Class",
                          "data": { "name": "String" }
                        },
                        {
                          "id": "9bbe32de-6eb0-3bec-a748-5aaa685c1e3d",
                          "eClass": "papaya:Class",
                          "data": { "name": "Void" }
                        },
                        {
                          "id": "5d3da980-a377-3ac2-af98-6ef491129625",
                          "eClass": "papaya:Class",
                          "data": { "name": "Byte" }
                        },
                        {
                          "id": "82913432-bea2-34dd-8494-daf282e8c1bc",
                          "eClass": "papaya:Class",
                          "data": { "name": "Short" }
                        },
                        {
                          "id": "5b0c2a7e-3c61-3c02-82be-49b80ef26eef",
                          "eClass": "papaya:Class",
                          "data": { "name": "Integer" }
                        },
                        {
                          "id": "87a5f484-87f8-33c8-8c08-5776c549bab9",
                          "eClass": "papaya:Class",
                          "data": { "name": "Long" }
                        },
                        {
                          "id": "76f89f89-afcf-3eeb-a69b-047848156376",
                          "eClass": "papaya:Class",
                          "data": { "name": "Float" }
                        },
                        {
                          "id": "b6465bc6-9276-39f3-8f2d-e258a3fba33b",
                          "eClass": "papaya:Class",
                          "data": { "name": "Double" }
                        },
                        {
                          "id": "e470812d-1c25-35b0-91ab-0ab5d0ee8969",
                          "eClass": "papaya:Class",
                          "data": { "name": "Boolean" }
                        },
                        {
                          "id": "398396c3-cd07-3328-9958-ff543110e66f",
                          "eClass": "papaya:Class",
                          "data": { "name": "Character" }
                        },
                        {
                          "id": "ace48fd5-f349-34cb-b1cd-cb26d263f4a7",
                          "eClass": "papaya:Interface",
                          "data": { "name": "AutoCloseable" }
                        },
                        {
                          "id": "21263b4c-48c5-3c37-b5ad-6bdcfba93fbc",
                          "eClass": "papaya:Interface",
                          "data": { "name": "Cloneable" }
                        },
                        {
                          "id": "4673de60-cecc-3db5-9118-4ed77d086d16",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "Comparable",
                            "typeParameters": [
                              {
                                "id": "34307930-6164-367e-9f1f-9f854b947d66",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "T" }
                              }
                            ]
                          }
                        },
                        {
                          "id": "063976c6-8b3c-3844-9538-31f07058880e",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "Iterable",
                            "typeParameters": [
                              {
                                "id": "d38b3363-03f1-324e-99e7-4e8c526db29e",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "T" }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  },
                  {
                    "id": "970f60b5-173c-3deb-976e-9fb06aa0c178",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "java.text",
                      "types": [
                        {
                          "id": "cdd464bc-46fd-3c86-9ceb-51d90b363d15",
                          "eClass": "papaya:Class",
                          "data": { "name": "Format", "abstract": true }
                        },
                        {
                          "id": "9ebf4224-5c40-3843-bac4-be871900a446",
                          "eClass": "papaya:Class",
                          "data": { "name": "MessageFormat" }
                        }
                      ]
                    }
                  },
                  {
                    "id": "fb6bc7a3-af0f-30ff-81ef-ebc956369b47",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "java.io",
                      "types": [
                        {
                          "id": "6c0e7943-49a8-3ab9-8ed5-54a2267a36f3",
                          "eClass": "papaya:Interface",
                          "data": { "name": "Serializable" }
                        },
                        {
                          "id": "469b6986-3691-3526-9422-0c8da444945a",
                          "eClass": "papaya:Interface",
                          "data": { "name": "Closeable" }
                        },
                        {
                          "id": "9fdc1a66-acde-3b96-abc0-0f913a753f57",
                          "eClass": "papaya:Interface",
                          "data": { "name": "Flushable" }
                        },
                        {
                          "id": "9a0b2931-0545-3c3c-a8d8-1f7f94903e11",
                          "eClass": "papaya:Class",
                          "data": { "name": "InputStream", "abstract": true }
                        },
                        {
                          "id": "88e5258e-ef48-3c0e-a388-9b72d91cdbc3",
                          "eClass": "papaya:Class",
                          "data": { "name": "OutputStream", "abstract": true }
                        },
                        {
                          "id": "48a89a4c-c693-3600-8ca7-11cd1df725e7",
                          "eClass": "papaya:Class",
                          "data": { "name": "ByteArrayInputStream" }
                        },
                        {
                          "id": "7dce4153-ee4d-3641-9b51-9fcc39b8a612",
                          "eClass": "papaya:Class",
                          "data": { "name": "ByteArrayOutputStream" }
                        }
                      ]
                    }
                  },
                  {
                    "id": "c8df05d7-7b66-3e43-8c8d-6655c2d3f74e",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "java.time",
                      "types": [
                        {
                          "id": "2100602d-1736-3bf4-85c8-fd042d2b5ee0",
                          "eClass": "papaya:Class",
                          "data": { "name": "Instant" }
                        }
                      ],
                      "packages": [
                        {
                          "id": "742b0a71-9ff4-3a89-8043-a18df2da5a19",
                          "eClass": "papaya:Package",
                          "data": {
                            "name": "temporal",
                            "types": [
                              {
                                "id": "2da1a42b-af9e-359d-8449-1253b945edb8",
                                "eClass": "papaya:Interface",
                                "data": { "name": "TemporalAccessor" }
                              },
                              {
                                "id": "9a117146-4923-3d45-81f4-2bc6d64a0327",
                                "eClass": "papaya:Interface",
                                "data": { "name": "TemporalAdjuster" }
                              },
                              {
                                "id": "1beee33d-3cf0-3811-b537-d50ed6e89f5a",
                                "eClass": "papaya:Interface",
                                "data": { "name": "Temporal" }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  },
                  {
                    "id": "ad28b035-0d52-3877-aec1-907074dace1b",
                    "eClass": "papaya:Package",
                    "data": {
                      "name": "java.util",
                      "types": [
                        {
                          "id": "69bed0ba-3a0d-3e61-ba0a-80479df373ec",
                          "eClass": "papaya:Class",
                          "data": { "name": "UUID" }
                        },
                        {
                          "id": "49e437da-c32d-3cdd-abf4-8977efc58856",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "Collection",
                            "typeParameters": [
                              {
                                "id": "5f09f9e0-2d75-343f-8d2c-b521444b2a22",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "E" }
                              }
                            ]
                          }
                        },
                        {
                          "id": "49eca604-88ed-3756-a78f-2f68561b101a",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "List",
                            "typeParameters": [
                              {
                                "id": "c11a4ac1-467a-344e-8c2e-8785a7281286",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "E" }
                              }
                            ]
                          }
                        },
                        {
                          "id": "5c2d8e60-570a-360c-9957-a4c1982fab3f",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "Set",
                            "typeParameters": [
                              {
                                "id": "1c5d4cb2-c0bb-3aac-b253-7a3db412fb80",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "E" }
                              }
                            ]
                          }
                        },
                        {
                          "id": "1f40f2cd-6426-367f-a6fa-919df71f7b56",
                          "eClass": "papaya:Interface",
                          "data": {
                            "name": "Map",
                            "typeParameters": [
                              {
                                "id": "9822429b-8382-36a6-b963-8316eaeb76a9",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "K" }
                              },
                              {
                                "id": "3c086116-1c70-3042-9736-7f151f25b892",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "V" }
                              }
                            ]
                          }
                        },
                        {
                          "id": "10d940ee-4f4a-3017-add2-d4f3fd1ea887",
                          "eClass": "papaya:Class",
                          "data": {
                            "name": "Optional",
                            "typeParameters": [
                              {
                                "id": "c69e499e-4d88-34b3-bb6d-0031c49aff30",
                                "eClass": "papaya:TypeParameter",
                                "data": { "name": "T" }
                              }
                            ]
                          }
                        }
                      ],
                      "packages": [
                        {
                          "id": "3f1fd7c6-e820-35f2-b8e8-ea6b4b24be04",
                          "eClass": "papaya:Package",
                          "data": {
                            "name": "concurrent",
                            "types": [
                              {
                                "id": "cad94141-d2cb-31f2-8fa4-60b3d2269eaf",
                                "eClass": "papaya:Interface",
                                "data": { "name": "Executor" }
                              },
                              {
                                "id": "ab9a1939-a303-399c-8de8-163c73307323",
                                "eClass": "papaya:Interface",
                                "data": { "name": "ExecutorService" }
                              },
                              {
                                "id": "2c4d1b60-f6fc-3cb7-9b2c-d14cb1f9c372",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Future",
                                  "typeParameters": [
                                    {
                                      "id": "c76303ed-0150-3929-b7c9-e47ec9b3d805",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "04513928-c1f1-37e9-ac5d-e3020395780d",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "CompletionStage",
                                  "typeParameters": [
                                    {
                                      "id": "2de873c7-97ff-3227-af5e-0e9b03e998e8",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "aa094373-60d6-318e-8ba3-bf563d6109b1",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "CompletableFuture",
                                  "typeParameters": [
                                    {
                                      "id": "7d36d6fe-c3d5-3d25-93e9-6f58c32f0f6c",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              }
                            ]
                          }
                        },
                        {
                          "id": "28a470aa-b33f-3fad-8b65-2fabefe5f82a",
                          "eClass": "papaya:Package",
                          "data": {
                            "name": "function",
                            "types": [
                              {
                                "id": "f6d69325-2db3-3e2b-8fad-62c6bca77b7c",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Function",
                                  "typeParameters": [
                                    {
                                      "id": "0543527d-ee5a-398f-94fe-7b80a2959864",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    },
                                    {
                                      "id": "b00284ab-6a46-3d31-a239-6c1c0869e0fe",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "R" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "1b65465d-ca60-3c42-bd8e-19b1bd70efe9",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "BiFunction",
                                  "typeParameters": [
                                    {
                                      "id": "6071e0c4-290b-3a0b-b0dc-ebf027c1760e",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    },
                                    {
                                      "id": "c3955a85-fe8f-3634-b4a5-a8df12947784",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "U" }
                                    },
                                    {
                                      "id": "fd952f9c-aae7-37e0-a4c5-56e22e4cb452",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "R" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "bc2c8154-c884-3685-8af0-1ea11d5b862b",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Supplier",
                                  "typeParameters": [
                                    {
                                      "id": "b7f3b52f-73d7-3784-a76d-c6b1526dc446",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "3dcab92e-8aa2-30c1-afd1-369a4953a8a2",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Consumer",
                                  "typeParameters": [
                                    {
                                      "id": "39d999e0-0068-3ff5-91d6-e90e067f1155",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "c280b54e-96e0-37a4-a1a8-9c0c78c64a72",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Predicate",
                                  "typeParameters": [
                                    {
                                      "id": "0be4e5f8-3e90-3af1-80e7-307e7aed9cfa",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "0aa9557c-fecc-307d-82b3-8f552c53831b",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "UnaryOperator",
                                  "typeParameters": [
                                    {
                                      "id": "2ecd8d1d-6b20-3c1d-80d3-6fed82ce6b53",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    }
                                  ]
                                }
                              }
                            ]
                          }
                        },
                        {
                          "id": "657bf534-499f-3886-9231-632e63758fc9",
                          "eClass": "papaya:Package",
                          "data": {
                            "name": "stream",
                            "types": [
                              {
                                "id": "c3548269-ed42-3205-b3aa-d5a9353258f9",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "BaseStream",
                                  "typeParameters": [
                                    {
                                      "id": "f924a42b-ceaa-31cf-82b4-49bf6c9de0d8",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
                                    },
                                    {
                                      "id": "7a191dc2-f2cf-34f9-80d9-86fcd11d0d17",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "S" }
                                    }
                                  ]
                                }
                              },
                              {
                                "id": "9b5d3509-89dd-31fa-a999-b122410447fc",
                                "eClass": "papaya:Interface",
                                "data": {
                                  "name": "Stream",
                                  "typeParameters": [
                                    {
                                      "id": "5cbbe5b4-960b-339c-99d2-2eb454ae3560",
                                      "eClass": "papaya:TypeParameter",
                                      "data": { "name": "T" }
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
              }
            }
          ]
        }
      }
    ]
  }',
  true,
  '2026-07-09 12:58:52.703625+00',
  '2026-07-09 12:59:22.361539+00'
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
  '1819334a-e362-4997-9950-67faa8d32042',
  'papaya',
  'java',
  '0.0.3',
  '3777ff6f-9206-48c1-a6ed-f3ae04ca03eb',
  'The standard library of the Java programming language',
  '2026-07-09 12:00:00.524',
  '2026-07-09 12:00:00.524'
);
