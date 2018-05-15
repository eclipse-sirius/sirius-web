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

import { Card, PrimaryTitle, Text } from '../cards/Card';

import './ProjectSummaryCard.css';

const PROJECT_SUMMARY_CARD__CLASS_NAMES = 'projectsummarycard';

const propTypes = {
  project: PropTypes.object.isRequired
};

/**
 * The ProjectSummaryCard is used to display a bird eye view of the project. It
 * displays the small amount of relevant properties of a project.
 */
export const ProjectSummaryCard = ({ project, ...props }) => {
  return (
    <Card className={PROJECT_SUMMARY_CARD__CLASS_NAMES} {...props}>
      <Link to={`/projects/${project.name}`}>
        <PrimaryTitle label={project.name} />
      </Link>
      <Text>{project.description || 'No description provided'}</Text>
    </Card>
  );
};
ProjectSummaryCard.propTypes = propTypes;
