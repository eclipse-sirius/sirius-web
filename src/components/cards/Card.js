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

import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';

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

const CARD_HEROTITLE__CLASS_NAMES = 'card-herotitle title-xl';

const heroTitlePropTypes = {
  label: PropTypes.string.isRequired
};

/**
 * The Hero Title of the Card.
 */
export const HeroTitle = ({ className, label, ...props }) => {
  const heroTitleClassNames = classNames(CARD_HEROTITLE__CLASS_NAMES, className);
  return (
    <Spacing top={M} right={M} bottom={M} left={M}>
      <h1 className={heroTitleClassNames} {...props}>
        {label}
      </h1>
    </Spacing>
  );
};
HeroTitle.propTypes = heroTitlePropTypes;

const CARD_PRIMARYTITLE__CLASS_NAMES = 'card-primarytitle title-l';

const primaryTitlePropTypes = {
  label: PropTypes.string.isRequired
};

/**
 * The Primary Title of the Card.
 */
export const PrimaryTitle = ({ className, label, ...props }) => {
  const primaryTitleClassNames = classNames(CARD_PRIMARYTITLE__CLASS_NAMES, className);
  return (
    <Spacing top={M} right={M} bottom={M} left={M}>
      <h2 className={primaryTitleClassNames} {...props}>
        {label}
      </h2>
    </Spacing>
  );
};
PrimaryTitle.propTypes = primaryTitlePropTypes;

const CARD_SECONDARYTITLE__CLASS_NAMES = 'card-secondarytitle title-m';

const secondaryTitlePropTypes = {
  label: PropTypes.string.isRequired
};

/**
 * The Secondary Title of the card.
 */
export const SecondaryTitle = ({ className, label, ...props }) => {
  const secondaryTitleClassNames = classNames(CARD_SECONDARYTITLE__CLASS_NAMES, className);
  return (
    <Spacing top={M} right={M} bottom={S} left={M}>
      <h3 className={secondaryTitleClassNames} {...props}>
        {label}
      </h3>
    </Spacing>
  );
};
SecondaryTitle.propTypes = secondaryTitlePropTypes;

const CARD_TEXT__CLASS_NAMES = 'card-text body-s';

export const Text = ({ className, ...props }) => {
  const textClassNames = classNames(CARD_TEXT__CLASS_NAMES, className);
  return (
    <Spacing top={S} right={M} bottom={M} left={M}>
      <div className={textClassNames} {...props} />
    </Spacing>
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

const CARD_ACTIONS__CLASS_NAMES = 'card-actions';

/**
 * The Actions component is used to display some actions or links.
 */
export const Actions = ({ className, ...props }) => {
  const actionsClassNames = classNames(CARD_ACTIONS__CLASS_NAMES, className);
  return (
    <div className={actionsClassNames} {...props}>
      {props.children}
    </div>
  );
};
