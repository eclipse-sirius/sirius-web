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

import { Root } from './common/Root';
import { NewProjectStory } from './new-project/NewProjectStory';
import { UploadProjectStory } from './upload-project/UploadProjectStory';
import { ViewStory } from './view/ViewStory';

export default {
  title: 'Views',
};

export const viewStory = () => (
  <Root>
    <ViewStory />
  </Root>
);
viewStory.story = {
  name: 'Layout',
};

export const newProjectStory = () => (
  <Root>
    <NewProjectStory />
  </Root>
);
newProjectStory.story = {
  name: 'New Project',
};

export const uploadProjectStory = () => (
  <Root>
    <UploadProjectStory />
  </Root>
);
uploadProjectStory.story = {
  name: 'Upload Project',
};
