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
import React from 'react';

import { ModalStory } from './modal/ModalStory';
import { ErrorModalStory } from './modal/ErrorModalStory';
import { DeleteProjectModalStory } from './modal/DeleteProjectModalStory';
import { NewDocumentModalStory } from './modal/NewDocumentModalStory';
import { NewObjectModalStory } from './modal/NewObjectModalStory';
import { NewRepresentationModalStory } from './modal/NewRepresentationModalStory';
import { RenameProjectModalStory } from './modal/RenameProjectModalStory';
import { UploadDocumentModalStory } from './modal/UploadDocumentModalStory';
import { ShareDiagramModalStory } from './modal/ShareDiagramModalStory';
import { Root } from './common/Root';

export default {
  title: 'Modals',
};

export const modalStory = () => (
  <Root>
    <ModalStory />
  </Root>
);
modalStory.story = {
  name: 'Layout',
};

export const errorModalStory = () => (
  <Root>
    <ErrorModalStory />
  </Root>
);
errorModalStory.story = {
  name: 'Error',
};

export const deleteProjectModalStory = () => (
  <Root>
    <DeleteProjectModalStory />
  </Root>
);
deleteProjectModalStory.story = {
  name: 'Delete Project Modal',
};

export const renameProjectModalStory = () => (
  <Root>
    <RenameProjectModalStory />
  </Root>
);
renameProjectModalStory.story = {
  name: 'Rename Project Modal',
};

export const newDocumentModalStory = () => (
  <Root>
    <NewDocumentModalStory />
  </Root>
);
newDocumentModalStory.story = {
  name: 'New Model Modal',
};

export const uploadDocumentModalStory = () => (
  <Root>
    <UploadDocumentModalStory />
  </Root>
);
uploadDocumentModalStory.story = {
  name: 'Upload Document Modal',
};

export const newRepresentationModalStory = () => (
  <Root>
    <NewRepresentationModalStory />
  </Root>
);
newRepresentationModalStory.story = {
  name: 'New Representation Modal',
};

export const newObjectModalStory = () => (
  <Root>
    <NewObjectModalStory />
  </Root>
);
newObjectModalStory.story = {
  name: 'New Object Modal',
};

export const shareDiagramModalStory = () => (
  <Root>
    <ShareDiagramModalStory />
  </Root>
);
shareDiagramModalStory.story = {
  name: 'ShareDiagramModal',
};
