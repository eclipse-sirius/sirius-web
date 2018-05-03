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

import { Card, PrimaryTitle } from '../cards/Card';
import { List, MainText, SingleLineTile, LIST_WITH_HIGHLIGHT__KIND } from '../list/List';

const PROJECT_SEMANTIC_RESOURCES_LIST_CARD__CLASS_NAMES = 'projectsemanticresourceslistcard';
const SEMANTIC_RESOURCES_SIZE__CLASS_NAMES = 'semanticresources-size caption-s';

const propTypes = {
  semanticResources: PropTypes.array.isRequired
};

const defaultProps = {
  semanticResources: []
};

/**
 * The ProjectSemanticResourcesListCard is a Card displaying all the semantic
 * resources of a project.
 */
export const ProjectSemanticResourcesListCard = ({ className, semanticResources, ...props }) => {
  const cardClassNames = classNames(PROJECT_SEMANTIC_RESOURCES_LIST_CARD__CLASS_NAMES, className);
  return (
    <Card className={cardClassNames} {...props}>
      <PrimaryTitle label="Semantic Resources" />
      <List kind={LIST_WITH_HIGHLIGHT__KIND}>
        {semanticResources.map(resource => (
          <SingleLineTile key={resource.path}>
            <MainText>{resource.path}</MainText>
            <div className={SEMANTIC_RESOURCES_SIZE__CLASS_NAMES}>{resource.size}</div>
          </SingleLineTile>
        ))}
      </List>
    </Card>
  );
};
ProjectSemanticResourcesListCard.propTypes = propTypes;
ProjectSemanticResourcesListCard.defaultProps = defaultProps;
