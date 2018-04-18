/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';

import { ProjectHeaderCard } from '../ProjectHeaderCard';
import { ProjectRepresentationsListCard } from '../ProjectRepresentationsListCard';
import { ProjectSemanticResourcesListCard } from '../ProjectSemanticResourcesListCard';

import './ProjectView.css';

const PROJECT_VIEW__CLASS_NAMES = 'projectview';
const PROJECT_VIEW_MAIN__CLASS_NAMES = 'projectview-main';
const PROJECT_VIEW_DETAILS__CLASS_NAMES = 'projectview-details';

/**
 * The ProjectView is used to display and manipulate a project.
 */
export const ProjectView = ({ className, project, ...props }) => {
  return (
    <div className={PROJECT_VIEW__CLASS_NAMES}>
      <ProjectHeaderCard name={project.name} />
      <div className={PROJECT_VIEW_MAIN__CLASS_NAMES}>
        <div className={PROJECT_VIEW_DETAILS__CLASS_NAMES}>
          <ProjectSemanticResourcesListCard semanticResources={project.semanticResources} />
          <ProjectRepresentationsListCard representations={project.representations} />
        </div>
      </div>
    </div>
  );
};
