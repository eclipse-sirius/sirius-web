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

import { Loading } from '../loading/Loading';
import { Spacing } from '../spacing/Spacing';
import { M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { SEMI_BOLD } from '../text/TextConstants';

import {
  BUTTON_DANGER__KIND,
  BUTTON_DEFAULT__KIND,
  BUTTON_PRIMARY__KIND,
  BUTTON_SUCCESS__KIND,
  BUTTON_WARNING__KIND
} from './ButtonConstants';

import './Button.css';

const propTypes = {
  kind: PropTypes.oneOf([
    BUTTON_DANGER__KIND,
    BUTTON_DEFAULT__KIND,
    BUTTON_PRIMARY__KIND,
    BUTTON_SUCCESS__KIND,
    BUTTON_WARNING__KIND
  ]),
  loading: PropTypes.bool
};
const defaultProps = {
  kind: BUTTON_DEFAULT__KIND
};

const BUTTON__CLASS_NAMES = 'button';
const BUTTON__LOADING__CLASS_NAMES = 'button--loading';
const BUTTON_CONTAINER__CLASS_NAMES = 'button-container';
const BUTTON_LOADINGINDICATOR__CLASS_NAMES = 'button-loadingindicator';
const BUTTON_LABEL__CLASS_NAMES = 'button-label';

/**
 * The button component.
 */
export const Button = ({ className, kind, loading, ...props }) => {
  let buttonClassNames = classNames(BUTTON__CLASS_NAMES, className);
  if (loading) {
    buttonClassNames = classNames(buttonClassNames, BUTTON__LOADING__CLASS_NAMES);
  } else {
    const kindClassNames = getKindClassNames(kind);
    buttonClassNames = classNames(buttonClassNames, kindClassNames);
  }

  return (
    <button className={buttonClassNames} {...props} disabled={loading}>
      <Spacing right={M} left={M}>
        <div className={BUTTON_CONTAINER__CLASS_NAMES}>
          <Loading className={BUTTON_LOADINGINDICATOR__CLASS_NAMES} />
          <Text className={BUTTON_LABEL__CLASS_NAMES} weight={SEMI_BOLD}>
            {props.children}
          </Text>
        </div>
      </Spacing>
    </button>
  );
};
Button.propTypes = propTypes;
Button.defaultProps = defaultProps;

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
