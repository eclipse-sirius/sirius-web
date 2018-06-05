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
import { AdditionalText, List, MainText, Tile } from '../list/List';
import { LIST_WITH_HIGHLIGHT__KIND, TWO_LINES } from '../list/ListConstants';
import { LoadingConsumer } from '../loading/Loading';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { SEMI_BOLD, LARGE } from '../text/TextConstants';

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
export const ProjectRepresentationsListCard = ({
  className,
  projectName,
  representations,
  ...props
}) => {
  const cardClassNames = classNames(PROJECT_REPRESENTATIONS_LIST_CARD__CLASS_NAMES, className);
  return (
    <Card className={cardClassNames} {...props}>
      <Spacing top={M} right={M} bottom={M} left={M}>
        <LoadingConsumer>
          {loading => (
            <Text weight={SEMI_BOLD} size={LARGE} loading={loading} hideOverflow>
              Representations
            </Text>
          )}
        </LoadingConsumer>
      </Spacing>
      <Representations projectName={projectName} representations={representations} />
    </Card>
  );
};
ProjectRepresentationsListCard.propTypes = propTypes;
ProjectRepresentationsListCard.defaultProps = defaultProps;

/**
 * Renders the representations of the card or a text indicating the lack of
 * representations.
 */
const Representations = ({ projectName, representations }) => {
  if (representations.length > 0) {
    return <RepresentationsList projectName={projectName} representations={representations} />;
  }
  return <EmptyRepresentations />;
};

/**
 * Renders the list of representations.
 */
const RepresentationsList = ({ projectName, representations }) => (
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
const EmptyRepresentations = () => (
  <Spacing top={S} right={M} bottom={M} left={M}>
    <LoadingConsumer>
      {loading => <Text loading={loading}>No representation has been found on the project.</Text>}
    </LoadingConsumer>
  </Spacing>
);
