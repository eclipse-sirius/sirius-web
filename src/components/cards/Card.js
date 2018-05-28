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

import './Card.css';

const CARD__CLASS_NAMES = 'card';

/**
 * The Card component is used as a common block of the user interface.
 */
export const Card = ({ className, ...props }) => {
  const cardClassNames = classNames(CARD__CLASS_NAMES, className);
  return (
    <div className={cardClassNames} {...props}>
      {props.children}
    </div>
  );
};

const CARD_DIVIDER__CLASS_NAMES = 'card-divider';

/**
 * The Divider is used to separate two main parts of the Card like the Header
 * and the Body or the Body and the Footer.
 */
export const Divider = ({ className, ...props }) => {
  const dividerClassNames = classNames(CARD_DIVIDER__CLASS_NAMES, className);
  return <div className={dividerClassNames} {...props} />;
};
