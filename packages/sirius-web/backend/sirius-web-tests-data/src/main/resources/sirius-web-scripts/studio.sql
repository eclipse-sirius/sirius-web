-- Sample empty studio project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'Empty Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'bd3017e3-d95f-4535-8701-af6ba982619f',
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
  '4fcf8346-afb8-4c4a-ac6a-be31e9dd910a',
  '250cabc0-a211-438c-8015-2d2aa136eb81',
  'bd3017e3-d95f-4535-8701-af6ba982619f',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample studio project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '01234836-0902-418a-900a-4c0afd20323e',
  'Studio',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '01234836-0902-418a-900a-4c0afd20323e',
  'siriusComponents://nature?kind=studio'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
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
  'ce03e33d-0dcc-4c8c-a670-4e5cbc978b32',
  '01234836-0902-418a-900a-4c0afd20323e',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/domain'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/view'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/form'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'http://www.eclipse.org/sirius-web/table'
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
  'f0e490c1-79f1-49a0-b1f2-3637f2958148',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Domain',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "domain":"http://www.eclipse.org/sirius-web/domain"
     },
     "content":[
       {
         "id":"f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf",
         "eClass":"domain:Domain",
         "data":{
           "name":"buck",
           "types":[
             {
               "id":"c341bf91-d315-4264-9787-c51b121a6375",
               "eClass":"domain:Entity",
               "data":{
                 "name":"Root",
                 "attributes":[
                   {
                     "id":"7ac92c9d-3cb6-4374-9774-11bb62962fe2",
                     "eClass":"domain:Attribute",
                     "data":{
                       "name":"label"
                     }
                   },
                   {
                     "id":"d51d676c-0cb7-414b-8358-bacbc5d33942",
                     "eClass":"domain:Attribute",
                     "data":{
                       "name":"description"
                     }
                   }
                 ],
                 "relations":[
                   {
                     "id":"f8fefc5d-4fee-4666-815e-94b24a95183f",
                     "eClass":"domain:Relation",
                     "data":{
                       "name":"humans",
                       "many":true,
                       "containment":true,
                       "targetType":"//@types.2"
                     }
                   }
                 ]
               }
             },
             {
               "id":"c6fdba07-dea5-4a53-99c7-7eefc1bfdfcc",
               "eClass":"domain:Entity",
               "data":{
                 "name":"NamedElement",
                 "attributes":[
                   {
                     "id":"520bb7c9-5f28-40f7-bda0-b35dd593876d",
                     "eClass":"domain:Attribute",
                     "data":{
                       "name":"name"
                     }
                   }
                 ],
                 "abstract":true
               }
             },
             {
               "id":"1731ffb5-bfb0-46f3-a23d-0c0650300005",
               "eClass":"domain:Entity",
               "data":{
                 "name":"Human",
                 "attributes":[
                   {
                     "id":"e86d3f45-d043-441e-b8ab-12e4b3f8915a",
                     "eClass":"domain:Attribute",
                     "data":{
                       "name":"description",
                       "type":"STRING"
                     }
                   },
                   {
                     "id":"703e6db4-a193-4da7-a872-6efba2b3a2c5",
                     "eClass":"domain:Attribute",
                     "data":{
                       "name":"promoted",
                       "type":"BOOLEAN"
                     }
                   },
                   {
                     "id": "a480dbc0-14b7-4f06-a4f7-4c86139a803a",
                     "eClass": "domain:Attribute",
                     "data":{
                       "name": "birthDate",
                       "type":"STRING"
                     }
                   }
                 ],
                 "superTypes":[
                   "//@types.1"
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
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  is_read_only,
  created_on,
  last_modified_on
) VALUES (
  'ed2a5355-991d-458f-87f1-ea3a18b1f104',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Form View',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "form":"http://www.eclipse.org/sirius-web/form",
       "table":"http://www.eclipse.org/sirius-web/table",
       "view":"http://www.eclipse.org/sirius-web/view"
     },
     "content":[
       {
         "id":"c4591605-8ea8-4e92-bb17-05c4538248f8",
         "eClass":"view:View",
         "data":{
           "descriptions":[
             {
               "id":"ed20cb85-a58a-47ad-bc0d-749ec8b2ea03",
               "eClass":"form:FormDescription",
               "data":{
                 "name":"Human Form",
                 "domainType":"buck::Human",
                 "pages":[
                   {
                     "id":"b0c73654-6f1b-4be5-832d-b97f053b5196",
                     "eClass":"form:PageDescription",
                     "data":{
                       "name":"Human",
                       "labelExpression":"aql:self.name",
                       "domainType":"buck::Human",
                       "groups":[
                         {
                           "id":"28d8d6de-7d6f-4434-9293-0ac4ef2461ac",
                           "eClass":"form:GroupDescription",
                           "data":{
                             "name":"Properties",
                             "labelExpression":"Properties",
                             "children":[
                               {
                                 "id":"b02b89b7-6c06-40f8-9366-83d5f885ada1",
                                 "eClass":"form:TextfieldDescription",
                                 "data":{
                                   "name":"Name",
                                   "labelExpression":"Name",
                                   "helpExpression":"The name of the human",
                                   "valueExpression":"aql:self.name",
                                   "body":[
                                     {
                                       "id":"ecdc23ff-fd4b-47a4-939d-1bc03e656d3d",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"a8b95d5b-833a-4b19-b783-3025225613de",
                                             "eClass":"view:SetValue",
                                             "data":{
                                               "featureName":"name",
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
                                 "id":"98e756a2-305f-4767-b75c-4130996ae6da",
                                 "eClass":"form:TextAreaDescription",
                                 "data":{
                                   "name":"Description",
                                   "labelExpression":"Description",
                                   "helpExpression":"The description of the human",
                                   "valueExpression":"aql:self.description",
                                   "body":[
                                     {
                                       "id":"59ea57d5-c365-4421-9648-f38a74644768",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"811bb719-ab53-49ea-9281-6558f7022ecc",
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
                                 "id":"ba20babb-0e75-4f66-a382-a2f02bce904a",
                                 "eClass":"form:CheckboxDescription",
                                 "data":{
                                   "name":"Promoted",
                                   "labelExpression":"Promoted",
                                   "helpExpression":"Has this human been promoted?",
                                   "valueExpression":"aql:self.promoted",
                                   "body":[
                                     {
                                       "id":"afac13bd-71ac-4287-baf6-3669f23ac806",
                                       "eClass":"view:ChangeContext",
                                       "data":{
                                         "children":[
                                           {
                                             "id":"0eaeca64-ee2b-4f2c-9454-c528181d0d64",
                                             "eClass":"view:SetValue",
                                             "data":{
                                               "featureName":"promoted",
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
                                 "id":"91a4fcd9-a176-4df1-8f88-52a406fc3f73",
                                 "eClass":"form:DateTimeDescription",
                                 "data":{
                                   "name":"BirthDate",
                                   "labelExpression":"Birth Date",
                                   "helpExpression":"The birth date of the human",
                                   "stringValueExpression":"aql:self.birthDate",
                                   "type":"DATE"
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
             },
             {
               "id": "d28d9ecb-102a-4eee-9d26-55543c5acb7f",
               "eClass": "table:TableDescription",
               "data":{
                 "name": "New Table Description",
                 "domainType": "buck::Root",
                 "titleExpression": "aql:New Table",
                 "columnDescriptions": [
                   {
                     "id": "3db9745f-6da7-445a-b768-9d5480105eca",
                     "eClass": "table:ColumnDescription",
                     "data": {
                       "name": "Column",
                       "domainType": "buck::Root",
                       "semanticCandidatesExpression": "aql:self",
                       "headerLabelExpression": "aql:self.name"
                     }
                   }
                 ],
                 "rowDescription": {
                   "id": "6c4c05cb-0e95-4556-adf5-54269fbf0843",
                   "eClass": "table:RowDescription",
                   "data": {
                     "name": "Row",
                     "semanticCandidatesExpression": "aql:self.eContents()->filter(buck::Human)->toPaginatedData(cursor,direction,size)"
                   }
                 },
                 "cellDescriptions": [
                   {
                     "id": "5cf0f787-43dc-4b8d-b513-51296053a96e",
                     "eClass": "table:CellDescription",
                     "data": {
                       "name": "Cell",
                       "preconditionExpression": "aql:true",
                       "valueExpression": "aql:self.name",
                       "cellWidgetDescription": {
                       "id": "9b3400c9-d5f0-46db-9d60-60ec4016d383",
                       "eClass": "table:CellLabelWidgetDescription"
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

INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  is_read_only,
  created_on,
  last_modified_on
) VALUES (
  'fc1d7b23-2818-4874-bb30-8831ea287a44',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Diagram View',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "diagram":"http://www.eclipse.org/sirius-web/diagram",
       "view":"http://www.eclipse.org/sirius-web/view"
     },
     "content":[
       {
         "id":"52e958f8-f630-43bd-aec6-b5e9b54efd27",
         "eClass":"view:View",
         "data":{
           "descriptions":[
             {
               "id":"7384dc2c-1b43-45c7-9c74-f972b28774c8",
               "eClass":"diagram:DiagramDescription",
               "data":{
                 "name":"Root Diagram",
                 "domainType":"buck::Root",
                 "nodeDescriptions":[
                   {
                     "id":"e91e6e23-1440-4fbf-b31c-3a21bf25d85b",
                     "eClass":"diagram:NodeDescription",
                     "data":{
                       "name":"Human Node",
                       "domainType":"buck::Human",
                       "semanticCandidatesExpression":"aql:self.humans",
                       "style":{
                         "id":"d8f8f5f4-5044-45ec-860a-aa1122e192e7",
                         "eClass":"diagram:RectangularNodeStyleDescription",
                         "data":{
                           "background":"//@colorPalettes.0/@colors.1",
                           "bordercolor":"//@colorPalettes.0/@colors.0"
                         }
                       },
                       "insideLabel":{
                         "id":"00075eeb-5664-47e2-8f37-f413a672e9fd",
                         "eClass":"diagram:InsideLabelDescription",
                         "data":{
                           "style":{
                             "id":"c8338087-e98e-43bd-ae1a-879b64308a7d",
                             "eClass":"diagram:InsideLabelStyle"
                           }
                         }
                       }
                     }
                   },
                   {
                     "id":"145f5833-f7b1-4c70-8a53-c3cc414e1af9",
                     "eClass":"diagram:NodeDescription",
                     "data":{
                       "name":"Human Node",
                       "domainType":"buck::Human",
                       "semanticCandidatesExpression":"aql:self.humans",
                       "style":{
                         "id":"98957bda-a73b-4dd7-b151-9aadc871aa27",
                         "eClass":"diagram:ImageNodeStyleDescription",
                         "data":{
                           "shape":"7f8ce6ef-a23f-4c62-a6f8-381d5c237742"
                         }
                       }
                     }
                   }
                 ],
                 "edgeDescriptions":[
                   {
                     "id":"3aa78047-dd0e-43b1-9628-a638b2fe7a2b",
                     "eClass":"diagram:EdgeDescription",
                     "data":{
                       "name":"Siblings",
                       "sourceExpression":"aql:self",
                       "sourceDescriptions":[
                         "//@descriptions.0/@nodeDescriptions.0"
                       ],
                       "targetExpression":"aql:self",
                       "targetDescriptions":[
                         "//@descriptions.0/@nodeDescriptions.1"
                       ],
                       "style":{
                         "id":"a2a3713f-57bc-422b-92e8-b22ed69e94a8",
                         "eClass":"diagram:EdgeStyle",
                         "data":{
                           "edgeWidth":"5"
                         }
                       }
                     }
                   }
                 ]
               }
             }
           ],
           "colorPalettes":[
             {
               "id":"f2e8bd95-c1e4-49b3-adcd-5c9c1477e4ca",
               "eClass":"view:ColorPalette",
               "data":{
                 "colors":[
                   {
                     "id":"0286a7e0-5a9d-40c8-aa23-e0a1c6fab90a",
                     "eClass":"view:FixedColor",
                     "data":{
                       "name":"Black",
                       "value":"#000000"
                     }
                   },
                   {
                     "id":"cccf659b-57a7-4517-bb8c-e284254cce1b",
                     "eClass":"view:FixedColor",
                     "data":{
                       "name":"White",
                       "value":"#FFFFFF"
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


INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  is_read_only,
  created_on,
  last_modified_on
) VALUES (
  '356e45e8-7d70-439e-b2dd-d0313cd65174',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Ellipse Diagram View',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "customnodes":"http://www.eclipse.org/sirius-web/customnodes",
       "diagram":"http://www.eclipse.org/sirius-web/diagram",
       "view":"http://www.eclipse.org/sirius-web/view"
     },
     "content":[
       {
         "id":"7ea6016a-89ea-4de9-9b4b-c1ff954ef0fd",
         "eClass":"view:View",
         "data":{
           "descriptions":[
             {
               "id":"99cb5ddd-4241-4474-8866-2b3b84cb440f",
               "eClass":"diagram:DiagramDescription",
               "data":{
                 "name":"Root Diagram",
                 "domainType":"buck::Root",
                 "nodeDescriptions":[
                   {
                     "id":"b4e36182-3577-4758-b8fe-7a74ae8d5b9d",
                     "eClass":"diagram:NodeDescription",
                     "data":{
                       "name":"Human Node",
                       "domainType":"buck::Human",
                       "semanticCandidatesExpression":"aql:self.humans",
                       "style":{
                         "id":"3b3637a2-c397-4837-b42f-25fee34e5af2",
                         "eClass":"customnodes:EllipseNodeStyleDescription",
                         "data":{
                           "background":"//@colorPalettes.0/@colors.1",
                           "bordercolor":"//@colorPalettes.0/@colors.0"
                         }
                       },
                       "insideLabel":{
                         "id":"fa2ebcb8-0069-49e4-8713-f4e94680e218",
                         "eClass":"diagram:InsideLabelDescription",
                         "data":{
                           "style":{
                             "id":"d22b407b-4c81-4a64-9fa7-2608e4253cde",
                             "eClass":"diagram:InsideLabelStyle"
                           }
                         }
                       }
                     }
                   }
                 ]
               }
             }
           ],
           "colorPalettes":[
             {
               "id":"110ac232-b1da-48eb-bfbe-86f9867d043b",
               "eClass":"view:ColorPalette",
               "data":{
                 "colors":[
                   {
                     "id":"52167425-b389-40c1-8a04-b17e9188fc31",
                     "eClass":"view:FixedColor",
                     "data":{
                       "name":"Black",
                       "value":"#000000"
                     }
                   },
                   {
                     "id":"cb50d6b9-632c-46f2-968b-dbdb107234fa",
                     "eClass":"view:FixedColor",
                     "data":{
                       "name":"White",
                       "value":"#FFFFFF"
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
  '2024-05-07 9:45:0.000',
  '2024-05-07 9:45:0.000'
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
  '7f8ce6ef-a23f-4c62-a6f8-381d5c237742',
  '01234836-0902-418a-900a-4c0afd20323e',
  'Placeholder',
  'image/svg+xml',
  '<svg version="1.1" xmlns="http://www.w3.org/2000/svg"><rect width="10px" height="10px" fill="black" /></svg>'::bytea,
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  semantic_data_id,
  representation_metadata_id
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6#74fe378b-0010-4909-8762-3f8425abf857',
  'f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf',
  'siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=d59c3558-31d3-387d-a720-098370b677fb',
  'Domain',
  'siriusComponents://representation?type=Diagram',
  '2026-01-21 13:46:0.000',
  '2026-01-21 13:46:0.000',
  '',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  '74fe378b-0010-4909-8762-3f8425abf857'
);

INSERT INTO representation_content (
  id,
  content,
  last_migration_performed,
  migration_version,
  created_on,
  last_modified_on,
  semantic_data_id,
  representation_metadata_id
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6#74fe378b-0010-4909-8762-3f8425abf857',
  '{
     "id": "74fe378b-0010-4909-8762-3f8425abf857",
     "kind": "siriusComponents://representation?type=Diagram",
     "targetObjectId": "f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf",
     "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=d59c3558-31d3-387d-a720-098370b677fb",
     "nodes": [
       {
         "id": "fb671c47-e46b-35a6-bfa8-324365544985",
         "type": "node:rectangle",
         "targetObjectId": "c341bf91-d315-4264-9787-c51b121a6375",
         "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Entity",
         "targetObjectLabel": "Root",
         "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=bcb8a632-cfd5-372c-a1ad-985545d347ba",
         "borderNode": false,
         "initialBorderNodePosition": "NONE",
         "modifiers": [],
         "state": "Normal",
         "collapsingState": "EXPANDED",
         "insideLabel": {
           "id": "834aee75-c755-3f97-9af0-fc4285c3b9a0",
           "text": "Root",
           "insideLabelLocation": "TOP_CENTER",
           "style": {
             "color": "rgb(0, 0, 0)",
             "fontSize": 14,
             "bold": false,
             "italic": false,
             "underline": false,
             "strikeThrough": false,
             "iconURL": ["/icons/full/obj16/Entity.svg"],
             "background": "transparent",
             "borderColor": "black",
             "borderSize": 0,
             "borderRadius": 3,
             "borderStyle": "Solid",
             "maxWidth": null,
             "visibility": "visible"
           },
           "isHeader": true,
           "headerSeparatorDisplayMode": "IF_CHILDREN",
           "overflowStrategy": "NONE",
           "textAlign": "LEFT",
           "customizedStyleProperties": []
         },
         "outsideLabels": [],
         "style": {
           "background": "rgb(250, 250, 250)",
           "borderColor": "rgb(251, 184, 0)",
           "borderSize": 3,
           "borderRadius": 8,
           "borderStyle": "Solid",
           "childrenLayoutStrategy": {
             "areChildNodesDraggable": true,
             "topGap": 0,
             "bottomGap": 0,
             "growableNodeIds": [],
             "kind": "List"
           }
         },
         "borderNodes": [],
         "childNodes": [
           {
             "id": "57647065-be83-39d1-8427-fdddf7dbffa0",
             "type": "node:icon-label",
             "targetObjectId": "7ac92c9d-3cb6-4374-9774-11bb62962fe2",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "label",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "edc56b33-470a-3625-90b4-e4821fe5b8f3",
               "text": "label",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/StringAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           },
           {
             "id": "6aa6a031-2f87-387c-b5a0-d015aa85669f",
             "type": "node:icon-label",
             "targetObjectId": "d51d676c-0cb7-414b-8358-bacbc5d33942",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "description",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "dc3b6f9d-01fc-3688-a15e-6ef09f018f8f",
               "text": "description",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/StringAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           }
         ],
         "defaultWidth": null,
         "defaultHeight": null,
         "labelEditable": true,
         "deletable": true,
         "pinned": false,
         "customizedStyleProperties": []
       },
       {
         "id": "2a705c17-3c82-30d9-b41d-8685c640e4c7",
         "type": "node:rectangle",
         "targetObjectId": "c6fdba07-dea5-4a53-99c7-7eefc1bfdfcc",
         "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Entity",
         "targetObjectLabel": "NamedElement",
         "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=bcb8a632-cfd5-372c-a1ad-985545d347ba",
         "borderNode": false,
         "initialBorderNodePosition": "NONE",
         "modifiers": [],
         "state": "Normal",
         "collapsingState": "EXPANDED",
         "insideLabel": {
           "id": "616269c9-0cf8-3a92-89b2-c8625f7b72de",
           "text": "NamedElement",
           "insideLabelLocation": "TOP_CENTER",
           "style": {
             "color": "rgb(0, 0, 0)",
             "fontSize": 14,
             "bold": false,
             "italic": true,
             "underline": false,
             "strikeThrough": false,
             "iconURL": [
               "/icons/full/obj16/Entity.svg",
               "/icons/full/obj16/Abstract.svg"
             ],
             "background": "transparent",
             "borderColor": "black",
             "borderSize": 0,
             "borderRadius": 3,
             "borderStyle": "Solid",
             "maxWidth": null,
             "visibility": "visible"
           },
           "isHeader": true,
           "headerSeparatorDisplayMode": "IF_CHILDREN",
           "overflowStrategy": "NONE",
           "textAlign": "LEFT",
           "customizedStyleProperties": []
         },
         "outsideLabels": [],
         "style": {
           "background": "rgb(117, 117, 117)",
           "borderColor": "rgb(251, 184, 0)",
           "borderSize": 3,
           "borderRadius": 8,
           "borderStyle": "Solid",
           "childrenLayoutStrategy": {
             "areChildNodesDraggable": true,
             "topGap": 0,
             "bottomGap": 0,
             "growableNodeIds": [],
             "kind": "List"
           }
         },
         "borderNodes": [],
         "childNodes": [
           {
             "id": "867412e7-9f34-3a0c-8c78-2ecfbc8cbbe2",
             "type": "node:icon-label",
             "targetObjectId": "520bb7c9-5f28-40f7-bda0-b35dd593876d",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "name",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "12a603a0-8032-3398-9cda-340ba83fab87",
               "text": "name",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/StringAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           }
         ],
         "defaultWidth": null,
         "defaultHeight": null,
         "labelEditable": true,
         "deletable": true,
         "pinned": false,
         "customizedStyleProperties": []
       },
       {
         "id": "34389cad-41a2-3873-9302-483887a67050",
         "type": "node:rectangle",
         "targetObjectId": "1731ffb5-bfb0-46f3-a23d-0c0650300005",
         "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Entity",
         "targetObjectLabel": "Human",
         "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=bcb8a632-cfd5-372c-a1ad-985545d347ba",
         "borderNode": false,
         "initialBorderNodePosition": "NONE",
         "modifiers": [],
         "state": "Normal",
         "collapsingState": "EXPANDED",
         "insideLabel": {
           "id": "048582f3-fc69-3594-a593-7e71b641188a",
           "text": "Human",
           "insideLabelLocation": "TOP_CENTER",
           "style": {
             "color": "rgb(0, 0, 0)",
             "fontSize": 14,
             "bold": false,
             "italic": false,
             "underline": false,
             "strikeThrough": false,
             "iconURL": ["/icons/full/obj16/Entity.svg"],
             "background": "transparent",
             "borderColor": "black",
             "borderSize": 0,
             "borderRadius": 3,
             "borderStyle": "Solid",
             "maxWidth": null,
             "visibility": "visible"
           },
           "isHeader": true,
           "headerSeparatorDisplayMode": "IF_CHILDREN",
           "overflowStrategy": "NONE",
           "textAlign": "LEFT",
           "customizedStyleProperties": []
         },
         "outsideLabels": [],
         "style": {
           "background": "rgb(250, 250, 250)",
           "borderColor": "rgb(251, 184, 0)",
           "borderSize": 3,
           "borderRadius": 8,
           "borderStyle": "Solid",
           "childrenLayoutStrategy": {
             "areChildNodesDraggable": true,
             "topGap": 0,
             "bottomGap": 0,
             "growableNodeIds": [],
             "kind": "List"
           }
         },
         "borderNodes": [],
         "childNodes": [
           {
             "id": "30e8852e-ea10-3681-a407-92c7a9e31f3f",
             "type": "node:icon-label",
             "targetObjectId": "e86d3f45-d043-441e-b8ab-12e4b3f8915a",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "description",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "15ce4ecb-38cb-3833-99a8-3956b4826138",
               "text": "description",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/StringAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           },
           {
             "id": "deeded4f-9fb5-3df1-b9b0-80d8880a3c6f",
             "type": "node:icon-label",
             "targetObjectId": "703e6db4-a193-4da7-a872-6efba2b3a2c5",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "promoted",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "ee9685e2-93ca-3124-80d0-d5f4e8362f29",
               "text": "promoted",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/BooleanAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           },
           {
             "id": "3d20835b-a31a-3025-aed3-8e2ec459c074",
             "type": "node:icon-label",
             "targetObjectId": "a480dbc0-14b7-4f06-a4f7-4c86139a803a",
             "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Attribute",
             "targetObjectLabel": "birthDate",
             "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=2aeee83f-2f8c-3348-9cb8-2829343542ee",
             "borderNode": false,
             "initialBorderNodePosition": "NONE",
             "modifiers": [],
             "state": "Normal",
             "collapsingState": "EXPANDED",
             "insideLabel": {
               "id": "1b2c46d2-3834-35c8-8a0a-1dcfcfd5f0df",
               "text": "birthDate",
               "insideLabelLocation": "TOP_LEFT",
               "style": {
                 "color": "rgb(66, 66, 66)",
                 "fontSize": 12,
                 "bold": false,
                 "italic": false,
                 "underline": false,
                 "strikeThrough": false,
                 "iconURL": ["/icons/full/obj16/StringAttribute.svg"],
                 "background": "transparent",
                 "borderColor": "black",
                 "borderSize": 0,
                 "borderRadius": 3,
                 "borderStyle": "Solid",
                 "maxWidth": null,
                 "visibility": "visible"
               },
               "isHeader": false,
               "headerSeparatorDisplayMode": "NEVER",
               "overflowStrategy": "NONE",
               "textAlign": "LEFT",
               "customizedStyleProperties": []
             },
             "outsideLabels": [],
             "style": {
               "background": "transparent",
               "childrenLayoutStrategy": { "kind": "FreeForm" }
             },
             "borderNodes": [],
             "childNodes": [],
             "defaultWidth": null,
             "defaultHeight": null,
             "labelEditable": true,
             "deletable": true,
             "pinned": false,
             "customizedStyleProperties": []
           }
         ],
         "defaultWidth": null,
         "defaultHeight": null,
         "labelEditable": true,
         "deletable": true,
         "pinned": false,
         "customizedStyleProperties": []
       }
     ],
     "edges": [
       {
         "id": "d0917969-7547-3408-a1d7-5d5ebf21cc93",
         "type": "edge:straight",
         "targetObjectId": "1731ffb5-bfb0-46f3-a23d-0c0650300005",
         "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Entity",
         "targetObjectLabel": "Human",
         "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=fee7b454-e061-3ab3-8314-6a29c723d158",
         "beginLabel": null,
         "centerLabel": null,
         "endLabel": null,
         "sourceId": "34389cad-41a2-3873-9302-483887a67050",
         "targetId": "2a705c17-3c82-30d9-b41d-8685c640e4c7",
         "modifiers": [],
         "state": "Normal",
         "style": {
           "size": 1,
           "lineStyle": "Solid",
           "sourceArrow": "None",
           "targetArrow": "InputClosedArrow",
           "color": "rgb(117, 117, 117)",
           "edgeType": "Manhattan"
         },
         "centerLabelEditable": true,
         "deletable": true,
         "customizedStyleProperties": []
       },
       {
         "id": "9b5c0041-55fc-3fea-86d2-b2f959b43a03",
         "type": "edge:straight",
         "targetObjectId": "f8fefc5d-4fee-4666-815e-94b24a95183f",
         "targetObjectKind": "siriusComponents://semantic?domain=domain&entity=Relation",
         "targetObjectLabel": "humans",
         "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=c5857f07-7382-3215-8c53-b690ca983655&sourceElementId=d3c1af57-d18b-33ac-9fd1-d5aed8ae9e35",
         "beginLabel": null,
         "centerLabel": {
           "id": "0d672268-41a2-3064-b726-4e9d89f7b713",
           "type": "label:edge-center",
           "text": "humans [0..*]",
           "style": {
             "color": "rgb(0, 0, 0)",
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
             "maxWidth": null,
             "visibility": "visible"
           },
           "customizedStyleProperties": []
         },
         "endLabel": null,
         "sourceId": "fb671c47-e46b-35a6-bfa8-324365544985",
         "targetId": "34389cad-41a2-3873-9302-483887a67050",
         "modifiers": [],
         "state": "Normal",
         "style": {
           "size": 1,
           "lineStyle": "Solid",
           "sourceArrow": "FillDiamond",
           "targetArrow": "InputArrow",
           "color": "rgb(0, 0, 0)",
           "edgeType": "Manhattan"
         },
         "centerLabelEditable": true,
         "deletable": true,
         "customizedStyleProperties": []
       }
     ],
     "layoutData": {
       "nodeLayoutData": {
         "30e8852e-ea10-3681-a407-92c7a9e31f3f": {
           "id": "30e8852e-ea10-3681-a407-92c7a9e31f3f",
           "position": { "x": 3.0, "y": 38.0 },
           "size": { "width": 144.0, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 115.453125, "height": 16.0 }
         },
         "34389cad-41a2-3873-9302-483887a67050": {
           "id": "34389cad-41a2-3873-9302-483887a67050",
           "position": { "x": 360.046875, "y": 0.0 },
           "size": { "width": 150.0, "height": 89.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 121.453125, "height": 89.0 }
         },
         "3d20835b-a31a-3025-aed3-8e2ec459c074": {
           "id": "3d20835b-a31a-3025-aed3-8e2ec459c074",
           "position": { "x": 3.0, "y": 70.0 },
           "size": { "width": 144.0, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 105.859375, "height": 16.0 }
         },
         "2a705c17-3c82-30d9-b41d-8685c640e4c7": {
           "id": "2a705c17-3c82-30d9-b41d-8685c640e4c7",
           "position": { "x": 175.0, "y": 0.0 },
           "size": { "width": 160.046875, "height": 70.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 160.046875, "height": 57.0 }
         },
         "deeded4f-9fb5-3df1-b9b0-80d8880a3c6f": {
           "id": "deeded4f-9fb5-3df1-b9b0-80d8880a3c6f",
           "position": { "x": 3.0, "y": 54.0 },
           "size": { "width": 144.0, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 107.734375, "height": 16.0 }
         },
         "57647065-be83-39d1-8427-fdddf7dbffa0": {
           "id": "57647065-be83-39d1-8427-fdddf7dbffa0",
           "position": { "x": 3.0, "y": 38.0 },
           "size": { "width": 144.0, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 82.0, "height": 16.0 }
         },
         "867412e7-9f34-3a0c-8c78-2ecfbc8cbbe2": {
           "id": "867412e7-9f34-3a0c-8c78-2ecfbc8cbbe2",
           "position": { "x": 3.0, "y": 38.0 },
           "size": { "width": 154.046875, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 86.546875, "height": 16.0 }
         },
         "fb671c47-e46b-35a6-bfa8-324365544985": {
           "id": "fb671c47-e46b-35a6-bfa8-324365544985",
           "position": { "x": 0.0, "y": 0.0 },
           "size": { "width": 150.0, "height": 73.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 121.453125, "height": 73.0 }
         },
         "6aa6a031-2f87-387c-b5a0-d015aa85669f": {
           "id": "6aa6a031-2f87-387c-b5a0-d015aa85669f",
           "position": { "x": 3.0, "y": 54.0 },
           "size": { "width": 144.0, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false,
           "handleLayoutData": [],
           "minComputedSize": { "width": 115.453125, "height": 16.0 }
         }
       },
       "edgeLayoutData": {
         "d0917969-7547-3408-a1d7-5d5ebf21cc93": {
           "id": "d0917969-7547-3408-a1d7-5d5ebf21cc93",
           "bendingPoints": [],
           "edgeAnchorLayoutData": []
         },
         "9b5c0041-55fc-3fea-86d2-b2f959b43a03": {
           "id": "9b5c0041-55fc-3fea-86d2-b2f959b43a03",
           "bendingPoints": [],
           "edgeAnchorLayoutData": []
         }
       },
       "labelLayoutData": {
         "0d672268-41a2-3064-b726-4e9d89f7b713": {
           "id": "0d672268-41a2-3064-b726-4e9d89f7b713",
           "position": { "x": 0.0, "y": 0.0 },
           "size": { "width": 84.171875, "height": 16.0 },
           "resizedByUser": false,
           "movedByUser": false
         }
       }
     }
   }',
   'none',
   '2025.12.0-202511141745',
   '2026-01-21 13:46:0.000',
   '2026-01-21 13:46:0.000',
   'e344d967-a639-4f6c-9c00-a466d51063c6',
   '74fe378b-0010-4909-8762-3f8425abf857'
);

-- Studio instance project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'bb66e0e9-4ab5-47ef-99f5-c6b26be995ea',
  'Instance',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '63f4353f-0c71-4122-93be-2d359fc0fa16',
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
  '70958e5d-9872-40c3-9ebe-1acadcd32b74',
  'bb66e0e9-4ab5-47ef-99f5-c6b26be995ea',
  '63f4353f-0c71-4122-93be-2d359fc0fa16',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  '63f4353f-0c71-4122-93be-2d359fc0fa16',
  'domain://buck'
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
  '5b7291aa-0686-4d4f-aa7b-aab416dde82e',
  '63f4353f-0c71-4122-93be-2d359fc0fa16',
  'Buck model',
  '{
     "json":{
       "version":"1.0",
       "encoding":"utf-8"
     },
     "ns":{
       "buck":"domain://buck"
     },
     "content":[
       {
         "id":"87fa4553-6889-4ce6-b017-d013987f9fae",
         "eClass":"buck:Root",
         "data":{
           "label":"Root",
           "humans":[
             {
               "id":"2d72cfca-500c-4e02-8813-d5c54172fff2",
               "eClass":"buck:Human",
               "data":{
                 "name":"John"
               }
             },
             {
               "id":"5d504e23-d154-4ec0-a8bf-b8c773fbcf9d",
               "eClass":"buck:Human",
               "data":{
                 "name":"Jane"
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
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  is_read_only,
  created_on,
  last_modified_on)
VALUES (
  '5a2ec092-0b05-410e-bdc2-0d56c0368165',
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  'Unsynchronized Diagram View',
  '{
    "json": {
      "version": "1.0",
      "encoding": "utf-8"
    },
    "ns": {
      "diagram": "http://www.eclipse.org/sirius-web/diagram",
      "view": "http://www.eclipse.org/sirius-web/view"
    },
    "migration": {
      "lastMigrationPerformed": "NodeDescriptionLayoutStrategyMigrationParticipant",
      "migrationVersion": "2025.6.0-202506011000"
    },
    "content": [
      {
        "id": "507ff474-a06f-45fb-af35-f7858851d42c",
        "eClass": "view:View",
        "data": {
          "descriptions": [
            {
              "id": "e42c06d3-3d01-40bf-871a-73495ea767c8",
              "eClass": "diagram:DiagramDescription",
              "data": {
                "name": "Unsynchronized Root Description",
                "domainType": "buck::Root",
                "titleExpression": "Unsynchronized Root Description",
                "nodeDescriptions": [
                  {
                    "id": "92cc1d59-487f-4e0a-8864-983915b562c8",
                    "eClass": "diagram:NodeDescription",
                    "data": {
                      "name": "Root Node",
                      "domainType": "buck::Root",
                      "semanticCandidatesExpression": "aql:self",
                      "synchronizationPolicy": "UNSYNCHRONIZED",
                      "style": {
                        "id": "c98d8b26-0aa7-4ab7-8d64-16a806f2629a",
                        "eClass": "diagram:RectangularNodeStyleDescription",
                        "data": {
                          "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                          "childrenLayoutStrategy": {
                            "id": "9a38cf8c-6720-407a-a558-50b47a9764b0",
                            "eClass": "diagram:FreeFormLayoutStrategyDescription"
                          },
                          "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#0afc1183-89e0-4854-8578-884dccce74b7"
                        }
                      },
                      "childrenDescriptions": [
                        {
                          "id": "2c363d7d-b5ed-45b5-8f73-c7c3bb02493b",
                          "eClass": "diagram:NodeDescription",
                          "data": {
                            "name": "Human Sub-node",
                            "domainType": "buck::Human",
                            "synchronizationPolicy": "UNSYNCHRONIZED",
                            "style": {
                              "id": "bede80a5-0007-4e48-a9d0-0e8821ab1592",
                              "eClass": "diagram:RectangularNodeStyleDescription",
                              "data": {
                                "borderColor": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#c24dfb04-0d0d-4ca1-be01-ab3b6716fe22",
                                "childrenLayoutStrategy": {
                                  "id": "550fd875-d7b7-40bb-845d-73e4bb30aa3b",
                                  "eClass": "diagram:FreeFormLayoutStrategyDescription"
                                },
                                "background": "view:FixedColor 1952d117-7d88-32c4-a839-3858e5e779ae#11d3f786-9d85-4a3e-995a-78a40df68221"
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
  '2025-10-17 15:34:38.323502 +00:00',
  '2025-10-17 15:34:38.323502 +00:00');
