-- Sample migration project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  'Migration Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '1d8aac3e-5fe7-4787-b0fc-1f8eb491cd5e',
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  'NodeDescription#labelExpression migration',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": {
       "diagram": "http://www.eclipse.org/sirius-web/diagram",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "content": [
       {
         "id": "9674f8f7-ff1a-4061-bb32-a4a235a9c2ca",
         "eClass": "view:View",
         "data": {
           "colorPalettes": [
             {
               "data": {
                 "colors": [
                   {
                     "data": {
                       "name": "color_empty",
                       "value": ""
                     },
                     "eClass": "view:FixedColor",
                     "id": "63184ddc-74c4-4888-bb65-418361689e2b"
                   }
                 ]
               },
               "eClass": "view:ColorPalette",
               "id": "d315989f-826f-490d-b898-d94200b0caa2"
             }
           ],
           "descriptions": [
             {
               "id": "22fb1f4d-109d-4e73-bff0-f7cd96fb5fbb",
               "eClass": "diagram:DiagramDescription",
               "data": {
                 "name": "NodeDescription#labelExpression migration",
                 "domainType": "flow::System",
                 "nodeDescriptions": [
                   {
                     "id": "6949ddfe-f480-473b-bc8a-6f2bdde07e4d",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "NodeWithoutImage migration",
                       "domainType": "flow::CompositeProcessor",
                       "labelExpression": "aql:''NodeWithoutImage''",
                       "childrenLayoutStrategy": {
                         "id": "20651d93-2ee5-41cb-b2bd-1e75958c73cf",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "id": "92fe9d3f-2c5b-41ab-81ea-8482c8cd57b9",
                         "eClass": "diagram:RectangularNodeStyleDescription",
                         "data": {
                           "borderColor": "//@colorPalettes.0/@colors.0",
                           "color": "//@colorPalettes.0/@colors.0"
                         }
                       }
                     }
                   },
                   {
                     "id": "88f95390-ac15-4381-ae82-7b23a2017bd4",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "NodeWithImage migration",
                       "domainType": "flow::CompositeProcessor",
                       "labelExpression": "aql:''NodeWithImage''",
                       "childrenLayoutStrategy": {
                         "id": "db92e810-5d51-4d0c-a6cf-6a1e00cea6d9",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "id": "fa35806a-bdd0-4ba7-81bf-8cfba8f8fef5",
                         "eClass": "diagram:ImageNodeStyleDescription"
                       }
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '14df1eb9-0915-4a62-ba83-b26ce5e2cfe1',
  'Migration NodeStyleDescriptionColor Studio',
  '2024-05-06 15:00:0.000',
  '2024-05-06 15:00:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '14df1eb9-0915-4a62-ba83-b26ce5e2cfe1',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  '14df1eb9-0915-4a62-ba83-b26ce5e2cfe1',
  '2024-05-06 15:00:0.000',
  '2024-05-06 15:00:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '42a2e4fd-1119-4d38-93bf-a036345e0f5d',
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  'NodeStyleDescription#color migration',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": {
       "diagram": "http://www.eclipse.org/sirius-web/diagram",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "content": [
       {
         "id": "9674f8f7-ff1a-4061-bb32-a4a235a9c2ca",
         "eClass": "view:View",
         "data": {
           "colorPalettes": [
             {
               "data": {
                 "colors": [
                   {
                     "data": {
                       "name": "color_empty",
                       "value": ""
                     },
                     "eClass": "view:FixedColor",
                     "id": "63184ddc-74c4-4888-bb65-418361689e2b"
                   }
                 ]
               },
               "eClass": "view:ColorPalette",
               "id": "d315989f-826f-490d-b898-d94200b0caa2"
             }
           ],
           "descriptions": [
             {
               "id": "22fb1f4d-109d-4e73-bff0-f7cd96fb5fbb",
               "eClass": "diagram:DiagramDescription",
               "data": {
                 "name": "NodeStyleDescription#color migration",
                 "domainType": "flow::System",
                 "nodeDescriptions": [
                   {
                     "id": "6949ddfe-f480-473b-bc8a-6f2bdde07e4d",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "NodeWithoutImage migration",
                       "domainType": "flow::CompositeProcessor",
                       "childrenLayoutStrategy": {
                         "id": "20651d93-2ee5-41cb-b2bd-1e75958c73cf",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "id": "92fe9d3f-2c5b-41ab-81ea-8482c8cd57b9",
                         "eClass": "diagram:RectangularNodeStyleDescription",
                         "data": {
                           "borderColor": "//@colorPalettes.0/@colors.0",
                           "color": "//@colorPalettes.0/@colors.0"
                         }
                       }
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
  '2024-05-06 15:00:0.000',
  '2024-05-06 15:00:0.000'
);

INSERT INTO representation_metadata (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  kind,
  created_on,
  last_modified_on
) VALUES (
  '35f1cd7b-e5bb-443d-95ef-bab372a92b0f',
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  '719d2b8f-ab70-438d-a999-306de10654a7',
  'siriusComponents://representationDescription?kind=TreeMap',
  'Hierarchy Migration',
  'siriusComponents://representation?type=TreeMap',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_content (
  id,
  representation_metadata_id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '8ad82a59-2fca-4024-92a2-11331a2bfbd1',
  '35f1cd7b-e5bb-443d-95ef-bab372a92b0f',
  '{
    "id": "35f1cd7b-e5bb-443d-95ef-bab372a92b0f",
    "descriptionId": "siriusComponents://representation?type=TreeMap",
    "targetObjectId": "",
    "label": "Migration Representation",
    "kind": "siriusComponents://representation?type=TreeMap",
    "children": [
     {
       "id": "7ec83ceb-19f0-33eb-8978-2e3eb4b81d30",
       "targetObjectId": "cb4a1580-0066-49ee-8170-578a6afd22d9",
       "label": "element1",
       "children": []
     }
    ]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000',
  'none',
  '0'
);


INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'a5441b64-83a5-4754-8794-57227bf8a322',
  'UserResizable Migration Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  'a5441b64-83a5-4754-8794-57227bf8a322',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  'a5441b64-83a5-4754-8794-57227bf8a322',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'a884a1a2-f7cc-4e61-8a0d-5ef96e76734d',
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  'NodeDescription#userResizable migration',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": {
       "diagram": "http://www.eclipse.org/sirius-web/diagram",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "content": [
       {
         "id": "d44190f4-5da1-42c5-955d-bfe884a84bd7",
         "eClass": "view:View",
         "data": {
           "colorPalettes": [
             {
               "data": {
                 "colors": [
                   {
                     "data": {
                       "name": "color_empty",
                       "value": ""
                     },
                     "eClass": "view:FixedColor",
                     "id": "4cca218a-89c9-4b8e-838b-ba8b13ab806d"
                   }
                 ]
               },
               "eClass": "view:ColorPalette",
               "id": "230ca8e1-2d22-4bd9-b1b6-f9b623d6cf95"
             }
           ],
           "descriptions": [
             {
               "id": "2677164f-963f-4ba7-a7f6-01f6a83b0c6d",
               "eClass": "diagram:DiagramDescription",
               "data": {
                 "name": "NodeDescription#userResizable migration",
                 "domainType": "flow::System",
                 "nodeDescriptions": [
                   {
                     "id": "ff5276cd-edeb-418c-8d52-d03edb958ceb",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "NodeWithUserResizableFalse migration",
                       "domainType": "flow::CompositeProcessor",
                       "userResizable": "false",
                       "childrenLayoutStrategy": {
                         "id": "20651d93-2ee5-41cb-b2bd-1e75958c73cf",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "id": "92fe9d3f-2c5b-41ab-81ea-8482c8cd57b9",
                         "eClass": "diagram:RectangularNodeStyleDescription",
                         "data": {
                           "borderColor": "//@colorPalettes.0/@colors.0",
                           "color": "//@colorPalettes.0/@colors.0"
                         }
                       }
                     }
                   },
                   {
                     "id": "88f95390-ac15-4381-ae82-7b23a2017bd4",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "NodeWithUserResizableTrue migration",
                       "domainType": "flow::CompositeProcessor",
                       "userResizable": "true",
                       "childrenLayoutStrategy": {
                         "id": "db92e810-5d51-4d0c-a6cf-6a1e00cea6d9",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "id": "fa35806a-bdd0-4ba7-81bf-8cfba8f8fef5",
                         "eClass": "diagram:ImageNodeStyleDescription"
                       }
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO representation_metadata (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  kind,
  created_on,
  last_modified_on
) VALUES (
  '9698833e-ffd4-435a-9aec-765622ce524e',
  'a5441b64-83a5-4754-8794-57227bf8a322',
  '79752a18-c7d8-41c0-8a27-a79ea9de09d8',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=e932123d-b916-3537-84d2-86a4f5873d93',
  'Diagram UserResizable Migration',
  'siriusComponents://representation?type=Diagram',
  '2024-07-04 9:42:0.000',
  '2024-07-04 9:42:0.000'
);
INSERT INTO representation_content (
  id,
  representation_metadata_id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '4682cc9d-f9a5-4364-9c5d-15f3cf25b225',
  '9698833e-ffd4-435a-9aec-765622ce524e',
  '{
    "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=e932123d-b916-3537-84d2-86a4f5873d93",
    "edges": [
      {
        "id": "e6049f1c-f281-3c83-bcf4-17fc95842de1",
        "type": "edge:straight",
        "targetObjectId": "57984ee9-af89-432d-8c1b-42b902791a3c",
        "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Relation",
        "targetObjectLabel": "standard",
        "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=262fe9c3-0eaa-362b-9e80-1b920e7bb5b7",
        "beginLabel": null,
        "centerLabel": {
          "id": "ea23f667-e18f-3aed-968d-5719e9c021db",
          "type": "label:edge-center",
          "text":"6",
          "position": {
            "x": -1.0,
            "y": -1.0
          },
          "size": {
            "width": -1.0,
            "height": -1.0
          },
          "alignment": {
            "x": -1.0,
            "y":-1.0
          },
          "style": {
            "color": "#B1BCBE",
            "fontSize": 14,
            "bold": false,
            "italic": false,
            "underline": false,
            "strikeThrough": false,
            "iconURL": []
          }
        },
        "endLabel": null,
        "sourceId": "9b410b8c-5fa1-3942-90b3-fdbf5c0689c5",
        "targetId": "82f6f57a-5611-3b5d-a6b0-4a31d60fcada",
        "modifiers": [],
        "state": "Normal",
        "style": {
          "size": 1,
          "lineStyle": "Dash",
          "sourceArrow": "None",
          "targetArrow": "InputClosedArrow",
          "color": "#B1BCBE"
        },
        "routingPoints":[],
        "sourceAnchorRelativePosition": {
          "x": -1.0,
          "y": -1.0
        },
        "targetAnchorRelativePosition": {
          "x": -1.0,
          "y": -1.0
        },
        "centerLabelEditable": true
      }
    ],
    "id": "9698833e-ffd4-435a-9aec-765622ce524e",
    "kind": "siriusComponents://representation?type=Diagram",
    "label": "Domain",
    "layoutData": {
        "edgeLayoutData": {
        },
        "labelLayoutData": {
        },
        "nodeLayoutData": {
            "9aab01cf-d990-3756-b487-a3e6a7420abd": {
                "id": "9aab01cf-d990-3756-b487-a3e6a7420abd",
                "position": {
                    "x": 72.0,
                    "y": 12.0
                },
                "resizedByUser": false,
                "size": {
                    "height": 70.0,
                    "width": 150.0
                }
            }
        }
    },
    "nodes": [
        {
            "borderNode": false,
            "borderNodes": [
            ],
            "childNodes": [
            ],
            "childrenLayoutStrategy": {
                "areChildNodesDraggable": true,
                "bottomGap": 0,
                "growableNodeIds": [
                ],
                "kind": "List",
                "topGap": 0
            },
            "collapsingState": "EXPANDED",
            "customizedProperties": [
            ],
            "userResizable": true,
            "defaultHeight": null,
            "defaultWidth": null,
            "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=fe82e80e-7308-35a9-986d-f010401063ee",
            "id": "9aab01cf-d990-3756-b487-a3e6a7420abd",
            "insideLabel": {
                "displayHeaderSeparator": true,
                "id": "a6bfa3c8-9cbb-3bd4-af78-314b83aadb21",
                "insideLabelLocation": "TOP_CENTER",
                "isHeader": true,
                "overflowStrategy": "NONE",
                "position": {
                    "x": -1.0,
                    "y": -1.0
                },
                "size": {
                    "width": -1.0,
                    "height": -1.0
                },
                "alignment": {
                    "x": -1.0,
                    "y":-1.0
                },
                "style": {
                    "background": "transparent",
                    "bold": false,
                    "borderColor": "black",
                    "borderRadius": 3,
                    "borderSize": 0,
                    "borderStyle": "Solid",
                    "color": "rgb(0, 0, 0)",
                    "fontSize": 14,
                    "iconURL": [
                        "/icons/full/obj16/Entity.svg"
                    ],
                    "italic": false,
                    "strikeThrough": false,
                    "underline": false
                },
                "text": "Root",
                "textAlign": "LEFT"
            },
            "labelEditable": false,
            "modifiers": [
            ],
            "outsideLabels": [
            ],
            "pinned": false,
            "position": {
                "x": -1.0,
                "y": -1.0
            },
            "size": {
                "height": -1.0,
                "width": -1.0
            },
            "state": "Normal",
            "targetObjectId": "47c3d0e1-099c-4cbe-aee9-5d6d4ad4e4be",
            "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Entity",
            "targetObjectLabel": "Root",
            "type": "node:rectangle"
        }
    ],
    "position": {
        "x": -1.0,
        "y": -1.0
    },
    "size": {
        "height": -1.0,
        "width": -1.0
    },
    "targetObjectId": "79752a18-c7d8-41c0-8a27-a79ea9de09d8"
  }',
  '2024-07-04 9:42:0.000',
  '2024-07-04 9:42:0.000',
  'none',
  '0'
);


INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '8ce6147e-1f5b-426f-b1be-dfeabd37a50a',
  'Migration DiagramLabelStyle#borderSize Studio',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '8ce6147e-1f5b-426f-b1be-dfeabd37a50a',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '65edc1f2-989c-4001-971b-29981179ebfa',
  '8ce6147e-1f5b-426f-b1be-dfeabd37a50a',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '65edc1f2-989c-4001-971b-29981179ebfa',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '65edc1f2-989c-4001-971b-29981179ebfa',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'fa110225-6b86-4bc1-b707-54c7c995cebf',
  '65edc1f2-989c-4001-971b-29981179ebfa',
  'NodeDescription#labelBorderStyle migration',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": {
       "diagram": "http://www.eclipse.org/sirius-web/diagram",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "content": [
       {
         "id": "567668c6-f841-4330-af9c-1a17ef28492b",
         "eClass": "view:View",
         "data": {
           "colorPalettes": [
             {
               "data": {
                 "colors": [
                   {
                     "data": {
                       "name": "color_empty",
                       "value": ""
                     },
                     "eClass": "view:FixedColor",
                     "id": "19e41e76-e016-4f15-abef-e762956f5275"
                   }
                 ]
               },
               "eClass": "view:ColorPalette",
               "id": "8c4140a3-a696-4016-a862-da474028fc2c"
             }
           ],
           "descriptions": [
             {
               "id": "c1a0ae1c-8420-4aa7-b443-1a05044076e2",
               "eClass": "diagram:DiagramDescription",
               "data": {
                 "name": "DiagramLabelStyle#borderSize migration",
                 "domainType": "flow::System",
                 "edgeDescriptions": [
                   {
                     "data": {
                       "centerLabelExpression": "",
                       "name": "LabelBorderSize Edge",
                       "semanticCandidatesExpression": "",
                       "sourceNodeDescriptions": [
                         "//@descriptions.0/@nodeDescriptions.0"
                       ],
                       "sourceNodesExpression": "aql:self",
                       "style": {
                         "data": {
                           "color": "//@colorPalettes.0/@colors.0"
                         },
                         "eClass": "diagram:EdgeStyle",
                         "id": "748824cb-cd5f-42fe-95a8-4f21172ba2ce"
                       },
                       "targetNodeDescriptions": [
                         "//@descriptions.0/@nodeDescriptions.1"
                       ],
                       "targetNodesExpression": "aql:self.linkedTo"
                     },
                     "eClass": "diagram:EdgeDescription",
                     "id": "c1077629-a4ec-4232-8eb5-5209b58c7464"
                   }
                 ],
                 "nodeDescriptions": [
                   {
                     "id": "4d1ec4d1-dd2c-4cb2-bbed-df02f0babbd8",
                     "eClass": "diagram:NodeDescription",
                     "data": {
                       "name": "LabelBorderSize migration",
                       "domainType": "flow::CompositeProcessor",
                       "insideLabel": {
                         "data": {
                           "style": {
                             "eClass": "diagram:InsideLabelStyle",
                             "id": "d70178c4-d36f-40d7-a3ab-ef8568e4ee2b"
                           }
                         },
                         "eClass": "diagram:InsideLabelDescription",
                         "id": "046b48ee-4554-4d8a-a457-5391b690d2a9"
                       },
                       "outsideLabels": [
                         {
                           "data": {
                             "style": {
                               "eClass": "diagram:OutsideLabelStyle",
                               "id": "c69b0853-26ab-40b0-979d-f0a4974ad865"
                             }
                           },
                           "eClass": "diagram:OutsideLabelDescription",
                           "id": "fd8638cc-8ebb-48e6-bf3f-2c09206f6a09"
                         },
                         {
                           "data": {
                             "style": {
                               "eClass": "diagram:OutsideLabelStyle",
                               "id": "601e5a1d-3d8a-49bc-96b9-f0c47ee594ee"
                             }
                           },
                           "eClass": "diagram:OutsideLabelDescription",
                           "id": "a8a2bf4c-82b7-4050-b4e7-09fd26a2f7c0"
                         }
                       ],
                       "childrenLayoutStrategy": {
                         "id": "558f8558-bee6-4ae9-8dc7-4344efe2b83e",
                         "eClass": "diagram:FreeFormLayoutStrategyDescription"
                       },
                       "style": {
                         "data": {
                            "background": "//@colorPalettes.0/@colors.0",
                            "borderColor": "//@colorPalettes.0/@colors.0"
                         },
                         "eClass": "diagram:RectangularNodeStyleDescription",
                         "id": "dacddb12-7c2c-4463-bf71-a7c3e68e2132"
                       }
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
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);

INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '8e4dc281-b458-4354-b2c8-a03b426b6966',
  'Migration NodeLabelStyle#showIcon Studio',
  '2024-07-18 15:00:0.000',
  '2024-07-18 15:00:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '8e4dc281-b458-4354-b2c8-a03b426b6966',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  '8e4dc281-b458-4354-b2c8-a03b426b6966',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '261b8eb9-7af9-47c8-9a8d-030dbffabcb0',
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  'NodeLabelStyle#showIcon migration',
  '{
      "json": { "version": "1.0", "encoding": "utf-8" },
      "ns": {
        "diagram": "http://www.eclipse.org/sirius-web/diagram",
        "view": "http://www.eclipse.org/sirius-web/view"
      },
      "content": [
        {
          "id": "9712889d-60e2-4c1d-9349-bd5de9b45d1b",
          "eClass": "view:View",
          "data": {
            "descriptions": [
              {
                "id": "3d1a4ce8-9ff2-4714-a3ce-6720b7b5c945",
                "eClass": "diagram:DiagramDescription",
                "data": {
                  "name": "NodeLabelStyle#showIcon migration",
                  "domainType": "flow::System",
                  "titleExpression": "NodeLabelStyle#showIcon diagram",
                  "nodeDescriptions": [
                    {
                      "id": "6b69cc8b-b1e8-4aed-9195-539351ac34d9",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "ShowIcon migration Node 1",
                        "domainType": "flow::CompositeProcessor",
                        "childrenLayoutStrategy": {
                          "id": "50b48332-c97e-4638-9912-feed821df798",
                          "eClass": "diagram:FreeFormLayoutStrategyDescription"
                        },
                        "style": {
                          "id": "e35c8df3-8286-47e4-8bba-b7e8342bdca2",
                          "eClass": "diagram:RectangularNodeStyleDescription"
                        },
                        "insideLabel": {
                          "id": "bd646979-6305-478d-9ab4-afeba966c4be",
                          "eClass": "diagram:InsideLabelDescription",
                          "data": {
                            "style": {
                              "id": "4a519202-e33a-4c9d-9f4d-be101165c7f3",
                              "eClass": "diagram:InsideLabelStyle",
                              "data": { "borderSize": "0", "showIcon": true }
                            }
                          }
                        }
                      }
                    },
                    {
                      "id": "6c5106eb-27fc-473a-bc55-461c7fcca989",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "ShowIcon migration Node 2",
                        "domainType": "flow::CompositeProcessor",
                        "childrenLayoutStrategy": {
                          "id": "bb2bef9c-17c3-43bd-9062-a9fa664be36b",
                          "eClass": "diagram:FreeFormLayoutStrategyDescription"
                        },
                        "style": {
                          "id": "be7cd982-20f6-429b-bd10-bae995ec8fb6",
                          "eClass": "diagram:RectangularNodeStyleDescription",
                          "data": {
                            "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#//@colorPalettes.1/@colors.0",
                            "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#//@colorPalettes.1/@colors.1"
                          }
                        },
                        "insideLabel": {
                          "id": "9738edfc-29db-49ae-bd43-ef3b4556eaaf",
                          "eClass": "diagram:InsideLabelDescription",
                          "data": {
                            "style": {
                              "id": "ac093a41-a0d6-412e-8dab-b12550fa7bf6",
                              "eClass": "diagram:InsideLabelStyle",
                              "data": { "borderSize": "0" }
                            }
                          }
                        }
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
  '2024-07-18 15:00:0.000',
  '2024-07-18 15:00:0.000'
);


INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '19d73d38-3de2-4d03-a8f1-ce36c2ed36db',
  'Migration SelectionDialogDescription#selectionCandidatesExpression Studio',
  '2024-07-18 15:00:0.000',
  '2024-07-18 15:00:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '19d73d38-3de2-4d03-a8f1-ce36c2ed36db',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  '19d73d38-3de2-4d03-a8f1-ce36c2ed36db',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '8652ae06-6754-4c46-aa4c-2c8db2c3b602',
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  'SelectionDialogDescription#selectionCandidatesExpression migration',
  '{
  "json": { "version": "1.0", "encoding": "utf-8" },
  "ns": { "diagram": "http://www.eclipse.org/sirius-web/diagram", "view": "http://www.eclipse.org/sirius-web/view" },
  "content": [
    {
      "id": "00deb638-5c32-4d94-983d-1d00517ac82f",
      "eClass": "view:View",
      "data": {
        "descriptions": [
          {
            "id": "683b678c-6263-4d21-b25a-502661859198",
            "eClass": "diagram:DiagramDescription",
            "data": {
              "name": "SelectionDialogDescription#selectionCandidatesExpression migration",
              "domainType": "wilson::Root",
              "titleExpression": "wilson diagram",
              "palette": {
                "id": "3e54cf6c-a1d0-4eaa-bfca-48fbb9a25695",
                "eClass": "diagram:DiagramPalette",
                "data": {
                  "nodeTools": [
                    {
                      "id": "b5b0dfc1-566c-48ae-9907-00f50a553dae",
                      "eClass": "diagram:NodeTool",
                      "data": {
                        "name": "Selection Tool",
                        "dialogDescription": {
                          "id": "6a4e7200-2040-46fb-89c3-9060f321ecf4",
                          "eClass": "diagram:SelectionDialogDescription",
                          "data": {
                            "selectionCandidatesExpression": "aql:self.eContents()",
                            "selectionMessage": "Select an element"
                          }
                        }
                      }
                    }
                  ]
                }
              }
            }
          }
        ]
      }
    }
  ]
}',
  '2024-07-18 15:00:0.000',
  '2024-07-18 15:00:0.000'
);

INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '590949b9-5d48-46ba-b206-29ad2473e5a5',
  'Migration InsideLabelStyle#displayHeaderSeparator Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '590949b9-5d48-46ba-b206-29ad2473e5a5',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
  '590949b9-5d48-46ba-b206-29ad2473e5a5',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
  'http://www.eclipse.org/sirius-web/diagram'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '6ab70d4b-f771-483f-aae6-566c6eb23b10',
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
  'InsideLabelStyle#displayHeaderSeparator migration',
  '{
     "json": { "version": "1.0", "encoding": "utf-8" },
     "ns": {
       "diagram": "http://www.eclipse.org/sirius-web/diagram",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "content": [
       {
         "id": "9674f8f7-ff1a-4061-bb32-a4a235a9c2ca",
         "eClass": "view:View",
         "data": {
           "colorPalettes": [
             {
               "data": {
                 "colors": [
                   {
                     "data": {
                       "name": "color_empty",
                       "value": ""
                     },
                     "eClass": "view:FixedColor",
                     "id": "63184ddc-74c4-4888-bb65-418361689e2b"
                   }
                 ]
               },
               "eClass": "view:ColorPalette",
               "id": "d315989f-826f-490d-b898-d94200b0caa2"
             }
           ],
           "descriptions": [
             {
               "id": "22fb1f4d-109d-4e73-bff0-f7cd96fb5fbb",
               "eClass": "diagram:DiagramDescription",
               "data": {
                 "name": "InsideLabelStyle#displayHeaderSeparator migration",
                 "domainType": "flow::System",
                 "nodeDescriptions": [
                    {
                      "id": "6b69cc8b-b1e8-4aed-9195-539351ac34d9",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "migration Node 1",
                        "domainType": "flow::CompositeProcessor",
                        "childrenLayoutStrategy": {
                          "id": "50b48332-c97e-4638-9912-feed821df798",
                          "eClass": "diagram:FreeFormLayoutStrategyDescription"
                        },
                        "style": {
                          "id": "e35c8df3-8286-47e4-8bba-b7e8342bdca2",
                          "eClass": "diagram:RectangularNodeStyleDescription"
                        },
                        "insideLabel": {
                          "id": "bd646979-6305-478d-9ab4-afeba966c4be",
                          "eClass": "diagram:InsideLabelDescription",
                          "data": {
                            "style": {
                              "id": "4a519202-e33a-4c9d-9f4d-be101165c7f3",
                              "eClass": "diagram:InsideLabelStyle",
                              "data": {
                                "borderSize": "0",
                                "displayHeaderSeparator": true,
                                "withHeader": true
                              }
                            }
                          }
                        }
                      }
                    },
                    {
                      "id": "6c5106eb-27fc-473a-bc55-461c7fcca989",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "migration Node 2",
                        "domainType": "flow::CompositeProcessor",
                        "childrenLayoutStrategy": {
                          "id": "bb2bef9c-17c3-43bd-9062-a9fa664be36b",
                          "eClass": "diagram:FreeFormLayoutStrategyDescription"
                        },
                        "style": {
                          "id": "be7cd982-20f6-429b-bd10-bae995ec8fb6",
                          "eClass": "diagram:RectangularNodeStyleDescription",
                          "data": {
                            "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#//@colorPalettes.1/@colors.0",
                            "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#//@colorPalettes.1/@colors.1"
                          }
                        },
                        "insideLabel": {
                          "id": "9738edfc-29db-49ae-bd43-ef3b4556eaaf",

                          "eClass": "diagram:InsideLabelDescription",
                          "data": {
                            "style": {
                              "id": "ac093a41-a0d6-412e-8dab-b12550fa7bf6",
                              "eClass": "diagram:InsideLabelStyle",
                              "data": {
                                "borderSize": "0",
                                "displayHeaderSeparator": false,
                                "withHeader": true
                              }
                            }
                          }
                        }
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
