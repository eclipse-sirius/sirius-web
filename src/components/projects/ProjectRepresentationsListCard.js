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

import { Card, PrimaryTitle, Text } from '../cards/Card';
import { LIST_WITH_HIGHLIGHT__KIND, AdditionalText, List, MainText, Tile } from '../list/List';
import { TWO_LINES } from '../list/ListConstants';

const PROJECT_REPRESENTATIONS_LIST_CARD__CLASS_NAMES = 'projectrepresentationslistcard';

const propTypes = {
  representations: PropTypes.array.isRequired
};

const defaultProps = {
  representations: []
};

/**
 * The ProjectRepresentationsListCard is a Card listing all the representations
 * of a project.
 */
export const ProjectRepresentationsListCard = ({ className, representations, ...props }) => {
  const cardClassNames = classNames(PROJECT_REPRESENTATIONS_LIST_CARD__CLASS_NAMES, className);
  return (
    <Card className={cardClassNames} {...props}>
      <PrimaryTitle label="Representations" />
      <Representations representations={representations} />
    </Card>
  );
};
ProjectRepresentationsListCard.propTypes = propTypes;
ProjectRepresentationsListCard.defaultProps = defaultProps;

/**
 * Renders the representations of the card or a text indicating the lack of
 * representations.
 */
const Representations = ({ representations }) => {
  if (representations.length > 0) {
    return <RepresentationsList representations={representations} />;
  }
  return <EmptyRepresentations />;
};

/**
 * Renders the list of representations.
 */
const RepresentationsList = ({ representations }) => (
  <List kind={LIST_WITH_HIGHLIGHT__KIND}>
    {representations.map(representation => (
      <Tile kind={TWO_LINES} key={representation.name}>
        <div>
          <MainText>{representation.name}</MainText>
          <AdditionalText>{representation.descriptionName}</AdditionalText>
        </div>
      </Tile>
    ))}
  </List>
);

/**
 * Renders a text indicating the lack of representations.
 */
const EmptyRepresentations = () => <Text>No representation has been found on the project.</Text>;
