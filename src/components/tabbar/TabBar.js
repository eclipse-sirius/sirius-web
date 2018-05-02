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

import { actionCreator, dispatcher } from './TabBarDispatcher';

import './TabBar.css';

const TABBAR__CLASS_NAMES = 'tabbar';
const TABBAR_NAV__CLASS_NAMES = 'tabbar-nav';
const TABBAR_NAV__DISABLED__CLASS_NAMES = 'tabbar-nav--disabled';
const TABBAR_TAB__CONTAIN_SELECTION__CLASS_NAMES = 'tabbar-nav--contain-selection';
const TABBAR_TABS__CLASS_NAMES = 'tabbar-tabs';
const TABBAR_TAB__CLASS_NAMES = 'tabbar-tab title-m';
const TABBAR_TAB_SELECTED__CLASS_NAMES = 'tabbar-tab--selected';

const propTypes = {
  tabs: PropTypes.array.isRequired,
  onTabClick: PropTypes.func.isRequired,
  selectedTabIndex: PropTypes.number.isRequired,
  numberOfTabsDisplayed: PropTypes.number.isRequired
};
const defaultProps = {
  tabs: [],
  onTabClick: () => {},
  selectedTabIndex: 0,
  numberOfTabsDisplayed: 3
};

/**
 * The TabBar component is used to display horizontally a list of elements with
 * two buttons to navigate into the list to the previous or next elements. It
 * also allows the selection of one specific element in the list.
 */
export class TabBar extends Component {
  constructor(props) {
    super(props);

    this.handleTabBarNavClick = this.handleTabBarNavClick.bind(this);
    this.handleTabClick = this.handleTabClick.bind(this);

    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  dispatch(action, callback) {
    this.setState((prevState, props) => dispatcher(prevState, props, action), callback);
  }

  handleTabBarNavClick({ target }) {
    const side = target.getAttribute('data-side');
    if (side === 'previous' && this.state.isPreviousAvailable) {
      const action = actionCreator.newHandlePreviousAction();
      this.dispatch(action);
    } else if (side === 'next' && this.state.isNextAvailable) {
      const action = actionCreator.newHandleNextAction();
      this.dispatch(action);
    }
  }

  handleTabClick(event) {
    const { onTabClick } = this.props;
    const indexValue = event.target.getAttribute('data-index');
    const selectedTabIndex = parseInt(indexValue, 10);

    const action = actionCreator.newHandleTabSelectedAction(selectedTabIndex);
    this.dispatch(action, () => onTabClick(selectedTabIndex));
  }

  getTabBarNavClassNames(isAvailable, containsSelection) {
    let tabBarNavClassNames = TABBAR_NAV__CLASS_NAMES;
    if (!isAvailable) {
      tabBarNavClassNames = classNames(TABBAR_NAV__CLASS_NAMES, TABBAR_NAV__DISABLED__CLASS_NAMES);
    } else if (containsSelection) {
      tabBarNavClassNames = classNames(
        TABBAR_NAV__CLASS_NAMES,
        TABBAR_TAB__CONTAIN_SELECTION__CLASS_NAMES
      );
    }
    return tabBarNavClassNames;
  }

  renderTabs(tabs, startIndex, selectedTabIndex, numberOfTabsDisplayed) {
    const tabsToDisplay = [];
    const endIndex = Math.min(tabs.length, startIndex + numberOfTabsDisplayed);
    for (let index = startIndex; index < endIndex; index++) {
      let tab = tabs[index];

      let tabClassNames = TABBAR_TAB__CLASS_NAMES;
      if (index === selectedTabIndex) {
        tabClassNames = classNames(TABBAR_TAB__CLASS_NAMES, TABBAR_TAB_SELECTED__CLASS_NAMES);
      }
      tabsToDisplay.push(
        <div key={tab} data-index={index} className={tabClassNames} onClick={this.handleTabClick}>
          {tab}
        </div>
      );
    }
    return tabsToDisplay;
  }

  render() {
    const { className, tabs, numberOfTabsDisplayed } = this.props;
    const { index, isPreviousAvailable, isNextAvailable, selectedTabIndex } = this.state;

    const previousContainsSelection = selectedTabIndex < index;
    const nextContainsSelection = index + numberOfTabsDisplayed < selectedTabIndex + 1;

    const tabBarClassNames = classNames(TABBAR__CLASS_NAMES, className);
    const previousClassNames = this.getTabBarNavClassNames(
      isPreviousAvailable,
      previousContainsSelection
    );
    const nextClassNames = this.getTabBarNavClassNames(isNextAvailable, nextContainsSelection);
    const tabsToDisplay = this.renderTabs(tabs, index, selectedTabIndex, numberOfTabsDisplayed);

    return (
      <div className={tabBarClassNames}>
        <div
          className={previousClassNames}
          onClick={this.handleTabBarNavClick}
          data-side="previous">
          &lt;
        </div>
        <div className={TABBAR_TABS__CLASS_NAMES}>{tabsToDisplay}</div>
        <div className={nextClassNames} onClick={this.handleTabBarNavClick} data-side="next">
          &gt;
        </div>
      </div>
    );
  }
}
TabBar.propTypes = propTypes;
TabBar.defaultProps = defaultProps;
