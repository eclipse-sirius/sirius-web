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

import { Body, Card, Header, Title } from '../cards/Card';
import { List, ListItem, ListItemDescription, ListItemTitle } from '../list/List';

import './ProjectSummaryCard.css';

const PROJECT_CARD__CLASS_NAMES = 'project-card';

const propTypes = {
  project: PropTypes.object.isRequired
};

/**
 * The ProjectSummaryCard is used to display a bird eye view of the project. It
 * displays the small amount of relevant properties of a project.
 */
export const ProjectSummaryCard = ({ project, ...props }) => {
  return (
    <Card className={PROJECT_CARD__CLASS_NAMES} {...props}>
      <Header>
        <Title>{project.name}</Title>
      </Header>
      <Body>
        <List>
          <ListItem>
            <ListItemTitle>Semantic Resources</ListItemTitle>
            <ListItemDescription>
              {project.semanticResourcesCount} resource(s) in the project
            </ListItemDescription>
          </ListItem>
        </List>
      </Body>
    </Card>
  );
};
ProjectSummaryCard.propTypes = propTypes;
