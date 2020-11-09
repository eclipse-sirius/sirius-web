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

import { ButtonStory } from './button/ButtonStory';
import { Root } from './common/Root';
import { FormStory } from './form/FormStory';
import { NavbarStory } from './navbar/NavbarStory';
import { TabBarStory } from './tabbar/TabBarStory';
import { MessageStory } from './message/MessageStory';
import { BannerStory } from './banner/BannerStory';

export default {
  title: 'Core Components',
};

export const buttonStory = () => (
  <Root>
    <ButtonStory />
  </Root>
);
buttonStory.story = {
  name: 'Button',
};

export const formStory = () => (
  <Root>
    <FormStory />
  </Root>
);
formStory.story = {
  name: 'Form',
};

export const navbarStory = () => (
  <Root>
    <NavbarStory />
  </Root>
);
navbarStory.story = {
  name: 'Navbar',
};

export const tabBarStory = () => (
  <Root>
    <TabBarStory />
  </Root>
);
tabBarStory.story = {
  name: 'Tabbar',
};

export const messageStory = () => (
  <Root>
    <MessageStory />
  </Root>
);
messageStory.story = {
  name: 'Message',
};

export const bannerStory = () => (
  <Root>
    <BannerStory />
  </Root>
);
bannerStory.story = {
  name: 'Banner',
};
