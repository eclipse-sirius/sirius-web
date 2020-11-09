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
import { Border } from 'stories/common/Border';
import { TabBar } from 'core/tab/TabBar';
import { Tab } from 'core/tab/Tab';

export const TabBarStory = () => {
  return (
    <Border>
      <TabBar>
        <Tab label="Tab1"></Tab>
        <Tab label="Tab2"></Tab>
        <Tab label="Tab3"></Tab>
      </TabBar>
    </Border>
  );
};
