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

import { Body, Card } from '../cards/Card';

import './BlankCard.css';

const propTypes = {
  title: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired
};

const BLANKCARD__CLASS_NAMES = 'blankcard';
const BLANKCARD_TITLE__CLASS_NAMES = 'title-l';

/**
 * The BlankCard component is used to render the empty state of a page.
 */
export const BlankCard = ({ className, title, message, ...props }) => {
  const blankCardClassNames = classNames(BLANKCARD__CLASS_NAMES, className);
  return (
    <Card {...props} className={blankCardClassNames}>
      <Body>
        <h1 className={BLANKCARD_TITLE__CLASS_NAMES}>{title}</h1>
        <p>{message}</p>
      </Body>
    </Card>
  );
};
BlankCard.propTypes = propTypes;
