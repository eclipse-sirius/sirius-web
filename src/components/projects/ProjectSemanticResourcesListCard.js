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

import { Card } from '../cards/Card';
import { LIST_WITH_HIGHLIGHT__KIND, List, MainText, Tile } from '../list/List';
import { SINGLE_LINE } from '../list/ListConstants';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { SEMI_BOLD, SMALL, LARGE } from '../text/TextConstants';

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
      <Spacing top={M} right={M} bottom={M} left={M}>
        <Text weight={SEMI_BOLD} size={LARGE} hideOverflow>
          Semantic Resources
        </Text>
      </Spacing>
      <SemanticResources semanticResources={semanticResources} />
    </Card>
  );
};
ProjectSemanticResourcesListCard.propTypes = propTypes;
ProjectSemanticResourcesListCard.defaultProps = defaultProps;

/**
 * Renders the semantic resources of the card or a text indicating the lack of
 * semantic resources.
 */
const SemanticResources = ({ semanticResources }) => {
  if (semanticResources.length > 0) {
    return <SemanticResourcesList semanticResources={semanticResources} />;
  }
  return <EmptySemanticResources />;
};

/**
 * Renders the list of semantic resources.
 */
const SemanticResourcesList = ({ semanticResources }) => (
  <List kind={LIST_WITH_HIGHLIGHT__KIND}>
    {semanticResources.map(resource => (
      <Tile kind={SINGLE_LINE} key={resource.path}>
        <MainText>{resource.path}</MainText>
        <Text size={SMALL}>{resource.size}</Text>
      </Tile>
    ))}
  </List>
);

/**
 * Renders a text indicating the lack of semantic resources.
 */
const EmptySemanticResources = () => (
  <Spacing top={S} right={M} bottom={M} left={M}>
    <Text>No semantic resource has been found on the project.</Text>
  </Spacing>
);
