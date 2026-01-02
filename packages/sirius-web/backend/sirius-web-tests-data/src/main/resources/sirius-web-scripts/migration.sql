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
  created_on,
  last_modified_on
) VALUES (
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
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
  '20f74a0a-be4d-4fda-acda-d4e2f91ffbce',
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  'main',
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
  is_read_only,
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
  false,
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
  created_on,
  last_modified_on
) VALUES (
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  '2024-05-06 15:00:0.000',
  '2024-05-06 15:00:0.000'
);
INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'cafe8acf-2586-4209-9550-66313839b5f8',
  '14df1eb9-0915-4a62-ba83-b26ce5e2cfe1',
  'ab42d745-3bae-45ee-9839-12aff3d431cf',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  is_read_only,
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
  false,
  '2024-05-06 15:00:0.000',
  '2024-05-06 15:00:0.000'
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
  '35f1cd7b-e5bb-443d-95ef-bab372a92b0f',
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
  '719d2b8f-ab70-438d-a999-306de10654a7',
  'siriusComponents://representationDescription?kind=TreeMap',
  'Hierarchy Migration',
  '',
  'siriusComponents://representation?type=TreeMap',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO representation_content (
  id,
  semantic_data_id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '35f1cd7b-e5bb-443d-95ef-bab372a92b0f',
  '89d67892-0cc9-4ca4-b30e-28688470c0d4',
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
  created_on,
  last_modified_on
) VALUES (
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
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
  'e4c9a564-b7c4-443b-87c3-a307bd51b893',
  'a5441b64-83a5-4754-8794-57227bf8a322',
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  'main',
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
  is_read_only,
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
  false,
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
  '9698833e-ffd4-435a-9aec-765622ce524e',
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
  '79752a18-c7d8-41c0-8a27-a79ea9de09d8',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=e932123d-b916-3537-84d2-86a4f5873d93',
  'Diagram UserResizable Migration',
  '',
  'siriusComponents://representation?type=Diagram',
  '2024-07-04 9:42:0.000',
  '2024-07-04 9:42:0.000'
);

INSERT INTO representation_content (
  id,
  semantic_data_id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '9698833e-ffd4-435a-9aec-765622ce524e',
  'e4a1dfda-81dd-481c-be93-63d96c6e7eb1',
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
          "a6bfa3c8-9cbb-3bd4-af78-314b83aadb21": {
            "id": "a6bfa3c8-9cbb-3bd4-af78-314b83aadb21",
            "position": {
              "x": 0.0,
              "y": 0.0
            }
          }
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
  created_on,
  last_modified_on
) VALUES (
  '65edc1f2-989c-4001-971b-29981179ebfa',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'aafc2138-5022-4a49-83e0-533816c9f8f3',
  '8ce6147e-1f5b-426f-b1be-dfeabd37a50a',
  '65edc1f2-989c-4001-971b-29981179ebfa',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  is_read_only,
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
  false,
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
  created_on,
  last_modified_on
) VALUES (
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'b3425015-23cc-4d50-a8aa-d4de4f0f878c',
  '8e4dc281-b458-4354-b2c8-a03b426b6966',
  '6d89dded-c843-475f-91b4-e2c91b9a883a',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  is_read_only,
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
  false,
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
  created_on,
  last_modified_on
) VALUES (
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  '2024-07-01 15:00:0.000',
  '2024-07-01 15:00:0.000'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '8d92b4d1-28c1-4929-9de9-16fdc190e6e6',
  '19d73d38-3de2-4d03-a8f1-ce36c2ed36db',
  '06d828d9-c2c6-46d0-a9c4-7fabd588755b',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  is_read_only,
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
  false,
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
  created_on,
  last_modified_on
) VALUES (
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
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
  'e556ac41-c343-452c-b60d-40a6eb2eec69',
  '590949b9-5d48-46ba-b206-29ad2473e5a5',
  'dc9643f8-b1ce-4c93-a176-379063d42b32',
  'main',
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
  is_read_only,
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
  false,
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);


INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'fc56cb7e-a2a4-477f-89a7-58bb34a2a4b3',
  'Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  'fc56cb7e-a2a4-477f-89a7-58bb34a2a4b3',
  'siriusComponents://nature?kind=studio'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '21d28dd9-1675-4873-a39a-678b19b23ed6',
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
  '122e8c46-7961-41ea-8f5b-64b0c653d22f',
  'fc56cb7e-a2a4-477f-89a7-58bb34a2a4b3',
  '21d28dd9-1675-4873-a39a-678b19b23ed6',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '21d28dd9-1675-4873-a39a-678b19b23ed6',
  'http://www.eclipse.org/sirius-web/view'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '21d28dd9-1675-4873-a39a-678b19b23ed6',
  'http://www.eclipse.org/sirius-web/form'
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
  '4ee1ea69-c79b-4635-937c-2f8de13421ba',
  '21d28dd9-1675-4873-a39a-678b19b23ed6',
  'Form View for WidgetDescriptionStyleLayoutPropertiesMigrationParticipant',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "form":"http://www.eclipse.org/sirius-web/form",
       "view":"http://www.eclipse.org/sirius-web/view"
     },
     "content":[
       {
         "id":"c8c9a6e2-a697-4a98-b0ce-3b9c0d077ae9",
         "eClass":"view:View",
         "data":{
           "descriptions":[
             {
               "id":"deaa26de-6383-4c36-baba-2feb8a6e675e",
               "eClass":"form:FormDescription",
               "data":{
                 "name":"Form View for WidgetDescriptionStyleLayoutPropertiesMigrationParticipant",
                 "domainType":"buck::Human",
                 "pages":[
                   {
                     "id":"7f2c5d12-16c5-4d99-becc-b64cfcf5f6d2",
                     "eClass":"form:PageDescription",
                     "data":{
                       "name":"Human",
                       "labelExpression":"aql:self.name",
                       "domainType":"buck::Human",
                       "groups":[
                         {
                           "id":"f4045d7d-bd11-4cca-80b6-7b27f1792bec",
                           "eClass":"form:GroupDescription",
                           "data":{
                             "name":"Properties",
                             "labelExpression":"Properties",
                             "children":[
                               {
                                 "id":"8cdbf72d-5a4c-4a33-8a6f-95706dea27a6",
                                 "eClass":"form:TextfieldDescription",
                                 "data":{
                                   "name":"Name",
                                   "labelExpression":"Name",
                                   "helpExpression":"The name of the human",
                                   "valueExpression":"aql:self.name",
                                   "body":[
                                     {
                                       "id":"a3b5b77d-45e6-49d1-b989-81ba9ba86ae2",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"6161cb1b-f549-45bc-ba56-ea1e677e4d19",
                                             "eClass":"view:SetValue",
                                             "data":{
                                               "featureName":"name",
                                               "valueExpression":"aql:newValue"
                                             }
                                           }
                                         ]
                                       }
                                     }
                                   ],
                                   "style":{
                                   "id":"3e446591-0b53-4b90-96a4-496516e881b4",
                                     "eClass":"form:TextfieldDescriptionStyle",
                                     "data":{
                                     }
                                   }
                                 }
                               },
                               {
                                 "id":"452706cd-9a89-42bf-98c6-039ba43d62e5",
                                 "eClass":"form:TextAreaDescription",
                                 "data":{
                                   "name":"Description",
                                   "labelExpression":"Description",
                                   "helpExpression":"The description of the human",
                                   "valueExpression":"aql:self.description",
                                   "body":[
                                     {
                                       "id":"6e52d5f2-eca7-44a9-9974-727253f56824",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"ed60f118-7cdc-48c8-a7fc-d19e24d1e4e2",
                                             "eClass":"view:SetValue",
                                             "data":{
                                               "featureName":"description",
                                               "valueExpression":"aql:newValue"
                                             }
                                           }
                                         ]
                                       }
                                     }
                                   ]
                                 }
                               },
                               {
                                 "id":"2976d3d8-f936-4f2e-b327-0982dcfd2ac9",
                                 "eClass":"form:CheckboxDescription",
                                 "data":{
                                   "name":"Promoted",
                                   "labelExpression":"Promoted",
                                   "helpExpression":"Has this human been promoted?",
                                   "valueExpression":"aql:self.promoted",
                                   "body":[
                                     {
                                       "id":"33ef0de5-4ded-44f3-a4d3-800d0b08cfcc",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"a089771a-2e0f-4411-8503-b72904e0ec5a",
                                             "eClass":"view:SetValue",
                                             "data":{
                                               "featureName":"promoted",
                                               "valueExpression":"aql:newValue"
                                             }
                                           }
                                         ]
                                       }
                                     }
                                   ],
                                   "style":{
                                   "id":"bc355252-88c8-47e8-9762-4a3ed3373170",
                                     "eClass":"form:CheckboxDescriptionStyle",
                                     "data":{
                                       "labelPlacement":"end"
                                     }
                                   }
                                 }
                               },
                               {
                                 "id":"99310282-5cba-47e8-93a2-70decb7529f3",
                                 "eClass":"form:DateTimeDescription",
                                 "data":{
                                   "name":"BirthDate",
                                   "labelExpression":"Birth Date",
                                   "helpExpression":"The birth date of the human",
                                   "stringValueExpression":"aql:self.birthDate",
                                   "type":"DATE",
                                   "style":{
                                     "id":"64994c02-30c8-434b-98c1-68e061abf86a",
                                     "eClass":"form:DateTimeDescriptionStyle",
                                     "data":{
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
               }
             }
           ]
         }
       }
     ]
   }',
  false,
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Test project for TreeDescriptionIconURLExpressionMigrationParticipantTests 
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '718868e6-de6d-4b00-b84f-56802aa6d5d0',
  'Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  '718868e6-de6d-4b00-b84f-56802aa6d5d0',
  'siriusComponents://nature?kind=studio'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '340026b5-1363-4c93-8c2b-7f11188cca8b',
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
  'e5193923-6b76-422e-828c-5773c0d3f991',
  '718868e6-de6d-4b00-b84f-56802aa6d5d0',
  '340026b5-1363-4c93-8c2b-7f11188cca8b',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '340026b5-1363-4c93-8c2b-7f11188cca8b',
  'http://www.eclipse.org/sirius-web/tree'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '340026b5-1363-4c93-8c2b-7f11188cca8b',
  'http://www.eclipse.org/sirius-web/view'
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
  'd92b61ec-46a3-4c69-9e08-c5f625a9ac6d',
  '340026b5-1363-4c93-8c2b-7f11188cca8b',
  'TreeDescription#iconUrlDescription migration',
  '{
     "json": {
     "version": "1.0",
     "encoding": "utf-8"
     },
     "ns": {
       "tree": "http://www.eclipse.org/sirius-web/tree",
       "view": "http://www.eclipse.org/sirius-web/view"
     },
     "migration": {
       "lastMigrationPerformed": "WidgetDescriptionStyleLayoutPropertiesMigrationParticipant",
       "migrationVersion": "2024.1.0-202411081600"
     },
     "content": [
       {
         "id": "a4168469-6c99-4685-a847-7a81d9684ce9",
         "eClass": "view:View",
         "data": {
           "descriptions": [
             {
               "id": "df899cf6-2f11-47d4-b9ac-def01094b84e",
               "eClass": "tree:TreeDescription",
               "data": {
                 "name": "TreeDescription#iconUrlDescription migration",
                 "domainType": "buck::Human",
                 "titleExpression": "New Tree Representation",
                 "kindExpression": "root",
                 "iconURLExpression": "iconUrl",
                 "treeItemIdExpression": "root",
                 "treeItemObjectExpression": "aql:self",
                 "elementsExpression": "aql:self",
                 "hasChildrenExpression": "aql:false",
                 "treeItemLabelDescriptions": [
                   {
                     "id": "6fb8754f-b51a-42ac-b9ce-e2c4a7194af9",
                     "eClass": "tree:TreeItemLabelDescription",
                     "data": {
                       "name": "Label",
                       "children": [
                         {
                           "id": "fecd22d2-66b0-4ced-95d1-04f969c04aac",
                           "eClass": "tree:TreeItemLabelFragmentDescription",
                           "data": {
                           "labelExpression": "aql:self.name"
                           }
                         }
                       ]
                     }
                   }
                 ]
               }
             }
           ],
           "colorPalettes": [
             {
               "id": "648a95a6-5ec3-462f-8e68-f3206463e53f",
               "eClass": "view:ColorPalette",
               "data": {
                 "colors": [
                   {
                     "id": "0ea0f185-849a-4644-a811-713406eabb8c",
                     "eClass": "view:FixedColor",
                     "data": { "name": "color_dark", "value": "#002639" }
                   },
                   {
                     "id": "03cadcd8-a8e7-4b11-a404-df32bb72b6fc",
                     "eClass": "view:FixedColor",
                     "data": { "name": "color_blue", "value": "#E5F5F8" }
                   },
                   {
                     "id": "07fc0265-6a78-4ecc-842f-7867dc4f634e",
                     "eClass": "view:FixedColor",
                     "data": { "name": "color_green", "value": "#B1D8B7" }
                   },
                   {
                     "id": "4dfe7438-03b0-453b-a6fd-57ae128b4a39",
                     "eClass": "view:FixedColor",
                     "data": { "name": "border_blue", "value": "#33B0C3" }
                   },
                   {
                     "id": "a48045ae-dd76-47e9-8b28-02f8f9ed0d1a",
                     "eClass": "view:FixedColor",
                     "data": { "name": "border_green", "value": "#76B947" }
                   },
                   {
                     "id": "afcc8880-c5ea-4fb7-a35f-f91c9fb44e55",
                     "eClass": "view:FixedColor",
                     "data": {
                       "name": "color_transparent",
                       "value": "transparent"
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
  false,
  '2024-04-12 9:42:0.000',
  '2024-04-12 9:42:0.000'
);

-- Test project for NodePaletteDeleteFromDiagramMigrationParticipantTests
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'b1f064d3-b928-319d-8853-022b0c5dd63a',
  'NodePaletteDeleteFromDiagramMigrationParticipantStudio',
  '2025-05-26 10:00:0.000',
  '2025-05-26 10:00:0.000'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  'b1f064d3-b928-319d-8853-022b0c5dd63a',
  'siriusComponents://nature?kind=studio'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'c81968f1-73c8-3973-bdae-7a2997132706',
  '2025-05-26 10:00:0.000',
  '2025-05-26 10:00:0.000'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '630a0daf-07ec-4640-8ad2-f4f61a920f92',
  'b1f064d3-b928-319d-8853-022b0c5dd63a',
  'c81968f1-73c8-3973-bdae-7a2997132706',
  'main',
  '2025-05-26 10:00:0.000',
  '2025-05-26 10:00:0.000'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'c81968f1-73c8-3973-bdae-7a2997132706',
  'http://www.eclipse.org/sirius-web/diagram'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'c81968f1-73c8-3973-bdae-7a2997132706',
  'http://www.eclipse.org/sirius-web/view'
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
  '02fddab4-1ae2-4d58-aa91-41552d5a2395',
  'c81968f1-73c8-3973-bdae-7a2997132706',
  'NodePalette#DeleteFromDiagram migration',
  '{
    "json": {
      "version": "1.0",
      "encoding": "utf-8"
    },
    "ns": {
      "diagram": "http://www.eclipse.org/sirius-web/diagram",
      "view": "http://www.eclipse.org/sirius-web/view"
    },
    "content": [
      {
        "id": "e87cbdac-6815-4675-90ee-cdbd4d39221f",
        "eClass": "view:View",
        "data": {
          "descriptions": [
            {
              "id": "e02836cd-2631-4233-bbd9-2aba5049b06d",
              "eClass": "diagram:DiagramDescription",
              "data": {
                "name": "NodePalette#DeleteFromDiagram migration",
                "domainType": "chaplygin::Root",
                "titleExpression": "chaplygin diagram",
                "nodeDescriptions": [
                  {
                    "id": "8dcec73a-1f46-44ba-acff-351b2d954eaa",
                    "eClass": "diagram:NodeDescription",
                    "data": {
                      "name": "Entity1 Node",
                      "domainType": "chaplygin::Entity1",
                      "synchronizationPolicy": "UNSYNCHRONIZED",
                      "palette": {
                        "id": "f075fb93-73e6-4fed-ab57-a59d7d1dc8a0",
                        "eClass": "diagram:NodePalette",
                        "data": {
                          "deleteTool": {
                            "id": "8b5d6d23-26b4-4279-adc5-2b15b37f8e59",
                            "eClass": "diagram:DeleteTool",
                            "data": {
                              "name": "Delete",
                              "body": [
                                {
                                  "id": "a9338e15-f5a4-4963-b4c4-48d95da7b842",
                                  "eClass": "view:ChangeContext",
                                  "data": {
                                    "expression": "aql:self.defaultDelete()"
                                  }
                                }
                              ]
                            }
                          },
                          "labelEditTool": {
                            "id": "beadfd82-f254-491c-a810-8b03535d9737",
                            "eClass": "diagram:LabelEditTool",
                            "data": {
                              "name": "Edit Label",
                              "body": [
                                {
                                  "id": "ade6614e-400d-45a7-a155-c75175de69fd",
                                  "eClass": "view:ChangeContext",
                                  "data": {
                                    "expression": "aql:self.defaultEditLabel(newLabel)"
                                  }
                                }
                              ]
                            }
                          },
                          "quickAccessTools": [],
                          "edgeTools": [],
                          "toolSections": []
                        }
                      },
                      "style": {
                        "id": "d910ff27-12f7-47ca-bc36-5aa82539cd82",
                        "eClass": "diagram:RectangularNodeStyleDescription",
                        "data": {
                          "borderColor": "c8e96959-0fd3-467e-af66-adf32a1a5f8b",
                          "background": "40f8e69d-a875-4bd5-9be8-6a0e89ae2a36"
                        }
                      }
                    }
                  },
                  {
                    "id": "5b3bd157-6222-4545-98a7-760b7dc95751",
                    "eClass": "diagram:NodeDescription",
                    "data": {
                      "name": "Entity2 Node",
                      "domainType": "chaplygin::Entity2",
                      "palette": {
                        "id": "8ba50bdf-4354-4c1e-81d8-74a5436d9639",
                        "eClass": "diagram:NodePalette",
                        "data": {
                          "deleteTool": {
                            "id": "6ef277cd-1ef5-46b5-aaf1-6dae40d39420",
                            "eClass": "diagram:DeleteTool",
                            "data": {
                              "name": "Delete",
                              "body": [
                                {
                                  "id": "9072bf72-6a30-43df-ada7-5813e521c34a",
                                  "eClass": "view:ChangeContext",
                                  "data": {
                                    "expression": "aql:self.defaultDelete()"
                                  }
                                }
                              ]
                            }
                          },
                          "labelEditTool": {
                            "id": "584a6455-ecbb-46fb-a465-4b0bb50686cd",
                            "eClass": "diagram:LabelEditTool",
                            "data": {
                              "name": "Edit Label",
                              "body": [
                                {
                                  "id": "2d389853-7fdd-44be-bf95-0f189d333592",
                                  "eClass": "view:ChangeContext",
                                  "data": {
                                    "expression": "aql:self.defaultEditLabel(newLabel)"
                                  }
                                }
                              ]
                            }
                          },
                          "toolSections": []
                        }
                      },
                      "style": {
                        "id": "6b414ab0-1334-472f-a9d8-f4680cdb8c69",
                        "eClass": "diagram:RectangularNodeStyleDescription",
                        "data": {
                          "borderColor": "0a1efb1d-58ca-440d-86bd-c3ad3ff87471",
                          "background": "cfa08285-18a5-4e01-8abd-3da01f048937"
                        }
                      }
                    }
                  }
                ],
                "edgeDescriptions": []
              }
            }
          ],
          "colorPalettes": [
            {
              "id": "ab3dbd46-3377-40f5-941f-57603cfe7dd4",
              "eClass": "view:ColorPalette",
              "data": {
                "colors": [
                  {
                    "id": "34f254c5-88ae-44d9-93bc-7ed33b5d15ae",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "color_dark",
                      "value": "#002639"
                    }
                  },
                  {
                    "id": "40f8e69d-a875-4bd5-9be8-6a0e89ae2a36",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "color_blue",
                      "value": "#E5F5F8"
                    }
                  },
                  {
                    "id": "cfa08285-18a5-4e01-8abd-3da01f048937",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "color_green",
                      "value": "#B1D8B7"
                    }
                  },
                  {
                    "id": "c8e96959-0fd3-467e-af66-adf32a1a5f8b",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "border_blue",
                      "value": "#33B0C3"
                    }
                  },
                  {
                    "id": "0a1efb1d-58ca-440d-86bd-c3ad3ff87471",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "border_green",
                      "value": "#76B947"
                    }
                  },
                  {
                    "id": "2a4a6d63-14d2-4a4c-a9d4-c98a38a73b75",
                    "eClass": "view:FixedColor",
                    "data": {
                      "name": "color_transparent",
                      "value": "transparent"
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
  false,
  '2025-05-26 10:00:0.000',
  '2025-05-26 10:00:0.000'
);

INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'c951f08b-e0c7-4367-975c-ef236ead2100',
  'Migration Studio',
  '2025-05-14 12:00:0.000',
  '2025-05-14 12:00:0.000'
);

INSERT INTO nature (
  project_id,
  name
) VALUES (
  'c951f08b-e0c7-4367-975c-ef236ead2100',
  'siriusComponents://nature?kind=studio'
);

INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  '2025-05-14 12:00:0.000',
  '2025-05-14 12:00:0.000'
);

INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '5b55243b-8405-4537-8e09-8c05185f7c25',
  'c951f08b-e0c7-4367-975c-ef236ead2100',
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  'main',
  '2025-05-14 12:00:0.000',
  '2025-05-14 12:00:0.000'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  'http://www.eclipse.org/sirius-web/view'
);

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  'http://www.eclipse.org/sirius-web/diagram'
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
  '3d6b34cb-4090-4f27-8c3b-e073fd79ed2d',
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  'NodeDescription#layoutStrategy migration',
  '{
      "json": {
        "version": "1.0",
        "encoding": "utf-8"
      },
      "ns": {
        "diagram": "http://www.eclipse.org/sirius-web/diagram",
        "view": "http://www.eclipse.org/sirius-web/view"
      },
      "content": [
        {
          "id": "2e1c0752-06b4-4b30-a739-0ec2ebf0bacb",
          "eClass": "view:View",
          "data": {
            "descriptions": [
              {
                "id": "6d5de90a-a35b-40ce-afe5-154eed6a9fe0",
                "eClass": "diagram:DiagramDescription",
                "data": {
                  "name": "NodeDescription#layoutStrategy migration",
                  "domainType": "chaplygin::Root",
                  "nodeDescriptions": [
                    {
                      "id": "021022da-66ac-47bf-a718-a3d2423838d7",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "Node",
                        "childrenLayoutStrategy": {
                          "id": "a38d5fa1-d309-4921-b47a-ba23a19a53c3",
                          "eClass": "diagram:ListLayoutStrategyDescription",
                          "data": {
                            "areChildNodesDraggableExpression": "aql:false",
                            "topGapExpression": "10",
                            "bottomGapExpression": "20",
                            "growableNodes": [
                              "668ed053-7661-4d9d-a166-5edb027d75db"
                            ]
                          }
                        },
                        "style": {
                          "id": "be60a21a-7573-4a84-ae05-feadd351104e",
                          "eClass": "diagram:RectangularNodeStyleDescription",
                          "data": {
                            "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                            "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#0afc1183-89e0-4854-8578-884dccce74b7"
                          }
                        },
                        "conditionalStyles": [
                          {
                            "id": "237e3934-7b28-42b4-9ecd-91a554455b70",
                            "eClass": "diagram:ConditionalNodeStyle",
                            "data": {
                              "style": {
                                "id": "4252a8ee-3188-40f9-9399-db2c93b4e327",
                                "eClass": "diagram:ImageNodeStyleDescription",
                                "data": {
                                  "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                                  "shape": "3c0ea081-3f50-4774-a54a-36a361442e94"
                                }
                              }
                            }
                          }
                        ],
                        "childrenDescriptions": [
                          {
                            "id": "668ed053-7661-4d9d-a166-5edb027d75db",
                            "eClass": "diagram:NodeDescription",
                            "data": {
                              "name": "Sub-node",
                              "childrenLayoutStrategy": {
                                "id": "610a8024-10b5-48cd-bab0-fa1b61cfc9a4",
                                "eClass": "diagram:FreeFormLayoutStrategyDescription"
                              },
                              "style": {
                                "id": "d761ff71-0216-47ac-86b3-a932df9e007a",
                                "eClass": "diagram:RectangularNodeStyleDescription",
                                "data": {
                                  "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                                  "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#0afc1183-89e0-4854-8578-884dccce74b7"
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
          }
        }
      ]
    }',
  false,
  '2025-05-14 12:00:0.000',
  '2025-05-14 12:00:0.000'
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
  '2de9aba3-31e3-4334-8c9c-e6d73af7efc4',
  '402f51c9-96df-46f2-b39b-35ebd506ff31',
  'NodeDescription#layoutStrategy already migrate',
  '{
      "json": {
        "version": "1.0",
        "encoding": "utf-8"
      },
      "ns": {
        "diagram": "http://www.eclipse.org/sirius-web/diagram",
        "view": "http://www.eclipse.org/sirius-web/view"
      },
      "content": [
        {
          "id": "2e1c0752-06b4-4b30-a739-0ec2ebf0bacb",
          "eClass": "view:View",
          "data": {
            "descriptions": [
              {
                "id": "6d5de90a-a35b-40ce-afe5-154eed6a9fe0",
                "eClass": "diagram:DiagramDescription",
                "data": {
                  "name": "NodeDescription#layoutStrategy already migrate",
                  "domainType": "chaplygin::Root",
                  "nodeDescriptions": [
                    {
                      "id": "021022da-66ac-47bf-a718-a3d2423838d7",
                      "eClass": "diagram:NodeDescription",
                      "data": {
                        "name": "Node",
                        "style": {
                          "id": "be60a21a-7573-4a84-ae05-feadd351104e",
                          "eClass": "diagram:RectangularNodeStyleDescription",
                          "data": {
                            "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                            "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#0afc1183-89e0-4854-8578-884dccce74b7",
                            "childrenLayoutStrategy": {
                              "id": "a38d5fa1-d309-4921-b47a-ba23a19a53c3",
                              "eClass": "diagram:ListLayoutStrategyDescription",
                              "data": {
                                "areChildNodesDraggableExpression": "aql:false",
                                "topGapExpression": "10",
                                "bottomGapExpression": "20"
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
  false,
  '2025-05-14 12:00:0.000',
  '2025-05-14 12:00:0.000'
);
