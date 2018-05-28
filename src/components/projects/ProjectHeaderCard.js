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

import { classNames } from '../../common/classnames';

import { Button } from '../buttons/Button';
import { Card, HeroTitle } from '../cards/Card';
import { TextField } from '../form/Form';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';

import './ProjectHeaderCard.css';

const PROJECTHEADERCARD__CLASS_NAMES = 'projectheadercard';

const projectHeaderCardPropTypes = {
  name: PropTypes.string.isRequired,
  description: PropTypes.string,
  onNewDescription: PropTypes.func.isRequired
};
const projectHeaderCardDefaultProps = {
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
        <HeroTitle label={name} />
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

const PROJECTHEADERCARD_VIEWDESCRIPTION__CLASS_NAMES = 'projectheadercard-viewdescription';

const viewDescriptionPropTypes = {
  description: PropTypes.string.isRequired,
  onEditClick: PropTypes.func.isRequired
};

/**
 * The ViewDescription component is used to display the description of the
 * project with a button used to edit it.
 */
const ViewDescription = ({ description, onEditClick }) => (
  <div className={PROJECTHEADERCARD_VIEWDESCRIPTION__CLASS_NAMES}>
    <p>{description}</p>
    <Spacing left={M}>
      <Button onClick={onEditClick}>Edit</Button>
    </Spacing>
  </div>
);
ViewDescription.propTypes = viewDescriptionPropTypes;

const PROJECTHEADERCARD_EDITDESCRIPTION__CLASS_NAMES = 'projectheadercard-editdescription';

const editDescriptionPropTypes = {
  description: PropTypes.string.isRequired,
  onNewDescription: PropTypes.func.isRequired
};
const editDescriptionDefaultProps = {
  description: ''
};

/**
 * The EditDescription component is used to edit the description of the project.
 */
class EditDescription extends Component {
  constructor(props) {
    super(props);
    this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
    this.handleDoneClick = this.handleDoneClick.bind(this);

    this.state = {
      description: props.description
    };
  }

  handleDescriptionChange({ target: { value } }) {
    this.setState({ description: value });
  }

  handleDoneClick() {
    const { onNewDescription } = this.props;
    const { description } = this.state;
    onNewDescription(description);
  }

  render() {
    const { description } = this.state;
    return (
      <div className={PROJECTHEADERCARD_EDITDESCRIPTION__CLASS_NAMES}>
        <TextField value={description} onChange={this.handleDescriptionChange} />
        <Spacing left={M}>
          <Button onClick={this.handleDoneClick}>Done</Button>
        </Spacing>
      </div>
    );
  }
}
EditDescription.propTypes = editDescriptionPropTypes;
EditDescription.defaultProps = editDescriptionDefaultProps;
