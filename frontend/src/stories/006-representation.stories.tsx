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

import { DiagramRepresentationStory } from './representations/DiagramRepresentationStory';
import { FormRepresentationStory } from './representations/FormRepresentationStory';
import { PaletteStory } from './representations/PaletteStory';
import { TreeRepresentationStory } from './representations/TreeRepresentationStory';
import { Root } from './common/Root';

export default {
  title: 'Representations',
};

export const formRepresentationStory = () => (
  <Root>
    <FormRepresentationStory />
  </Root>
);
formRepresentationStory.story = {
  name: 'Form',
};

export const diagramRepresentationStory = () => (
  <Root>
    <DiagramRepresentationStory />
  </Root>
);
diagramRepresentationStory.story = {
  name: 'Diagram',
};

export const paletteStory = () => (
  <Root>
    <PaletteStory />
  </Root>
);
paletteStory.story = {
  name: 'Palette',
};

export const treeRepresentationStory = () => (
  <Root>
    <TreeRepresentationStory />
  </Root>
);
treeRepresentationStory.story = {
  name: 'Tree',
};
