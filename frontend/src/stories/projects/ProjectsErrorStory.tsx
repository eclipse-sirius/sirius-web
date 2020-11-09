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
import { MemoryRouter } from 'react-router-dom';

import { Border } from 'stories/common/Border';
import { ProjectsErrorView } from 'views/projects/ProjectsErrorView';

export const ProjectsErrorStory = () => {
  return (
    <Border>
      <MemoryRouter>
        <ProjectsErrorView message="An unexpected error has occurred, please contact your administrator" />
      </MemoryRouter>
    </Border>
  );
};
