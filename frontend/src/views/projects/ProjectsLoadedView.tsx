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
import { Table } from 'core/table/Table';
import PropTypes from 'prop-types';
import React from 'react';
import { View } from 'views/View';
import { ProjectCard } from './ProjectCard';
import { ProjectsViewContainer } from './ProjectsViewContainer';

const projectType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
});
const propTypes = {
  projects: PropTypes.arrayOf(projectType.isRequired).isRequired,
  onProjectUpdated: PropTypes.func.isRequired,
};

export const ProjectsLoadedView = ({ projects, onProjectUpdated }) => {
  return (
    <View>
      <ProjectsViewContainer>
        <Table>
          {projects.map((project) => (
            <ProjectCard project={project} key={project.id} onProjectUpdated={onProjectUpdated} />
          ))}
        </Table>
      </ProjectsViewContainer>
    </View>
  );
};
ProjectsLoadedView.propTypes = propTypes;
