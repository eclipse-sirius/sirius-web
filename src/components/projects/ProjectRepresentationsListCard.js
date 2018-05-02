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
import {
  LIST_WITH_HIGHLIGHT__KIND,
  AdditionalText,
  List,
  MainText,
  TwoLineTile
} from '../list/List';

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
      <Header>
        <Title>Representations</Title>
      </Header>
      <Body>
        <List kind={LIST_WITH_HIGHLIGHT__KIND}>
          {representations.map(representation => (
            <TwoLineTile key={representation.name}>
              <div>
                <MainText>{representation.name}</MainText>
                <AdditionalText>{representation.descriptionName}</AdditionalText>
              </div>
            </TwoLineTile>
          ))}
        </List>
      </Body>
    </Card>
  );
};
ProjectRepresentationsListCard.propTypes = propTypes;
ProjectRepresentationsListCard.defaultProps = defaultProps;
