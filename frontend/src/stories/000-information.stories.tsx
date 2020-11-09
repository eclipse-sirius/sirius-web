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

import { ConceptsStory } from './information/ConceptsStory';
import { PropertiesStory } from './information/PropertiesStory';
import { RelationsStory } from './information/RelationsStory';
import { Root } from './common/Root';

export default {
  title: 'Information',
};

export const conceptsStory = () => (
  <Root>
    <ConceptsStory />
  </Root>
);
conceptsStory.story = {
  name: 'Concepts',
};

export const propertiesStory = () => (
  <Root>
    <PropertiesStory />
  </Root>
);
propertiesStory.story = {
  name: 'Properties',
};

export const relationsStory = () => (
  <Root>
    <RelationsStory />
  </Root>
);
relationsStory.story = {
  name: 'Relations',
};
