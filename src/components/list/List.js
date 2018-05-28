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
import { S } from '../spacing/SpacingConstants';

import { SINGLE_LINE, TWO_LINES, THREE_LINES } from './ListConstants';

import './List.css';

const TILE_MAINICON__CLASS_NAMES = 'tile-mainicon';

const mainIconPropTypes = {};

/**
 * The main icon is used to display an image before the main text.
 */
export const MainIcon = ({ className, ...props }) => {
  const mainIconClassNames = classNames(TILE_MAINICON__CLASS_NAMES, className);
  return <div className={mainIconClassNames} {...props} />;
};
MainIcon.propTypes = mainIconPropTypes;

const TILE_ADDITIONALICON__CLASS_NAMES = 'tile-additionalicon';

const additionalIconPropTypes = {};

/**
 * The additional icon is used to display images after the main text.
 */
export const AdditionalIcon = ({ className, ...props }) => {
  const additionalIconClassNames = classNames(TILE_ADDITIONALICON__CLASS_NAMES, className);
  return <div className={additionalIconClassNames} {...props} />;
};
AdditionalIcon.propTypes = additionalIconPropTypes;

const TILE_MAINTEXT__CLASS_NAMES = 'tile-maintext body-s';

const mainTextPropTypes = {
  children: PropTypes.string
};

/**
 * The MainText component is used as the main text content of a tile.
 */
export const MainText = ({ className, children, ...props }) => {
  const mainTextClassNames = classNames(TILE_MAINTEXT__CLASS_NAMES, className);
  return (
    <h4 className={mainTextClassNames} {...props}>
      {children}
    </h4>
  );
};
MainText.propTypes = mainTextPropTypes;

const TILE_ADDITIONALTEXT__CLASS_NAMES = 'tile-additionaltext caption-xs';

const additionalTextPropTypes = {
  children: PropTypes.string
};

/**
 * The AdditionalText component is used as the additional text content of a tile.
 */
export const AdditionalText = ({ className, children, ...props }) => {
  const additionalTextClassNames = classNames(TILE_ADDITIONALTEXT__CLASS_NAMES, className);
  return (
    <h5 className={additionalTextClassNames} {...props}>
      {children}
    </h5>
  );
};
AdditionalText.propTypes = additionalTextPropTypes;

const TILE__CLASS_NAMES = 'tile';
const TILE__SINGLELINE__CLASS_NAMES = 'tile--singleline';
const TILE__TWOLINE__CLASS_NAMES = 'tile--twoline';
const TILE__THREELINE__CLASS_NAMES = 'tile--threeline';

const tilePropTypes = {
  kind: PropTypes.oneOf([SINGLE_LINE, TWO_LINES, THREE_LINES]).isRequired
};

/**
 * The SingleLineTile is used to represent one line of the list component. It
 * can display a main icon, some text and additional icons. The text displayed
 * can only use one line for the main text.
 */
export const Tile = ({ className, kind, ...props }) => {
  let kindClassName = TILE__SINGLELINE__CLASS_NAMES;
  if (kind === TWO_LINES) {
    kindClassName = TILE__TWOLINE__CLASS_NAMES;
  } else if (kind === THREE_LINES) {
    kindClassName = TILE__THREELINE__CLASS_NAMES;
  }
  const tileClassNames = classNames(TILE__CLASS_NAMES, kindClassName, className);
  return <li className={tileClassNames} {...props} />;
};
Tile.propTypes = tilePropTypes;

/** The list with separator kind. */
export const LIST_WITH_SEPARATOR__KIND = 'list--separated';

/** The list with highlight kind. */
export const LIST_WITH_HIGHLIGHT__KIND = 'list--highlighted';

const LIST__CLASS_NAMES = 'list';

const listPropTypes = {
  kind: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  children: PropTypes.oneOfType([PropTypes.arrayOf(Tile), PropTypes.objectOf(Tile)])
};
const listDefaultProps = {
  kind: ''
};

/**
 * The List component is used to display a list of elements.
 *
 * Those elements can be separated by a line by using LIST_WITH_SEPARATOR__KIND
 * in the property kind. You can also add some highlight to the element over
 * which the mouse is located with LIST_WITH_HIGHLIGHT__KIND. Both properties
 * can be used at the same time.
 *
 * The List component can only have ListItem children.
 */
export const List = ({ children, className, kind, ...props }) => {
  const kinds = computeKinds(kind);
  const listClassNames = classNames(LIST__CLASS_NAMES, ...kinds, className);
  return (
    <ul className={listClassNames} {...props}>
      <Spacing top={S} bottom={S}>
        {children}
      </Spacing>
    </ul>
  );
};
List.propTypes = listPropTypes;
List.defaultProps = listDefaultProps;

/**
 * Computes the kind ot the link.
 * @param kind The kind (which can be an array or a string)
 * @return The computed kind
 */
const computeKinds = kind => {
  let kinds = [];
  if (Array.isArray(kind)) {
    kinds = kind;
  } else if (typeof kind === 'string') {
    kinds.push(kind);
  }
  return kinds;
};
