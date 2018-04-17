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

import './List.css';

const LISTITEM__CLASS_NAMES = 'listitem';
const LISTITEM_LINK__CLASS_NAMES = 'listitem--link';

const listItemPropTypes = {
  to: PropTypes.string
};

/**
 * The ListItem is used to represent one item of the link component. It can
 * display some content like text but it can also be used as a link directly.
 */
export const ListItem = ({ className, to, ...props }) => {
  const additionalClassNames = [];
  if (to) {
    additionalClassNames.push(LISTITEM_LINK__CLASS_NAMES);
  }
  if (className) {
    additionalClassNames.push(className);
  }
  const listItemClassNames = classNames(LISTITEM__CLASS_NAMES, ...additionalClassNames);

  if (to) {
    return (
      <li className={listItemClassNames} {...props}>
        <Link to={to}>{props.children}</Link>
      </li>
    );
  }
  return <li className={listItemClassNames} {...props} />;
};
ListItem.propTypes = listItemPropTypes;

/** The list with separator kind. */
export const LIST_WITH_SEPARATOR__KIND = 'list--separated';

/** The list with highlight kind. */
export const LIST_WITH_HIGHLIGHT__KIND = 'list--highlighted';

const LIST__CLASS_NAMES = 'list';

const listPropTypes = {
  kind: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  children: PropTypes.oneOfType([PropTypes.arrayOf(ListItem), PropTypes.objectOf(ListItem)])
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
export const List = ({ className, kind, ...props }) => {
  const kinds = computeKinds(kind);
  const listClassNames = classNames(LIST__CLASS_NAMES, ...kinds, className);
  return <ul className={listClassNames} {...props} />;
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

const LISTITEM_TITLE__CLASS_NAMES = 'listitem-title title-m';

/**
 * The ListItemTitle is used to create richer LinkItems with both a title and a
 * description.
 */
export const ListItemTitle = ({ className, ...props }) => {
  const listItemTitleClassNames = classNames(LISTITEM_TITLE__CLASS_NAMES, className);
  return <div className={listItemTitleClassNames} {...props} />;
};

const LISTITEM_DESCRIPTION__CLASS_NAMES = 'listitem-description body-m';

/**
 * The ListItemDescription is used to create richer LinkItems with both a title
 * and a description.
 */
export const ListItemDescription = ({ className, ...props }) => {
  const listItemDescriptionClassNames = classNames(LISTITEM_DESCRIPTION__CLASS_NAMES, className);
  return <div className={listItemDescriptionClassNames} {...props} />;
};
