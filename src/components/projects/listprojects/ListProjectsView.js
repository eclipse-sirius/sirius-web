/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';

import { classNames } from '../../../common/classnames';

import { ProjectsListCard } from '../ProjectsListCard';

const LIST_PROJECTS_VIEW__CLASS_NAMES = 'listprojectsview';

/**
 * The ListProjectsView component is used to display the list of all the projects.
 */
export const ListProjectsView = ({ className }) => {
  const listProjectsViewClassNames = classNames(LIST_PROJECTS_VIEW__CLASS_NAMES, className);
  return (
    <div className={listProjectsViewClassNames}>
      <ProjectsListCard className={className} />
    </div>
  );
};
