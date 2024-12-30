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
INSERT INTO project_image (
  id,
  project_id,
  label,
  content_type,
  content,
  created_on,
  last_modified_on
) VALUES (
  'ff37f0eb-effb-4c57-b17f-76bc7ea64f5b',
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'Placeholder',
  'image/svg+xml',
  '<svg version="1.1" xmlns="http://www.w3.org/2000/svg"><rect width="10px" height="10px" fill="black" /></svg>'::bytea,
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
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "ecore":"http://www.eclipse.org/emf/2002/Ecore"
    },
    "content":[
      {
        "id":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
        "eClass":"ecore:EPackage",
        "data":{
          "name":"Sample",
          "eClassifiers":[
            {
              "id":"f0eecd16-d9da-4c98-a422-c73897bc48f5",
              "eClass":"ecore:EClass",
              "data":{
                "name":"SampleEClass"
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
  project_id,
  target_object_id,
  description_id,
  label,
  documentation,
  kind,
  created_on,
  last_modified_on
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  'documentation',
  'siriusComponents://representation?type=Portal',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_metadata_icon_url (
  representation_metadata_id,
  url,
  index
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '/portal-images/portal.svg',
  0
);
INSERT INTO representation_content (
  id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '{
    "id":"e81eec5c-42d6-491c-8bcc-9beb951356f8",
    "kind":"siriusComponents://representation?type=Portal",
    "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
    "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
    "views":[
      {
        "id":"bbf6c9b9-ee36-32c5-aee0-f4d64efcccb7",
        "representationId":"05e44ccc-9363-443f-a816-25fc73e3e7f7"
      }
    ],
    "layoutData":[
      {
        "portalViewId":"bbf6c9b9-ee36-32c5-aee0-f4d64efcccb7",
        "x":0,
        "y":0,
        "width":500,
        "height":200
      }
    ]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000',
  'none',
  '0'
);
INSERT INTO representation_metadata (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  documentation,
  kind,
  created_on,
  last_modified_on
) VALUES (
  '05e44ccc-9363-443f-a816-25fc73e3e7f7',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  '',
  'siriusComponents://representation?type=Portal',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_content (
  id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '05e44ccc-9363-443f-a816-25fc73e3e7f7',
  '{
    "id":"05e44ccc-9363-443f-a816-25fc73e3e7f7",
    "kind":"siriusComponents://representation?type=Portal",
    "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
    "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
    "views":[],
    "layoutData":[]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000',
  'none',
  '0'
);

-- Sample Flow project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '02b932be-8ba9-40ff-a6e2-61630d47f398',
  'Flow Sample',
  '2025-01-01 9:42:0.000',
  '2025-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '02b932be-8ba9-40ff-a6e2-61630d47f398',
  'siriusWeb://nature?kind=flow'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '1624215f-061b-45b0-b193-6d60c03de344',
  '02b932be-8ba9-40ff-a6e2-61630d47f398',
  '2025-01-01 9:42:0.000',
  '2025-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '1624215f-061b-45b0-b193-6d60c03de344',
  'http://www.obeo.fr/dsl/designer/sample/flow'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '74991611-86f9-4e13-947d-8f14046a6910',
  '1624215f-061b-45b0-b193-6d60c03de344',
  'Robot Flow',
  '{
    "json": {
      "version": "1.0",
      "encoding": "utf-8"
    },
    "ns": {
      "flow": "http://www.obeo.fr/dsl/designer/sample/flow"
    },
    "content": [
      {
        "id": "0aaa281a-b6b6-4140-b610-582509bc1158",
        "eClass": "flow:System",
        "data": {
          "elements": [
            {
              "id": "bec959eb-092b-4842-95a9-6de2329bd68d",
              "eClass": "flow:CompositeProcessor",
              "data": {
                "consumption": 200,
                "usage": "standard",
                "name": "Central_Unit",
                "elements": [
                  {
                    "id": "8c25a032-292e-4d31-9f56-eddb2910f57d",
                    "eClass": "flow:Processor",
                    "data": {
                      "usage": "standard",
                      "incomingFlows": [
                        "//@elements.2/@outgoingFlows.0"
                      ],
                      "capacity": 4,
                      "load": 4,
                      "consumption": 40,
                      "name": "DSP",
                      "volume": 4
                    }
                  },
                  {
                    "id": "3098dcdb-5466-4307-98f5-7c9dd9b80ad7",
                    "eClass": "flow:Processor",
                    "data": {
                      "usage": "high",
                      "incomingFlows": [
                        "//@elements.0/@elements.1/@outgoingFlows.0",
                        "//@elements.1/@elements.4/@outgoingFlows.0"
                      ],
                      "capacity": 15,
                      "load": 18,
                      "outgoingFlows": [
                        {
                          "id": "fda112ec-4b2b-465a-af47-af096a008306",
                          "eClass": "flow:DataFlow",
                          "data": {
                            "target": "//@elements.0/@elements.1"
                          }
                        }
                      ],
                      "consumption": 150,
                      "name": "Motion_Engine",
                      "volume": 18
                    }
                  },
                  {
                    "id": "a4ee2197-8945-4850-a015-6d5d1624986c",
                    "eClass": "flow:Fan",
                    "data": {
                      "consumption": 10
                    }
                  }
                ],
                "temperature": 25,
                "weight": 23,
                "routingRules": "Case Robot.temperature >= 70 C ! critical !"
              }
            },
            {
              "id": "abdc3be6-7fe7-412f-beb6-0804d19fa577",
              "eClass": "flow:CompositeProcessor",
              "data": {
                "elements": [
                  {
                    "id": "dcbdd616-4f01-44c7-b0c1-ebb23bbd0c1c",
                    "eClass": "flow:Processor",
                    "data": {
                      "usage": "standard",
                      "load": 8,
                      "name": "Radar_Capture",
                      "volume": 8
                    }
                  },
                  {
                    "id": "b54a30f6-b47d-4be9-a34b-b0932f413bb8",
                    "eClass": "flow:DataSource",
                    "data": {
                      "usage": "standard",
                      "name": "Back_Camera",
                      "volume": 6
                    }
                  },
                  {
                    "id": "6bbd4e43-e629-4c6f-a179-d451e9fffe32",
                    "eClass": "flow:DataSource",
                    "data": {
                      "name": "Radar",
                      "volume": 8
                    }
                  },
                  {
                    "id": "5bb53827-ba5e-4964-b389-6427d8c08576",
                    "eClass": "flow:Processor",
                    "data": {
                      "load": 8,
                      "consumption": 100,
                      "name": "Engine",
                      "volume": 8
                    }
                  },
                  {
                    "id": "e70d2b31-6d24-4327-ab65-03fdc329baf4",
                    "eClass": "flow:Processor",
                    "data": {
                      "usage": "standard",
                      "load": 6,
                      "outgoingFlows": [
                        {
                          "id": "4e978006-321b-4e8a-8a81-835666a163b9",
                          "eClass": "flow:DataFlow",
                          "data": {
                            "target": "//@elements.0/@elements.1"
                          }
                        }
                      ],
                      "name": "GPU",
                      "volume": 6
                    }
                  },
                  {
                    "id": "2e3a49d7-bbf8-419f-bb3e-30927bf9534f",
                    "eClass": "flow:Fan",
                    "data": {
                      "speed": 20
                    }
                  }
                ]
              }
            },
            {
              "id": "2986f359-9850-483f-b5b1-130ce97bd542",
              "eClass": "flow:DataSource",
              "data": {
                "outgoingFlows": [
                  {
                    "id": "9a7034e1-9364-4ef3-ab55-84bb93d0dc6b",
                    "eClass": "flow:DataFlow",
                    "data": {
                      "usage": "standard",
                      "capacity": 4,
                      "load": 4,
                      "target": "//@elements.0/@elements.0"
                    }
                  }
                ],
                "name": "Wifi",
                "volume": 4
              }
            }
          ]
        }
      }
    ]
   }',
  '2025-01-01 9:42:0.000',
  '2025-01-02 9:42:0.000'
);
INSERT INTO representation_metadata (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  documentation,
  kind,
  created_on,
  last_modified_on
) VALUES (
  'a3ca97fa-bf7d-4b15-861d-e88cee71da42',
  '02b932be-8ba9-40ff-a6e2-61630d47f398',
  '0aaa281a-b6b6-4140-b610-582509bc1158',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275',
  'Topography Unsynchronized with content',
  'documentation',
  'siriusComponents://representation?type=Diagram',
  '2025-01-01 9:42:0.000',
  '2025-01-02 9:42:0.000'
);
INSERT INTO representation_content (
  id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  'a3ca97fa-bf7d-4b15-861d-e88cee71da42',
  '{
    "id": "a3ca97fa-bf7d-4b15-861d-e88cee71da42",
    "kind": "siriusComponents://representation?type=Diagram",
    "targetObjectId": "0aaa281a-b6b6-4140-b610-582509bc1158",
    "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275",
    "nodes": [
      {
        "id": "bc68f231-ca2b-3981-b3cd-8e48c558c2ce",
        "type": "node:rectangle",
        "targetObjectId": "bec959eb-092b-4842-95a9-6de2329bd68d",
        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
        "targetObjectLabel": "Central_Unit",
        "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=1207e7e1-4194-3cbb-a036-4748c52f629f",
        "borderNode": false,
        "modifiers": [],
        "state": "Normal",
        "collapsingState": "EXPANDED",
        "insideLabel": {
          "id": "bf451861-24a0-3798-bd2d-5b359b2f037b",
          "text": "Central_Unit",
          "insideLabelLocation": "TOP_CENTER",
          "style": {
            "color": "#002B3C",
            "fontSize": 14,
            "bold": true,
            "italic": false,
            "underline": false,
            "strikeThrough": false,
            "iconURL": [],
            "background": "transparent",
            "borderColor": "black",
            "borderSize": 0,
            "borderRadius": 3,
            "borderStyle": "Solid",
            "maxWidth": null
          },
          "isHeader": true,
          "headerSeparatorDisplayMode": "NEVER",
          "overflowStrategy": "NONE",
          "textAlign": "LEFT"
        },
        "outsideLabels": [],
        "style": {
          "background": "#F0F0F0",
          "borderColor": "#B1BCBE",
          "borderSize": 1,
          "borderRadius": 0,
          "borderStyle": "Solid"
        },
        "childrenLayoutStrategy": {
          "kind": "FreeForm"
        },
        "borderNodes": [],
        "childNodes": [],
        "defaultWidth": 150,
        "defaultHeight": 70,
        "labelEditable": true,
        "pinned": false
      }
    ],
    "edges": [],
    "layoutData": {
      "nodeLayoutData": {
        "bc68f231-ca2b-3981-b3cd-8e48c558c2ce": {
          "id": "bc68f231-ca2b-3981-b3cd-8e48c558c2ce",
          "position": {
            "x": 350.0,
            "y": 210.0
          },
          "size": {
            "width": 150.0,
            "height": 70.0
          },
          "resizedByUser": false
        }
      },
      "edgeLayoutData": {},
      "labelLayoutData": {}
    }
   }',
  '2025-01-01 9:42:0.000',
  '2025-01-02 9:42:0.000',
  'none',
  '0'
);
