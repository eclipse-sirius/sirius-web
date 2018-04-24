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

import './Button.css';

/** The default button kind. */
export const BUTTON_DEFAULT__KIND = 'default';

/** The primary button kind. */
export const BUTTON_PRIMARY__KIND = 'primary';

/** The success button kind. */
export const BUTTON_SUCCESS__KIND = 'success';

/** The warning button kind. */
export const BUTTON_WARNING__KIND = 'warning';

/** The danger button kind. */
export const BUTTON_DANGER__KIND = 'danger';

const propTypes = {
  kind: PropTypes.string
};
const defaultProps = {
  kind: BUTTON_DEFAULT__KIND
};

/**
 * The button component.
 */
export const Button = ({ className, kind, ...props }) => {
  const kindClassNames = getKindClassNames(kind);
  const buttonClassNames = classNames(BUTTON__CLASS_NAMES, kindClassNames, className);
  return (
    <button className={buttonClassNames} {...props}>
      {props.children}
    </button>
  );
};
Button.propTypes = propTypes;
Button.defaultProps = defaultProps;

const BUTTON__CLASS_NAMES = 'button';
const BUTTON_DEFAULT_KIND__CLASS_NAMES = 'button--default';
const BUTTON_PRIMARY_KIND__CLASS_NAMES = 'button--primary';
const BUTTON_SUCCESS_KIND__CLASS_NAMES = 'button--success';
const BUTTON_WARNING_KIND__CLASS_NAMES = 'button--warning';
const BUTTON_DANGER_KIND__CLASS_NAMES = 'button--danger';

/**
 * Computes the name of the kind class from the given kind.
 * @param kind The kind of the button
 * @returns The class names for the given kind
 */
const getKindClassNames = kind => {
  let kindClassNames;
  switch (kind) {
    case BUTTON_DEFAULT__KIND:
      kindClassNames = BUTTON_DEFAULT_KIND__CLASS_NAMES;
      break;
    case BUTTON_PRIMARY__KIND:
      kindClassNames = BUTTON_PRIMARY_KIND__CLASS_NAMES;
      break;
    case BUTTON_SUCCESS__KIND:
      kindClassNames = BUTTON_SUCCESS_KIND__CLASS_NAMES;
      break;
    case BUTTON_WARNING__KIND:
      kindClassNames = BUTTON_WARNING_KIND__CLASS_NAMES;
      break;
    case BUTTON_DANGER__KIND:
      kindClassNames = BUTTON_DANGER_KIND__CLASS_NAMES;
      break;
    default:
      kindClassNames = BUTTON_DEFAULT_KIND__CLASS_NAMES;
      break;
  }
  return kindClassNames;
};
