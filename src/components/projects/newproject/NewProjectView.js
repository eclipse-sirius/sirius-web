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
import { Redirect } from 'react-router-dom';

import { classNames } from '../../../common/classnames';
import { UNSUPPORTED_STATE } from '../../../common/errors';

import { ErrorCard } from '../../error/ErrorCard';

import { NewProjectCard } from '../NewProjectCard';

import {
  PRISTINE__STATE,
  MODIFIED__STATE,
  REDIRECT__STATE
} from './NewProjectViewFiniteStateMachine';

import './NewProjectView.css';

const propTypes = {
  stateId: PropTypes.string.isRequired,
  errors: PropTypes.arrayOf(PropTypes.string).isRequired,
  isValid: PropTypes.bool.isRequired,
  onSubmit: PropTypes.func.isRequired,
  name: PropTypes.string.isRequired,
  nameIsValid: PropTypes.bool.isRequired,
  nameErrors: PropTypes.arrayOf(PropTypes.string).isRequired,
  onNameChange: PropTypes.func.isRequired
};

const defaultProps = {
  errors: [],
  isValid: false,
  onSubmit: () => {},
  name: '',
  nameIsValid: false,
  nameErrors: [],
  onNameChange: () => {}
};

/**
 * The NewProjectView component is used to display a new project form.
 */
export const NewProjectView = ({
  className,
  stateId,
  errors,
  isValid,
  onSubmit,
  name,
  nameIsValid,
  nameErrors,
  onNameChange,
  ...props
}) => {
  switch (stateId) {
    case PRISTINE__STATE:
    case MODIFIED__STATE:
      return renderForm(
        className,
        errors,
        isValid,
        onSubmit,
        name,
        nameIsValid,
        nameErrors,
        onNameChange
      );
    case REDIRECT__STATE:
      return renderRedirectState(name);
    default:
      const undefinedStateError = {
        title: `The new project page is in an unsupported state: ${stateId}`,
        message: 'Contact your administrator to find a suitable solution',
        code: UNSUPPORTED_STATE
      };
      return renderErrorState(className, undefinedStateError, props);
  }
};
NewProjectView.propTypes = propTypes;
NewProjectView.defaultProps = defaultProps;

const NEW_PROJECT_VIEW__CLASS_NAMES = 'newprojectview';

/**
 * Renders the form used to create a new project.
 */
const renderForm = (
  className,
  errors,
  isValid,
  onSubmit,
  name,
  nameIsValid,
  nameErrors,
  onNameChange
) => {
  const newProjectViewClassNames = classNames(NEW_PROJECT_VIEW__CLASS_NAMES, className);
  return (
    <div className={newProjectViewClassNames}>
      <NewProjectCard
        errors={errors}
        isValid={isValid}
        onSubmit={onSubmit}
        name={name}
        nameIsValid={nameIsValid}
        nameErrors={nameErrors}
        onNameChange={onNameChange}
      />
    </div>
  );
};

/**
 * Renders a redirect component used to redirect the router to the page of the
 * newly created project.
 *
 * @param {*} name The name of the project
 */
const renderRedirectState = name => <Redirect to={`/projects/${name}`} />;

/**
 * Renders the error.
 * @param {*} className The class name of the project list
 * @param {*} error The error to render
 * @param {*} props The properties of the component
 */
const renderErrorState = (className, error, props) => {
  const newProjectViewErrorClassNames = classNames('', className);
  return <ErrorCard className={newProjectViewErrorClassNames} {...error} {...props} />;
};
