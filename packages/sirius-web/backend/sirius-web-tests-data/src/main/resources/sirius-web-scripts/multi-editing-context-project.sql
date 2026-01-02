
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '64e672ab-5d76-4233-a2a9-090455fcf293',
  'MultiContextFlowProject',
  '2025-10-07 13:41:33.8111+00',
  '2025-10-07 13:41:33.8111+00'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  '64e672ab-5d76-4233-a2a9-090455fcf293',
  'siriusWeb://nature?kind=flow'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  '2025-10-07 13:41:33.836021+00',
  '2025-10-07 13:41:34.21109+00'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  '2025-10-07 13:41:41.302205+00',
  '2025-10-07 13:41:41.55467+00'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '0905ce75-84ae-4ca1-9f1c-60c750ce99f6',
  '64e672ab-5d76-4233-a2a9-090455fcf293',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  'main',
  '2025-10-07 13:41:33.85962+00',
  '2025-10-07 13:41:33.85962+00'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '72eb0700-b517-4d25-99c1-7f3fb97fdf35',
  '64e672ab-5d76-4233-a2a9-090455fcf293',
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  'main2',
  '2025-10-07 13:41:41.326203+00',
  '2025-10-07 13:41:41.326203+00'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  'http://www.obeo.fr/dsl/designer/sample/flow'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  'http://www.obeo.fr/dsl/designer/sample/flow'
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
  'e4eca16c-7266-47cb-8310-5603a16a3ec0',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  'Flow',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
     "content": [
       {
         "id": "ff66bbf7-be79-4b89-8c32-d1603c9966af",
         "eClass": "flow:System",
         "data": {
           "name": "NewSystem",
           "elements": [
             {
               "id": "89d67776-6687-436f-8469-88a685053638",
               "eClass": "flow:CompositeProcessor",
               "data": {
                 "name": "CompositeProcessor1",
                 "elements": [
                   {
                     "id": "d5f4a659-904e-4135-9947-de298b0cbaf4",
                     "eClass": "flow:Processor",
                     "data": {
                       "incomingFlows": ["d4a7f861-c178-47f3-abfc-ed171398e1e7"],
                       "name": "Processor1"
                     }
                   }
                 ]
               }
             },
             {
               "id": "d0bc85f6-d597-4f36-bd1e-0980f7fb0fe4",
               "eClass": "flow:DataSource",
               "data": {
                 "outgoingFlows": [
                   {
                     "id": "d4a7f861-c178-47f3-abfc-ed171398e1e7",
                     "eClass": "flow:DataFlow",
                     "data": {
                       "usage": "standard",
                       "capacity": 6,
                       "load": 6,
                       "target": "d5f4a659-904e-4135-9947-de298b0cbaf4"
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
   }
',
  '2025-10-07 13:41:34.20591+00',
  '2025-10-07 13:41:34.20591+00',
  'f'
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
  '1109cf7e-232a-4976-906f-4b92d31e6842',
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  'Flow',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
     "content": [
       {
         "id": "f926f2ec-681b-434c-b3e6-0fda47388768",
         "eClass": "flow:System",
         "data": {
           "name": "NewSystem2",
           "elements": [
             {
               "id": "ed151a41-6f24-4aea-83a2-79ece2bcec4a",
               "eClass": "flow:CompositeProcessor",
               "data": {
                 "name": "CompositeProcessor2",
                 "elements": [
                   {
                     "id": "dafeb7c5-de89-463e-8950-e322ec619b45",
                     "eClass": "flow:Processor",
                     "data": {
                       "incomingFlows": ["562e726e-207f-4c16-a2b4-e1f47828283c"],
                       "name": "Processor2"
                     }
                   }
                 ]
               }
             },
             {
               "id": "7fc90016-bb2e-428e-b99e-2fc813ca8a06",
               "eClass": "flow:DataSource",
               "data": {
                 "outgoingFlows": [
                   {
                     "id": "562e726e-207f-4c16-a2b4-e1f47828283c",
                     "eClass": "flow:DataFlow",
                     "data": {
                       "usage": "standard",
                       "capacity": 6,
                       "load": 6,
                       "target": "dafeb7c5-de89-463e-8950-e322ec619b45"
                     }
                   }
                 ],
                 "name": "DataSource2",
                 "volume": 6
               }
             }
           ]
         }
       }
     ]
   }
',
  '2025-10-07 13:41:41.549341+00',
  '2025-10-07 13:41:41.549341+00',
  'f'
);

INSERT INTO representation_metadata (
  id,
  target_object_id,
  description_id,
  label,
  kind,
  created_on,
  last_modified_on,
  documentation,
  semantic_data_id
) VALUES (
  '4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  'ff66bbf7-be79-4b89-8c32-d1603c9966af',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38',
  'Topography',
  'siriusComponents://representation?type=Diagram',
  '2025-10-07 13:41:34.133346+00',
  '2025-10-07 13:41:34.133346+00',
  '',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300'
);

INSERT INTO representation_metadata (
  id,
  target_object_id,
  description_id,
  label,
  kind,
  created_on,
  last_modified_on,
  documentation,
  semantic_data_id
) VALUES (
  '4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  'f926f2ec-681b-434c-b3e6-0fda47388768',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38',
  'Topography',
  'siriusComponents://representation?type=Diagram',
  '2025-10-07 13:41:41.494685+00',
  '2025-10-07 13:41:41.494685+00',
  '',
  '3f155db4-6771-459a-9fec-6a3a77238d31'
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
  '4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  '{
     "id":"4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9",
     "kind":"siriusComponents://representation?type=Diagram",
     "targetObjectId":"ff66bbf7-be79-4b89-8c32-d1603c9966af",
     "descriptionId":"siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
     "nodes":[
       {
         "id":"383eee20-d3c2-3de0-a762-2ecc7fa15e71",
         "type":"node:image",
         "targetObjectId":"d0bc85f6-d597-4f36-bd1e-0980f7fb0fe4",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=DataSource",
         "targetObjectLabel":"DataSource1",
         "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
         "borderNode":false,
         "initialBorderNodePosition":"NONE",
         "modifiers":[

         ],
         "state":"Normal",
         "collapsingState":"EXPANDED",
         "insideLabel":null,
         "outsideLabels":[
           {
             "id":"8991212d-e80f-3a8b-b4d4-48375d06063a",
             "text":"DataSource1",
             "outsideLabelLocation":"BOTTOM_MIDDLE",
             "style":{
               "color":"#002B3C",
               "fontSize":14,
               "bold":false,
               "italic":false,
               "underline":false,
               "strikeThrough":false,
               "iconURL":[

               ],
               "background":"transparent",
               "borderColor":"black",
               "borderSize":0,
               "borderRadius":3,
               "borderStyle":"Solid",
               "maxWidth":null,
               "visibility":"visible"
             },
             "overflowStrategy":"NONE",
             "textAlign":"LEFT",
             "customizedStyleProperties":[

             ]
           }
         ],
         "style":{
           "imageURL":"/flow-images/sensor.svg",
           "scalingFactor":1,
           "borderColor":"black",
           "borderSize":0,
           "borderRadius":3,
           "borderStyle":"Solid",
           "positionDependentRotation":false,
           "childrenLayoutStrategy":{
             "kind":"FreeForm"
           }
         },
         "borderNodes":[

         ],
         "childNodes":[

         ],
         "defaultWidth":66,
         "defaultHeight":90,
         "labelEditable":true,
         "pinned":false,
         "customizedStyleProperties":[

         ]
       },
       {
         "id":"c5f72a35-9093-3474-bcea-bde15a5fb9d5",
         "type":"node:rectangle",
         "targetObjectId":"89d67776-6687-436f-8469-88a685053638",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
         "targetObjectLabel":"CompositeProcessor1",
         "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
         "borderNode":false,
         "initialBorderNodePosition":"NONE",
         "modifiers":[

         ],
         "state":"Normal",
         "collapsingState":"EXPANDED",
         "insideLabel":{
           "id":"1de3e085-b6d1-34c4-9894-a630de203b9e",
           "text":"CompositeProcessor1",
           "insideLabelLocation":"TOP_CENTER",
           "style":{
             "color":"#002B3C",
             "fontSize":14,
             "bold":true,
             "italic":false,
             "underline":false,
             "strikeThrough":false,
             "iconURL":[

             ],
             "background":"transparent",
             "borderColor":"black",
             "borderSize":0,
             "borderRadius":3,
             "borderStyle":"Solid",
             "maxWidth":null,
             "visibility":"visible"
           },
           "isHeader":true,
           "headerSeparatorDisplayMode":"NEVER",
           "overflowStrategy":"NONE",
           "textAlign":"LEFT",
           "customizedStyleProperties":[

           ]
         },
         "outsideLabels":[

         ],
         "style":{
           "background":"#F0F0F0",
           "borderColor":"#B1BCBE",
           "borderSize":1,
           "borderRadius":0,
           "borderStyle":"Solid",
           "childrenLayoutStrategy":{
             "kind":"FreeForm"
           }
         },
         "borderNodes":[

         ],
         "childNodes":[
           {
             "id":"fb35ab6e-fe6b-3237-940d-3bce4e9b2eb4",
             "type":"node:image",
             "targetObjectId":"d5f4a659-904e-4135-9947-de298b0cbaf4",
             "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=Processor",
             "targetObjectLabel":"Processor1",
             "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=d25f3cf7-9ccf-3947-9e1b-03933f469fd2",
             "borderNode":false,
             "initialBorderNodePosition":"NONE",
             "modifiers":[

             ],
             "state":"Normal",
             "collapsingState":"EXPANDED",
             "insideLabel":null,
             "outsideLabels":[
               {
                 "id":"707936b0-4c70-3d78-9ad9-7c6ab7056d96",
                 "text":"Processor1",
                 "outsideLabelLocation":"BOTTOM_MIDDLE",
                 "style":{
                   "color":"#002B3C",
                   "fontSize":14,
                   "bold":false,
                   "italic":false,
                   "underline":false,
                   "strikeThrough":false,
                   "iconURL":[

                   ],
                   "background":"transparent",
                   "borderColor":"black",
                   "borderSize":0,
                   "borderRadius":3,
                   "borderStyle":"Solid",
                   "maxWidth":null,
                   "visibility":"visible"
                 },
                 "overflowStrategy":"NONE",
                 "textAlign":"LEFT",
                 "customizedStyleProperties":[

                 ]
               }
             ],
             "style":{
               "imageURL":"/flow-images/cpu_unused.svg",
               "scalingFactor":1,
               "borderColor":"black",
               "borderSize":0,
               "borderRadius":3,
               "borderStyle":"Solid",
               "positionDependentRotation":false,
               "childrenLayoutStrategy":{
                 "kind":"FreeForm"
               }
             },
             "borderNodes":[

             ],
             "childNodes":[

             ],
             "defaultWidth":150,
             "defaultHeight":150,
             "labelEditable":true,
             "pinned":false,
             "customizedStyleProperties":[

             ]
           }
         ],
         "defaultWidth":150,
         "defaultHeight":70,
         "labelEditable":true,
         "pinned":false,
         "customizedStyleProperties":[

         ]
       }
     ],
     "edges":[
       {
         "id":"1833eef0-bbf8-377a-bf03-fa679b8a12e4",
         "type":"edge:straight",
         "targetObjectId":"d4a7f861-c178-47f3-abfc-ed171398e1e7",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=DataFlow",
         "targetObjectLabel":"standard",
         "descriptionId":"siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=0575c2ae-0469-341f-ae36-9ace98e0a2c6",
         "beginLabel":null,
         "centerLabel":{
           "id":"29b88153-83d4-315d-88c0-566cdff828cd",
           "type":"label:edge-center",
           "text":"6",
           "style":{
             "color":"#B1BCBE",
             "fontSize":14,
             "bold":false,
             "italic":false,
             "underline":false,
             "strikeThrough":false,
             "iconURL":[

             ],
             "background":"transparent",
             "borderColor":"black",
             "borderSize":0,
             "borderRadius":3,
             "borderStyle":"Solid",
             "maxWidth":null,
             "visibility":"visible"
           },
           "customizedStyleProperties":[

           ]
         },
         "endLabel":null,
         "sourceId":"383eee20-d3c2-3de0-a762-2ecc7fa15e71",
         "targetId":"fb35ab6e-fe6b-3237-940d-3bce4e9b2eb4",
         "modifiers":[

         ],
         "state":"Normal",
         "style":{
           "size":1,
           "lineStyle":"Dash",
           "sourceArrow":"None",
           "targetArrow":"InputClosedArrow",
           "color":"#B1BCBE",
           "edgeType":"Manhattan"
         },
         "centerLabelEditable":true,
         "customizedStyleProperties":[

         ]
       }
     ],
     "layoutData":{
       "nodeLayoutData":{
         "fb35ab6e-fe6b-3237-940d-3bce4e9b2eb4":{
           "id":"fb35ab6e-fe6b-3237-940d-3bce4e9b2eb4",
           "position":{
             "x":8.0,
             "y":32.79999923706055
           },
           "size":{
             "width":150.0,
             "height":150.0
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         },
         "c5f72a35-9093-3474-bcea-bde15a5fb9d5":{
           "id":"c5f72a35-9093-3474-bcea-bde15a5fb9d5",
           "position":{
             "x":86.0,
             "y":0.0
           },
           "size":{
             "width":199.0625,
             "height":199.60000038146973
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         },
         "383eee20-d3c2-3de0-a762-2ecc7fa15e71":{
           "id":"383eee20-d3c2-3de0-a762-2ecc7fa15e71",
           "position":{
             "x":0.0,
             "y":0.0
           },
           "size":{
             "width":66.0,
             "height":90.0
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         }
       },
       "edgeLayoutData":{
         "1833eef0-bbf8-377a-bf03-fa679b8a12e4":{
           "id":"1833eef0-bbf8-377a-bf03-fa679b8a12e4",
           "bendingPoints":[

           ],
           "edgeAnchorLayoutData":[

           ]
         }
       },
       "labelLayoutData":{
         "29b88153-83d4-315d-88c0-566cdff828cd":{
           "id":"29b88153-83d4-315d-88c0-566cdff828cd",
           "position":{
             "x":0.0,
             "y":0.0
           }
         },
         "707936b0-4c70-3d78-9ad9-7c6ab7056d96":{
           "id":"707936b0-4c70-3d78-9ad9-7c6ab7056d96",
           "position":{
             "x":0.0,
             "y":0.0
           }
         },
         "8991212d-e80f-3a8b-b4d4-48375d06063a":{
           "id":"8991212d-e80f-3a8b-b4d4-48375d06063a",
           "position":{
             "x":0.0,
             "y":0.0
           }
         }
       }
     }
   }',
  'none',
  '2025.6.0-202506011650',
  '2025-10-07 13:41:34.167224+00',
  '2025-10-07 13:41:34.611967+00'
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
  '4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  '{
     "id":"4687c6ae-e968-4680-a4ad-5db22dd7f6ef",
     "kind":"siriusComponents://representation?type=Diagram",
     "targetObjectId":"f926f2ec-681b-434c-b3e6-0fda47388768",
     "descriptionId":"siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
     "nodes":[
       {
         "id":"7fe768dc-dc9d-3f89-8dec-5b9f63fe7740",
         "type":"node:image",
         "targetObjectId":"7fc90016-bb2e-428e-b99e-2fc813ca8a06",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=DataSource",
         "targetObjectLabel":"DataSource1",
         "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
         "borderNode":false,
         "initialBorderNodePosition":"NONE",
         "modifiers":[

         ],
         "state":"Normal",
         "collapsingState":"EXPANDED",
         "insideLabel":null,
         "outsideLabels":[
           {
             "id":"a4a0207b-73a1-3364-8aaa-92279f5b31d3",
             "text":"DataSource1",
             "outsideLabelLocation":"BOTTOM_MIDDLE",
             "style":{
               "color":"#002B3C",
               "fontSize":14,
               "bold":false,
               "italic":false,
               "underline":false,
               "strikeThrough":false,
               "iconURL":[

               ],
               "background":"transparent",
               "borderColor":"black",
               "borderSize":0,
               "borderRadius":3,
               "borderStyle":"Solid",
               "maxWidth":null,
               "visibility":"visible"
             },
             "overflowStrategy":"NONE",
             "textAlign":"LEFT",
             "customizedStyleProperties":[

             ]
           }
         ],
         "style":{
           "imageURL":"/flow-images/sensor.svg",
           "scalingFactor":1,
           "borderColor":"black",
           "borderSize":0,
           "borderRadius":3,
           "borderStyle":"Solid",
           "positionDependentRotation":false,
           "childrenLayoutStrategy":{
             "kind":"FreeForm"
           }
         },
         "borderNodes":[

         ],
         "childNodes":[

         ],
         "defaultWidth":66,
         "defaultHeight":90,
         "labelEditable":true,
         "pinned":false,
         "customizedStyleProperties":[

         ]
       },
       {
         "id":"6255bcc2-05ea-37d5-b738-71afb6172351",
         "type":"node:rectangle",
         "targetObjectId":"ed151a41-6f24-4aea-83a2-79ece2bcec4a",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
         "targetObjectLabel":"CompositeProcessor1",
         "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
         "borderNode":false,
         "initialBorderNodePosition":"NONE",
         "modifiers":[

         ],
         "state":"Normal",
         "collapsingState":"EXPANDED",
         "insideLabel":{
           "id":"c334f273-f24e-34a3-8ec3-915faa874a3f",
           "text":"CompositeProcessor1",
           "insideLabelLocation":"TOP_CENTER",
           "style":{
             "color":"#002B3C",
             "fontSize":14,
             "bold":true,
             "italic":false,
             "underline":false,
             "strikeThrough":false,
             "iconURL":[

             ],
             "background":"transparent",
             "borderColor":"black",
             "borderSize":0,
             "borderRadius":3,
             "borderStyle":"Solid",
             "maxWidth":null,
             "visibility":"visible"
           },
           "isHeader":true,
           "headerSeparatorDisplayMode":"NEVER",
           "overflowStrategy":"NONE",
           "textAlign":"LEFT",
           "customizedStyleProperties":[

           ]
         },
         "outsideLabels":[

         ],
         "style":{
           "background":"#F0F0F0",
           "borderColor":"#B1BCBE",
           "borderSize":1,
           "borderRadius":0,
           "borderStyle":"Solid",
           "childrenLayoutStrategy":{
             "kind":"FreeForm"
           }
         },
         "borderNodes":[

         ],
         "childNodes":[
           {
             "id":"c66cb367-8b98-3a03-8575-e0cf673cdcb6",
             "type":"node:image",
             "targetObjectId":"dafeb7c5-de89-463e-8950-e322ec619b45",
             "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=Processor",
             "targetObjectLabel":"Processor1",
             "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=d25f3cf7-9ccf-3947-9e1b-03933f469fd2",
             "borderNode":false,
             "initialBorderNodePosition":"NONE",
             "modifiers":[

             ],
             "state":"Normal",
             "collapsingState":"EXPANDED",
             "insideLabel":null,
             "outsideLabels":[
               {
                 "id":"a35c19a7-f2d8-3c93-a088-4f300d459566",
                 "text":"Processor1",
                 "outsideLabelLocation":"BOTTOM_MIDDLE",
                 "style":{
                   "color":"#002B3C",
                   "fontSize":14,
                   "bold":false,
                   "italic":false,
                   "underline":false,
                   "strikeThrough":false,
                   "iconURL":[

                   ],
                   "background":"transparent",
                   "borderColor":"black",
                   "borderSize":0,
                   "borderRadius":3,
                   "borderStyle":"Solid",
                   "maxWidth":null,
                   "visibility":"visible"
                 },
                 "overflowStrategy":"NONE",
                 "textAlign":"LEFT",
                 "customizedStyleProperties":[

                 ]
               }
             ],
             "style":{
               "imageURL":"/flow-images/cpu_unused.svg",
               "scalingFactor":1,
               "borderColor":"black",
               "borderSize":0,
               "borderRadius":3,
               "borderStyle":"Solid",
               "positionDependentRotation":false,
               "childrenLayoutStrategy":{
                 "kind":"FreeForm"
               }
             },
             "borderNodes":[

             ],
             "childNodes":[

             ],
             "defaultWidth":150,
             "defaultHeight":150,
             "labelEditable":true,
             "pinned":false,
             "customizedStyleProperties":[

             ]
           }
         ],
         "defaultWidth":150,
         "defaultHeight":70,
         "labelEditable":true,
         "pinned":false,
         "customizedStyleProperties":[

         ]
       }
     ],
     "edges":[
       {
         "id":"10c416f9-fca3-39a0-b8c7-bf6ec9c6ff0c",
         "type":"edge:straight",
         "targetObjectId":"562e726e-207f-4c16-a2b4-e1f47828283c",
         "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=DataFlow",
         "targetObjectLabel":"standard",
         "descriptionId":"siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=0575c2ae-0469-341f-ae36-9ace98e0a2c6",
         "beginLabel":null,
         "centerLabel":{
           "id":"3d878913-84f6-3198-8d04-646b988b7267",
           "type":"label:edge-center",
           "text":"6",
           "style":{
             "color":"#B1BCBE",
             "fontSize":14,
             "bold":false,
             "italic":false,
             "underline":false,
             "strikeThrough":false,
             "iconURL":[

             ],
             "background":"transparent",
             "borderColor":"black",
             "borderSize":0,
             "borderRadius":3,
             "borderStyle":"Solid",
             "maxWidth":null,
             "visibility":"visible"
           },
           "customizedStyleProperties":[

           ]
         },
         "endLabel":null,
         "sourceId":"7fe768dc-dc9d-3f89-8dec-5b9f63fe7740",
         "targetId":"c66cb367-8b98-3a03-8575-e0cf673cdcb6",
         "modifiers":[

         ],
         "state":"Normal",
         "style":{
           "size":1,
           "lineStyle":"Dash",
           "sourceArrow":"None",
           "targetArrow":"InputClosedArrow",
           "color":"#B1BCBE",
           "edgeType":"Manhattan"
         },
         "centerLabelEditable":true,
         "customizedStyleProperties":[

         ]
       }
     ],
     "layoutData":{
       "nodeLayoutData":{
         "c66cb367-8b98-3a03-8575-e0cf673cdcb6":{
           "id":"c66cb367-8b98-3a03-8575-e0cf673cdcb6",
           "position":{
             "x":8.0,
             "y":32.79999923706055
           },
           "size":{
             "width":150.0,
             "height":150.0
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         },
         "7fe768dc-dc9d-3f89-8dec-5b9f63fe7740":{
           "id":"7fe768dc-dc9d-3f89-8dec-5b9f63fe7740",
           "position":{
             "x":0.0,
             "y":0.0
           },
           "size":{
             "width":66.0,
             "height":90.0
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         },
         "6255bcc2-05ea-37d5-b738-71afb6172351":{
           "id":"6255bcc2-05ea-37d5-b738-71afb6172351",
           "position":{
             "x":86.0,
             "y":0.0
           },
           "size":{
             "width":199.0625,
             "height":199.60000038146973
           },
           "resizedByUser":false,
           "handleLayoutData":[

           ]
         }
       },
       "edgeLayoutData":{
         "10c416f9-fca3-39a0-b8c7-bf6ec9c6ff0c":{
           "id":"10c416f9-fca3-39a0-b8c7-bf6ec9c6ff0c",
           "bendingPoints":[

           ],
           "edgeAnchorLayoutData":[

           ]
         }
       },
       "labelLayoutData":{
         "3d878913-84f6-3198-8d04-646b988b7267":{
           "id":"3d878913-84f6-3198-8d04-646b988b7267",
           "position":{
             "x":0.0,
             "y":0.0
           }
         },
         "a4a0207b-73a1-3364-8aaa-92279f5b31d3":{
           "id":"a4a0207b-73a1-3364-8aaa-92279f5b31d3",
           "position":{
             "x":0.0,
             "y":0.0
           }
         },
         "a35c19a7-f2d8-3c93-a088-4f300d459566":{
           "id":"a35c19a7-f2d8-3c93-a088-4f300d459566",
           "position":{
             "x":0.0,
             "y":0.0
           }
         }
       }
     }
   }',
  'none',
  '2025.6.0-202506011650',
  '2025-10-07 13:41:41.521312+00',
  '2025-10-07 13:41:41.890948+00'
);
