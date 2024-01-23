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
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns":{"domain":"http://www.eclipse.org/sirius-web/domain"},"content":[{"id":"f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf","eClass":"domain:Domain","data":{"name":"buck","types":[{"id":"c341bf91-d315-4264-9787-c51b121a6375","eClass":"domain:Entity","data":{"name":"Root","attributes":[{"id":"7ac92c9d-3cb6-4374-9774-11bb62962fe2","eClass":"domain:Attribute","data":{"name":"label"}}],"relations":[{"id":"f8fefc5d-4fee-4666-815e-94b24a95183f","eClass":"domain:Relation","data":{"name":"humans","many":true,"containment":true,"targetType":"//@types.2"}}]}},{"id":"c6fdba07-dea5-4a53-99c7-7eefc1bfdfcc","eClass":"domain:Entity","data":{"name":"NamedElement","attributes":[{"id":"520bb7c9-5f28-40f7-bda0-b35dd593876d","eClass":"domain:Attribute","data":{"name":"name"}}],"abstract":true}},{"id":"1731ffb5-bfb0-46f3-a23d-0c0650300005","eClass":"domain:Entity","data":{"name":"Human","attributes":[{"id":"e86d3f45-d043-441e-b8ab-12e4b3f8915a","eClass":"domain:Attribute","data":{"name":"description"}},{"id":"703e6db4-a193-4da7-a872-6efba2b3a2c5","eClass":"domain:Attribute","data":{"name":"promoted","type":"BOOLEAN"}}],"superTypes":["//@types.1"]}}]}}]}',
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
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns":{"form":"http://www.eclipse.org/sirius-web/form","view":"http://www.eclipse.org/sirius-web/view"},"content":[{"id":"c4591605-8ea8-4e92-bb17-05c4538248f8","eClass":"view:View","data":{"descriptions":[{"id":"ed20cb85-a58a-47ad-bc0d-749ec8b2ea03","eClass":"form:FormDescription","data":{"name":"Human Form","domainType":"buck::Human","pages":[{"id":"b0c73654-6f1b-4be5-832d-b97f053b5196","eClass":"form:PageDescription","data":{"name":"Human","labelExpression":"aql:self.name","domainType":"buck::Human","groups":[{"id":"28d8d6de-7d6f-4434-9293-0ac4ef2461ac","eClass":"form:GroupDescription","data":{"name":"Properties","labelExpression":"Properties","children":[{"id":"b02b89b7-6c06-40f8-9366-83d5f885ada1","eClass":"form:TextfieldDescription","data":{"name":"Name","labelExpression":"Name","helpExpression":"The name of the human","valueExpression":"aql:self.name","body":[{"id":"ecdc23ff-fd4b-47a4-939d-1bc03e656d3d","eClass":"view:ChangeContext","data":{"children":[{"id":"a8b95d5b-833a-4b19-b783-3025225613de","eClass":"view:SetValue","data":{"featureName":"name","valueExpression":"aql:newValue"}}]}}]}},{"id":"98e756a2-305f-4767-b75c-4130996ae6da","eClass":"form:TextAreaDescription","data":{"name":"Description","labelExpression":"Description","helpExpression":"The description of the human","valueExpression":"aql:self.description","body":[{"id":"59ea57d5-c365-4421-9648-f38a74644768","eClass":"view:ChangeContext","data":{"children":[{"id":"811bb719-ab53-49ea-9281-6558f7022ecc","eClass":"view:SetValue","data":{"featureName":"description","valueExpression":"aql:newValue"}}]}}]}},{"id":"ba20babb-0e75-4f66-a382-a2f02bce904a","eClass":"form:CheckboxDescription","data":{"name":"Promoted","labelExpression":"Promoted","helpExpression":"Has this human been promoted?","valueExpression":"aql:self.promoted","body":[{"id":"afac13bd-71ac-4287-baf6-3669f23ac806","eClass":"view:ChangeContext","data":{"children":[{"id":"0eaeca64-ee2b-4f2c-9454-c528181d0d64","eClass":"view:SetValue","data":{"featureName":"promoted","valueExpression":"aql:newValue"}}]}}]}}]}}]}}]}}]}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

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
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns": {"ecore":"http://www.eclipse.org/emf/2002/Ecore"},"content":[{"id":"3237b215-ae23-48d7-861e-f542a4b9a4b8","eClass":"ecore:EPackage","data":{"name":"Sample"}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
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
  last_modified_on
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '99d336a2-3049-439a-8853-b104ffb22653',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  'siriusComponents://representation?type=Portal',
  '{"id":"e81eec5c-42d6-491c-8bcc-9beb951356f8","kind":"siriusComponents://representation?type=Portal","descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672","label":"Portal","targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8","views":[],"layoutData":[]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);


-- Sample Papaya project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  'Papaya Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  'papaya'
);
INSERT INTO semantic_data (
  id,
  project_id,
  created_on,
  last_modified_on
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'c3d7df85-e0bd-472c-aec1-c05cc88276e4',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'domain://papaya_core'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'domain://papaya_logical_architecture'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  'a4495c9c-d00c-4f0e-a591-1176d102a4a1',
  'cc89c500-c27e-4968-9c67-15cf767c6ef0',
  'Papaya',
  '{"json":{"version":"1.0","encoding":"utf-8"},"ns":{"papaya_core":"domain://papaya_core","papaya_logical_architecture":"domain://papaya_logical_architecture","papaya_planning":"domain://papaya_planning"},"content":[{"id":"aa0b7b22-ade2-4148-9ee2-c5972bd72ab7","eClass":"papaya_core:Root","data":{"projects":[{"id":"24430514-64bc-4b78-a904-4dab25d55a09","eClass":"papaya_planning:Project","data":{"name":"Sirius Web","iterations":[{"id":"8a8e1113-b7ce-4549-a80d-91349879e3d8","eClass":"papaya_planning:Iteration","data":{"name":"2024.3.0","startDate":"2023-12-11T09:00:00.00Z","endDate":"2024-02-02T18:00:00.00Z"}},{"id":"87a8edac-9b4e-40a4-9a5c-672602bf6792","eClass":"papaya_planning:Iteration","data":{"name":"2024.5.0","startDate":"2024-02-05T09:00:00.00Z","endDate":"2024-03-29T18:00:00.00Z"}}],"tasks":[{"id":"31395bb3-1e09-42a5-b450-034955c45ac2","eClass":"papaya_planning:Task","data":{"name":"Improve some features of the diagram","priority":1,"done":true}},{"id":"e6e8f081-27f5-40e3-a8ab-1e6f0f13df12","eClass":"papaya_planning:Task","data":{"name":"Improve the code of the new architecture","priority":1,"done":true}},{"id":"087a700e-c509-458d-9634-5b4132ce10e3","eClass":"papaya_planning:Task","data":{"name":"Add additional tests","priority":2,"done":false}},{"id":"e1c5bd66-54c2-45f1-ae3a-99d3f039affd","eClass":"papaya_planning:Task","data":{"name":"Update the documentation","priority":3,"done":false}}],"contributions":[{"id":"25ba0aea-74c0-4c2c-8b2a-beb3d53a5abc","eClass":"papaya_planning:Contribution","data":{"name":"Contribute improvements to the diagrams","done":true}},{"id":"2dd4563a-e1e2-4c23-9e7d-b327c565624c","eClass":"papaya_planning:Contribution","data":{"name":"Contribute improvements to the new architecture","done":true}},{"id":"fbf744b6-6c9b-4c8a-a959-ed90e793c9b7","eClass":"papaya_planning:Contribution","data":{"name":"Contribute additional tests","done":false}}]}}],"components":[{"id":"fad0f4c9-e668-44f3-8deb-aef0edb6ddff","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-web-domain"}},{"id":"13e0b82e-3d24-403a-bfc1-4bda81846e55","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-web-application"}},{"id":"5c313fbf-d254-4a37-962b-7817cfa18526","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-web-infrastructure"}},{"id":"e462e8ac-39d3-4ab2-b20f-ea7f0a0283d6","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-web-starter"}},{"id":"92221ad3-a0b5-4774-b941-87cda3edb772","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-web"}},{"id":"48f65c15-6655-41c8-8771-b15411b69137","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-representations","packages":[{"id":"3a380f80-2e0d-43dc-9174-f052236edaba","eClass":"papaya_logical_architecture:Package","data":{"name":"org.eclipse.sirius.components.representations","types":[{"id":"87793552-d100-4115-8e01-a5c0ffa7a557","eClass":"papaya_logical_architecture:Class","data":{"name":"IComponent"}},{"id":"5b5101d0-fb1d-43fa-bc6f-4376d4623fc8","eClass":"papaya_logical_architecture:Class","data":{"name":"IProps"}},{"id":"163a8585-7cb7-410b-8442-ca5b29f7af3d","eClass":"papaya_logical_architecture:Class","data":{"name":"IRepresentation"}},{"id":"ef16f62f-88c9-4d1c-8185-77e7ad8bc7ee","eClass":"papaya_logical_architecture:Class","data":{"name":"IRepresentationDescription"}},{"id":"3923f053-719c-4ccd-8974-4847befc6e3d","eClass":"papaya_logical_architecture:Class","data":{"name":"VariableManager"}},{"id":"82a00dcd-dd99-4b70-bb82-2a467717007f","eClass":"papaya_logical_architecture:Class","data":{"name":"BaseRenderer"}}]}}]}},{"id":"576ce0c5-d35a-4972-8a3c-5217db790c45","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-core"}},{"id":"9b5f24e4-39fd-469c-a200-91574eca0c22","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-collaborative"}},{"id":"bf69f94e-ef80-4019-9ee5-d2a09b1d7565","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-diagrams","packages":[{"id":"e738abf2-ab49-416e-902e-9865548cbfbb","eClass":"papaya_logical_architecture:Package","data":{"name":"org.eclipse.sirius.components.diagrams","types":[{"id":"8e7dd20b-6e0e-4c6d-a2b0-1b03c2edb1e2","eClass":"papaya_logical_architecture:Class","data":{"name":"Diagram"}},{"id":"5053d925-249e-414e-a77e-9d2da8829955","eClass":"papaya_logical_architecture:Class","data":{"name":"Node"}},{"id":"d0391a0f-9caa-4ca4-bcdd-26e8f4aefb85","eClass":"papaya_logical_architecture:Class","data":{"name":"Edge"}}]}},{"id":"71efa238-25cf-4869-9000-b4e93646b1cb","eClass":"papaya_logical_architecture:Package","data":{"name":"org.eclipse.sirius.components.diagrams.components","types":[{"id":"8fb02c57-7a6f-451f-81b2-c92e57c261ec","eClass":"papaya_logical_architecture:Class","data":{"name":"DiagramComponent"}},{"id":"60cc8eef-6dae-4f40-bb85-a7c76faf9d88","eClass":"papaya_logical_architecture:Class","data":{"name":"NodeComponent"}},{"id":"eeaba3fc-01ae-49ce-bb6c-1a5f8d6a0d72","eClass":"papaya_logical_architecture:Class","data":{"name":"EdgeComponent"}}]}},{"id":"724f4a5e-3856-4b3d-bad0-4c66d717503b","eClass":"papaya_logical_architecture:Package","data":{"name":"org.eclipse.sirius.components.diagrams.description","types":[{"id":"66bbb032-ebd8-48a6-aad7-0f4bb24c0edd","eClass":"papaya_logical_architecture:Class","data":{"name":"DiagramDescription"}},{"id":"a7ba387d-d226-48be-aeb5-7fdeb38b7c00","eClass":"papaya_logical_architecture:Class","data":{"name":"NodeDescription"}},{"id":"8e806118-71c3-43ca-863a-18d203b4e390","eClass":"papaya_logical_architecture:Class","data":{"name":"EdgeDescription"}}]}}]}},{"id":"1c7a30b6-268e-4b67-95ee-7e2475f4cdd0","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-collaborative-diagrams"}},{"id":"a61b291c-c976-410c-ad2a-d9139200f948","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-forms"}},{"id":"780cbc79-6f69-4734-92f1-9a4ddb6691c8","eClass":"papaya_logical_architecture:Component","data":{"name":"sirius-components-collaborative-forms"}}]}}]}',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
