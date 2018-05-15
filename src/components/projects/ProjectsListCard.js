/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/
import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Card, PrimaryTitle } from '../cards/Card';
import {
  LIST_WITH_HIGHLIGHT__KIND,
  AdditionalText,
  List,
  MainText,
  TwoLineTile
} from '../list/List';

import './ProjectsListCard.css';

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
      <PrimaryTitle label="Projects" />
      <List kind={LIST_WITH_HIGHLIGHT__KIND}>
        {projects.map(project => {
          return (
            <Link to={`projects/${project.name}`} key={project.name}>
              <TwoLineTile>
                <div>
                  <MainText>{project.name}</MainText>
                  <AdditionalText>
                    {project.description || 'No description provided'}
                  </AdditionalText>
                </div>
              </TwoLineTile>
            </Link>
          );
        })}
      </List>
    </Card>
  );
};
ProjectsListCard.propTypes = propTypes;
ProjectsListCard.defaultProps = defaultProps;
