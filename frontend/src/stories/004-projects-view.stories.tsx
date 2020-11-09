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

import { ProjectsEmptyStory } from './projects/ProjectsEmptyStory';
import { ProjectsErrorStory } from './projects/ProjectsErrorStory';
import { ProjectsLoadedStory } from './projects/ProjectsLoadedStory';
import { ProjectsLoadingStory } from './projects/ProjectsLoadingStory';
import { Root } from './common/Root';

export default {
  title: 'Projects View',
};

export const projectsEmptyStory = () => (
  <Root>
    <ProjectsEmptyStory />
  </Root>
);
projectsEmptyStory.story = {
  name: 'Empty',
};

export const projectsLoadingStory = () => (
  <Root>
    <ProjectsLoadingStory />
  </Root>
);
projectsLoadingStory.story = {
  name: 'Loading',
};

export const projectsErrorStory = () => (
  <Root>
    <ProjectsErrorStory />
  </Root>
);
projectsErrorStory.story = {
  name: 'Error',
};

export const projectsLoadedStory = () => (
  <Root>
    <ProjectsLoadedStory />
  </Root>
);
projectsLoadedStory.story = {
  name: 'Loaded',
};
