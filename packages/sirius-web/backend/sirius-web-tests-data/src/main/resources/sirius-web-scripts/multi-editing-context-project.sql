
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

INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
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
  '
  {
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "flow":"http://www.obeo.fr/dsl/designer/sample/flow"
    },
    "content":[
      {
        "id":"01791d50-5f04-4a41-9e24-7833bd081051",
        "eClass":"flow:System",
        "data":{
          "name":"NewSystem",
          "elements":[
            {
              "id":"59e2326d-768b-45f2-abfa-51fb8e4cc377",
              "eClass":"flow:CompositeProcessor",
              "data":{
                "name":"CompositeProcessor"
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
  'false'
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
  '4e7e526f-b0c3-45f3-82d3-1148c99de300#4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '01791d50-5f04-4a41-9e24-7833bd081051',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38',
  'Topography',
  'siriusComponents://representation?type=Diagram',
  '2025-10-07 13:41:34.133346+00',
  '2025-10-07 13:41:34.133346+00',
  '',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300'
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
  '4e7e526f-b0c3-45f3-82d3-1148c99de300#4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '4e4a4c0e-9a24-48a5-a52a-97bbdc35fcf9',
  '4e7e526f-b0c3-45f3-82d3-1148c99de300',
  '
  {
    "id":"f53ce563-2375-4189-a83f-fcbaca070db5",
    "kind":"siriusComponents://representation?type=Diagram",
    "targetObjectId":"01791d50-5f04-4a41-9e24-7833bd081051",
    "descriptionId":"siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
    "nodes":[
      {
        "id":"9d5cdcfe-c35c-3ab4-9ecf-a327e655a049",
        "type":"node:rectangle",
        "targetObjectId":"59e2326d-768b-45f2-abfa-51fb8e4cc377",
        "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
        "targetObjectLabel":"CompositeProcessor",
        "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
        "borderNode":false,
        "initialBorderNodePosition":"NONE",
        "modifiers":[

        ],
        "state":"Normal",
        "collapsingState":"EXPANDED",
        "insideLabel":{
          "id":"b8ba4173-826a-383f-abc0-7af3335f6770",
          "text":"CompositeProcessor",
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

        ],
        "defaultWidth":150,
        "defaultHeight":70,
        "labelEditable":true,
        "deletable":true,
        "pinned":false,
        "customizedStyleProperties":[

        ]
      }
    ],
    "edges":[

    ],
    "layoutData":{
      "nodeLayoutData":{
        "9d5cdcfe-c35c-3ab4-9ecf-a327e655a049":{
          "id":"9d5cdcfe-c35c-3ab4-9ecf-a327e655a049",
          "position":{
            "x":31.038314176245166,
            "y":-55.455938697318
          },
          "size":{
            "width":199.0546875,
            "height":77.0
          },
          "resizedByUser":true,
          "movedByUser":true,
          "handleLayoutData":[

          ],
          "minComputedSize":{
            "width":191.140625,
            "height":70.0
          }
        }
      },
      "edgeLayoutData":{

      },
      "labelLayoutData":{

      }
    }
  }
  ',
  'none',
  '2025.6.0-202506011650',
  '2025-10-07 13:41:34.167224+00',
  '2025-10-07 13:41:34.611967+00'
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
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  'Flow',
  '
  {
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "flow":"http://www.obeo.fr/dsl/designer/sample/flow"
    },
    "content":[
      {
        "id":"01791d50-5f04-4a41-9e24-7833bd081051",
        "eClass":"flow:System",
        "data":{
          "name":"NewSystem2",
          "elements":[
            {
              "id":"59e2326d-768b-45f2-abfa-51fb8e4cc377",
              "eClass":"flow:CompositeProcessor",
              "data":{
                "name":"CompositeProcessor2"
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
  'false'
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
  'a8aac84f-184b-4caf-90a8-ed7be4eaa243',
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  'Alternate',
  '
  {
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "flow":"http://www.obeo.fr/dsl/designer/sample/flow"
    },
    "content":[
      {
        "id":"b1483511-ee86-49a9-a194-8e50e51f1ee4",
        "eClass":"flow:System",
        "data":{
          "name":"NewSystem3",
          "elements":[]
        }
      }
    ]
  }
  ',
  '2025-10-07 13:41:41.549341+00',
  '2025-10-07 13:41:41.549341+00',
  'false'
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
  '3f155db4-6771-459a-9fec-6a3a77238d31#4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '01791d50-5f04-4a41-9e24-7833bd081051',
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
  '3f155db4-6771-459a-9fec-6a3a77238d31#4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '4687c6ae-e968-4680-a4ad-5db22dd7f6ef',
  '3f155db4-6771-459a-9fec-6a3a77238d31',
  '
  {
    "id":"f53ce563-2375-4189-a83f-fcbaca070db5",
    "kind":"siriusComponents://representation?type=Diagram",
    "targetObjectId":"01791d50-5f04-4a41-9e24-7833bd081051",
    "descriptionId":"siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
    "nodes":[
      {
        "id":"9d5cdcfe-c35c-3ab4-9ecf-a327e655a049",
        "type":"node:rectangle",
        "targetObjectId":"59e2326d-768b-45f2-abfa-51fb8e4cc377",
        "targetObjectKind":"siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
        "targetObjectLabel":"CompositeProcessor",
        "descriptionId":"siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
        "borderNode":false,
        "initialBorderNodePosition":"NONE",
        "modifiers":[

        ],
        "state":"Normal",
        "collapsingState":"EXPANDED",
        "insideLabel":{
          "id":"b8ba4173-826a-383f-abc0-7af3335f6770",
          "text":"CompositeProcessor2",
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

        ],
        "defaultWidth":150,
        "defaultHeight":70,
        "labelEditable":true,
        "deletable":true,
        "pinned":false,
        "customizedStyleProperties":[

        ]
      }
    ],
    "edges":[

    ],
    "layoutData":{
      "nodeLayoutData":{
        "9d5cdcfe-c35c-3ab4-9ecf-a327e655a049":{
          "id":"9d5cdcfe-c35c-3ab4-9ecf-a327e655a049",
          "position":{
            "x":31.038314176245166,
            "y":-55.455938697318
          },
          "size":{
            "width":199.0546875,
            "height":77.0
          },
          "resizedByUser":true,
          "movedByUser":true,
          "handleLayoutData":[

          ],
          "minComputedSize":{
            "width":191.140625,
            "height":70.0
          }
        }
      },
      "edgeLayoutData":{

      },
      "labelLayoutData":{

      }
    }
  }
  ',
  'none',
  '2025.6.0-202506011650',
  '2025-10-07 13:41:41.521312+00',
  '2025-10-07 13:41:41.890948+00'
);
