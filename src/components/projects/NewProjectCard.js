/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';

import { classNames } from '../../common/classnames';

import { Button, BUTTON_PRIMARY__KIND } from '../buttons/Button';
import { Card, Divider, PrimaryTitle } from '../cards/Card';
import {
  ActionGroup,
  Description,
  ErrorGroup,
  Error,
  Form,
  Field,
  Label,
  TextField
} from '../form/Form';

const NEWPROJECT_CARD__CLASS_NAMES = 'newprojectcard';

const propTypes = {};

/**
 * The NewProjectCard is used to create a new project.
 */
export const NewProjectCard = ({
  className,
  errors,
  isValid,
  onSubmit,
  name,
  nameIsValid,
  nameErrors,
  onNameChange,
  ...props
}) => {
  const newProjectCardClassNames = classNames(NEWPROJECT_CARD__CLASS_NAMES, className);
  let nameClassName = '';
  if (!nameIsValid && nameErrors.length > 0) {
    nameClassName = 'fielderror';
  }

  return (
    <Card className={newProjectCardClassNames} {...props}>
      <PrimaryTitle label="New Project" />
      <Divider />
      <Form onSubmit={onSubmit}>
        <ErrorGroup>{errors.map(error => <Error key={error}>{error}</Error>)}</ErrorGroup>
        <Field>
          <Label htmlFor="name">Name</Label>
          <Description>
            The name of the project can only contain letters and numbers separated by dots, dashes
            or underscores.
          </Description>
          <TextField
            id="name"
            name="name"
            className={nameClassName}
            placeholder="Enter the name"
            value={name}
            onChange={onNameChange}
          />
          <ErrorGroup>{nameErrors.map(error => <Error key={error}>{error}</Error>)}</ErrorGroup>
        </Field>
        <ActionGroup>
          <Button kind={BUTTON_PRIMARY__KIND} disabled={!isValid}>
            Create Project
          </Button>
        </ActionGroup>
      </Form>
    </Card>
  );
};
NewProjectCard.propTypes = propTypes;
