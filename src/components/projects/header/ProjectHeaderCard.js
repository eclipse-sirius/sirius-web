/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../../common/classnames';

import { Card } from '../../cards/Card';
import { LoadingConsumer } from '../../loading/Loading';
import { Spacing } from '../../spacing/Spacing';
import { S, M } from '../../spacing/SpacingConstants';
import { Text } from '../../text/Text';
import { SEMI_BOLD, EXTRA_LARGE } from '../../text/TextConstants';

import { EditDescription } from './EditDescription';
import { ViewDescription } from './ViewDescription';

const PROJECTHEADERCARD__CLASS_NAMES = 'projectheadercard';

const projectHeaderCardPropTypes = {
  name: PropTypes.string.isRequired,
  description: PropTypes.string,
  onNewDescription: PropTypes.func.isRequired
};
const projectHeaderCardDefaultProps = {
  name: '',
  onNewDescription: () => {}
};

/**
 * The ProjectHeaderCard is used to contain the most important properties of a
 * project and the main actions used to interact with it.
 */
export class ProjectHeaderCard extends Component {
  constructor(props) {
    super(props);

    this.handleEditClick = this.handleEditClick.bind(this);
    this.handleNewDescription = this.handleNewDescription.bind(this);

    this.state = { isViewingDescription: true };
  }

  handleEditClick() {
    this.setState({ isViewingDescription: false });
  }

  handleNewDescription(description) {
    this.setState({ isViewingDescription: true }, () => {
      const { onNewDescription } = this.props;
      onNewDescription(description);
    });
  }

  render() {
    const { className, name, description, onNewDescription, ...props } = this.props;
    const { isViewingDescription } = this.state;

    const cardClassNames = classNames(PROJECTHEADERCARD__CLASS_NAMES, className);
    return (
      <Card className={cardClassNames} {...props}>
        <Spacing top={M} right={M} bottom={M} left={M}>
          <LoadingConsumer>
            {loading => (
              <Text weight={SEMI_BOLD} size={EXTRA_LARGE} hideOverflow loading={loading}>
                {name}
              </Text>
            )}
          </LoadingConsumer>
        </Spacing>
        <Spacing top={S} right={M} bottom={S} left={M}>
          {isViewingDescription ? (
            <ViewDescription
              description={description || 'No description provided'}
              onEditClick={this.handleEditClick}
            />
          ) : (
            <EditDescription
              description={description}
              onNewDescription={this.handleNewDescription}
            />
          )}
        </Spacing>
      </Card>
    );
  }
}
ProjectHeaderCard.propTypes = projectHeaderCardPropTypes;
ProjectHeaderCard.defaultProps = projectHeaderCardDefaultProps;
