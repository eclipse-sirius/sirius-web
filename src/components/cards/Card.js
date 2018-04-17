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
import { Link } from 'react-router-dom';

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

const CARD_HEADER__CLASS_NAMES = 'card-header';

/**
 * The Header component is used to display a header in the Card.
 */
export const Header = ({ className, ...props }) => {
  const headerClassNames = classNames(CARD_HEADER__CLASS_NAMES, className);
  return (
    <div className={headerClassNames} {...props}>
      {props.children}
    </div>
  );
};

/** The large title kind. */
export const TITLE_LARGE__KIND = 'large';

const CARD_TITLE__CLASS_NAMES = 'card-title';
const CARD_TITLE_REGULAR__CLASS_NAMES = 'title-l';
const CARD_TITLE_LARGE__CLASS_NAMES = 'title-xl';

const titlePropTypes = {
  kind: PropTypes.string
};
const titleDefaultProps = {
  kind: ''
};

/**
 * The Title of the Card, to be used inside a Header.
 */
export const Title = ({ className, kind, ...props }) => {
  let additionalClassNames = CARD_TITLE_REGULAR__CLASS_NAMES;
  if (kind === TITLE_LARGE__KIND) {
    additionalClassNames = CARD_TITLE_LARGE__CLASS_NAMES;
  }
  const titleClassNames = classNames(CARD_TITLE__CLASS_NAMES, additionalClassNames, className);
  return (
    <h1 className={titleClassNames} {...props}>
      {props.children}
    </h1>
  );
};
Title.propTypes = titlePropTypes;
Title.defaultProps = titleDefaultProps;

const CARD_DIVIDER__CLASS_NAMES = 'card-divider';

/**
 * The Divider is used to separate two main parts of the Card like the Header
 * and the Body or the Body and the Footer.
 */
export const Divider = ({ className, ...props }) => {
  const dividerClassNames = classNames(CARD_DIVIDER__CLASS_NAMES, className);
  return <div className={dividerClassNames} {...props} />;
};

const CARD_BODY__CLASS_NAMES = 'card-body';

/**
 * The Body is the main part of the Card where most of its content will be located.
 */
export const Body = ({ className, ...props }) => {
  const bodyClassNames = classNames(CARD_BODY__CLASS_NAMES, className);
  return (
    <div className={bodyClassNames} {...props}>
      {props.children}
    </div>
  );
};

const CARD_FOOTER__CLASS_NAMES = 'card-footer';

/**
 * The Footer is used to display some actions or links.
 */
export const Footer = ({ className, ...props }) => {
  const footerClassNames = classNames(CARD_FOOTER__CLASS_NAMES, className);
  return (
    <div className={footerClassNames} {...props}>
      {props.children}
    </div>
  );
};

const CARD_FOOTERACTION__CLASS_NAMES = 'card-footerlink';

/**
 * The FooterLink is a child of the Footer used as a Link.
 */
export const FooterLink = ({ to, className, ...props }) => {
  const footerActionClassNames = classNames(CARD_FOOTERACTION__CLASS_NAMES, className);
  return (
    <Link to={to} className={footerActionClassNames} {...props}>
      {props.children}
    </Link>
  );
};
