/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
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

INSERT INTO representation_data (
  id,
  project_id,
  target_object_id,
  description_id,
  label,
  kind,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '35f1cd7b-e5bb-443d-95ef-bab372a92b0f',
  'a3b86086-23f5-41cb-97b9-5ac7234a98af',
  '719d2b8f-ab70-438d-a999-306de10654a7',
  'siriusComponents://representationDescription?kind=TreeMap',
  'Hierarchy Migration',
  'siriusComponents://representation?type=TreeMap',
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