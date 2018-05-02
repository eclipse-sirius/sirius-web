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

import { Body, Card, Header, Title } from '../cards/Card';
import { List, MainText, SingleLineTile, LIST_WITH_HIGHLIGHT__KIND } from '../list/List';

const PROJECT_SEMANTIC_RESOURCES_LIST_CARD__CLASS_NAMES = 'projectsemanticresourceslistcard';

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
      <Header>
        <Title>Semantic Resources</Title>
      </Header>
      <Body>
        <List kind={LIST_WITH_HIGHLIGHT__KIND}>
          {semanticResources.map(resource => (
            <SingleLineTile key={resource.path}>
              <MainText>{resource.path}</MainText>
            </SingleLineTile>
          ))}
        </List>
      </Body>
    </Card>
  );
};
ProjectSemanticResourcesListCard.propTypes = propTypes;
ProjectSemanticResourcesListCard.defaultProps = defaultProps;
