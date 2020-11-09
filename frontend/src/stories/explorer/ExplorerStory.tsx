/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React, { useState } from 'react';
import { MemoryRouter } from 'react-router-dom';
import { Explorer } from 'explorer/Explorer';
import { NewDocumentModal } from 'modals/new-document/NewDocumentModal';
import { NewObjectModal } from 'modals/new-object/NewObjectModal';
import { NewRepresentationModal } from 'modals/new-representation/NewRepresentationModal';
import { UploadDocumentModal } from 'modals/upload-document/UploadDocumentModal';
import { ProjectContext } from 'project/ProjectProvider';
import { Border } from 'stories/common/Border';

const createTree = (expandedIds) => {
  const ben = {
    id: 'ben',
    kind: 'Document',
    label: 'Ben',
    imageURL: '',
    hasChildren: false,
    expanded: false,
    children: [],
  };

  const leia = {
    id: 'leia',
    kind: 'Document',
    label: 'Leia',
    imageURL: '',
    hasChildren: true,
    expanded: expandedIds.includes('leia'),
    children: [ben],
  };

  const luke = {
    id: 'luke',
    kind: 'Document',
    label: 'Luke',
    imageURL: '',
    hasChildren: false,
    expanded: false,
    children: [],
  };

  const anakin = {
    id: 'anakin',
    kind: 'Document',
    label: 'Anakin',
    imageURL: '',
    hasChildren: true,
    expanded: expandedIds.includes('anakin'),
    children: [luke, leia],
  };

  const tree = {
    id: 'treeId',
    label: 'Family',
    children: [anakin],
  };

  return tree;
};

const selection = { id: 'luke' };
const displayedRepresentationId = '';

export const ExplorerStory = () => {
  const [state, setState] = useState({
    modal: undefined,
    expanded: [],
  });

  const expand = (item) => {
    setState((prevState) => {
      const newState = { ...prevState, expanded: [...prevState.expanded] };
      const index = newState.expanded.indexOf(item.id);
      if (index === -1) {
        newState.expanded.push(item.id);
      } else {
        newState.expanded.splice(index, 1);
      }
      return newState;
    });
  };

  const tree = createTree(state.expanded);

  const closeModal = () => {
    setState((prevState) => {
      return { ...prevState, modal: undefined };
    });
  };

  const projectId = 'war-mantle';

  let modal = null;
  if (state.modal === 'CreateDocument') {
    modal = <NewDocumentModal projectId={projectId} onDocumentCreated={closeModal} onClose={closeModal} />;
  } else if (state.modal === 'UploadDocument') {
    modal = <UploadDocumentModal projectId={projectId} onDocumentUploaded={closeModal} onClose={closeModal} />;
  } else if (state.modal === 'CreateRepresentation') {
    modal = (
      <NewRepresentationModal
        projectId={projectId}
        objectId={selection.id}
        onRepresentationCreated={closeModal}
        onClose={closeModal}
      />
    );
  } else if (state.modal === 'CreateObject') {
    modal = (
      <NewObjectModal projectId={projectId} objectId={selection.id} onObjectCreated={closeModal} onClose={closeModal} />
    );
  }

  const project = {
    id: 'test',
    name: 'Test',
    visibility: 'PRIVATE',
    accessLevel: 'EDIT',
  };

  return (
    <MemoryRouter>
      <Border>
        <ProjectContext.Provider value={project}>
          <Explorer tree={tree} onExpand={(item) => expand(item)} selection={selection} setSelection={() => {}} />
        </ProjectContext.Provider>
        {modal}
      </Border>
    </MemoryRouter>
  );
};
