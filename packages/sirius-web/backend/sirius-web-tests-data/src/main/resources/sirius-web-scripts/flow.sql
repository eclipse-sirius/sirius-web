INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12',
  '2025-09-09 07:47:38.33098+00',
  '2025-09-09 07:47:39.432824+00'
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
  '96d6cae1-da84-40d8-94c3-f4af14462485',
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12',
  'Flow', '{
   "json": { "version": "1.0", "encoding": "utf-8" },
   "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
   "content": [
     {
       "id": "5b75dcbe-10af-452a-af98-4461ee13ea40",
       "eClass": "flow:System",
       "data": {
         "name": "NewSystem",
         "elements": [
           {
             "id": "aac30ae6-05b1-4e98-9e97-e3b440d43e17",
             "eClass": "flow:CompositeProcessor",
             "data": {
               "name": "CompositeProcessor1",
               "elements": [
                 {
                   "id": "ef69655b-b26f-4a4c-9b4e-4e7e00a7edb9",
                   "eClass": "flow:Processor",
                   "data": {
                     "incomingFlows": ["755b55dc-0016-449b-8ef0-1670330e6303"],
                     "name": "Processor1"
                   }
                 }
               ]
             }
           },
           {
             "id": "f52b7dbd-d895-4c95-8912-91a1642508cb",
             "eClass": "flow:CompositeProcessor",
             "data": {
               "name": "CompositeProcessor2",
               "elements": [
                 {
                   "id": "430094ca-c2d3-45a0-a69b-f39d9bc5a7be",
                   "eClass": "flow:Processor",
                   "data": {
                     "incomingFlows": ["03ae2875-3dbe-4c85-a2fe-83bd86b5248c"],
                     "name": "Processor2"
                   }
                 }
               ]
             }
           },
           {
             "id": "6582c159-49d5-4cf6-b4e4-b0655a6206f8",
             "eClass": "flow:DataSource",
             "data": {
               "outgoingFlows": [
                 {
                   "id": "755b55dc-0016-449b-8ef0-1670330e6303",
                   "eClass": "flow:DataFlow",
                   "data": {
                     "usage": "standard",
                     "capacity": 6,
                     "load": 6,
                     "target": "ef69655b-b26f-4a4c-9b4e-4e7e00a7edb9"
                   }
                 },
                {
                   "id": "03ae2875-3dbe-4c85-a2fe-83bd86b5248c",
                   "eClass": "flow:DataFlow",
                   "data": {
                     "usage": "standard",
                     "capacity": 6,
                     "load": 6,
                     "target": "430094ca-c2d3-45a0-a69b-f39d9bc5a7be"
                   }
                 }
               ],
               "name": "DataSource1",
               "volume": 6
             }
           }
         ]
       }
     }
   ]
  }',
  '2025-09-09 07:47:39.414445+00',
  '2025-09-09 07:47:39.414445+00',
  false
);

INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'd419bbee-9cba-4b85-972c-660d875ad705',
  'Flow',
  '2025-09-09 07:47:37.463514+00',
  '2025-09-09 07:47:37.463514+00'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  'd419bbee-9cba-4b85-972c-660d875ad705',
  'siriusWeb://nature?kind=flow'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '0c4fce76-26f5-4c04-bbe5-abd081f4a1c1',
  'd419bbee-9cba-4b85-972c-660d875ad705',
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12',
  'main',
  '2025-09-09 07:47:38.389785+00',
  '2025-09-09 07:47:38.389785+00'
);

INSERT INTO representation_metadata (
  id,
  representation_metadata_id,
  target_object_id,
  description_id,
  label,
  kind,
  created_on,
  last_modified_on,
  documentation,
  semantic_data_id
) VALUES (
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12#40569b77-1967-46cc-99da-c526f5ffbc85',
  '40569b77-1967-46cc-99da-c526f5ffbc85',
  '5b75dcbe-10af-452a-af98-4461ee13ea40',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38',
  'Topography',
  'siriusComponents://representation?type=Diagram',
  '2025-09-09 07:47:39.194852+00',
  '2025-09-09 07:47:39.194852+00',
  '',
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12'
);

INSERT INTO representation_content (
  id,
  representation_metadata_id,
  semantic_data_id,
  content,
  last_migration_performed,
  migration_version,
  created_on,
  last_modified_on
) VALUES (
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12#40569b77-1967-46cc-99da-c526f5ffbc85',
  '40569b77-1967-46cc-99da-c526f5ffbc85',
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12',
  '{
    "id": "40569b77-1967-46cc-99da-c526f5ffbc85",
    "kind": "siriusComponents://representation?type=Diagram",
    "targetObjectId": "5b75dcbe-10af-452a-af98-4461ee13ea40",
    "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
    "nodes": [
      {
        "id": "2c6c3606-5ed2-35f1-b05e-cba434e1a812",
        "type": "node:image",
        "targetObjectId": "6582c159-49d5-4cf6-b4e4-b0655a6206f8",
        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataSource",
        "targetObjectLabel": "DataSource1",
        "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
        "borderNode": false,
        "modifiers": [],
        "state": "Normal",
        "collapsingState": "EXPANDED",
        "insideLabel": null,
        "outsideLabels": [
          {
            "id": "5c379a04-e070-3cb3-8659-f5178016eb01",
            "text": "DataSource1",
            "outsideLabelLocation": "BOTTOM_MIDDLE",
            "style": {
              "color": "#002B3C",
              "fontSize": 14,
              "bold": false,
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
            "overflowStrategy": "NONE",
            "textAlign": "LEFT"
          }
        ],
        "style": {
          "imageURL": "/flow-images/sensor.svg",
          "scalingFactor": 1,
          "borderColor": "black",
          "borderSize": 0,
          "borderRadius": 3,
          "borderStyle": "Solid",
          "positionDependentRotation": false,
          "childrenLayoutStrategy": { "kind": "FreeForm" }
        },
        "borderNodes": [],
        "childNodes": [],
        "defaultWidth": 66,
        "defaultHeight": 90,
        "labelEditable": true,
        "pinned": false,
        "customizedStyleProperties": []
      },
      {
        "id": "655fe01a-0a64-3b6f-91d6-1eff88b11dbf",
        "type": "node:rectangle",
        "targetObjectId": "aac30ae6-05b1-4e98-9e97-e3b440d43e17",
        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
        "targetObjectLabel": "CompositeProcessor1",
        "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
        "borderNode": false,
        "modifiers": [],
        "state": "Normal",
        "collapsingState": "EXPANDED",
        "insideLabel": {
          "id": "2837ac1f-08ea-36e9-a369-7bca4796b888",
          "text": "CompositeProcessor1",
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
          "textAlign": "LEFT",
          "customizedStyleProperties": []
        },
        "outsideLabels": [],
        "style": {
          "background": "#F0F0F0",
          "borderColor": "#B1BCBE",
          "borderSize": 1,
          "borderRadius": 0,
          "borderStyle": "Solid",
          "childrenLayoutStrategy": { "kind": "FreeForm" }
        },
        "borderNodes": [],
        "childNodes": [
          {
            "id": "8a1ebfd1-b2ec-3ec0-b26e-f6297fc7d8f2",
            "type": "node:image",
            "targetObjectId": "ef69655b-b26f-4a4c-9b4e-4e7e00a7edb9",
            "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=Processor",
            "targetObjectLabel": "Processor1",
            "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=d25f3cf7-9ccf-3947-9e1b-03933f469fd2",
            "borderNode": false,
            "modifiers": [],
            "state": "Normal",
            "collapsingState": "EXPANDED",
            "insideLabel": null,
            "outsideLabels": [
              {
                "id": "7bc370e6-892e-3dc1-8fc7-e3cfaf59ffae",
                "text": "Processor1",
                "outsideLabelLocation": "BOTTOM_MIDDLE",
                "style": {
                  "color": "#002B3C",
                  "fontSize": 14,
                  "bold": false,
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
                "overflowStrategy": "NONE",
                "textAlign": "LEFT"
              }
            ],
            "style": {
              "imageURL": "/flow-images/cpu_unused.svg",
              "scalingFactor": 1,
              "borderColor": "black",
              "borderSize": 0,
              "borderRadius": 3,
              "borderStyle": "Solid",
              "positionDependentRotation": false,
              "childrenLayoutStrategy": { "kind": "FreeForm" }
            },
            "borderNodes": [],
            "childNodes": [],
            "defaultWidth": 150,
            "defaultHeight": 150,
            "labelEditable": true,
            "pinned": false,
            "customizedStyleProperties": []
          }
        ],
        "defaultWidth": 150,
        "defaultHeight": 70,
        "labelEditable": true,
        "pinned": false,
        "customizedStyleProperties": []
      }
    ],
    "edges": [
      {
        "id": "2415b5f8-c769-3999-ada1-c4d07e629e83",
        "type": "edge:straight",
        "targetObjectId": "755b55dc-0016-449b-8ef0-1670330e6303",
        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataFlow",
        "targetObjectLabel": "standard",
        "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=0575c2ae-0469-341f-ae36-9ace98e0a2c6",
        "beginLabel": null,
        "centerLabel": {
          "id": "a80de3e6-d507-3ee0-8743-2d4614c30d34",
          "type": "label:edge-center",
          "text": "6",
          "style": {
            "color": "#B1BCBE",
            "fontSize": 14,
            "bold": false,
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
          }
        },
        "endLabel": null,
        "sourceId": "2c6c3606-5ed2-35f1-b05e-cba434e1a812",
        "targetId": "8a1ebfd1-b2ec-3ec0-b26e-f6297fc7d8f2",
        "modifiers": [],
        "state": "Normal",
        "style": {
          "size": 1,
          "lineStyle": "Dash",
          "sourceArrow": "None",
          "targetArrow": "InputClosedArrow",
          "color": "#B1BCBE"
        },
        "centerLabelEditable": true
      },
      {
        "id": "f2bab5c4-7e0b-3c1e-b224-b1172e445a53",
        "type": "edge:straight",
        "targetObjectId": "03ae2875-3dbe-4c85-a2fe-83bd86b5248c",
        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataFlow",
        "targetObjectLabel": "standard",
        "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=b6557d5c-4233-4ce9-aabd-6bb61dc98d33",
        "beginLabel": null,
        "centerLabel": {
          "id": "998bfe83-4efb-4c53-b15b-42027a9d9c9d",
          "type": "label:edge-center",
          "text": "6",
          "style": {
            "color": "#B1BCBE",
            "fontSize": 14,
            "bold": false,
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
          }
        },
        "endLabel": null,
        "sourceId": "562d5a71-dd43-4269-ad92-656b917a6bc0",
        "targetId": "cc5f93e9-e0e7-44c5-b36d-2966c1c084e3",
        "modifiers": [],
        "state": "Normal",
        "style": {
          "size": 1,
          "lineStyle": "Dash",
          "sourceArrow": "None",
          "targetArrow": "InputClosedArrow",
          "color": "#B1BCBE"
        },
        "centerLabelEditable": true
      }
    ],
    "layoutData": {
      "nodeLayoutData": {
        "655fe01a-0a64-3b6f-91d6-1eff88b11dbf": {
          "id": "655fe01a-0a64-3b6f-91d6-1eff88b11dbf",
          "position": { "x": 86.0, "y": 0.0 },
          "size": { "width": 190.77500915527344, "height": 199.60000038146973 },
          "resizedByUser": false,
          "handleLayoutData": []
        },
        "2c6c3606-5ed2-35f1-b05e-cba434e1a812": {
          "id": "2c6c3606-5ed2-35f1-b05e-cba434e1a812",
          "position": { "x": 0.0, "y": 0.0 },
          "size": { "width": 66.0, "height": 90.0 },
          "resizedByUser": false,
          "handleLayoutData": []
        },
        "8a1ebfd1-b2ec-3ec0-b26e-f6297fc7d8f2": {
          "id": "8a1ebfd1-b2ec-3ec0-b26e-f6297fc7d8f2",
          "position": { "x": 8.0, "y": 32.79999923706055 },
          "size": { "width": 150.0, "height": 150.0 },
          "resizedByUser": false,
          "handleLayoutData": [
          {
            "edgeId": "2415b5f8-c769-3999-ada1-c4d07e629e83",
            "position": {
              "x": 0.0,
              "y": 97
            },
            "handlePosition": "left",
            "type": "target"
          }
        ]
        }
      },
      "edgeLayoutData": {
        "2415b5f8-c769-3999-ada1-c4d07e629e83": {
          "id": "2415b5f8-c769-3999-ada1-c4d07e629e83",
          "bendingPoints": [],
          "edgeAnchorLayoutData": []
        }
      },
      "labelLayoutData": {}
    }
  }',
  'none',
  '2025.6.0-202506011650',
  '2025-09-09 07:47:39.326207+00',
  '2025-09-09 07:47:40.332223+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '6dfe736e-c582-4148-8dcf-9b1baa6e3a12',
  'http://www.obeo.fr/dsl/designer/sample/flow'
);
