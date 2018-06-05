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

import { Button } from '../../buttons/Button';
import { TextField } from '../../form/Form';
import { Spacing } from '../../spacing/Spacing';
import { M } from '../../spacing/SpacingConstants';

import './EditDescription.css';

const EDITDESCRIPTION__CLASS_NAMES = 'editdescription';

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
export class EditDescription extends Component {
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
      <div className={EDITDESCRIPTION__CLASS_NAMES}>
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
