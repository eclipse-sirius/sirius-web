/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Body, Card, Divider, Header, Title } from '../cards/Card';
import { LIST_WITH_HIGHLIGHT__KIND, LIST_WITH_SEPARATOR__KIND, List, ListItem } from '../list/List';

const PROJECTS_LIST_CARD__CLASS_NAMES = 'projectslistcard';

const propTypes = {
  projects: PropTypes.array.isRequired
};
const defaultProps = {
  projects: []
};

/**
 * The ProjectsListCard is used to display a list of projects as a card.
 */
export const ProjectsListCard = ({ className, projects, ...props }) => {
  const projectsListCardClassNames = classNames(PROJECTS_LIST_CARD__CLASS_NAMES, className);
  return (
    <Card className={projectsListCardClassNames} {...props}>
      <Header>
        <Title>Projects</Title>
      </Header>
      <Divider />
      <Body>
        <List kind={[LIST_WITH_HIGHLIGHT__KIND, LIST_WITH_SEPARATOR__KIND]}>
          {projects.map(project => {
            return (
              <ListItem key={project.name} to={`projects/${project.name}`}>
                {project.name}
              </ListItem>
            );
          })}
        </List>
      </Body>
    </Card>
  );
};
ProjectsListCard.propTypes = propTypes;
ProjectsListCard.defaultProps = defaultProps;
