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
import { Spacing } from '../spacing/Spacing';
import { M, L } from '../spacing/SpacingConstants';

import './ErrorCard.css';

const propTypes = {
  code: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired
};

const ERRORCARD__CLASS_NAMES = 'errorcard';
const ERRORCARD_CONTAINER__CLASS_NAMES = 'errorcard-container';
const ERRORCARD_TITLE__CLASS_NAMES = 'title-l';

/**
 * The ErrorCard component is used to display errors.
 */
export const ErrorCard = ({ className, code, title, message, ...props }) => {
  const errorCardClassNames = classNames(ERRORCARD__CLASS_NAMES, className);
  return (
    <Card {...props} className={errorCardClassNames}>
      <Spacing top={L} right={L} bottom={L} left={L}>
        <div className={ERRORCARD_CONTAINER__CLASS_NAMES}>
          <Spacing top={M} right={M} bottom={M} left={M}>
            <h1 className={ERRORCARD_TITLE__CLASS_NAMES}>{title}</h1>
          </Spacing>
          <p>{`${message} (code: ${code})`}</p>
        </div>
      </Spacing>
    </Card>
  );
};
ErrorCard.propTypes = propTypes;
