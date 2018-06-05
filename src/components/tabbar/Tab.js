/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Text } from '../text/Text';
import { SEMI_BOLD, MEDIUM } from '../text/TextConstants';

import './Tab.css';

const TAB__CLASS_NAMES = 'tab';
const TAB_SELECTED__CLASS_NAMES = 'tab--selected';

const propTypes = {
  selected: PropTypes.bool.isRequired,
  index: PropTypes.number.isRequired,
  onClick: PropTypes.func.isRequired
};
const defaultProps = {
  selected: false,
  onClick: () => {}
};

/**
 * The Tab component is used to display one tab in a TabBar.
 */
export class Tab extends Component {
  constructor(props) {
    super(props);

    this.handleTabClick = this.handleTabClick.bind(this);
  }

  handleTabClick(event) {
    const { index, onClick } = this.props;
    onClick(event, index);
  }

  render() {
    const { className, children, selected } = this.props;
    let tabClassNames = TAB__CLASS_NAMES;
    if (selected) {
      tabClassNames = classNames(tabClassNames, TAB_SELECTED__CLASS_NAMES);
    }
    tabClassNames = classNames(tabClassNames, className);
    return (
      <div className={tabClassNames} onClick={this.handleTabClick}>
        <Text weight={SEMI_BOLD} size={MEDIUM} hideOverflow>
          {children}
        </Text>
      </div>
    );
  }
}
Tab.propTypes = propTypes;
Tab.defaultProps = defaultProps;
