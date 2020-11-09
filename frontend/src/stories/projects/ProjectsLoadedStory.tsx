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
import { ProjectsLoadedView } from 'views/projects/ProjectsLoadedView';

const projects = [
  { id: 'stellar-sphere', name: 'Stellar Sphere' },
  { id: 'mark-aurora', name: 'Mark Aurora' },
  { id: 'pax-aurora', name: 'Pax Aurora' },
  { id: 'war-mantle', name: 'War Mantle' },
  { id: 'cluster-prism', name: 'Cluster Prism' },
  { id: 'black-saber', name: 'Black Saber' },
];

export const ProjectsLoadedStory = () => {
  return (
    <Border>
      <MemoryRouter>
        <ProjectsLoadedView projects={projects} onProjectUpdated={() => {}} />
      </MemoryRouter>
    </Border>
  );
};
