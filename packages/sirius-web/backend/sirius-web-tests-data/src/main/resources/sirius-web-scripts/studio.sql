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
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'bd3017e3-d95f-4535-8701-af6ba982619f',
  '250cabc0-a211-438c-8015-2d2aa136eb81',
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
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'e344d967-a639-4f6c-9c00-a466d51063c6',
  '01234836-0902-418a-900a-4c0afd20323e',
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
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
                     "domainType": "buck::Human",
                     "semanticCandidatesExpression": "aql:self.humans"
                   }
                 },
                 "cellDescriptions": [
                   {
                     "id": "5cf0f787-43dc-4b8d-b513-51296053a96e",
                     "eClass": "table:CellDescription",
                     "data": {
                       "name": "Cell",
                       "domainType": "vaughan::Human",
                       "semanticCandidatesExpression": "aql:self",
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);


INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
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
  project_id,
  created_on,
  last_modified_on
) VALUES (
  '63f4353f-0c71-4122-93be-2d359fc0fa16',
  'bb66e0e9-4ab5-47ef-99f5-c6b26be995ea',
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
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);